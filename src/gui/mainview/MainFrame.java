package gui.mainview;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main frame that will contain our graphical interface.
 * @author Erwan
 *
 */
public class MainFrame extends JFrame {

	private JPanel lefthalf;
	
	private JPanel righthalf;
	
	private ListsPanel listspanel;
	
	private ClockPanel clockpanel;
	
	private Console console;
	
	private UberMap ubermap;
	
	private static final long serialVersionUID = 3029088012234747342L;

	
	/**
	 * Creates a main frame of dimensions 1920*1080.
	 * @param title
	 */
	public MainFrame(String title) {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
	}

	//getters and setters
	

	public JPanel getLefthalf() {
		return lefthalf;
	}

	public void setLefthalf(JPanel lefthalf) {
		this.lefthalf = lefthalf;
	}

	public JPanel getRighthalf() {
		return righthalf;
	}

	public void setRighthalf(JPanel righthalf) {
		this.righthalf = righthalf;
	}

	public ListsPanel getListspanel() {
		return listspanel;
	}

	public void setListspanel(ListsPanel listspanel) {
		this.listspanel = listspanel;
	}

	public ClockPanel getClockpanel() {
		return clockpanel;
	}

	public void setClockpanel(ClockPanel clockpanel) {
		this.clockpanel = clockpanel;
	}

	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

	public UberMap getUbermap() {
		return ubermap;
	}

	public void setUbermap(UberMap ubermap) {
		this.ubermap = ubermap;
	}
	
}
