package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * Action Listener that closes the JDialog windows when the "Cancel" button is clicked.
 * @author Erwan
 *
 */
public class CloseDialogListener implements ActionListener {

	/**
	 * The JDialog associated with this ActionListener.
	 */
	private JDialog dialog;
	
	/**
	 * Instantiates a new Action Listener that will affect the given JDialog parameter.
	 * @param dialog The JDialog that will be closed on action.
	 */
	public CloseDialogListener(JDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

}
