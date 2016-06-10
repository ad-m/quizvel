package client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;
import client.window.QuestionPanel;

/**
 * Klasa akcji - weryfikacji udzielonych odpowiedzi i wystawienia oceny.
 * 
 * @author adas
 *
 */
public class SurveyCheckAction implements ActionListener {
	private JFrame frame;
	private List<QuestionPanel> question_panels;

	public SurveyCheckAction(JFrame frame, List<QuestionPanel> question_panels) {
		super();
		this.frame = frame;
		this.question_panels = question_panels;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Integer> answers = get_answers();
		try {
			int result = DAO.getInstance().checkSurvey(answers);
			String rank = null;
			if (result > 3) {
				rank = "bdb";
			} else {
				rank = "ndst";
			}
			JOptionPane.showMessageDialog(frame, "Your result is " + result + " points. Your rank is" + rank);
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(frame, e1);
		}
	}

	private List<Integer> get_answers() {
		List<Integer> answers = new LinkedList<Integer>();
		for (QuestionPanel question_panel : question_panels) {
			answers.add(question_panel.getChoiceId());
		}
		return answers;
	}
}