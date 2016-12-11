package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private ServerSocket server;

	public Server() {
		try {
			server = new ServerSocket(1234);

			while (true) {
				Socket socket = server.accept();			
				Connection con = new Connection(socket);
				connections.add(con);
				con.start();
			}
		} catch (IOException e) {
				e.printStackTrace();
		} finally {
			//closeAll();
		}
	}
	
	
//	private void closeAll() {
//		try {
//			server.close();
//
//			synchronized(connections) {
//				Iterator<Connection> iter = connections.iterator();
//				while(iter.hasNext()) {
//					((Connection) iter.next()).close();
//				}
//			}
//		} catch (Exception e) {
//			System.err.println("Exception в методе closeAll.");
//		}
//	}


	
	private class Connection extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		private Socket socket;
		
		private String name = "";
		
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
			try {
			name = in.readLine();				
			synchronized(connections) {				
				for (int i=0;i<connections.size();i++) {
					connections.get(i).out.println(name + " camse now");
				}
			}
				
			String str = "";
			while (true) {
				str = in.readLine();
				
				
				synchronized(connections) {
					for (int i=0;i<connections.size();i++) {
						connections.get(i).out.println(name + ": " + str);
					}
				}
			}
			
				
//			synchronized(connections) {
//				for (int i=0;i<connections.size();i++) {
//					connections.get(i).out.println(name + " has left");
//				}
//			}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close();

				
			}
		}

		
		public void close() {
			try {
				in.close();
				out.close();
				socket.close();
				connections.remove(this);
			} catch (Exception e) {  System.err.println("Exception в методе close");	}
			}	
		}

}
