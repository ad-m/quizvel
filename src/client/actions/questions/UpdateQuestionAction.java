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
import client.window.QuestionDialog;
import core.model.Question;

public final class UpdateQuestionAction implements ActionListener {

	private JFrame frame;
	private ListModel<Question> model;
	private JList<Question> list;

	public UpdateQuestionAction(JFrame frame, ListModel<Question> model, JList<Question> list) {
		super();
		this.frame = frame;
		this.model = model;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedValue() != null) {
			System.out.print(list.getSelectedValue());
			QuestionDialog vv = new QuestionDialog(frame, list.getSelectedValue());
			if (vv.getStatus()) {
				vv.updateObject();
				System.out.print(list.getSelectedValue());
				model.updateIndex(list.getSelectedIndex());
				try {
					DAO.getInstance().saveQuestion(list.getSelectedIndex(), vv.getObject());
				} catch (IOException | ServerErrorException e1) {
					ExceptionDialog.showExceptionDialog(frame, e1);
				}
			}
		} else {
			JOptionPane.showMessageDialog(frame, "No object selected in list.");
		}
	}
}