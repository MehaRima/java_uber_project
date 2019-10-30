package gui.mainview;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import exceptions.InvalidLocationException;

/**
 * Run this file to display the graphical user interface.
 * @author Erwan
 *
 */
public class GUImain {
	
	private static Font defaultFont = new Font("Arial", Font.BOLD, 15);

	private static MainFrame mainframe;
	
	public static void main(String[] args) throws IOException, InterruptedException, InvalidLocationException {
		
		MainFrame frame = new MainFrame("myUber_GUI");
		ImageIcon icon = new ImageIcon("src/resources/icons8_Car_50px_border.png");
		frame.setIconImage(icon.getImage());
		GUImain.mainframe = frame;
		
		frame.setJMenuBar(new TopMenuBar());
		CustomContentPane pane = new CustomContentPane();
		frame.setContentPane(pane);
		
		JPanel lefthalf = new JPanel();
		frame.setLefthalf(lefthalf);
		lefthalf.setLayout(new BoxLayout(lefthalf, BoxLayout.PAGE_AXIS));
		lefthalf.setOpaque(false);
		
		lefthalf.add(Box.createRigidArea(new Dimension(0, 60)));
		frame.setClockpanel(new ClockPanel());
		UberMap map = new UberMap();
		frame.setUbermap(map);
		map.addLabels();
		lefthalf.add(frame.getClockpanel());
		lefthalf.add(frame.getUbermap());
		
		JPanel righthalf = new JPanel();
		frame.setRighthalf(righthalf);
		righthalf.setLayout(new BoxLayout(righthalf, BoxLayout.PAGE_AXIS));
		righthalf.setOpaque(false);
		
		frame.setConsole(new Console());
		frame.setListspanel(new ListsPanel());
		righthalf.add(frame.getConsole());
		righthalf.add(frame.getListspanel());

		pane.add(lefthalf);
		pane.add(righthalf);
		
		frame.setVisible(true);
	}

	//getters and setters 
	
	public static Font getDefaultFont() {
		return defaultFont;
	}

	public static void setDefaultFont(Font defaultFont) {
		GUImain.defaultFont = defaultFont;
	}

	public static MainFrame getMainframe() {
		return mainframe;
	}

	public static void setMainframe(MainFrame mainframe) {
		GUImain.mainframe = mainframe;
	}

	
	
}
