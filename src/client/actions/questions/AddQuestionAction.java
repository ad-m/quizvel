package client.actions.questions;

import java.io.IOException;

import javax.swing.JFrame;

import client.actions.generic.AbstractAddObjectAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;
import client.window.ListModel;
import client.window.QuestionDialog;
import client.window.WindowObject;
import core.model.Question;

/**
 * Klasa akcji dodawania pytania do ListModelu i serwera.
 * 
 * @author adas
 *
 */
public final class AddQuestionAction extends AbstractAddObjectAction<Question> {

	public AddQuestionAction(JFrame frame, ListModel<Question> list) {
		super(frame, list);
	}

	@Override
	public void savePerformed(Question obj) {
		super.savePerformed(obj);
		try {
			DAO.getInstance().saveQuestion(obj);
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(this.getFrame(), e1);
		}
	}

	@Override
	public WindowObject<Question> getDialog(JFrame frame) {
		return new QuestionDialog(frame);
	}

}