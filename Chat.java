package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Chat {
	
	public static volatile MainWindow win;
	public static boolean nameflag = false;
	public static ArrayList<UserData> ArrList = new ArrayList<UserData>();
	static String[] Field = new String[3];
	public static String Name;
		
	public static void main(String[] args) {
		TextIN(ArrList);
		
		win = new MainWindow();	
		new LoginWindow();	
		
		while(true) {
			if (win.flag == true) break;
		}
		win.run();		
		try {
			win.socket.close();
		} catch (Exception e) {	e.printStackTrace(); }
	}
			
	public static void TextOUT(List<UserData> ArrList) {
		// Запись в текстовый файл 
		try{
			PrintWriter out = new PrintWriter(new FileWriter("User Data File.txt"));
			for(int i=0;i<ArrList.size();i++){
				out.println(ArrList.get(i).getNickname() + "|" + ArrList.get(i).getPassword() + "|" + ArrList.get(i).getIp());
			}
			out.close();
		} catch(IOException ex){}		
	}
	
	public static void TextIN(List<UserData> ArrList) {
		// Чтение из текстового файла
		String str;
		try{
			BufferedReader in = new BufferedReader(new FileReader("User Data File.txt"));			
			while ((str = in.readLine())!= null){
				Field = str.split("\\|");	
				String strNickname = Field[0];
				String strPassword = Field[1];
				String strIP = Field[2];
				UserData tmpUD = new UserData(strNickname,strPassword,strIP);
				ArrList.add(tmpUD);
			}
			in.close();
		} catch(IOException ex){}
	}
	
}
