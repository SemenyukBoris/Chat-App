package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
	
	public static volatile ArrayList<Connection> connections = new ArrayList<Connection>();
	//public static ArrayList<UserData> ArrList = new ArrayList<UserData>();
	public ServerSocket server;
	public static BufferedReader in, inS;
	public static PrintWriter out, outS;
	public static String[] Field, Field2 = new String[2];
	public static boolean loginflag;
	String log, pass, str;
	private static boolean reg_login_flag;
	private static String message;
	
	public Server() {
		
		try {
			server = new ServerSocket(1234);
			
			boolean flagloop = false;
			while (true) {
				System.out.println(connections.size());
				flagloop = false;
				Socket socket = server.accept();
				inS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outS = new PrintWriter(socket.getOutputStream(), true);
				
				System.out.println("+1");
				
				while(true) {

					str = inS.readLine();
					
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
						CheckReg(log);
						if (reg_login_flag == false) {
							synchronized(MainServer.ArrList){
								System.out.println("Login: " + log + " не зарегистрирован." );
								UserData new_user = new UserData(log, pass);
								MainServer.ArrList.add(new_user);
								String result = "registerback" + "|" + "successfuly" + "#" + "successfuly";
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
						//synchronized(MainServer.ArrList)
						Check(log, pass);	
						System.out.println("Проверил и выслал ответ.");
						
						if (loginflag == true) {
							Connection con = new Connection(socket);
							connections.add(con);
							con.start();
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
					String result = "loginback" + "|" + "successfuly" + "#" + "successfuly";
					loginflag = true;
					outS.println(result);					
				}
			}
		}
		if (loginflag == false) {
			String result = "loginback" + "|" + "wrong" + "#" + "wrong";
			outS.println(result);
			// выслать в поток команду loginback с сообщением wrong
		}
	}

	
	public static void TextIN(List<UserData> ArrList) {
		String str;
		try{
			BufferedReader in = new BufferedReader(new FileReader("User Data File.txt"));			
			while ((str = in.readLine())!= null){
				Field = str.split("\\|");	
				String strNickname = Field[0];
				String strPassword = Field[1];
				UserData tmpUD = new UserData(strNickname,strPassword);
				ArrList.add(tmpUD);
			}
			in.close();
		} catch(IOException ex){}
	}	
	
	public void Print(List<UserData> ArrList){
		for (int i=0;i<ArrList.size(); i++) {
			ArrList.get(i).print();
		}
	}
	
}
