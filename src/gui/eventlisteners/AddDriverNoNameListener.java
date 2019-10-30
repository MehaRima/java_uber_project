package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import cli.AddDriverCommand;
import exceptions.NoExistingIDCarException;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that calls the "AddDriverNoName" command when the user presses the corresponding button in the "Add Driver" dialog.
 * @author Erwan
 *
 */
public class AddDriverNoNameListener implements ActionListener {

	private JDialog dialog;
	
	private JComboBox<Object> carIDselector;

	public AddDriverNoNameListener(JDialog dialog, JComboBox<Object> carIDselector) {
		this.dialog = dialog;
		this.carIDselector = carIDselector;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String carID = this.carIDselector.getSelectedItem().toString();
		try {
			AddDriverCommand.addDriverNoName(carID);

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
		} catch (NoExistingIDCarException e) {
			e.printStackTrace();
		}
	}

}

