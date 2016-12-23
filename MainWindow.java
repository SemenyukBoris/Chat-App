package Application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollPane;
import java.awt.Font;


public class MainWindow extends JFrame implements  Runnable{

	private static final long serialVersionUID = 1L;
		
	private String message;
	private String time;
	static String[] Field = new String[2];
	static String[] Field2 = new String[2];
	public boolean flag;
	public boolean flagclose;
	private Date currentDate;
	public static boolean flagstop = false;
	public BufferedReader in;
	public boolean flagend;
	public PrintWriter out;
	public static Socket socket;
	
	private static JTextPane chatzone;
	private JTextField inputmess;
	private JButton ButtonSend, ButtonDisconnect;
	
	
	public MainWindow() {	
		this.setTitle("Chat");
		this.setMinimumSize(new Dimension(600,400));
		this.setLocationRelativeTo(null);
			
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				message = "exit|" + Chat.Name;
				System.out.println("Отправляю на сервер: " + message);
				out.println(message);
				Disconnect();
				flagstop = true;
		    	dispose();
		    }
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowOpened(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowIconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowActivated(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {} 
		});
				
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 574, 273);
		getContentPane().add(scrollPane);
		
		chatzone = new JTextPane();
		scrollPane.setViewportView(chatzone);
		
		inputmess = new JTextField();
		inputmess.setBounds(10, 331, 480, 25);
		getContentPane().add(inputmess);
		inputmess.setColumns(10);
		
		ButtonSend = new JButton("Send");
		ButtonSend.addActionListener(send);
		ButtonSend.setFont(new Font("Tahoma", Font.BOLD, 11));
		ButtonSend.setBounds(500, 331, 84, 25);
		getContentPane().add(ButtonSend);
		
		
		ButtonDisconnect = new JButton("Disconnect");
		ButtonDisconnect.addActionListener(disconnect);
		ButtonDisconnect.setFont(new Font("Tahoma", Font.BOLD, 11));
		ButtonDisconnect.setBounds(484, 11, 100, 25);
		getContentPane().add(ButtonDisconnect);
								
	}
	
	public void EndOfTextArea(){
		chatzone.setCaretPosition(chatzone.getDocument().getLength());
	}
	
	public void log(String msg, Color cl) {
		StyledDocument doc = chatzone.getStyledDocument();
        Style style = chatzone.addStyle("JAVA", null);
        StyleConstants.setForeground(style, cl);
        
        try {
            doc.insertString(doc.getLength(), msg + "\n", style);
        } catch (BadLocationException ex) {
        }
	}
			
	public synchronized void Connect() {
		try {
			System.out.println(Chat.IP);
			socket = new Socket(Chat.IP,1234);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Disconnect() {
		try {
			this.in.close();
			this.out.close();
			MainWindow.socket.close();
		} catch (Exception e) {  System.err.println("Exception в методе close");	}
	}
	
	private Action send = new AbstractAction() {
		private static final long serialVersionUID = 2L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ButtonSend && inputmess.getText().length() > 0) {
				if (flag == true ) {					
					message = "message|" + inputmess.getText();
					System.out.println("Отправляю на сервер: " + message);
					out.println(message);
					inputmess.setText("");
				} 
			}
		}
	};
			
	private Action disconnect = new AbstractAction() {
		private static final long serialVersionUID = 4L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ButtonDisconnect) {	
				message = "exit|" + Chat.Name;
				System.out.println("Отправляю на сервер: " + message);
				out.println(message);
				//Chat.win.Disconnect();	
				flagend = true;
				
			}
		}
	};


	
	@Override
	public void run() {	
		chatzone.setText(null);
		while(true){
			try {
				String str = in.readLine();
				Field = str.split("\\|");
				String operation = Field[0];
				String message = Field[1];
				String new_mess;
				Locale local = new Locale("ru","RU");
				DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local); 
				currentDate = new Date(); 				
				time = df.format(currentDate);
				 				
				if (operation.equals("message") == true) {
					new_mess = "[" + time + "] " + message;
					log(new_mess, Color.BLUE);
					EndOfTextArea();
				}
				if (operation.equals("login") == true){
					new_mess = "[" + time + "] " + message;
					log(new_mess, new Color(0, 138, 0));
				}
				if (operation.equals("exit") == true) {
					Field2 = message.split("#");
					String name = Field2[0];
					System.out.println(name);
					new_mess = "[" + time + "] " + name + " has left.";
					log(new_mess, Color.RED);
					if (Chat.Name.equals(name) == true)	{
						Disconnect();
						ButtonSend.setEnabled(false);
						ButtonDisconnect.setEnabled(false);
						break;
					}					
				}
			} catch (IOException e) {
				System.err.println("Exception в методе run.");
				e.printStackTrace();
			}
		}
		System.out.println("Конец метода RUN :)");
	}
}
