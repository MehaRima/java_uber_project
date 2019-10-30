package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Listener that sorts the Customers and Drivers list with the desired sort policy on click of an item of the JComboBoxes in the ListsPanel.
 * @author Erwan
 *
 */
public class SortListsListener implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			String custSortPolicy = GUImain.getMainframe().getListspanel().getCustList().getSortbox().getSelectedItem().toString();
			String drivSortPolicy = GUImain.getMainframe().getListspanel().getDrivList().getSortbox().getSelectedItem().toString();
			
			ListsPanel updatedListsPanel = new ListsPanel(custSortPolicy, drivSortPolicy);
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
