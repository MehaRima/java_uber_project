package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.CloseDialogListener;
import gui.eventlisteners.SetupListener;

/**
 * Dialog box that pops up when the "Setup" button is clicked.
 * @author Erwan
 *
 */
public class CustomSetupDialog extends JDialog {

	private static final long serialVersionUID = 8167759839522880144L;
	
	
	/**
	 * The text field where the user can input the desired number of Standard Cars to be created.
	 */
	private JTextField nSCars;
	
	/**
	 * The text field where the user can input the desired number of Berlines to be created.
	 */
	private JTextField nBerline;
	
	/**
	 * The text field where the user can input the desired number of Vans to be created.
	 */
	private JTextField nVan;
	
	/**
	 * The text field where the user can input the desired number of Customers to be created.
	 */
	private JTextField nCustomers;
	
	public CustomSetupDialog() {
		this.setTitle("Custom Setup");
		this.setSize(700, 130);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel SCars = new JLabel("Number of Standard Cars :");
		JLabel Berline = new JLabel("Number of Berlines :");
		JLabel Van = new JLabel("Number of Vans :");
		JLabel Customers = new JLabel("Number of Customers :");
		
		nSCars = new JTextField(2);
		nBerline = new JTextField(2);
		nVan = new JTextField(2);
		nCustomers = new JTextField(2);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(SCars);
		inputs.add(nSCars);
		inputs.add(Berline);
		inputs.add(nBerline);
		inputs.add(Van);
		inputs.add(nVan);
		inputs.add(Customers);
		inputs.add(nCustomers);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton okay = new JButton("OK");
		okay.addActionListener(new SetupListener(this));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(okay);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}


	public JTextField getnSCars() {
		return nSCars;
	}

	public void setnSCars(JTextField nSCars) {
		this.nSCars = nSCars;
	}

	public JTextField getnBerline() {
		return nBerline;
	}

	public void setnBerline(JTextField nBerline) {
		this.nBerline = nBerline;
	}

	public JTextField getnVan() {
		return nVan;
	}

	public void setnVan(JTextField nVan) {
		this.nVan = nVan;
	}

	public JTextField getnCustomers() {
		return nCustomers;
	}

	public void setnCustomers(JTextField nCustomers) {
		this.nCustomers = nCustomers;
	}
	
	
}
