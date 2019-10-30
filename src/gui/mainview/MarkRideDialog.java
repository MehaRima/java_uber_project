package gui.mainview;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AttributeMarkListener;
import gui.eventlisteners.CloseDialogListener;
import mainClasses.Ride;

/**
 * Dialog that pops up when clicking on a completed Ride in the Book Of Rides list.
 * @author Erwan
 *
 */
public class MarkRideDialog extends JDialog {
	
	private static final long serialVersionUID = 1875645169074796405L;
	
	private Ride ride;
	
	public MarkRideDialog(Ride ride) {
		this.ride = ride;
		
		this.setTitle("Attribute a mark");
		this.setSize(600, 110);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel marklabel = new JLabel("What mark do you want to attribute for this Ride ? (integer between 1 and 5) : ");
		JTextField mark = new JTextField(4);
		
		JPanel questionpanel = new JPanel();
		questionpanel.add(marklabel);
		questionpanel.add(mark);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton givemark = new JButton("Attribute Mark");
		givemark.addActionListener(new AttributeMarkListener(this.ride, mark));
		givemark.addActionListener(new CloseDialogListener(this));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(givemark);
		
		this.getContentPane().add(questionpanel);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
	
	

}
