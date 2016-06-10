package client.window;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.actions.AddChoiceAction;
import client.actions.CloseAction;
import client.actions.RemoveAction;
import client.actions.UpdateObjectAction;
import core.model.Choice;
import core.model.Question;

/**
 * Okno edycji pytania
 * 
 * @author adas
 *
 */
public class QuestionDialog extends JDialog implements WindowObject<Question> {

	private static final long serialVersionUID = -1626032456115322781L;
	private JFrame frame;
	private JTextField textField = new JTextField();;
	private JScrollPane scrollPane;
	private JButton btnAddChoice;
	private JButton btnRemoveChoice;
	private JButton btnSaveQuestion;
	private JButton btnCancelQuestion;
	private JList<Choice> list;
	private Question object;
	private ListModel<Choice> model;
	private boolean status = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Question question = new Question("Question text");
					question.add(new Choice("X"));
					question.add(new Choice("Y"));
					new QuestionDialog(null, question);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public QuestionDialog(JFrame frame, Question question) {
		super(frame, "", true);
		this.frame = frame;
		this.object = question;
		this.model = new ListModel<Choice>();
		list = new JList<Choice>(this.model);
		copyData();
		initialize();
	}

	public QuestionDialog(JFrame frame) {
		this(frame, new Question());
	}

	private void initialize() {
		setBounds(100, 100, 420, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Todo: Sprawdzic
															// poprawna wartosc
		getContentPane().setLayout(null);
		JLabel lblPytanie = new JLabel("Pytanie");
		lblPytanie.setBounds(28, 12, 70, 15);
		getContentPane().add(lblPytanie);

		textField.setBounds(139, 10, 273, 19);
		getContentPane().add(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane(list);
		scrollPane.setBounds(5, 39, 408, 131);
		getContentPane().add(scrollPane);

		scrollPane.setViewportView(list);
		scrollPane.setSize(400, 120);
		scrollPane.setLocation(5, 70);

		btnAddChoice = new JButton("Add choice");
		btnAddChoice.setBounds(40, 194, 150, 25);
		btnAddChoice.addActionListener(new AddChoiceAction(frame, model));
		getContentPane().add(btnAddChoice);

		btnRemoveChoice = new JButton("Remove choice");
		btnRemoveChoice.setBounds(200, 194, 150, 25);
		btnRemoveChoice.addActionListener(new RemoveAction(frame, list, model));
		getContentPane().add(btnRemoveChoice);

		btnSaveQuestion = new JButton("Save question");
		btnSaveQuestion.setBounds(40, 231, 150, 25);
		btnSaveQuestion.addActionListener(new UpdateObjectAction(frame, this));
		getContentPane().add(btnSaveQuestion);

		btnCancelQuestion = new JButton("Cancel question");
		btnCancelQuestion.setBounds(200, 231, 150, 25);
		btnCancelQuestion.addActionListener(new CloseAction(this));
		getContentPane().add(btnCancelQuestion);

		setVisible(true);
		dispose();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.window.WindowObject#getObject()
	 */
	@Override
	public Question getObject() {
		return this.object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.window.WindowObject#updateObject()
	 */
	@Override
	public void updateObject() {
		this.object.setText(this.textField.getText());
		this.object.clear();
		for (Choice choice : this.model) {
			this.object.add(choice);
		}
		this.object.setCorrectId(this.list.getSelectedIndex());
		this.status = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.window.WindowObject#setStatus(boolean)
	 */
	@Override
	public void setStatus(boolean s) {
		this.status = s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.window.WindowObject#getStatus()
	 */
	@Override
	public boolean getStatus() {
		return this.status;
	}

	public void copyData() {
		this.textField.setText(this.object.getText());
		for (Choice choice : this.object) {
			this.model.addElement(choice);
		}

	}

}
