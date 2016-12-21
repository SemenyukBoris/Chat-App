package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Login implements Runnable{
	private static String info;
	public static BufferedReader in;
	public static PrintWriter out;
	public Socket socket;
	private static boolean loginflag = false;
	public static ServerSocket server;
	static String[] Field = new String[2];
	static String[] Log = new String[2];
	
	public void Connection(Socket socket) {
		this.socket = socket;	
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);	
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	public void close(){
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {  System.err.println("Exception в методе close");	}
	}
	
	public static void Check(String log, String pass){
		for (int i=0; i<MainServer.ArrList.size(); i++){
			if (MainServer.ArrList.get(i).getNickname().equals(log) == true) {
				if (MainServer.ArrList.get(i).getPassword().equals(pass) == true) {
					String result = "loginback" + "|" + "successfuly" + "#" + "successfuly";
					out.println(result);
					loginflag = true;
				}
			}
		}
		if (loginflag == false) {
			String result = "loginback" + "|" + "wrong" + "#" + "wrong";
			out.println(result);
		}
	}
	
	
	
	@Override
	public void run(){
		try{
			while(true) {
				info = in.readLine();
				Field = info.split("\\|");
				String operation = Field[0];
				String message = Field[1];
				System.out.println("Получил на проверку операцию:" + operation);
				if (operation.equals("login") == true) {					
					Log = message.split("#");
					String log = Log[0];
					String pass = Log[1];
					System.out.println("Получил на проверку :" + log + "#" + pass);
					Check(log, pass);
					break;
				} 
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
