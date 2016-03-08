package test;

import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ShredderUI.frmMain;

public class WindowCommunication {

	private static void createAndShowUI() {
		JFrame frame = new JFrame("WindowCommunication");
		frame.getContentPane().add(new MyFramePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// let's be sure to start Swing on the Swing event thread
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}

class MyFramePanel extends JPanel {
	private JTextField field = new JTextField(10);
	private JButton openDialogeBtn = new JButton("Open Dialog");

	// here my main gui has a reference to the JDialog and to the
	// MyDialogPanel which is displayed in the JDialog
	private MyDialogPanel dialogPanel = new MyDialogPanel();
	private JDialog dialog;

	public MyFramePanel() {
		openDialogeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTableAction();
			}
		});
		field.setEditable(false);
		field.setFocusable(false);

		add(field);
		add(openDialogeBtn);
	}

	private void openTableAction() {
		// lazy creation of the JDialog
		if (dialog == null) {
			Window win = SwingUtilities.getWindowAncestor( this);
			if (win != null) {
				dialog = new JDialog(win, "My Dialog", ModalityType.APPLICATION_MODAL);
				dialog.getContentPane().add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
			}
		}
		dialog.setVisible(true); // here the modal dialog takes over

		// this line starts *after* the modal dialog has been disposed
		// **** here's the key where I get the String from JTextField in the GUI
		// held
		// by the JDialog and put it into this GUI's JTextField.
		field.setText(dialogPanel.getFieldText());
	}
}

class MyDialogPanel extends JPanel {
	private JTextField field = new JTextField(10);
	private JButton okButton = new JButton("OK");

	public MyDialogPanel() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okButtonAction();
			}
		});
		add(field);
		add(okButton);
	}

	// to allow outside classes to get the text held by the JTextField
	public String getFieldText() {
		return field.getText();
	}

	// This button's action is simply to dispose of the JDialog.
	private void okButtonAction() {
		// win is here the JDialog that holds this JPanel, but it could be a
		// JFrame or
		// any other top-level container that is holding this JPanel
		Window win = SwingUtilities.getWindowAncestor(this);
		if (win != null) {
			win.dispose();
		}
	}
}
