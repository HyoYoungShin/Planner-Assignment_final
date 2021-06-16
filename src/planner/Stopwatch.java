package planner;

import java.awt.EventQueue;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import planner.MemoCalendar.ThreadConrol;

import java.util.Scanner;
import java.io.*;

// 디지털 stopwatch 
public class Stopwatch extends JFrame implements ActionListener {


	private static Thread th = null;
	// Frame 선언
	private JFrame frm;
	// start , end, reset, save 버튼 선언
	private JButton start;
	private JButton end;
	private JButton reset;
	private JButton save;
	
	// 버튼을 담을 패널 선언
	private JPanel buttons;
	// time 정보 String을 담을 패널 선언 (시간 / 분 / 초 / 밀리초)
	private JPanel times;

	// time 정보를 담을 label 선언 (시간 / 분 / 초 / 밀리초)
	private JLabel hour_jl;
	private JLabel min_jl;
	private JLabel sec_jl;
	private JLabel msec_jl;
	// : , . 표시 정보를 담을 label 선언
	private JLabel colon_t1;
	private JLabel colon_t2;
	private JLabel colon_comma;

	private long hour = 0;
	private long min = 0;
	private long sec = 0;
	private long msec = 0;

	private String hour_str = "";
	private String min_str = "";
	private String sec_str = "";
	private String msec_str = "";

	private boolean is;

//    private int count = 0;
	Date start_date = new Date();

	// time 계산할 변수
	long start_time = 0;
	long present_time = 0;
	long compare_time = 0;
	long before_compare_time = 0;
	// Reset 버튼을 클릭하였을 경우
	// 시간의 모든 정보를 00으로 초기화한후 재 배치
	// count를 0으로 초기화한다.
	// boolean 값은 false로 변경

	public void resetTimes() {
		hour_jl.setText("00");
		min_jl.setText("00");
		sec_jl.setText("00");
		msec_jl.setText("00");
//        count = 0;
		repaint();
	}

	public void setTimes() {
		hour = Integer.parseInt(hour_jl.getText());
		min = Integer.parseInt(min_jl.getText());
		sec = Integer.parseInt(sec_jl.getText());
		msec = Integer.parseInt(msec_jl.getText());

		while (is) {
			Date check_date = new Date();
			present_time = check_date.getTime();

			// time 처리 부분
			compare_time = present_time - start_time + before_compare_time;
			String one = compare_time / 1000 + "";
			int totalsec = Integer.parseInt(one, 10);
			msec = compare_time % 1000;
			String two = totalsec / 60 + "";
			int totalmin = Integer.parseInt(two, 10);
			sec = totalsec % 60;
			String three = totalmin / 60 + "";
			hour = Integer.parseInt(three, 10);
			min = totalmin % 60;

			if (msec == 100) {
				sec++;
				msec = 0;
//                count = 0;
			}

			if (sec == 60) {
				sec = 0;
				min++;
			}

			if (min == 60) {
				min = 0;
				hour++;
			}

			hour_str = String.format("%02d", hour);
			min_str = String.format("%02d", min);
			sec_str = String.format("%02d", sec);
			msec_str = String.format("%03d", msec);

			hour_jl.setText(hour_str);
			min_jl.setText(min_str);
			sec_jl.setText(sec_str);
			msec_jl.setText(msec_str);

//            count++;
		}
	}

	// Button 클릭 시 이벤트 처리
	// start / end / reset
	// boolean 값을 통해 true / false 를 설정하여 스탑워치를 동작하도록 한다.

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource();
		start_date = new Date();
		if (jb == start) {
			start_time = start_date.getTime();
			is = true;
			end.setEnabled(true);
			reset.setEnabled(false);
			save.setEnabled(false);
			
			
		}

		else if (jb == end) {
			is = false;
			start_time = start_date.getTime();
			before_compare_time = compare_time;
			end.setEnabled(false);
			reset.setEnabled(true);
			save.setEnabled(true);
		}

		else if (jb == reset) {
			is = false;
			start_time = start_date.getTime();
			before_compare_time = 0;
			resetTimes();
			reset.setEnabled(false);
			save.setEnabled(true);
		}
		
