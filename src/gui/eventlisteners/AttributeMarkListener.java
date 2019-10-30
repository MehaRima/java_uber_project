package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import exceptions.MarkAttributionException;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;
import mainClasses.Ride;

/**
 * Listener that attributes the input mark to the selected Ride and associated Driver on click of the "Attribute Mark" button of a MarkRideDialog.
 * @author Erwan
 *
 */
public class AttributeMarkListener implements ActionListener {

	private Ride r;
	
	private JTextField mark;
	
	public AttributeMarkListener(Ride r, JTextField mark) {
		this.r = r;
		this.mark = mark;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int given_mark = Integer.parseInt(this.mark.getText());
			r.getAssociatedCustomer().markRide(r, given_mark);
			
			ListsPanel updatedListsPanel = new ListsPanel();
			UberMap updatedUberMap = new UberMap();
			updatedUberMap.addLabels();

			GUImain.getMainframe().getRighthalf().remove(GUImain.getMainframe().getListspanel());
			GUImain.getMainframe().getLefthalf().remove(GUImain.getMainframe().getUbermap());

			GUImain.getMainframe().getRighthalf().add(updatedListsPanel);
			GUImain.getMainframe().getLefthalf().add(updatedUberMap);

			GUImain.getMainframe().setListspanel(updatedListsPanel);
			GUImain.getMainframe().setUbermap(updatedUberMap);

			GUImain.getMainframe().getRighthalf().revalidate();
			GUImain.getMainframe().getLefthalf().revalidate();
			GUImain.getMainframe().getRighthalf().repaint();
			GUImain.getMainframe().getLefthalf().repaint();
			
		} catch (MarkAttributionException e1) {
			new ErrorDialog(e1.getMessage());
		} catch (NumberFormatException e1) {
			new ErrorDialog("Input Error : make sure your mark is an integer between 1 and 5.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
