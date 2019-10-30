package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import cli.TimeCommands;
import gui.mainview.ClockPanel;
import gui.dialogs.ErrorDialog;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that advances time by the given amount when the corresponding field/button right to the Clock is filled/pressed.
 * @author Erwan
 *
 */
public class AdvanceInputTimeListener implements ActionListener {

	private JTextField textfield;
	
	public AdvanceInputTimeListener(JTextField textfield) {
		this.textfield = textfield;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String textfieldcontent = this.textfield.getText();
		try {
			int nbOfMinutes = Integer.parseInt(textfieldcontent);
			if (nbOfMinutes < 0) {
				throw new NumberFormatException();
			}
			TimeCommands.passTimeCommand(nbOfMinutes);
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
		} catch (NumberFormatException exc) {
			new ErrorDialog("Input Error : you can only advance time by a positive integer.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
