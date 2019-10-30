package gui.eventlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.dialogs.CustomerActionDialog;
import mainClasses.Customer;

/**
 * Listener that calls a CustomerActionDialog when the label of a Customer is clicked.
 * @author Erwan
 *
 */
public class ClickOnCustomerListener implements MouseListener {

	private Customer c;
	
	public ClickOnCustomerListener(Customer c) {
		this.c = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		new CustomerActionDialog(this.c);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
