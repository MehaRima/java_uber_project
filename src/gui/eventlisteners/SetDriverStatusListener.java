package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import cli.SetDriverStatusCommand;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Listener that changes the Driver status according to the selected value in SetDriverStatusDialog.
 * @author Erwan
 *
 */
public class SetDriverStatusListener implements ActionListener {

	private String driverID;
	
	private JComboBox<Object> statusselector;
	
	private JDialog dialog;
	
	public SetDriverStatusListener(String driverID, JComboBox<Object> statusselector, JDialog dialog) {
		this.driverID = driverID;
		this.statusselector = statusselector;
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			SetDriverStatusCommand.setDriverStatusWithID(driverID, this.statusselector.getSelectedItem().toString());
			
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
			
		} catch (CarAlreadyUsedException e) {
			new ErrorDialog("This Driver's Car is already in use !");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidDateException e) {
			e.printStackTrace();
		}
		
	}

}
