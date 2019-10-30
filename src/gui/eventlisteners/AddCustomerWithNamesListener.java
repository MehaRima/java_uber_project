package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JTextField;

import cli.AddCustomerCommand;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that calls the "AddCustomerwithName" command when the user presses "OK" in the "Add Customer" dialog, given he has filled the corresponding fields.
 * @author Erwan
 *
 */
public class AddCustomerWithNamesListener implements ActionListener {

	private JDialog dialog;

	private JTextField namefield;

	private JTextField surnamefield;

	public AddCustomerWithNamesListener(JDialog dialog, JTextField namefield, JTextField surnamefield) {
		this.dialog = dialog;
		this.namefield = namefield;
		this.surnamefield = surnamefield;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = this.namefield.getText();
		String surname = this.surnamefield.getText();
		if (name.length() == 0 || surname.length() == 0 ) {
			new ErrorDialog("You must give a Name and a Surname for the Customer, or use the 'automatically generate name and surname' button.");
		} else {
			try {
				AddCustomerCommand.addCustomerWithName(name, surname);

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
