package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AskForPriceListener;
import gui.eventlisteners.CloseDialogListener;
import mainClasses.Customer;

/**
 * Dialog that pops up when the "Ask for Price" button of the CustomerActionDialog is pressed. Lets the Customer compute prices for each type of Ride at the given hour of the day.
 * @author Erwan
 *
 */
public class AskForPriceDialog extends JDialog {

	private static final long serialVersionUID = 5202782824966791859L;
	
	private Customer c;
	
	public AskForPriceDialog(Customer c) {
		this.c = c;
		this.setTitle("Ask for Price");
		this.setSize(750, 120);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel destinationlabel = new JLabel("Destination of the Ride (formatted as 'x:y') :");
		JLabel hourlabel = new JLabel("    Booking hour (integer between 0 and 23), or 'now' :");
		
		JTextField destination = new JTextField(6);
		JTextField hour = new JTextField(4);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(destinationlabel);
		inputs.add(destination);
		inputs.add(hourlabel);
		inputs.add(hour);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton estimate = new JButton("Estimate Ride Fares (estimations will be printed in the Console)");
		estimate.addActionListener(new AskForPriceListener(this.c, destination, hour, this));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(estimate);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
}
