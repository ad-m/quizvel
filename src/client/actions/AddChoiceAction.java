package client.actions;

import javax.swing.JFrame;

import client.actions.generic.AbstractAddObjectAction;
import client.window.ChoiceDialog;
import client.window.ListModel;
import client.window.WindowObject;
import core.model.Choice;

/**
 * Klasa odpowiedzialna za dodanie nowej opcji wyboru do przekazanego
 * ``ListModel``.
 * 
 * @author adas
 */
public class AddChoiceAction extends AbstractAddObjectAction<Choice> {

	public AddChoiceAction(JFrame frame, ListModel<Choice> list) {
		super(frame, list);
	}

	@Override
	public WindowObject<Choice> getDialog(JFrame frame) {
		return new ChoiceDialog(frame);
	}

}
