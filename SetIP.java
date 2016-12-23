package Application;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;

public class SetIP extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	private static final Object monitorIP = new Object();
	public BufferedReader in;
	public PrintWriter out;
	public static Socket socket;
	private JButton btnNewButton;
	public String strIP;

	
	public SetIP(){
		this.setTitle("Set Server IP");
		this.setResizable(false);
		this.setMinimumSize(new Dimension(350, 150));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Set server IP :");
		lblNewLabel.setBounds(62, 42, 93, 17);
		getContentPane().add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(194, 40, 86, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(connect);
		btnNewButton.setBounds(115, 81, 100, 25);
		getContentPane().add(btnNewButton);
		
		
		
		
	}

	private Action connect = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) { 
			if (e.getSource() == btnNewButton & textField_1.getText().length() > 0 ){			
				strIP = textField_1.getText();
				Chat.set.runIP();
			}
		}
	};
	private JTextField textField_1;
	
	
	@Override
	public void run() {		
		Chat.set.waitIP();
			try {
				Chat.win.socket = new Socket(textField_1.getText(),1234);
				Chat.win.in = new BufferedReader(new InputStreamReader(Chat.win.socket.getInputStream()));
				Chat.win.out = new PrintWriter(Chat.win.socket.getOutputStream(),true);					
			}catch (IOException e) {
				System.err.println("IOException в методе run IP.");
				e.printStackTrace();					
			} 				
	}
	
	
	public void waitIP() {
        synchronized (monitorIP) {
            try {
            	monitorIP.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void runIP() {
        synchronized (monitorIP) {
        	monitorIP.notifyAll();
        }
    }
}
