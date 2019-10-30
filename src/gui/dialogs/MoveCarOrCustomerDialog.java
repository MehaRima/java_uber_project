package gui.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.CloseDialogListener;
import gui.eventlisteners.MoveCarOrCustomerListener;

/**
 * Dialog box that pops up when a Car/Customer icon is clicked.
 * @author Erwan
 *
 */
public class MoveCarOrCustomerDialog extends JDialog {
	
	private static final long serialVersionUID = -321290040638435395L;

	private JTextField newXpos;
	
	private JTextField newYpos;
	
	private Object o;
	
	public MoveCarOrCustomerDialog(Object o) {
		this.o = o;
		this.setTitle("Move");
		this.setSize(400, 130);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel xposlabel = new JLabel("New X position :");
		JLabel yposlabel = new JLabel("New Y position :");
		
		newXpos = new JTextField(2);
		newYpos = new JTextField(2);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputs.add(xposlabel);
		inputs.add(newXpos);
		inputs.add(yposlabel);
		inputs.add(newYpos);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CloseDialogListener(this));
		JButton move = new JButton("Move to new Coordinates");
		move.addActionListener(new MoveCarOrCustomerListener(this, this.o, newXpos, newYpos));
		
		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(move);
		
		this.getContentPane().add(inputs);
		this.getContentPane().add(buttons);
		this.setVisible(true);

	}
}
