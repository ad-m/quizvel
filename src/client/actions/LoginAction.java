package client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.AdminWindow;
import client.window.ExceptionDialog;
import client.window.UserWindow;
import core.model.User;

/**
 * Klasa akcji logowania użytkownika z wykorzystaniem przekazanych pól tekstu.
 * Wyświetla komunikaty błedów i umożliwia promowanie użytkownika logującego
 * się.
 * 
 * @author adas
 *
 */

public class LoginAction implements ActionListener {
	private JFrame frame;
	private JTextField textField_login;
	private JPasswordField textField_password;

	public LoginAction(JFrame frame, JTextField textField_login, JPasswordField textField_password) {
		super();
		this.frame = frame;
		this.textField_login = textField_login;
		this.textField_password = textField_password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			User user = authenticate();
			if (user == null) {
				JOptionPane.showMessageDialog(frame, "Authentication fail", "Authentication",
						JOptionPane.ERROR_MESSAGE);
			} else {
				frame.dispose();
				showUserWindow(user);
			}
		} catch (IOException | ServerErrorException ex) {
			// TODO Auto-generated catch block
			ExceptionDialog.showExceptionDialog(frame, ex);
		}
	}

	/**
	 * @return wynik autoryzacji użytkownika
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	private User authenticate() throws IOException, ServerErrorException {
		return DAO.getInstance().authenticate(textField_login.getText(), new String(textField_password.getPassword()));
	}

	/**
	 * wyświetlenie okna powitalnego
	 * 
	 * @param user
	 *            logowany użytkownik
	 */
	private void showUserWindow(User user) {
		JOptionPane.showMessageDialog(frame, getHelloMessage(user), "Welcome", JOptionPane.INFORMATION_MESSAGE);

		if (user.isAdmin()) {
			if (JOptionPane.showConfirmDialog(frame, "Do you want go to admin section?", "Admin section",
					JOptionPane.YES_NO_OPTION) == 0) {
				new AdminWindow();
			} else {
				new UserWindow();
			}
		} else {
			askSecret(user);
		}
	}

	/**
	 * wyświetlnie z pytaniem o promocje zalogowanego użytkownika
	 * 
	 * @param user
	 *            logowany użytkownik
	 */
	private void askSecret(User user) {
		if (JOptionPane.showConfirmDialog(frame, "Do you want provide a secret?", "Secret section",
				JOptionPane.YES_NO_OPTION) == 0) {
			processPromote(user);
		} else {
			new UserWindow();
		}
	}

	/**
	 * wykonanie akcji promocji użytkownika
	 * 
	 * @param user
	 *            logowany użytkownik
	 */
	private void processPromote(User user) {
		String secret = JOptionPane.showInputDialog(frame, "What's a secret?");
		try {
			if (DAO.getInstance().promote(secret)) {
				JOptionPane.showMessageDialog(frame, getHelloMessage(user), "Promoted!",
						JOptionPane.INFORMATION_MESSAGE);
				new AdminWindow();
			} else {
				JOptionPane.showMessageDialog(frame, "Secret incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
				new UserWindow();
			}
		} catch (IOException | ServerErrorException e) {
			ExceptionDialog.showExceptionDialog(frame, e);
		}
	}

	/**
	 * @param user
	 *            logowany użytkownik
	 * @return adekwantny komunikat dla użytkownika
	 */
	private String getHelloMessage(User user) {

		StringBuilder message = new StringBuilder("Hello " + user.getUsername() + "!");
		if (user.isAdmin()) {
			message.append(" You are admin!");
		}
		return message.toString();
	}

}