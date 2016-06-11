package client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.dao.DAO;
import client.dao.ServerErrorException;
import client.window.ExceptionDialog;

public class SaveAction implements ActionListener {
	private JFrame frame;

	public SaveAction(JFrame frame) {
		super();
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (DAO.getInstance().save()) {
				JOptionPane.showMessageDialog(frame, "Database saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(frame, "Unknown save error!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException | ServerErrorException e1) {
			ExceptionDialog.showExceptionDialog(frame, e1);
		}

	}
}