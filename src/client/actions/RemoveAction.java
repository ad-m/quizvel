package client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import client.window.ListModel;

/**
 * Klasa generyczna odpowiedzialna za akcje usuwanie zaznaczonego elementu w
 * JList z generycznego ListModel.
 * 
 * @author adas
 *
 */
public final class RemoveAction implements ActionListener {
	private JList<?> list;
	private ListModel<?> model;
	private JFrame frame;

	public RemoveAction(JFrame frame, JList<?> list, ListModel<?> model) {
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
				model.remove(index);
			}
			JOptionPane.showMessageDialog(frame, text + " removed!");
		}
	}
}