package client.actions.generic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import client.window.ExceptionDialog;
import client.window.WindowObject;

/**
 * Generyczna klasa odpowiedzialna za akcje aktualizacji zaznaczonego obiektu.
 * 
 * @author adas
 *
 */
public class SaveObjectAction implements ActionListener {
	JFrame frame;
	WindowObject<?> dialog;

	public SaveObjectAction(JFrame frame, WindowObject<?> dialog) {
		this.frame = frame;
		this.dialog = dialog;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			dialog.updateObject();
		} catch (NumberFormatException ex) {
			ExceptionDialog.showExceptionDialog(frame, ex);
		}
		dialog.dispose();
	}
}
