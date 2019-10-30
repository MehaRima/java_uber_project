package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;

import cli.Ask4PriceCommand;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import gui.dialogs.ErrorDialog;
import mainClasses.Customer;

/**
 * Listener that returns a ride fare estimation in the Console when the "Estimate fares" button of AskForPriceDialog is pressed.
 * @author Erwan
 *
 */
public class AskForPriceListener implements ActionListener {

	private Customer c;
	
	private JTextField destination;
	
	private JTextField bookinghour;
	
	private JDialog dialog;
	
	public AskForPriceListener(Customer c, JTextField destination, JTextField bookinghour, JDialog dialog) {
		this.c = c;
		this.destination = destination;
		this.bookinghour = bookinghour;
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			System.out.println(Ask4PriceCommand.ask4PricesCommand("" + c.getID(), destination.getText(), bookinghour.getText()));
			dialog.dispose();
		} catch (NumberFormatException e1) {
			new ErrorDialog("Input Error : make sure the destination is formatted as 'x:y', and that the hour is an integer between 0 and 23.");
		} catch (InvalidLocationException e1) {
			new ErrorDialog("Input Error : make sure the destination coordinates are within the [-50, 50] range.");
		} catch (CustomerIDNotFoundException e1) {
			e1.printStackTrace();
		}
	}

}
