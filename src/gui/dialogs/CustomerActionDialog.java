package gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.eventlisteners.CloseDialogListener;
import gui.eventlisteners.OpenMoveCarOrCustDialogListener;
import mainClasses.Customer;

/**
 * Dialog that pops up when clicking on a Customer on the map, asking if the user wants to move the Customer, ask for prices or simulated a Ride.
 * @author Erwan
 *
 */
public class CustomerActionDialog extends JDialog {

	private static final long serialVersionUID = -4218388323760760662L;

	private Customer c;
	
	public CustomerActionDialog(Customer c) {
		this.c = c;
		this.setTitle("Customer Action");
		this.setSize(600, 110);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel question = new JLabel("What action do you want to carry out ?");
		
		JPanel questionpanel = new JPanel();
		questionpanel.add(question);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton movecust = new JButton("Move Customer");
		movecust.addActionListener(new OpenMoveCarOrCustDialogListener(this.c));
		movecust.addActionListener(new CloseDialogListener(this));
		JButton ask4price = new JButton("Ask for Ride prices");
		ask4price.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AskForPriceDialog(c);
			}
			
		});
		ask4price.addActionListener(new CloseDialogListener(this));
		JButton simulateride = new JButton("Simulate a Ride");
		simulateride.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SimRideDialog(c);
			}
			
		});
		simulateride.addActionListener(new CloseDialogListener(this));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(movecust);
		buttons.add(ask4price);
		buttons.add(simulateride);
		
		this.getContentPane().add(questionpanel);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
}
