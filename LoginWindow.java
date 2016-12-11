package Application;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LoginWindow extends JFrame {
	private static final long serialVersionUID = 4L;
	
	
	
	private JButton login, register;
	private JTextField inputlog, inputpass;
	
//	private JLabel label1, label2, label3;
	
	public LoginWindow() {
		this.setTitle("Welcome to Chat");
		this.setLayout(new GridLayout(6,1));
		this.setMinimumSize(new Dimension(300,220));
		this.setLocationRelativeTo(null);
		
		this.getContentPane().add(new JLabel("Login or register account"));

		this.getContentPane().add(new JLabel("Nickname: "));
		this.getContentPane().add(inputlog(""));
		this.getContentPane().add(new JLabel("Password: "));
		this.getContentPane().add(inputpass(""));
		this.getContentPane().add(fourthPanel());
		
		
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);	
		
		
	}
			
	private JTextField inputlog(String log) {
		inputlog = new JTextField();
		inputlog.setText(log);
		return inputlog;
	}
	
	private JTextField inputpass(String pass) {
		inputpass = new JTextField();
		inputpass.setText(pass);
		return inputpass;
	}
		
	private JPanel fourthPanel() {
		JPanel panel4 = new JPanel(new FlowLayout());
		login = new JButton(ButtonLogin);
		login.setText("Login");
		register = new JButton(ButtonRegister);
		register.setText("Register");
		panel4.add(login);
		panel4.add(register);
		return panel4;
	}
	
	private void hideWindow() {
		this.setVisible(false);
	}
	
	private Action ButtonLogin = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == login) {
				for (int i=0;i<Chat.ArrList.size();i++){	
		    		if (Chat.ArrList.get(i).getNickname().equals(inputlog.getText()) == true){
		    			if (Chat.ArrList.get(i).getPassword().equals(inputpass.getText()) == true){		    				
		    				Chat.Name = Chat.ArrList.get(i).getNickname();
		    				hideWindow();
		    			} else {
		    				// Окно с ошибкой пароля
		    			}
		    			break;
		    		}		    		
		    	  }
			}
		}
	};
	
	private Action ButtonRegister = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			// Click on button REGISTER
		}
	};
	
	
}
