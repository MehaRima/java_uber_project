package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.CloseDialogListener;
import gui.eventlisteners.SimulateRideListener;
import mainClasses.Customer;

/**
 * Dialog that pops up when the user clicks on the "Simulate Ride" function of a CustomerActionDialog.
 * @author Erwan
 *
 */
public class SimRideDialog extends JDialog {

	private static final long serialVersionUID = -3711684411487627727L;
	
	private Customer c;
	
	public SimRideDialog(Customer c) {
		this.c = c ;
		this.setTitle("Simulate a Ride");
		this.setSize(700, 150);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel destinationlabel = new JLabel("Destination of the Ride (formatted as 'x:y') :");
		JLabel timelabel = new JLabel("Booking time and date (formatted as 'minmin/hh/dd/mm/yyyy), or 'now' :");
		JLabel ridetypelabel = new JLabel("Type of Ride :");
		
		JTextField destination = new JTextField(6);
		JTextField time = new JTextField(8);
		String[] ridetypes = {"UberX", "UberBlack", "UberVan", "UberPool"};
		JComboBox<String> ridetypeselector = new JComboBox<String>(ridetypes);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(destinationlabel);
		inputs.add(destination);
		inputs.add(ridetypelabel);
		inputs.add(ridetypeselector);
		inputs.add(timelabel);
		inputs.add(time);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton simulation = new JButton("Start the Ride Simulation (you will see the execution of the steps by manually passing time");
		simulation.addActionListener(new SimulateRideListener(this.c, destination, time, ridetypeselector, this));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(simulation);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
}
