package Application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;


public class LoginWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 4L;	
	private static final Object monitorLog = new Object();
	private JButton login, register;
	private JTextField inputlog;
	public static boolean flaglogin;
	
	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	
	private String message;
	private static String info;
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	public JPanel myRootPane = new JPanel();
	private JOptionPane qwe2, qwe3, qwe4;
	

	
	public LoginWindow() {
		setResizable(false);
		this.setTitle("Welcome to Chat");
		this.setMinimumSize(new Dimension(350, 253));
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	message = "break|null";
				Chat.win.out.println(message);
				try {
					Chat.win.in.close();
					Chat.win.out.close();
					Chat.win.socket.close();
				} catch (Exception e) {  System.err.println("Exception � ������ close");	}
		    	dispose();
		    }
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowOpened(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowIconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowActivated(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {} 
		});
		
		
		JLabel label = new JLabel("Already got an account? Login straight away.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(44, 12, 256, 25);
		this.getContentPane().add(label);

		JLabel label_1 = new JLabel("Nickname: ");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(40, 48, 61, 25);
		this.getContentPane().add(label_1);
		this.getContentPane().add(inputlog(""));
		JLabel label_2 = new JLabel("Password: ");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(40, 84, 61, 25);
		this.getContentPane().add(label_2);
		login = new JButton(ButtonLogin);
		login.setFont(new Font("Tahoma", Font.BOLD, 11));
		login.setBounds(112, 120, 120, 25);
		getContentPane().add(login);
		login.setText("Login");
		
		JLabel label_3 = new JLabel("If you do not have an account yet, then create it.");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(33, 156, 278, 25);
		this.getContentPane().add(label_3);
		
		register = new JButton(ButtonRegister);
		register.setFont(new Font("Tahoma", Font.BOLD, 11));
		register.setBounds(112, 192, 120, 25);
		getContentPane().add(register);
		register.setText("Register");
		
		passwordField = new JPasswordField();
		passwordField.setBounds(111, 84, 200, 25);
		getContentPane().add(passwordField);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
		
	}
	
	
	public void Connect() {
		try {
			socket = new Socket("127.0.0.1",1234);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JTextField inputlog(String log) {
		inputlog = new JTextField();
		inputlog.setBounds(111, 48, 200, 25);
		inputlog.setText(log);
		return inputlog;
	}
	
				
	@Override
	public void run() {	
		waitLogin();
		while(true){
			try {				
				info = Chat.win.in.readLine();
				System.out.println(info);
				if (info == null) {continue;}
				Field = info.split("\\|");
				String operation = Field[0];
				String message = Field[1];
				System.out.println("������� �� �������� ��������:" + operation);
				
				if (operation.equals("loginback") == true) {
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("������� �� �������� ���������:" + log + "#" + pass);
					if ((log.equals("successfuly") == true) && (pass.equals("successfuly") == true)) {
						Chat.reg.dispose();
						Chat.win.setVisible(true);						
						break;
					}
					else {
						qwe2 = new JOptionPane();
						qwe2.showMessageDialog(null,"����� ����� �� ���������������, ��� ������ ��������.", "������ ����� ������.", qwe2.WARNING_MESSAGE);
						remove(qwe2);
					}
				} 
				else {
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("������� �� �������� ���������:" + log + "#" + pass);
					if ((log.equals("successfuly") == true && pass.equals("successfuly") == true)) {
						JOptionPane.showMessageDialog(this, "����������� ������ �������. ������ �������������.");
						Chat.reg.setVisible(false);
						Chat.log.setVisible(true);
					}
					if ((log.equals("wrong") == true && pass.equals("wrong") == true)){
						qwe3 = new JOptionPane();
						qwe3.showMessageDialog(this,"����� ����� ��� ���������������, �������� ������.", "������ ����� ������.", qwe3.WARNING_MESSAGE);
						remove(qwe3);
					}
				}
			} catch (IOException e) {
				System.err.println("IOException � ������ run loginwindow.");
				e.printStackTrace();					
			} 
		}
	}
	
	public void waitLogin() {
        synchronized (monitorLog) {
            try {
                monitorLog.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void runLogin() {
        synchronized (monitorLog) {
            monitorLog.notifyAll();
        }
    }
	
	private Action ButtonLogin = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == login) & (inputlog.getText().length() > 0) & (passwordField.getPassword().length > 0 ) ) { 
				runLogin();
				System.out.println("����� LOGIN");
				String log = inputlog.getText();
				Chat.Name = log;
				String pass = new String(passwordField.getPassword());
				String new_mess = "login" + "|" + log + "#" + pass;
				Chat.win.out.println(new_mess);	
				System.out.println("�������� �� ������ " + new_mess);								
			}
			else {
				qwe4 = new JOptionPane();
				qwe4.showMessageDialog(null,"��������� ������ ����", "������ ����� ������.", qwe4.WARNING_MESSAGE);
				remove(qwe4);
			}
		}
	};
	
	private Action ButtonRegister = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Chat.log.inputlog.setText("");
			Chat.log.passwordField.setText("");
			Chat.log.setVisible(false);
			Chat.reg.setVisible(true);
		}
	};
	private JPasswordField passwordField;
}
