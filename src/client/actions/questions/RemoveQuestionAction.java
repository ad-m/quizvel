package client.actions.questions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

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
public final class RemoveQuestionAction implements ActionListener {
	private JList<?> list;
	private ListModel<?> model;
	private JFrame frame;

	public RemoveQuestionAction(JFrame frame, JList<?> list, ListModel<?> model) {
		super();
		this.frame = frame;
		this.list = list;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedValue() != null) {
			String text = list.getSelectedValue().toString();
			int index = list.getSelectedIndex();
			if (index != -1) {
				try {
					DAO.getInstance().deleteQuestion(index);
				} catch (IOException | ServerErrorException e1) {
					ExceptionDialog.showExceptionDialog(frame, e1);
				}
				model.remove(index);
				JOptionPane.showMessageDialog(frame, text + " removed!");
			}

		}
	}
}