package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AddCarDriverNoNameListener;
import gui.eventlisteners.AddCarDriverWithNamesListener;
import gui.eventlisteners.CloseDialogListener;

/**
 * Dialog box that pops up when the "Add Car with Driver" button is clicked.
 * @author Erwan
 *
 */
public class AddCarDriverDialog extends JDialog {

	private static final long serialVersionUID = -3699441423819771157L;
	
	public AddCarDriverDialog() {
		this.setTitle("Add Car + Driver");
		this.setSize(800, 150);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel cartype = new JLabel("Type of Car :");
		JLabel name = new JLabel("Name of the Driver :");
		JLabel surname = new JLabel("Surname of the Driver :");
		
		String[] cartypes = {"Standard Car", "Berline", "Van"};
		JComboBox<Object> cartypechoices = new JComboBox<Object>(cartypes);
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
		JButton random = new JButton("Add Car of given type and Driver with automatically generated name and surname");
		random.addActionListener(new AddCarDriverNoNameListener(this, cartypechoices));
		JButton okay = new JButton("OK");
		okay.addActionListener(new AddCarDriverWithNamesListener(this, namefield, surnamefield, cartypechoices));

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(random);
		buttons.add(okay);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);
	}
	
	
}
