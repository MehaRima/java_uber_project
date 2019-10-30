package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import cli.AddCarDriverCommand;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Adds a Car of the given type and Driver with automatically generated name and surname on press of the corresponding button.
 * @author Erwan
 *
 */
public class AddCarDriverNoNameListener implements ActionListener {
	
	private JDialog dialog;
	
	private JComboBox<Object> cartypeselector;

	public AddCarDriverNoNameListener(JDialog dialog, JComboBox<Object> cartypeselector) {
		this.dialog = dialog;
		this.cartypeselector = cartypeselector;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String carID = this.cartypeselector.getSelectedItem().toString();
		try {
			carID = carID.split(" ")[0].toLowerCase();
			AddCarDriverCommand.addCarDriverNoName(carID);

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
