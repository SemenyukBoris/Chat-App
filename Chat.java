package Application;

import java.util.ArrayList;


public class Chat {
	
	public static MainWindow win;
	public static LoginWindow log;
	public static RegisterWindow reg;
	public static SetIPServer serv;
	public static boolean nameflag, flagclose = false;
	public static ArrayList<UserData> ArrList = new ArrayList<UserData>();
	static String[] Field = new String[3];
	public static String Name;
	public static String IP;
	public static boolean flagIP;
		
	public static void main(String[] args) {
				
		
		serv = new SetIPServer();
		
//		while(true) {
//			if (IP != null) break;
//		}
		
		win = new MainWindow();			
		log = new LoginWindow();				
		reg = new RegisterWindow();
		
		win.Connect();

		
		log.run();
				
		log.dispose();
		reg.dispose();
		System.out.println(Chat.Name);
		win.out.println("connect|" + Name);
		win.flag = true;
		win.run();		
	}
			
}
