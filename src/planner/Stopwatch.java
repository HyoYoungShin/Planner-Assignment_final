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

// ������ stopwatch 
public class Stopwatch extends JFrame implements ActionListener {


	private static Thread th = null;
	// Frame ����
	private JFrame frm;
	// start , end, reset, save ��ư ����
	private JButton start;
	private JButton end;
	private JButton reset;
	private JButton save;
	
	// ��ư�� ���� �г� ����
	private JPanel buttons;
	// time ���� String�� ���� �г� ���� (�ð� / �� / �� / �и���)
	private JPanel times;

	// time ������ ���� label ���� (�ð� / �� / �� / �и���)
	private JLabel hour_jl;
	private JLabel min_jl;
	private JLabel sec_jl;
	private JLabel msec_jl;
	// : , . ǥ�� ������ ���� label ����
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

	// time ����� ����
	long start_time = 0;
	long present_time = 0;
	long compare_time = 0;
	long before_compare_time = 0;
	// Reset ��ư�� Ŭ���Ͽ��� ���
	// �ð��� ��� ������ 00���� �ʱ�ȭ���� �� ��ġ
	// count�� 0���� �ʱ�ȭ�Ѵ�.
	// boolean ���� false�� ����

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

			// time ó�� �κ�
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

	// Button Ŭ�� �� �̺�Ʈ ó��
	// start / end / reset
	// boolean ���� ���� true / false �� �����Ͽ� ��ž��ġ�� �����ϵ��� �Ѵ�.

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

	// ������ ����
	public Stopwatch() {
		// Frame ����
		frm = new JFrame();
		frm.setTitle("���νð�");

		// init() �޼��带 ȣ���Ͽ�
		// Frame �� �������� ��ġ�Ѵ�.
		init();
		// x ��ư ���� �� -> ��ŭ �����ߴ��� ����ϰ� ����
		frm.addWindowListener(new WindowDestroyer());
		// Frame ũ��
		frm.setSize(400, 150);
		frm.setLocationRelativeTo(null);//stopwatch�� ����� ��ġ
		// Frame ���
		frm.setResizable(false);
		frm.setVisible(true);
		
		ThreadConrol1 threadCn2 = new ThreadConrol1();
		threadCn2.start();
	}

	// Frame �� �������� ��ġ�ϱ� ���� �޼���
	// ���α׷��� ����� ��ư���� ��ġ�ϰ� �ȴ�.
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
		}//������ ����� data �������� (������ �ð�)

		
		// String ���� ���� Panel�� ����
		times = new JPanel();

		// �ð� ������ ���� Label ����
		hour_jl = new JLabel();
		min_jl = new JLabel();
		sec_jl = new JLabel();
		msec_jl = new JLabel();
		colon_t1 = new JLabel(":");
		colon_t2 = new JLabel(":");
		colon_comma = new JLabel(".");

		// String font ����
		Font font = new Font("����", Font.PLAIN, 50); // font name, font type, font size
		Font font_sub = new Font("����", Font.PLAIN, 30);

		// font ����
		hour_jl.setFont(font);
		min_jl.setFont(font);
		sec_jl.setFont(font);
		colon_t1.setFont(font);
		colon_t2.setFont(font);
		// �и��ʴ� ���� �� ũŰ���� �۰�...
		msec_jl.setFont(font_sub);
		colon_comma.setFont(font_sub);

		// JLabel �� ������ �Է� ��(String)�� ��ġ (.setText)
		hour_str = String.format("%02d", hour);
		min_str = String.format("%02d", min);
		sec_str = String.format("%02d", sec);
		msec_str = String.format("%03d", msec);

		hour_jl.setText(hour_str);
		min_jl.setText(min_str);
		sec_jl.setText(sec_str);
		msec_jl.setText(msec_str);

		// String ������ �гο� ��´�.
		times.add(hour_jl);
		times.add(colon_t1);
		times.add(min_jl);
		times.add(colon_t2);
		times.add(sec_jl);
		times.add(colon_comma);
		times.add(msec_jl);

		// Button ����
		start = new JButton("start");
		end = new JButton("end");
		reset = new JButton("reset");
		save = new JButton("save");

		// actionListener ����
		start.addActionListener(this);
		end.addActionListener(this);
		reset.addActionListener(this);
		save.addActionListener(this);

		// Button�� ���� Panel�� ����
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());

		// button�� �гο� ��´�.
		buttons.add(start);
		buttons.add(end);
		buttons.add(reset);
		buttons.add(save);

		// ���� �ʱ⿡ ��ư enable ����
		end.setEnabled(false);
		reset.setEnabled(true);
		save.setEnabled(false);

		// Frame�� Button�� ��� Panel ��ġ
		frm.add("North", times);
		frm.add("South", buttons);

	}
	
	public class ThreadConrol1 extends Thread {
	@Override
		public void run() {
			while (true) {
				System.out.print("");// �̰� �Ⱦ��� �ȵ�ư���
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