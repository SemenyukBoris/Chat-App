package Application;

public class MainTest {
	
	public static volatile MainWindow win;
	
	public static void main(String[] args) {
		System.out.println("Сервер запущен.");
		new Server();
		
	}

}
