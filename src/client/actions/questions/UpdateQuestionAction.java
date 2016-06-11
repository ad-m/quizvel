package client.actions.questions;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;

import client.actions.generic.AbstractUpdateObjectAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;
import client.window.ListModel;
import client.window.QuestionDialog;
import core.model.Question;

/**
 * Klasa odpowiedzialna za akcje aktualizacji zaznaczonego w JList pytania z
 * ListModel i serwera.
 * 
 * @author adas
 *
 */

public class UpdateQuestionAction extends AbstractUpdateObjectAction<Question> {

	public UpdateQuestionAction(JFrame frame, ListModel<Question> model, JList<Question> list) {
		super(frame, model, list);
	}

	@Override
	public QuestionDialog get_dialog(JFrame frame, Question obj) {
		return new QuestionDialog(frame, obj);
	}

	@Override
	public void updatePerformed(int index, Question obj) {
		super.updatePerformed(index, obj);
		try {
			DAO.getInstance().saveQuestion(index, obj);
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(frame, e1);
		}
	}

}