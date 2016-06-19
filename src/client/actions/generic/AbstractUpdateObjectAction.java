package client.actions.generic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import client.window.ListModel;
import client.window.WindowObject;
import core.model.DataModel;

public abstract class AbstractUpdateObjectAction<T extends DataModel> implements ActionListener {

	protected JFrame frame;
	protected ListModel<T> model;
	protected JList<T> list;

	public AbstractUpdateObjectAction(JFrame frame, ListModel<T> model, JList<T> list) {
		super();
		this.frame = frame;
		this.model = model;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(frame, "No object selected in list.");
			return;
		}

		WindowObject<T> vv = getDialog(frame, list.getSelectedValue());
		if (vv.getStatus()) {
			vv.updateObject();
			updatePerformed(list.getSelectedIndex(), vv.getObject());
		}
	}

	public void updatePerformed(int index, T obj) {
		model.set(index, obj);
	}

	abstract public WindowObject<T> getDialog(JFrame frame, T obj);

}