package client.window;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginWindow extends JFrame {
	private static final long serialVersionUID = -7136970263621192432L;

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(300,120);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane();
		JPanel panel = new JPanel();

		this.getContentPane().add(panel);
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		JLabel lblLogin = new JLabel("Login: ");
		panel.add(lblLogin);
		JTextField textLogin = new JTextField("", 15);
		layout.putConstraint(SpringLayout.NORTH, lblLogin, 5, SpringLayout.NORTH, textLogin);
		layout.putConstraint(SpringLayout.EAST, lblLogin, 5, SpringLayout.WEST, textLogin);
		layout.putConstraint(SpringLayout.NORTH, textLogin, 5, SpringLayout.NORTH, panel);
		panel.add(textLogin);

		JLabel lblPassword = new JLabel("Password: ");
		panel.add(lblPassword);
		JTextField textPassword = new JPasswordField("", 15);
		layout.putConstraint(SpringLayout.NORTH, lblPassword, 5, SpringLayout.NORTH, textPassword);
		layout.putConstraint(SpringLayout.EAST, lblPassword, 5, SpringLayout.WEST, textPassword);
		layout.putConstraint(SpringLayout.NORTH, textPassword, 5, SpringLayout.SOUTH, textLogin);
		layout.putConstraint(SpringLayout.EAST, textLogin, 0, SpringLayout.EAST, textPassword);
		layout.putConstraint(SpringLayout.WEST, textPassword, 90, SpringLayout.WEST, panel);
		panel.add(textPassword);
		JButton btn = new JButton("Login");
		this.getContentPane().add(btn, BorderLayout.SOUTH);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
