
import java.awt.EventQueue;

import javax.swing.JOptionPane;

import client.dao.DAO;
import client.window.LoginWindow;

public class Client {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (DAO.getInstance().healt_check()) {
						new LoginWindow();
					} else {
						JOptionPane.showMessageDialog(null, "No connection to server.", "Connection error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
