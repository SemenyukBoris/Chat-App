package Server;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;


import java.awt.Font;

public class ServerWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel labelON, labelREG;
	
	
	public ServerWindow() {
		setResizable(false);
		this.setMinimumSize(new Dimension(350, 150));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(false);
		this.setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane.showOptionDialog(windowEvent.getWindow(), "Остановить работу сервера?" + "\n" + "Все подключения будут разорваны.",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0) {          
                	synchronized(Server.connections){
	                	if (Server.connections.size() > 0){
	                		for (int i=0;i<Server.connections.size(); i++){
	                			Server.connections.get(i).out.println("server|closed");
	                		}
	                		
	                	}
                	}
                	MainServer.TextOUT(MainServer.ArrList);
                	windowEvent.getWindow().setVisible(false);
                	windowEvent.getWindow().dispose();
                    System.exit(0);
                }
            }
		    
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowOpened(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowIconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowActivated(java.awt.event.WindowEvent windowEvent) {} 
		    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {} 
		});
		
		JLabel lblonline = new JLabel("User's online :");
		lblonline.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblonline.setHorizontalAlignment(SwingConstants.RIGHT);
		lblonline.setBounds(25, 26, 150, 21);
		getContentPane().add(lblonline);
		
		JLabel lblNewLabel = new JLabel("User's registered :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(25, 73, 150, 21);
		getContentPane().add(lblNewLabel);
		
		labelON = new JLabel("0");
		labelON.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelON.setBounds(232, 26, 46, 21);
		getContentPane().add(labelON);
		
		
		String reg = Integer.toString(MainServer.ArrList.size());
		labelREG = new JLabel(reg);
		labelREG.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelREG.setBounds(232, 73, 46, 21);
		getContentPane().add(labelREG);
	}
	
	public void ChangeOnlineCount(int count, int oper){
		if (oper == 1){
			int i = Integer.parseInt(labelON.getText());
			i--;
			labelON.setText(Integer.toString(i));
		}
		if (oper == 2){
			int i = Integer.parseInt(labelON.getText());
			i++;
			labelON.setText(Integer.toString(i));
		}
		if (oper == -1){
			int i = Integer.parseInt(labelREG.getText());
			i--;
			labelREG.setText(Integer.toString(i));
		}
		if (oper == -2){
			int i = Integer.parseInt(labelREG.getText());
			i++;
			labelREG.setText(Integer.toString(i));
		}
	}
	
}
