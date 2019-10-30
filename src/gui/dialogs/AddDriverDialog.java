package gui.dialogs;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AddDriverNoNameListener;
import gui.eventlisteners.AddDriverWithNamesListener;
import gui.eventlisteners.CloseDialogListener;
import helpers.Environment;
import mainClasses.Car;

/**
 * Run this file through a terminal to start the Command Line Interface
 * @author Erwan
 *
 */
public class AddDriverDialog extends JDialog {

	private static final long serialVersionUID = -6981034123286169292L;
	
	
	public AddDriverDialog() {
		this.setTitle("Add Driver");
		this.setSize(850, 150);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel cartype = new JLabel("Car to Associate to :");
		JLabel name = new JLabel("Name of the Driver :");
		JLabel surname = new JLabel("Surname of the Driver :");
		
		ArrayList<String> carnames = new ArrayList<String>();
		for (Car c : Environment.getInstance().getCarsList()) {carnames.add(c.getID());}		
		JComboBox<Object> cartypechoices = new JComboBox<Object>(carnames.toArray());
		JTextField namefield = new JTextField(13);
		JTextField surnamefield = new JTextField(13);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(cartype);
		inputs.add(cartypechoices);
		inputs.add(name);
		inputs.add(namefield);
		inputs.add(surname);
		inputs.add(surnamefield);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton random = new JButton("Add Driver with automatically generated name and surname");
		random.addActionListener(new AddDriverNoNameListener(this, cartypechoices));
		JButton okay = new JButton("OK");
		okay.addActionListener(new AddDriverWithNamesListener(this, namefield, surnamefield, cartypechoices));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(random);
		buttons.add(okay);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
	
	
}
