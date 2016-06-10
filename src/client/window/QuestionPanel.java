package client.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import core.model.Choice;
import core.model.Question;

/**
 * Klasa służacego do wyświetlania pytan ankiety i zaznaczania wybranej
 * odpowiedzi.
 * 
 * @author adas
 *
 */
public class QuestionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5500638339607489494L;
	JFrame frame;
	Question question;
	JPanel panel;
	ButtonGroup group;

	public QuestionPanel(JFrame frame, Question question) {
		super();
		this.frame = frame;
		this.question = question;
		this.panel = new JPanel();
		this.group = new ButtonGroup();
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout(0, 0));
		panel.setLayout(new FlowLayout());
		JLabel lblQuestionText = new JLabel(question.getText());
		this.add(lblQuestionText, BorderLayout.NORTH);
		this.add(panel, BorderLayout.SOUTH);

		int i = 0;
		for (Choice choice : question) {
			JRadioButton choice_button = new JRadioButton(choice.getText());
			choice_button.setActionCommand(Integer.toString(i++));
			panel.add(choice_button);
			group.add(choice_button);
		}
	}

	public int getChoiceId() {
		if (group.getSelection() == null) {
			return 0;
		}
		return Integer.valueOf(group.getSelection().getActionCommand());
	}

	public Question getQuestion() {
		return question;
	}
}
