package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;

import cli.AddCustomerCommand;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that calls the "AddCustomerNoName" command when the user presses the corresponding button in the "Add Customer" dialog.
 * @author Erwan
 *
 */
public class AddCustomerNoNameListener implements ActionListener {

	private JDialog dialog;

	public AddCustomerNoNameListener(JDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			AddCustomerCommand.addCustomerNoName();

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
			dialog.dispose();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

