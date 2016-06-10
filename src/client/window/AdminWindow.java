package client.window;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.actions.questions.AddQuestionAction;
import client.actions.questions.RemoveQuestionAction;
import client.actions.questions.UpdateQuestionAction;
import client.dao.DAO;
import client.dao.ServerErrorException;
import core.model.Question;

/**
 * Klasa widoku GUI interfejsu użytkownika.
 * 
 * @author adas
 *
 */
public class AdminWindow {
	private JFrame frame;
	private ListModel<Question> model;
	private JList<Question> list;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DAO.getInstance().authenticate("xyz", "xyz");
					new AdminWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminWindow() {
		model = new ListModel<Question>();
		try {
			model.addAll(DAO.getInstance().getQuestions());
		} catch (IOException | ServerErrorException e) {
			ExceptionDialog.showExceptionDialog(frame, e);
		}
		list = new JList<Question>(model);
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 850, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane(list);

		scrollPane.setViewportView(list);
		scrollPane.setSize(830, 225);
		scrollPane.setLocation(5, 5);

		frame.getContentPane().add(scrollPane);

		JPanel panel = new JPanel();
		panel.setSize(830, 50);
		panel.setLocation(5, 230);
		frame.getContentPane().add(panel);

		JButton btnAddQuestion = new JButton("Add question");
		btnAddQuestion.addActionListener(new AddQuestionAction(frame, model));
		panel.add(btnAddQuestion);

		JButton btnRemoveQuestion = new JButton("Remove question");
		btnRemoveQuestion.addActionListener(new RemoveQuestionAction(frame, list, model));
		panel.add(btnRemoveQuestion);

		JButton btnEditQuestion = new JButton("Edit question");
		btnEditQuestion.addActionListener(new UpdateQuestionAction(frame, model, list));
		panel.add(btnEditQuestion);

		JButton btnSave = new JButton("Save database");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (DAO.getInstance().save()) {
						JOptionPane.showMessageDialog(frame, "Database saved!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame, "Unknown save error!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException | ServerErrorException e1) {
					ExceptionDialog.showExceptionDialog(frame, e1);
				}

			}
		});
		panel.add(btnSave);

		frame.setVisible(true);
	};
}
