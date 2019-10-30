package gui.eventlisteners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.mainview.MarkRideDialog;
import helpers.Environment;
import mainClasses.Ride;

/**
 * Listener that opens an "MarkRideDialog" when a row in the Rides list table is selected.
 * @author Erwan
 *
 */
public class RidesListSelectionListener implements ListSelectionListener {

private JTable table;
	
	public RidesListSelectionListener(JTable table) {
		this.table = table;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting()) {
			String rideID = table.getValueAt(table.getSelectedRow(), 0).toString(); // gets the ID of the Ride at the given row
			Ride selectedRide = null;
			for (Ride r : Environment.getInstance().getBookOfRides()) {
				if (r.getID() == Integer.parseInt(rideID)) {
					selectedRide = r;
				}
			}
			if (selectedRide != null) {
				new MarkRideDialog(selectedRide);
			} else {
				System.out.println("Error : selected Ride is not present in the Book Of Rides.");
			}
		}
	}

}
