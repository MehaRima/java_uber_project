package gui.dialogs;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.eventlisteners.CloseDialogListener;

/**
 * Dialog box that pops up when an error has occurred.
 * @author Erwan
 *
 */
public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = 1206796059718748138L;

	public ErrorDialog(String errormsg) {
		this.setTitle("Error");
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		JPanel buttonpanel = new JPanel();
		
		JLabel errorlabel = new JLabel(errormsg + "         ");
		JButton OK = new JButton("OK");
		OK.addActionListener(new CloseDialogListener(this));

		
		buttonpanel.add(OK);
		this.add(errorlabel);
		this.add(buttonpanel);
		this.pack();
		this.setVisible(true);
	}
	
}
