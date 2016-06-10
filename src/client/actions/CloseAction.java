package client.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa ActionListener zamykajaca kliente okono.
 * 
 * @author adas
 *
 */
public class CloseAction implements ActionListener {
	private Window frame;

	public CloseAction(Window frame) {
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.setVisible(false);
		frame.dispose();
	}
}