		else if(jb == save) {
			try {
				PrintWriter outputStream = new PrintWriter(new FileOutputStream("time.txt"));
				outputStream.println(compare_time);
				outputStream.close();
			} catch (FileNotFoundException x) {
				return;
			}
		}
	}

	// 생성자 선언
	public Stopwatch() {
		// Frame 생성
		frm = new JFrame();
		frm.setTitle("공부시간");

		// init() 메서드를 호출하여
		// Frame 에 컨텐츠를 배치한다.
		init();
		// x 버튼 동작 시 -> 얼만큼 공부했는지 출력하고 종료
		frm.addWindowListener(new WindowDestroyer());
		// Frame 크기
		frm.setSize(400, 150);
		frm.setLocationRelativeTo(null);//stopwatch를 가운데에 배치
		// Frame 출력
		frm.setResizable(false);
		frm.setVisible(true);
		
		ThreadConrol1 threadCn2 = new ThreadConrol1();
		threadCn2.start();
	}

	// Frame 내 컨텐츠를 배치하기 위한 메서드
	// 프로그램이 실행된 버튼등을 배치하게 된다.
	public void init() {
		try {
			Scanner inputStream = new Scanner(new File("time.txt"));
			before_compare_time = inputStream.nextLong();
			inputStream.close();
			
			String one = before_compare_time / 1000 + "";
			int totalsec = Integer.parseInt(one, 10);
			msec = before_compare_time % 1000;
			String two = totalsec / 60 + "";
			int totalmin = Integer.parseInt(two, 10);
			sec = totalsec % 60;
			String three = totalmin / 60 + "";
			hour = Integer.parseInt(three, 10);
			min = totalmin % 60;
			
		} catch (FileNotFoundException e) {
			before_compare_time = 0;
		}//기존에 저장된 data 가져오기 (공부한 시간)

		
		// String 값을 담을 Panel을 생성
		times = new JPanel();

		// 시간 정보를 담을 Label 생성
		hour_jl = new JLabel();
		min_jl = new JLabel();
		sec_jl = new JLabel();
		msec_jl = new JLabel();
		colon_t1 = new JLabel(":");
		colon_t2 = new JLabel(":");
		colon_comma = new JLabel(".");

		// String font 설정
		Font font = new Font("굴림", Font.PLAIN, 50); // font name, font type, font size
		Font font_sub = new Font("굴림", Font.PLAIN, 30);

		// font 설정
		hour_jl.setFont(font);
		min_jl.setFont(font);
		sec_jl.setFont(font);
		colon_t1.setFont(font);
		colon_t2.setFont(font);
		// 밀리초는 기존 초 크키보다 작게...
		msec_jl.setFont(font_sub);
		colon_comma.setFont(font_sub);

		// JLabel 에 각각의 입력 값(String)을 배치 (.setText)
		hour_str = String.format("%02d", hour);
		min_str = String.format("%02d", min);
		sec_str = String.format("%02d", sec);
		msec_str = String.format("%03d", msec);

		hour_jl.setText(hour_str);
		min_jl.setText(min_str);
		sec_jl.setText(sec_str);
		msec_jl.setText(msec_str);

		// String 내용을 패널에 담는다.
		times.add(hour_jl);
		times.add(colon_t1);
		times.add(min_jl);
		times.add(colon_t2);
		times.add(sec_jl);
		times.add(colon_comma);
		times.add(msec_jl);

		// Button 생성
		start = new JButton("start");
		end = new JButton("end");
		reset = new JButton("reset");
		save = new JButton("save");

		// actionListener 설정
		start.addActionListener(this);
		end.addActionListener(this);
		reset.addActionListener(this);
		save.addActionListener(this);

		// Button을 담을 Panel을 생성
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());

		// button을 패널에 담는다.
		buttons.add(start);
		buttons.add(end);
		buttons.add(reset);
		buttons.add(save);

		// 실행 초기에 버튼 enable 상태
		end.setEnabled(false);
		reset.setEnabled(true);
		save.setEnabled(false);

		// Frame에 Button이 담긴 Panel 배치
		frm.add("North", times);
		frm.add("South", buttons);

	}
	
	public class ThreadConrol1 extends Thread {
	@Override
		public void run() {
			while (true) {
				System.out.print("");// 이거 안쓰면 안들아가서
				if (is) {
					try {
						setTimes();
						repaint();
						Thread.sleep(10);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
	}
		
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stopwatch frame = new Stopwatch();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}