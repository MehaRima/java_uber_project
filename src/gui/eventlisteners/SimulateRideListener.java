package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import cli.SimRideCommand;
import exceptions.CarAlreadyUsedException;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PastTimeException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import gui.dialogs.ErrorDialog;
import gui.mainview.ClockPanel;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;
import mainClasses.Customer;

/**
 * Listener that simulates a Ride with the given parameters when the "Simulate Ride" button of a SimRideDialog is clicked.
 * @author Erwan
 *
 */
public class SimulateRideListener implements ActionListener {
	
	private Customer c;
	
	private JTextField destination;
	
	private JTextField time;
	
	private JComboBox<String> ridetypeselector;
	
	private JDialog dialog;

	public SimulateRideListener(Customer c, JTextField destination, JTextField time, JComboBox<String> ridetypeselector, JDialog dialog) {
		this.c = c;
		this.destination = destination;
		this.time = time;
		this.ridetypeselector = ridetypeselector;
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			SimRideCommand.simRideWithInfo("" + this.c.getID(), this.destination.getText(), this.time.getText(), "" + (this.ridetypeselector.getSelectedIndex()+1), "0");
			
			ClockPanel updatedClockPanel = new ClockPanel();
			ListsPanel updatedListsPanel = new ListsPanel();
			UberMap updatedUberMap = new UberMap();
			updatedUberMap.addLabels();
			
			
			GUImain.getMainframe().getRighthalf().remove(GUImain.getMainframe().getListspanel());
			GUImain.getMainframe().getLefthalf().remove(GUImain.getMainframe().getUbermap());
			GUImain.getMainframe().getLefthalf().remove(GUImain.getMainframe().getClockpanel());
			
			GUImain.getMainframe().getRighthalf().add(updatedListsPanel);
			GUImain.getMainframe().getLefthalf().add(updatedClockPanel);
			GUImain.getMainframe().getLefthalf().add(updatedUberMap);
			
			GUImain.getMainframe().setListspanel(updatedListsPanel);
			GUImain.getMainframe().setClockpanel(updatedClockPanel);
			GUImain.getMainframe().setUbermap(updatedUberMap);
			
			GUImain.getMainframe().getRighthalf().revalidate();
			GUImain.getMainframe().getLefthalf().revalidate();
			GUImain.getMainframe().getRighthalf().repaint();
			GUImain.getMainframe().getLefthalf().repaint();
			
			this.dialog.dispose();
		} catch (NumberFormatException e) {
			new ErrorDialog(e.getMessage());
		} catch (CustomerIDNotFoundException e) {
			e.printStackTrace();
		} catch (RideRequestException e) {
			e.printStackTrace();
		} catch (PoolRequestException e) {
			new ErrorDialog(e.getMessage());
		} catch (NoMorePotentialsDriverException e) {
			new ErrorDialog(e.getMessage());
		} catch (InvalidLocationException e) {
			new ErrorDialog(e.getMessage());
		} catch (InvalidDateException e) {
			e.printStackTrace();
		} catch (PastTimeException e) {
			new ErrorDialog(e.getMessage());
		} catch (CarAlreadyUsedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoAskedForPoolRequestException e) {
			e.printStackTrace();
		}
	}

}
