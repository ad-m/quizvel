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

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			User user = authenticate();
			if (user == null) {
				JOptionPane.showMessageDialog(frame, "Authentication fail", "Authentication",
						JOptionPane.ERROR_MESSAGE);
			} else {
				frame.dispose();
				show_new_window(user);
			}
		} catch (IOException | ServerErrorException ex) {
			// TODO Auto-generated catch block
			ExceptionDialog.showExceptionDialog(frame, ex);
		}
	}

	private User authenticate() throws IOException, ServerErrorException {
		return DAO.getInstance().authenticate(textField_login.getText(), new String(textField_password.getPassword()));
	}

	private void show_new_window(User user) {
		JOptionPane.showMessageDialog(frame, get_hello_message(user), "Welcome", JOptionPane.PLAIN_MESSAGE);

		if (user.isAdmin() && JOptionPane.showConfirmDialog(frame, "Do you want go to admin section?", "Admin section",
				JOptionPane.YES_NO_OPTION) == 0) {
			new AdminWindow();
			System.out.println("Admin window opened!");
		} else {
			new UserWindow();
		}
	}

	private String get_hello_message(User user) {

		StringBuilder message = new StringBuilder("Hello " + user.getUsername() + "!");
		if (user.isAdmin()) {
			message.append(" You are admin!");
		}
		return message.toString();
	}

}