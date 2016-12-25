package Application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
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


public class LoginWindow extends JFrame {
	private static final long serialVersionUID = 4L;	
	private JButton login, register;
	public JTextField inputlog;
	public JPasswordField passwordField;
	public static boolean flaglogin;
	
	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	public JPanel myRootPane = new JPanel();
	
	public static JOptionPane qwe1, qwe2, qwe3, qwe4;
	

	
	public LoginWindow() {
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("Welcome to Chat");
		this.setMinimumSize(new Dimension(350, 253));
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		getContentPane().setLayout(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {	
		    	Object[] options = { "Да", "Нет!" };
                int n = JOptionPane.showOptionDialog(windowEvent.getWindow(), "Закрыть окно входа?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0) {
                	Chat.win.out.println("disconnect");
                	windowEvent.getWindow().setVisible(false);
                	windowEvent.getWindow().dispose();
    		    	System.exit(0);
                }
				
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
	
	private Action ButtonLogin = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == login) & (inputlog.getText().length() > 0) & (passwordField.getPassword().length > 0 ) ) { 				
				String log = inputlog.getText();
				Chat.Name = log;
				String pass = new String(passwordField.getPassword());
				String new_mess = "login" + "|" + log + "#" + pass;
				Chat.win.out.println(new_mess);									
			}
			else {
				JOptionPane.showMessageDialog(null,"Пустые поля ввода. Пожалуйста укажите логин и пароль.", "Ошибка ввода данных.", JOptionPane.WARNING_MESSAGE);
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
	
}
