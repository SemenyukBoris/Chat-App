package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private String name = "";
	private String info;
	private String messprotocol;
	static String[] Field = new String[2];
	static String[] Log = new String[2];

	
	public Connection(Socket socket) {
		this.socket = socket;	
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);	
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}

		
	@Override
	public void run() {
		System.out.println("Connection. Подключение созданно :) ");
		try {
			System.out.println("Connection. Жду первую строку.");
			info = in.readLine();
			Field = info.split("\\|");
			String operation = Field[0];
			String message = Field[1];	
			
			System.out.println(message);
			
			if (operation.equals("connect") == true){
				name = message;
				
				messprotocol = "login|";
				synchronized(Server.connections) {				
					for (int i=0;i<Server.connections.size();i++) {
						Server.connections.get(i).out.println(messprotocol + name + " cames now.");
					}
				}
				
				messprotocol = "message|";
				String info = "";
				while (true) {	
					System.out.println("Connection. Зашел в цикл и сообщений.");
					info = in.readLine();
					System.out.println(info);
					Field = info.split("\\|");
					operation = Field[0];
					message = Field[1];
					
					
					if(operation.equals("exit")) break;
						
					
					// Операция "message"
					if (operation.equals("message") == true)	{								
						synchronized(Server.connections) {
							for (int i=0;i<Server.connections.size();i++) {
								Server.connections.get(i).out.println(messprotocol + name + ": " + message);
							}
						}
					}		
				}// конец цикла в случае exit после операции connect;
				messprotocol = "exit|";
				System.out.println("Вышел с цикла сообщений.");
				synchronized(Server.connections) {
					for (int i=0;i<Server.connections.size();i++) {
						Server.connections.get(i).out.println(messprotocol + name + "#has left.");
					}
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			// -- online count;
			MainServer.servw.ChangeOnlineCount(Server.connections.size(), 1);
			close();
		}
		}
		
	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
			synchronized(Server.connections) {
				Server.connections.remove(this);
			}
		} catch (Exception e) {  System.err.println("Exception в методе close");	}
		}	
}