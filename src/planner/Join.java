package planner;

import java.awt.EventQueue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Join extends JFrame {

	private JPanel contentPane;
	private JLabel lblJoin;
	private JButton joinCompleteBtn;
	private JButton idCheckBtn;
	private JTextField tfId;
	private JTextField tfPassword;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join frame = new Join();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Join() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(430, 490);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblJoin = new JLabel("ȸ������");
		Font f1 = new Font("����", Font.BOLD, 20); //�ü� ���� ����
		lblJoin.setFont(f1); 
		lblJoin.setBounds(159, 41, 101, 20);
		contentPane.add(lblJoin);
		
		JLabel lblUsername = new JLabel("password");
		lblUsername.setBounds(69, 163, 69, 20);
		contentPane.add(lblUsername);
		
		JLabel label = new JLabel("id");
		label.setBounds(69, 113, 69, 20);
		contentPane.add(label);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(69, 210, 69, 20);
		contentPane.add(lblName);
		
		JLabel lblEmail = new JLabel("email");
		lblEmail.setBounds(69, 257, 69, 20);
		contentPane.add(lblEmail);
		
		JLabel lblPhone = new JLabel("phone");
		lblPhone.setBounds(69, 304, 69, 20);
		contentPane.add(lblPhone);
		
		tfId = new JTextField();
		tfId.setColumns(10);
		tfId.setBounds(153, 106, 136, 35);
		contentPane.add(tfId);
		
		idCheckBtn = new JButton("�ߺ�Ȯ��");
		idCheckBtn.setBounds(310, 108, 84, 30);
		contentPane.add(idCheckBtn);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(153, 156, 186, 35);
		contentPane.add(tfPassword);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(153, 203, 186, 35);
		contentPane.add(tfName);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(153, 250, 186, 35);
		contentPane.add(tfEmail);
		
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(153, 297, 186, 35);
		contentPane.add(tfPhone);
		
		joinCompleteBtn = new JButton("ȸ�����ԿϷ�");
		joinCompleteBtn.setBounds(206, 363, 139, 29);
		contentPane.add(joinCompleteBtn);
		joinCompleteBtn.setEnabled(false);
		
		setVisible(true);
		
		//�ߺ�Ȯ�� �׼�
		idCheckBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent T) {
				try {
					String s;
					String[] array;
					int check = 0;
					BufferedReader bos = new BufferedReader(new FileReader("member.txt"));
					while((s=bos.readLine()) != null)
					{
						array = s.split("/");
						if(tfId.getText().equals(array[0]))
						{
							JOptionPane.showMessageDialog(null, "ID �ߺ��Դϴ�");
							dispose();
							Join frame = new Join();
							check = 1;
						}
					}
					bos.close();
					
					if(check == 0) {
						JOptionPane.showMessageDialog(null, "��밡���� ID�Դϴ�.");
						joinCompleteBtn.setEnabled(true);
					}
					
				}
				catch(IOException E10) {
					E10.printStackTrace();
				}
				
			}
		});
		
		//ȸ�����ԿϷ� �׼�
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent T) {
				if(tfPassword.getText().equals("") || tfName.getText().equals("") || tfEmail.getText().equals("") || 
						tfPhone.getText().equals("") || tfId.getText().equals(""))
				{
					if(tfId.getText().equals(""))
						JOptionPane.showMessageDialog(null, "���̵� �Է��Ͻÿ�.");
					if(tfPassword.getText().equals(""))
						JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��Ͻÿ�.");
					if(tfName.getText().equals(""))
						JOptionPane.showMessageDialog(null, "�̸��� �Է��Ͻÿ�.");
					if(tfEmail.getText().equals(""))
						JOptionPane.showMessageDialog(null, "�̸����� �Է��Ͻÿ�.");
					if(tfPhone.getText().equals(""))
						JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է��Ͻÿ�.");
				}
				else
				{
					try {
						BufferedWriter bos = new BufferedWriter(new FileWriter("member.txt",true));
						bos.write(tfId.getText()+"/");
						bos.write(tfPassword.getText()+"/");
						bos.write(tfName.getText()+"/");
						bos.write(tfEmail.getText()+"/");
						bos.write(tfPhone.getText()+"\r\n");
						bos.close();
						JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
						dispose();
						Main frame = new Main();
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����Ͽ����ϴ�.");
					}
				}
				
				
			}
		});

	}
}

