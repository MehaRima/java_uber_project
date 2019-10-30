package gui.eventlisteners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.dialogs.SetDriverStatusDialog;

/**
 * Listener that opens a "SetDriverStatusDialog" when a row in the Drivers list table is selected.
 * @author Erwan
 *
 */
public class DriversListSelectionListener implements ListSelectionListener {

	private JTable table;
	
	public DriversListSelectionListener(JTable table) {
		this.table = table;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting()) {
			String driverID = table.getValueAt(table.getSelectedRow(), 0).toString(); // gets the ID of the driver at the given row
			new SetDriverStatusDialog(driverID);
		}
	}

}
