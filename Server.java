package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
	
	public static volatile ArrayList<Connection> connections = new ArrayList<Connection>();
	public ServerSocket server;
	public static BufferedReader in, inS;
	public static PrintWriter out, outS;
	public static String[] Field, Field2 = new String[2];
	public static boolean loginflag;
	String log, pass, str;
	private static boolean reg_login_flag;
	boolean flagloop = false;
	private static String message;
	
	public Server() {
		
		try {
			server = new ServerSocket(1234);
			
			while (true) {
				System.out.println(connections.size());
				flagloop = false;
				Socket socket = server.accept();
				inS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outS = new PrintWriter(socket.getOutputStream(), true);
				
				System.out.println("+1");
				
				while(true) {
					
					str = inS.readLine();
					if (str.equals("disconnect") == true) break;
					System.out.println("Сервер. Получил от юзера: " + str);
					
					Field = str.split("\\|");
					
					String operation = Field[0];
					
					if (operation.equals("break") == true) break;
					
					reg_login_flag = false;
					
					if (operation.equals("register") == true){
						System.out.println("if сработал на register");
						message = Field[1];
						Field2 = message.split("#");
						log = Field2[0];
						pass = Field2[1];
						synchronized(MainServer.ArrList){
							CheckReg(log);}
						if (reg_login_flag == false) {
							synchronized(MainServer.ArrList){
								System.out.println("Login: " + log + " не зарегистрирован." );
								UserData new_user = new UserData(log, pass);
								MainServer.ArrList.add(new_user);
								String result = "registerback" + "|" + "successfuly" + "#" + log + "%" + pass;
								MainServer.servw.ChangeOnlineCount(MainServer.ArrList.size(), -2);
								outS.println(result);
							}
						}
						if (reg_login_flag == true){
							System.out.println("Login: " + log + " занят." );
							String result = "registerback" + "|" + "wrong" + "#" + "wrong";
							outS.println(result);
						}
					}					
					if (operation.equals("login") == true){
						System.out.println("if сработал на login");
						message = Field[1];					
						Field2 = message.split("#");
						log = Field2[0];
						pass = Field2[1];					
					
						loginflag = false;
						System.out.println("Проверяю " + log + "|" + pass);
						Check(log, pass);	
						System.out.println("Проверил и выслал ответ.");
						
						if (loginflag == true) {
							Connection con = new Connection(socket);
							connections.add(con);
							con.start();
							MainServer.servw.ChangeOnlineCount(Server.connections.size(), 2);
							System.out.println("Добавил новой подлкючение.");
							break;
						}
					}										
					log = null;
					pass = null;
				}
				
			}
		} catch (IOException e) {
				e.printStackTrace();
		} finally {
			//closeAll();
		}
	}
	
	
	public void Connection(Socket socket) {	
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);	
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	public static synchronized void CheckReg(String log){
		for (int i=0; i<MainServer.ArrList.size(); i++){
			if (MainServer.ArrList.get(i).getNickname().equals(log) == true){
				reg_login_flag = true;
			}
		}
	}

	public static synchronized void Check(String log, String pass){
		for (int i=0; i<MainServer.ArrList.size(); i++){
			if (MainServer.ArrList.get(i).getNickname().equals(log) == true) {
				
				if (MainServer.ArrList.get(i).getPassword().equals(pass) == true) {
					// выслать в поток команду loginback с сообщением successful
					if (MainServer.ArrList.get(i).getisOnline() == false) {
						loginflag = true;
						MainServer.ArrList.get(i).setisOnline(true);
						String result = "loginback" + "|" + "successfuly" + "#" + log + "%" + pass;				
						outS.println(result);	
					} 
					else {
						loginflag = true;
						String result = "loginback" + "|" + "wrong" + "#" + "online";				
						outS.println(result);
					}
					
				}
			}
		}
		if (loginflag == false) {
			String result = "loginback" + "|" + "wrong" + "#" + "wrong";
			outS.println(result);
			// выслать в поток команду loginback с сообщением wrong
		}
	}
	
	public void Print(List<UserData> ArrList){
		for (int i=0;i<ArrList.size(); i++) {
			ArrList.get(i).print();
		}
	}
	
}
