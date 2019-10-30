package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JTextField;

import cli.MoveCarCommand;
import cli.MoveCustomerCommand;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;
import mainClasses.Car;
import mainClasses.Customer;

/**
 * Listener that is called when the user moves a Car or Customer through the MoveCarDialog.
 * @author Erwan
 *
 */
public class MoveCarOrCustomerListener implements ActionListener {
	
	private JDialog dialog;
	
	private Object o;
	
	private JTextField newXpos;
	
	private JTextField newYpos;
	
	public MoveCarOrCustomerListener(JDialog dialog, Object o, JTextField newXpos, JTextField newYpos) {
		this.dialog = dialog;
		this.o = o;
		this.newXpos = newXpos;
		this.newYpos = newYpos;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (o instanceof Car) {
			Car car = (Car) o;
			try {
				MoveCarCommand.moveCarCommand(car.getID(), this.newXpos.getText(), this.newYpos.getText());
				
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
			} catch (NumberFormatException e) {
				new ErrorDialog("Input Error : please enter Double values between -50 and 50.");
			} catch (InvalidLocationException e) {
				new ErrorDialog("Input Error : please enter Double values between -50 and 50.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Customer) {
			Customer cust = (Customer) o;
			String custID = "" + cust.getID();
			try {
				MoveCustomerCommand.moveCustomerCommand(custID, this.newXpos.getText(), this.newYpos.getText());
				
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
			} catch (NumberFormatException e) {
				new ErrorDialog("Input Error : please enter Double values between -50 and 50.");
			} catch (InvalidLocationException e) {
				new ErrorDialog("Input Error : please enter Double values between -50 and 50.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CustomerIDNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
