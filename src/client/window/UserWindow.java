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

/**
 * @author adas
 *
 */
public class UserWindow {
	private JFrame frame;

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

	public UserWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);

		List<QuestionPanel> question_panels = initializeQuestionTabs(tabbedPane);
		JButton saveButton = new JButton("check");
		saveButton.addActionListener(new SurveyCheckAction(frame, question_panels));

		frame.getContentPane().add(saveButton, BorderLayout.SOUTH);

	}

	/**
	 * inicjalizacja i dodanie paneli pytań.
	 * 
	 * @param tabbedPane
	 * @return zwrocenie listy stworzonych paneli.
	 */
	private List<QuestionPanel> initializeQuestionTabs(JTabbedPane tabbedPane) {
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
