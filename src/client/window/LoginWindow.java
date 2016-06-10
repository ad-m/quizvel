package client.window;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.actions.LoginAction;
import client.actions.RegisterAction;

/**
 * 
 * @author adas
 *
 */
public class LoginWindow {
	private JFrame frame;
	private JTextField textField_login;
	private JPasswordField textField_password;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(110, 95, 230, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField_login = new JTextField();
		textField_login.setBounds(100, 10, 120, 20);
		frame.getContentPane().add(textField_login);
		textField_login.setColumns(10);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(lblLogin);

		textField_password = new JPasswordField();
		textField_password.setBounds(100, 30, 120, 20);
		frame.getContentPane().add(textField_password);
		textField_password.setColumns(10);

		JLabel lblRegister = new JLabel("Password:");
		lblRegister.setBounds(10, 30, 80, 20);
		frame.getContentPane().add(lblRegister);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(10, 60, 205, 25);
		frame.getContentPane().add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 90, 205, 25);
		frame.getContentPane().add(btnRegister);

		btnLogin.addActionListener(new LoginAction(frame, textField_login, textField_password));

		btnRegister.addActionListener(new RegisterAction(frame, textField_login, textField_password));
	}
}
