package Application;

import java.io.BufferedReader;
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

public class RegisterWindow extends JFrame {
	private static final long serialVersionUID = 21L;
	
	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	private JTextField inputnick;
	private JLabel textlabel;
	private JLabel textlabel_2;
	private JButton CreateButton;
	private JButton CanselButton;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	
	public static JOptionPane qwe5, qwe6;;
	
	public RegisterWindow() {
		
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("Register New Account");
		this.setMinimumSize(new Dimension(350, 225));
		this.setVisible(false);
		this.setLocationRelativeTo(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Object[] options = { "Да", "Нет!" };
                int n = JOptionPane.showOptionDialog(windowEvent.getWindow(), "Закрыть окно регистрации?",
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
			String str = new String(passwordField.getPassword());
			String str1 = new String(passwordField_1.getPassword());
			if (e.getSource() == CreateButton & inputnick.getText().length() > 0 & str.length() > 0 == true & str1.length() > 0 == true){					
				if (str.equals(str1) == true ) {						
					System.out.println("Отправка данных на сервер для проверки");			
					String nick = inputnick.getText();
					String pass = new String(passwordField.getPassword());
					String new_mess = "register" + "|" + nick + "#" + pass;
					Chat.win.out.println(new_mess);	
					System.out.println("Отправил на сервер " + new_mess);
				}
				else {
					JOptionPane.showMessageDialog(null,"Введеные пароли не совпадает. Повторите еще раз.", "Ошибка ввода данных", JOptionPane.WARNING_MESSAGE);
					System.out.println("Пароль не совпадает");
					passwordField.setText("");
					passwordField_1.setText("");
				}
				
			}		
			else {
				JOptionPane.showMessageDialog(null,"Заполните все поля.", "Ошибка ввода данных.", JOptionPane.WARNING_MESSAGE);
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

}
