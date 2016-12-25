package Application;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;


public class Reception implements Runnable{

	private static final Object monitorReception = new Object();
	public static BufferedReader in;
	public static PrintWriter out;
	private static String info;
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	static String[] transfer = new String[2];
	
	public void waitReception() {
        synchronized (monitorReception) {
            try {
            	monitorReception.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void runReception() {
        synchronized (monitorReception) {
        	monitorReception.notifyAll();
        }
    }

	
	@Override
	public void run() {	
		while(true){
			try {	
				info = Chat.win.in.readLine();
				if (info == null) {
					Chat.win.out.println("disconnect");
					Chat.win.Disconnect();
					break;
				}
				Field = info.split("\\|");
				String operation = Field[0];
				String message = Field[1];
				
				if (operation.equals("loginback") == true) {
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("Отправляю на сервер сообщение:" + log + "#" + pass);
					if ((log.equals("successfuly") == true)) {
						transfer = pass.split("%");						
						log = transfer[0];
						pass = transfer[1];
						Chat.Name = log;
						Chat.win.out.println("connect|" + log);					
						Chat.win.flag = true;
						Chat.win.setVisible(true);
						Chat.reg.dispose();	
						Chat.log.dispose();
					}
					else {
						if (pass.equals("online") == true) {
							JOptionPane.showMessageDialog(null,"Этот пользователь уже ONLINE.");
						}
						if (pass.equals("wrong") == true) {
							JOptionPane.showMessageDialog(null,"Такой логин не зарегистрирован, или пароль неверный.");
						}
					}
				}
				
				if (operation.equals("registerback") == true) {
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("Отправляю на сервер сообщение:" + log + "#" + pass);
					if ((log.equals("successfuly") == true)) {			
						JOptionPane.showMessageDialog(null, "Регистрация прошла успешно. Теперь авторизуйтесь.");
						Chat.reg.setVisible(false);
						transfer = pass.split("%");
						log = transfer[0];
						pass = transfer[1];
						Chat.log.inputlog.setText(log);
						Chat.log.setVisible(true);					
					}
					else {
						JOptionPane.showMessageDialog(null, "Такой логин уже зарегистрирован, выберите другой.");
					}
				}
				
				if (operation.equals("login") == true) {
					Chat.win.NewMessage("login", message);
				}
				
				if (operation.equals("message") == true) {
					Chat.win.NewMessage("message", message);
				}
				
				if (operation.equals("exit") == true) {
					Chat.win.NewMessage("exit", message);
					String[] Field2 = message.split("#");
					String name = Field2[0];
					if (name.equals(Chat.Name) == true | name.equals("qwerty") == true) break;		
					
				}
				
				if (operation.equals("server") == true) {
					Chat.win.NewMessage("server", message);
				}								
			} catch (IOException e) {
				e.printStackTrace();					
			}
		}
	}
}
