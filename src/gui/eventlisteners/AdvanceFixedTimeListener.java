package gui.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cli.TimeCommands;
import gui.mainview.ClockPanel;
import gui.mainview.GUImain;
import gui.mainview.ListsPanel;
import gui.mainview.UberMap;

/**
 * Action Listener that advances time by a fixed amount when the corresponding button left to the Clock is pressed.
 * @author Erwan
 *
 */
public class AdvanceFixedTimeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			TimeCommands.passTimeCommand(15);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
