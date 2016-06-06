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
import client.window.ExceptionDialog;
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
			User user = DAO.getInstance().authenticate(textField_login.getText(),
					new String(textField_password.getPassword()));
			if (user == null) {
				JOptionPane.showMessageDialog(frame, "Authentication fail", "Authentication",
						JOptionPane.ERROR_MESSAGE);
			} else {
				frame.dispose();
				String message = "Hello " + user.getUsername() + "!";
				if (user.isAdmin()) {
					message += " You are admin!";
				}
				JOptionPane.showMessageDialog(frame, message, "Welcome", JOptionPane.PLAIN_MESSAGE);
			}

		} catch (IOException | ServerErrorException ex) {
			// TODO Auto-generated catch block
			ExceptionDialog.showExceptionDialog(frame, ex);
		}
	}

}