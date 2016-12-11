package Application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainWindow extends JFrame implements  Runnable{

	private static final long serialVersionUID = 1L;
	
	private JButton send, connect;
	private JTextField inputmess, inputnick;
	private JTextArea chatzone;
	private String chatname  = "global";;
	
	public String message;
	public boolean flag;
	
	private BufferedReader in;
	private PrintWriter out;
	public Socket socket;
	
	public MainWindow() {
		this.setTitle("Chat");
		this.setLayout(new BorderLayout(5, 5));
		this.setMinimumSize(new Dimension(600,400));
		this.setLocationRelativeTo(null);
		
		JPanel panel2 = new JPanel(new FlowLayout());
		connect = new JButton(ButtonConnect);
		connect.setText("Connect");
		inputnick = new JTextField(20);
		panel2.add(inputnick);
		panel2.add(connect);
		
		this.getContentPane().add(panel2, BorderLayout.NORTH);		
		this.getContentPane().add(chatzone(), BorderLayout.CENTER);
		this.getContentPane().add(southPanel(), BorderLayout.SOUTH);
				
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);	
						
	}
	
	public void log(String msg) {
		chatzone.setText(chatzone.getText() + msg + "\n");
	}
	
	private JPanel southPanel() {
		JPanel panel1 = new JPanel(new BorderLayout(5,5));
		send = new JButton(ButtonSend);
		send.setText("Send");
		inputmess = new JTextField();
		panel1.add(inputmess, BorderLayout.CENTER);
		panel1.add(send, BorderLayout.EAST);
		return panel1;
	}
		
	private JTextArea chatzone(){
		chatzone = new JTextArea();
		chatzone.setLineWrap(true);
		chatzone.setWrapStyleWord(true);
		chatzone.setEditable(true);
		return chatzone;
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
	
	private Action ButtonSend = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == send && inputmess.getText().length() > 0) {
				if (flag == true ) {
					String str = inputmess.getText();
					out.println(str);
				} else {
					// X3 
				}
			}
		}
	};
		
	private Action ButtonConnect = new AbstractAction() {
		private static final long serialVersionUID = 3L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == connect) {
				if (chatname.equals(inputnick.getText()) == true) {	
					Connect();
					flag = true;
					out.println(Chat.Name);	

				}
			}
		}
	};
	

	@Override
	public void run() {
		while(true){
			try {
				String str = in.readLine();
				log(str);
			} catch (IOException e) {
				System.err.println("Exception в методе run.");
				e.printStackTrace();
			}
		}		
	}

		
	
}
