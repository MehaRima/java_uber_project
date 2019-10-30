package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.dialogs.MoveCarOrCustomerDialog;

/**
 * Mouse listener that opens a dialog window when a Car/Customer is clicked on the map.
 * @author Erwan
 *
 */
public class OpenMoveCarOrCustDialogListener implements MouseListener, ActionListener {
	
	private Object o;
	
	public OpenMoveCarOrCustDialogListener(Object o) {
		this.o = o;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new MoveCarOrCustomerDialog(this.o);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		new MoveCarOrCustomerDialog(this.o);
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
