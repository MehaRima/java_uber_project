package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AddCustomerNoNameListener;
import gui.eventlisteners.AddCustomerWithNamesListener;
import gui.eventlisteners.CloseDialogListener;

/**
 * Dialog box that pops up when the "AddCustomer" button is clicked.
 * @author Erwan
 *
 */
public class AddCustomerDialog extends JDialog {
	
	private static final long serialVersionUID = -234714654723134856L;
	
	
	public AddCustomerDialog() {
		this.setTitle("Add Customer");
		this.setSize(650, 130);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel name = new JLabel("Name of the Customer :");
		JLabel surname = new JLabel("Surname of the Customer :");
		
		JTextField namefield = new JTextField(13);
		JTextField surnamefield = new JTextField(13);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(name);
		inputs.add(namefield);
		inputs.add(surname);
		inputs.add(surnamefield);
		
		JButton cancel = new JButton("Cancel");
		JButton random = new JButton("Add Customer with automatically generated name and surname");
		random.addActionListener(new AddCustomerNoNameListener(this));
		JButton okay = new JButton("OK");
		okay.addActionListener(new AddCustomerWithNamesListener(this, namefield, surnamefield));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		cancel.addActionListener(new CloseDialogListener(this));
		buttons.add(random);
		buttons.add(okay);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}

	
}
