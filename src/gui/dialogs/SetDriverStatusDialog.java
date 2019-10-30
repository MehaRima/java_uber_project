package gui.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.eventlisteners.CloseDialogListener;
import gui.eventlisteners.SetDriverStatusListener;

/**
 * JDialog that pops up when a row of the Drivers list is clicked.
 * @author Erwan
 *
 */
public class SetDriverStatusDialog extends JDialog {

	private static final long serialVersionUID = 7914464042305122039L;
	
	private JComboBox<Object> statusselector;
	
	private String driverID;
	
	public SetDriverStatusDialog(String driverID) {
		this.driverID = driverID;
		
		this.setTitle("Set Driver Status");
		this.setSize(400, 130);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel statuslabel = new JLabel("New Status of the Driver :");
		String[] statuses = {"on_duty", "off_duty", "offline"};
		this.statusselector = new JComboBox<Object>(statuses);
		
		JPanel inputs = new JPanel();
		inputs.add(statuslabel);
		inputs.add(statusselector);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton setstatus = new JButton("Set Status");
		setstatus.addActionListener(new SetDriverStatusListener(this.driverID, this.statusselector, this));
		
		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(setstatus);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}

}
