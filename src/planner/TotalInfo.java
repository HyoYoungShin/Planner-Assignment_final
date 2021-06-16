package planner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class TotalInfo extends JFrame {

	private JFrame mainFrame;
	private JPanel subPane, subPane1;
	private JButton closeBtn;

	String contents[][] = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemoCalendar frame = new MemoCalendar();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TotalInfo() {
		mainFrame = new JFrame("Total Information");
		
		BorderLayout layout = new BorderLayout();
		
		mainFrame.setSize(600, 565);
		mainFrame.setLayout(layout);
		mainFrame.setLocationRelativeTo(null);
		
		subPane = new JPanel();
		subPane.setPreferredSize(new Dimension(600,400));
		
		String header[] = {"일자", "제목", "구분"};
		
		String contents[][] = null;

		// 모델에 데이터 추가
		DefaultTableModel model = new DefaultTableModel(contents, header);
		try{
			File f = new File("MemoData/");
			File []fileList = f.listFiles();
			
			int row = 0;
			String inputStr[] = new String[3];
			
			for(File file : fileList) {
				if(file.isFile()) {
					String fileName = file.getName();
					
					String Date = fileName.substring(0, 8);
					String fieldCheck = fileName.substring(9, 10);
					String field = null;
					if(Integer.parseInt(fieldCheck) == 0)
						field = "Assign";
					else if(Integer.parseInt(fieldCheck) == 1)
						field = "Test";
					else if(Integer.parseInt(fieldCheck) == 2)
						field = "Diary";
					
					BufferedReader in = new BufferedReader(new FileReader(file));
					
					while(true){
						String tempStr = in.readLine();
						if(tempStr == null) break;
						
						inputStr[0] = Date;
						inputStr[1] = tempStr;
						inputStr[2] = field;
						model.addRow(inputStr);
						
						row++;
					}
					in.close();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		JTable table = new JTable(model);

		// 내용 가운데 정렬
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		// 헤더 가운데 정렬
		DefaultTableCellRenderer renderer =  
                (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getTableHeader().setDefaultRenderer(renderer);
		
		JScrollPane scrollpane = new JScrollPane(table);
		subPane.add(scrollpane);
		
		subPane1 = new JPanel();
		subPane1.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		closeBtn = new JButton("닫기");
		closeBtn.setPreferredSize(new Dimension(150,50));
		subPane1.add(closeBtn);
		
		mainFrame.add(subPane, layout.CENTER);
		mainFrame.add(subPane1, layout.SOUTH);
		
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
	}
}
