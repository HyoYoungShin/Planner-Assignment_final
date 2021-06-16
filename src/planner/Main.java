package planner;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JPasswordField tfPassword;
	private JButton loginBtn, joinBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("id");
		lblLogin.setBounds(41, 52, 69, 35);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(41, 103, 69, 35);
		contentPane.add(lblPassword);
		
		tfId = new JTextField();
		tfId.setBounds(157, 52, 176, 35);
		contentPane.add(tfId);
		tfId.setColumns(10);
		
		joinBtn = new JButton("회원가입");
		joinBtn.setBounds(229, 154, 104, 29);
		contentPane.add(joinBtn);
		
		loginBtn = new JButton("로그인");
		loginBtn.setBounds(108, 154, 106, 29);
		contentPane.add(loginBtn);
		
		tfPassword = new JPasswordField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(157, 103, 176, 35);
		tfPassword.setEchoChar('*');
		contentPane.add(tfPassword);
		
		setVisible(true);
		//회원가입 액션
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Join frame = new Join();
			}
		});
		
		//로그인 액션
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String s;
					String[] array;
					int check = 0;
					BufferedReader bos = new BufferedReader(new FileReader("member.txt"));
					while((s=bos.readLine()) != null)
					{
						check = 1;
						array = s.split("/");
						if(tfId.getText().equals(array[0]) && tfPassword.getText().equals(array[1]))
						{
							JOptionPane.showMessageDialog(null, "로그인이 되었습니다");
							dispose();
							SevenDays frame = new SevenDays();//7일 일정으로 연결 변경
							break;
						}
						else
						{
							check = 0;
						}
					}
					if(check == 0)
					{
						JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다");
						dispose();
						Main frame = new Main();
					}
					bos.close();
					dispose();
				}
				catch(IOException E10) {
					E10.printStackTrace();
				}
				
			}
		});
	}
}

