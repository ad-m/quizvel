package client.actions.questions;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;

import client.actions.generic.RemoveObjectAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;
import client.window.ListModel;

/**
 * Klasa odpowiedzialna za akcjego usuwanie zaznaczonego w JList pytania z
 * ListModel i serwera.
 * 
 * @author adas
 *
 */
public final class RemoveQuestionAction extends RemoveObjectAction {
	public RemoveQuestionAction(JFrame frame, JList<?> list, ListModel<?> model) {
		super(frame, list, model);
	}

	@Override
	public void removePerformed(int index) {
		super.removePerformed(index);
		try {
			DAO.getInstance().deleteQuestion(index);
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(frame, e1);
		}
	}

}