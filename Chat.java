package Application;


public class Chat {
	
	public static MainWindow win;
	public static LoginWindow log;
	public static RegisterWindow reg;
	public static SetIP set;
	public static boolean nameflag, flagclose = false;
	static String[] Field = new String[3];
	public static String Name;
	public static String IP;
	public static boolean flagIP;
		
	public static void main(String[] args) {
								
		
		win = new MainWindow();			
		log = new LoginWindow();				
		reg = new RegisterWindow();
		
		set = new SetIP();
		set.run();
		set.setVisible(false);
		
		log.run();
		log.dispose();
		reg.dispose();
		System.out.println(Chat.Name);
		win.out.println("connect|" + Name);
		win.flag = true;
		win.run();	
	}
			
}
