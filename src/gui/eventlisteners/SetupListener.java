package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cli.SetupCommand;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import gui.dialogs.CustomSetupDialog;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that calls the "Setup" command after the "OK" button of dialog "Custom Setup" has been pressed, given that the "Number of ... cars" fields have been filled correctly.
 * @author Erwan
 *
 */
public class SetupListener implements ActionListener {

	private CustomSetupDialog dialog;
	
	public SetupListener(CustomSetupDialog customSetupDialog) {
		this.dialog = customSetupDialog;
	}
	
	/**
	 * On action performed, calls the "SetupCommand" and refreshes the GUI accordingly.
	 */
	public void actionPerformed(ActionEvent e) {
		String nSCars = dialog.getnSCars().getText();
		if (nSCars.contentEquals("")) {
			nSCars = "0";
		}
		String nBerline = dialog.getnBerline().getText();
		if (nBerline.contentEquals("")) {
			nBerline = "0";
		}
		String nVan = dialog.getnVan().getText();
		if (nVan.contentEquals("")) {
			nVan = "0";
		}
		String nCustomers = dialog.getnCustomers().getText();
		if (nCustomers.contentEquals("")) {
			nCustomers = "0";
		}
		try {
			SetupCommand.setup(nSCars, nBerline, nVan, nCustomers);
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
			
		} catch (NumberFormatException exc) {
			new ErrorDialog("There was a problem with your input : make sure to enter only positive integers.");
		} catch (InvalidLocationException exc) {
			exc.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CarAlreadyUsedException e1) {
			e1.printStackTrace();
		} catch (InvalidDateException e1) {
			e1.printStackTrace();
		}
	}

}
