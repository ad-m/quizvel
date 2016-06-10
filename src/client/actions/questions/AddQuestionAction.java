package client.actions.questions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;
import client.window.ListModel;
import client.window.QuestionDialog;
import core.model.Question;

public final class AddQuestionAction implements ActionListener {
	private ListModel<Question> model;
	private JFrame frame;

	public AddQuestionAction(JFrame frame, ListModel<Question> model) {
		super();
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		QuestionDialog vw = new QuestionDialog(frame);
		// if (vw.getStatus()) {
		if (vw.getStatus()) {
			Question obj = vw.getObject();
			try {
				DAO.getInstance().saveQuestion(obj);
			} catch (IOException | ServerErrorException e1) {
				ExceptionDialog.showExceptionDialog(frame, e1);
			}
			model.addElement(obj);
		}

		vw.dispose();

	}
}