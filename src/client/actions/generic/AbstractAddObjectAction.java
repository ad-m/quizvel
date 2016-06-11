package client.actions.generic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import client.window.ListModel;
import client.window.WindowObject;
import core.model.DataModel;

/**
 * Klasa dodawania opcji do listy opcji wyboru w pytaniu
 * 
 * @author adas
 *
 */
public abstract class AbstractAddObjectAction<T extends DataModel> implements ActionListener {

	private ListModel<T> list;
	private JFrame frame;

	public AbstractAddObjectAction(JFrame frame, ListModel<T> list) {
		this.list = list;
		this.frame = frame;
	}

	public JFrame getFrame() {
		return frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WindowObject<T> vw = this.getDialog(frame);
		if (vw.getStatus()) {
			savePerformed(vw.getObject());
		}
		vw.dispose();

	}

	public void savePerformed(T obj) {
		list.addElement(obj);
	}

	public abstract WindowObject<T> getDialog(JFrame frame);
}