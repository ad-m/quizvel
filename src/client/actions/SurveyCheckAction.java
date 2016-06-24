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
		try {
			JOptionPane.showMessageDialog(frame, getSuccessMessage(DAO.getInstance().checkSurvey(getAnswers())));
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(frame, e1);
		}
	}

	/**
	 * metoda odpowiedzialna za komunikat wyniku ankietu.
	 * 
	 * @param result
	 *            liczba punktów
	 * @return wiadomość tekstowa
	 */
	private String getSuccessMessage(int result) {
		return "Your result is " + result + " points. Your rank is " + (result > 3 ? "bdb" : "ndst");
	}

	private List<Integer> getAnswers() {
		List<Integer> answers = new LinkedList<Integer>();
		for (QuestionPanel question_panel : question_panels) {
			answers.add(question_panel.getChoiceId());
		}
		return answers;
	}
}