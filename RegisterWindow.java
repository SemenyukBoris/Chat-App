package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegisterWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 21L;
	private static final Object monitorReg = new Object();
	
	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	
	private JTextField inputnick;
	private JLabel textlabel;
	private JLabel textlabel_2;
	private JButton CreateButton;
	private JButton CanselButton;
	private static JOptionPane pass;
	
	
	private String message;
	private static String info;
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	
	
	public RegisterWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Register New Account");
		this.setMinimumSize(new Dimension(350, 225));
		this.setVisible(false);
		this.setLocationRelativeTo(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	message = "break|null";
		    	Chat.win.out.println(message);
				try {
					Chat.win.in.close();
					Chat.win.out.close();
					Chat.win.socket.close();
				} catch (Exception e) {  System.err.println("Exception в методе close");	}
		    	dispose();
		    }
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowOpened(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowIconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowActivated(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {} 
		});
		
		
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("Nickname:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(54, 56, 58, 22);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(54, 89, 57, 22);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Repeat password:");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(10, 122, 101, 22);
		getContentPane().add(label_3);
		
		inputnick = new JTextField();
		inputnick.setBounds(121, 57, 200, 20);
		getContentPane().add(inputnick);
		inputnick.setColumns(10);
		
		textlabel = new JLabel("Registration will not take a lot of time.");
		textlabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textlabel.setBounds(81, 11, 182, 14);
		getContentPane().add(textlabel);
		
		textlabel_2 = new JLabel("Just fill in the fields below.");
		textlabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textlabel_2.setBounds(109, 31, 126, 14);
		getContentPane().add(textlabel_2);
		
		CreateButton = new JButton("Create");
		CreateButton.addActionListener(create);
		CreateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		CreateButton.setBounds(67, 156, 100, 25);
		getContentPane().add(CreateButton);
		
		CanselButton = new JButton("Cancel");
		CanselButton.addActionListener(cansel);
		CanselButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		CanselButton.setBounds(177, 156, 100, 25);
		getContentPane().add(CanselButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(121, 90, 200, 20);
		getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(121, 123, 200, 20);
		getContentPane().add(passwordField_1);
	}

	
	private Action create = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == CreateButton & inputnick.getText().length() > 0 ){
				String str = new String(passwordField.getPassword());
				String str1 = new String(passwordField_1.getPassword());				
				if (str.equals(str1) == true) {
					System.out.println("Отправка данных на сервер для проверки");
					
					System.out.println("Нажал CREATE");
					String nick = inputnick.getText();
					String pass = new String(passwordField.getPassword());
					String new_mess = "register" + "|" + nick + "#" + pass;
					Chat.win.out.println(new_mess);	
					System.out.println("Отправил на сервер " + new_mess);
				}
				else {
					pass = new JOptionPane();
					pass.showMessageDialog(null,"Введеные пароли не совпадает. Повторите еще раз.", "Ошибка ввода данных.", pass.WARNING_MESSAGE);
					//JOptionPane.showMessageDialog(null, "Пароль не совпадает. Введите еще раз.", "Game started", JOptionPane.INFORMATION_MESSAGE);
					remove(pass);
					System.out.println("Пароль не совпадает");
					passwordField.setText("");
					passwordField_1.setText("");
				}
				
			}
		}
	};
	
	private Action cansel = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == CanselButton){		
				Chat.reg.inputnick.setText("");
				Chat.reg.passwordField.setText("");
				Chat.reg.passwordField_1.setText("");
				Chat.reg.setVisible(false);
				Chat.log.setVisible(true);
			}
		}
	};
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	
	public void waitReg() {
        synchronized (monitorReg) {
            try {
            	monitorReg.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void runReg() {
        synchronized (monitorReg) {
        	monitorReg.notifyAll();
        }
    }
	
	
	@Override
	public void run() {		
		waitReg();
		while(true){
			try {				
				info = Chat.win.in.readLine();
				System.out.println(info);
				if (info == null) {continue;}
				Field = info.split("\\|");
				String operation = Field[0];
				String message = Field[1];
				System.out.println("Получил на проверку операцию:" + operation);
				
				if (operation.equals("registerback") == true) {
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("Получил на проверку результат:" + log + "#" + pass);
					if ((log.equals("successfuly") == true && pass.equals("successfuly") == true)) {
						JOptionPane.showMessageDialog(null, "Регистрация прошла успешно. Теперь авторизуйтесь.");
						Chat.reg.setVisible(false);
						Chat.log.setVisible(true);
						Chat.reg.dispose();								
						break;
					}
					else {
						JOptionPane.showMessageDialog(null, "Такой логин уже зарегистрирован, выберите другой.");
					}
				}
			} catch (IOException e) {
				System.err.println("IOException в методе run loginwindow.");
				e.printStackTrace();					
			} 
		}
	}
}
