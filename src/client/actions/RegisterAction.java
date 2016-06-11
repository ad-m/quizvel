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

/**
 * Klasa odpowiedzialna za akcje rejestracje użytkownika z wykorzystaniem
 * przekazanych pól tekstu.
 * 
 * @author adas
 *
 */
public class RegisterAction implements ActionListener {

	private JFrame frame;
	private JTextField textField_login;
	private JPasswordField textField_password;

	public RegisterAction(JFrame frame, JTextField textField_login, JPasswordField textField_password) {
		super();
		this.frame = frame;
		this.textField_login = textField_login;
		this.textField_password = textField_password;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (DAO.getInstance().register(textField_login.getText(), new String(textField_password.getPassword()))) {
				JOptionPane.showMessageDialog(frame, "Account registered. Try login now! See you soon!");
			} else {
				JOptionPane.showMessageDialog(frame, "Registration fail", "Authentication", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException | ServerErrorException ex) {
			ExceptionDialog.showExceptionDialog(frame, ex);
		}

	}
}