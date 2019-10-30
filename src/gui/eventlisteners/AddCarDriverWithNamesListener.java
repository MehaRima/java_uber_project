package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import cli.AddCarDriverCommand;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Adds a Car of the given type and Driver with the given name and surname on press of the "OK" button.
 * @author Erwan
 *
 */
public class AddCarDriverWithNamesListener implements ActionListener {
	
	private JDialog dialog;

	private JTextField namefield;

	private JTextField surnamefield;
	
	private JComboBox<Object> cartypeselector;

	public AddCarDriverWithNamesListener(JDialog dialog, JTextField namefield,  JTextField surnamefield, JComboBox<Object> cartypeselector) {
		this.dialog = dialog;
		this.namefield = namefield;
		this.surnamefield = surnamefield;
		this.cartypeselector = cartypeselector;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = this.namefield.getText();
		String surname = this.surnamefield.getText();
		String carID = this.cartypeselector.getSelectedItem().toString();
		if (name.length() == 0 || surname.length() == 0 ) {
			new ErrorDialog("You must give a Name and a Surname for the Driver, or use the 'automatically generate name and surname' button.");
		} else {
			try {
				carID = carID.split(" ")[0].toLowerCase();
				AddCarDriverCommand.addCarDriverWithName(name, surname, carID);

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
}
