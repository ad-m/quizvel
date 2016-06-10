package client.window;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.actions.RemoveAction;
import client.actions.questions.AddQuestionAction;
import client.actions.questions.UpdateQuestionAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import core.model.Choice;
import core.model.Question;
import server.storage.QuestionStorage;

public class AdminWindow {
	private JFrame frame;
	private ListModel<Question> model;
	private JList<Question> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Question question = new Question("Lorem ipsum");
					question.add(new Choice("X"));
					QuestionStorage.getInstance().add(question.clone());
					question.add(new Choice("Y"));
					QuestionStorage.getInstance().add(question.clone());
					question.add(new Choice("Z"));
					QuestionStorage.getInstance().add(question.clone());
					question.add(new Choice("D"));
					QuestionStorage.getInstance().add(question.clone());

					new AdminWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminWindow() {
		model = new ListModel<Question>();
		try {
			model.addAll(DAO.getInstance().getQuestions());
		} catch (IOException | ServerErrorException e) {
			ExceptionDialog.showExceptionDialog(frame, e);
		}
		list = new JList<Question>(model);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 850, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane(list);

		scrollPane.setViewportView(list);
		scrollPane.setSize(830, 225);
		scrollPane.setLocation(5, 5);

		frame.getContentPane().add(scrollPane);

		JPanel panel = new JPanel();
		panel.setSize(830, 50);
		panel.setLocation(5, 230);
		frame.getContentPane().add(panel);

		JButton btnAddQuestion = new JButton("Add question");
		btnAddQuestion.addActionListener(new AddQuestionAction(frame, model));
		panel.add(btnAddQuestion);

		JButton btnRemoveQuestion = new JButton("Remove question");
		btnRemoveQuestion.addActionListener(new RemoveAction(frame, list, model));
		panel.add(btnRemoveQuestion);

		JButton btnEditQuestion = new JButton("Edit question");
		btnEditQuestion.addActionListener(new UpdateQuestionAction(frame, model, list));
		panel.add(btnEditQuestion);

		frame.setVisible(true);
	};
}
