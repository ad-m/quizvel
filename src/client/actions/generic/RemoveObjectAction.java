package client.actions.generic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import client.window.ListModel;

/**
 * Klasa odpowiedzialna za usuwanie zaznaczonego w ``JList`` obiektu z
 * ``ListModel``.
 * 
 * @author adas
 *
 */
public class RemoveObjectAction implements ActionListener {

	protected JList<?> list;
	protected ListModel<?> model;
	protected JFrame frame;

	public RemoveObjectAction(JFrame frame, JList<?> list, ListModel<?> model) {
		super();
		this.frame = frame;
		this.list = list;
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedValue() != null) {
			String text = list.getSelectedValue().toString();
			int index = list.getSelectedIndex();
			if (index != -1) {
				removePerformed(index);
				JOptionPane.showMessageDialog(frame, text + " removed!");
			}

		}
	}

	public void removePerformed(int index) {
		model.remove(index);
	}

}