package planner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SevenDays extends JFrame {

	private JPanel contentPane;
	private JFrame mainFrame;
	private JPanel subPane, subPane1;
	private JButton closeBtn;

	int SevenCount = 0;// ���� ���翩�� count ����

	String contents[][] = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SevenDays frame = new SevenDays();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SevenDays() {
		mainFrame = new JFrame("Schedule for 7day");

		BorderLayout layout = new BorderLayout();

		mainFrame.setSize(600, 565);
		mainFrame.setLayout(layout);
		mainFrame.setLocationRelativeTo(null);

		subPane = new JPanel();
		subPane.setPreferredSize(new Dimension(600, 400));

		String header[] = { "����", "����", "����" };

		String contents[][] = null;

		// �𵨿� ������ �߰�
		DefaultTableModel model = new DefaultTableModel(contents, header);
		try {
			File f = new File("MemoData/");
			File[] fileList = f.listFiles();

			int row = 0;

			String pattern = "yyyMMdd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);// ���糯¥����
			String Today = simpleDateFormat.format(new Date());

			String inputStr[] = new String[3];

			for (File file : fileList) {
				if (file.isFile()) {
					String fileName = file.getName();

					String Date = fileName.substring(0, 8);

					if (Integer.parseInt(Date) >= Integer.parseInt(Today)
							&& Integer.parseInt(Date) <= Integer.parseInt(Today) + 7) {// ������ �����ϳ��� ���� ���� Ȯ��

						String fieldCheck = fileName.substring(9, 10);
						String field = null;
						if (Integer.parseInt(fieldCheck) == 0)
							field = "Assign";
						else if (Integer.parseInt(fieldCheck) == 1)
							field = "Test";
						else if (Integer.parseInt(fieldCheck) == 2)
							field = null;

						BufferedReader in = new BufferedReader(new FileReader(file));

						while (true) {
							String tempStr = in.readLine();
							if (tempStr == null)
								break;

							if (Integer.parseInt(fieldCheck) != 2) {
								inputStr[0] = Date;
								inputStr[1] = tempStr;
								inputStr[2] = field;
								model.addRow(inputStr);

								row++;
							}
						}
						in.close();
						SevenCount++;
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JTable table = new JTable(model);

		// ���� ��� ����
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

		}
		// ��� ��� ����
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getTableHeader().setDefaultRenderer(renderer);

		JScrollPane scrollpane = new JScrollPane(table);
		subPane.add(scrollpane);

		subPane1 = new JPanel();
		subPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		closeBtn = new JButton("�ݱ�");
		closeBtn.setPreferredSize(new Dimension(150, 50));
		subPane1.add(closeBtn);

		mainFrame.add(subPane, layout.CENTER);
		mainFrame.add(subPane1, layout.SOUTH);

		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		if (SevenCount == 0) {// 7�� �̳��� ���� ������ �˸�â���� Ķ������
			mainFrame.dispose();
			JOptionPane.showMessageDialog(null, "�����ϵ��� ���� ����!");
			MemoCalendar frame = new MemoCalendar();

		}

		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				MemoCalendar frame = new MemoCalendar();
			}
		});
	}

}
