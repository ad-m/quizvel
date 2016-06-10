package client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import client.window.ChoiceDialog;
import client.window.ListModel;
import core.model.Choice;

public final class AddChoiceAction implements ActionListener {

	private ListModel<Choice> list;
	private JFrame frame;

	public AddChoiceAction(JFrame frame, ListModel<Choice> list) {
		super();
		this.list = list;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChoiceDialog vw = new ChoiceDialog(frame);
		// if (vw.getStatus()) {
		if (vw.getStatus()) {
			Choice obj = vw.getObject();
			list.addElement(obj);
		}

		vw.dispose();

	}
}