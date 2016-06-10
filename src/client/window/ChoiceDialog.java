package client.window;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client.actions.CloseAction;
import client.actions.UpdateObjectAction;
import core.model.Choice;

public class ChoiceDialog extends JDialog implements WindowObject<Choice> {
	private static final long serialVersionUID = -3475156254787631881L;
	private Choice object;
	private JFrame frame;
	private JLabel lblText = new JLabel("Text");
	private JTextField txtText = new JTextField("Text");
	private boolean status = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ChoiceDialog(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChoiceDialog(JFrame frame, Choice choice) {
		super(frame, "", true);
		this.frame = frame;
		this.object = choice;
		copyData();
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ChoiceDialog(JFrame frame) {
		this(frame, new Choice(""));
	}

	private void copyData() {
		txtText.setText(this.object.getText());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 225, 100);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		lblText.setBounds(5, 5, 45, 20);
		getContentPane().add(lblText);

		txtText.setBounds(50, 5, 170, 20);
		getContentPane().add(txtText);
		txtText.setColumns(10);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(15, 35, 84, 25);
		getContentPane().add(btnSave);
		btnSave.addActionListener(new UpdateObjectAction(frame, this));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(110, 35, 84, 25);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(new CloseAction(this));
		setVisible(true);

	}

	@Override
	public Choice getObject() {
		return this.object;
	}

	@Override
	public void updateObject() {
		this.status = true;
		this.object.setText(txtText.getText());
	}

	@Override
	public void setStatus(boolean s) {
		this.status = s;

	}

	@Override
	public boolean getStatus() {
		return this.status;
	}
}
