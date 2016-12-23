package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainServer {
	public static ArrayList<UserData> ArrList = new ArrayList<UserData>();
	static String[] Field = new String[3];
	public static String Name;
	
	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	public static ServerSocket server;
	public static ServerWindow servw;

	
	public static void main(String[] args) {
		
		System.out.println("Сервер запущен.");
		TextIN(ArrList);
		System.out.println("Данные полученны.");
		
		servw = new ServerWindow();
		new Server();
		
		
	}
	
	public static void TextOUT(List<UserData> ArrList) {
		try{
			PrintWriter out = new PrintWriter(new FileWriter("User Data File.txt"));
			for(int i=0;i<ArrList.size();i++){
				out.println(ArrList.get(i).getNickname() + "|" + ArrList.get(i).getPassword());
			}
			out.close();
		} catch(IOException ex){}		
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

}
