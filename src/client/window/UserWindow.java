package client.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import client.actions.SurveyCheckAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import core.model.Question;

public class UserWindow {
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DAO.getInstance().authenticate("xyz", "xyz");
					new UserWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);

		List<QuestionPanel> question_panels = initialize_question_tab(tabbedPane);
		JButton saveButton = new JButton("check");
		saveButton.addActionListener(new SurveyCheckAction(frame, question_panels));

		frame.getContentPane().add(saveButton, BorderLayout.SOUTH);

	}

	private List<QuestionPanel> initialize_question_tab(JTabbedPane tabbedPane) {
		List<Question> question_list;
		List<QuestionPanel> result = new LinkedList<QuestionPanel>();
		try {
			question_list = DAO.getInstance().getSurvey();
			int i = 0;
			for (Question question : question_list) {
				QuestionPanel question_panel = new QuestionPanel(frame, question);
				tabbedPane.addTab("Question no. " + (i++), question_panel);
				result.add(question_panel);
			}

		} catch (IOException | ServerErrorException e) {
			ExceptionDialog.showExceptionDialog(frame, e);
		}
		return result;
	}

}
