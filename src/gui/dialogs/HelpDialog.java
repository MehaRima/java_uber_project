package gui.dialogs;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Dialog that pops up when the "Help" button of the menu bar is clicked.
 * @author Erwan
 *
 */
public class HelpDialog extends JDialog {
	
	private static final long serialVersionUID = -5879181293585394309L;

	public HelpDialog() {
		this.setTitle("Help");
		this.setSize(1200, 840);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder(5, 10, 10, 10));
		
		JTextArea creation = new JTextArea();
		creation.setEditable(false);
		creation.setOpaque(false);
		creation.setLineWrap(true);
		creation.setWrapStyleWord(true);
		String creationtitle = "Objects Creation";
    	Border creationborder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), creationtitle);
    	creation.setBorder(creationborder);
    	String creationmsg = "You have several options to create objects (Customers, Drivers, Cars...) in our myUber application :"
    			+ "\n\n - The 'Setup' button of the menu bar allows you to quickly create a starting configuration, containing the desired number of each type of Cars, with a Driver assigned to each, and the desired number of Customers, all randomly located."
    			+ "\n\nThe 'Add Objects' submenu of the menu bar allows you to :"
    			+ "\n\n - Create a new Customer through the 'Add Customer' button (possibly with automatically generated name and surname)."
    			+ "\n\n - Create a new Car of the chosen type with an assigned Driver through the 'Add Car + Driver' button (possibly with automatically generated name and surname)."
    			+ "\n\n - Create a new Driver, assigned to the desired already-existing Car through the 'Add Driver' button (possibly with automatically generated name and surname).";
    	creation.setText(creationmsg);
    	
    	JTextArea actions = new JTextArea();
		actions.setEditable(false);
		actions.setOpaque(false);
		actions.setLineWrap(true);
		actions.setWrapStyleWord(true);
		String actionstitle = "Actions";
    	Border actionsborder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), actionstitle);
    	actions.setBorder(actionsborder);
    	String actionsmsg = "There are several actions that you can perform on the objects that you created :\n"
    			+ "\n\n - To move a Customer, click on their label on the Map, and then click the 'Move Customer' button : you can now enter the desired new Coordinates in the [-50, 50] range."
    			+ "\n\n - To move a Car, click on its label (indicating its current Driver's ID) on the Map : you can now enter the desired new Coordinates in the [-50, 50] range."
    			+ "\n\n - To change the status of a Driver, click on their row in the Drivers List, and then choose the new status from the dropdown list."
    			+ "\n\n - To ask for Ride prices for a certain Customer, click on the Customer's label on the Map, and then click the 'Ask For Prices' button : you will be able to enter the destination and time at which you want the estimation to occur. The results will then be displayed in the interface's Console."
    			+ "\n\n - To simulate a Ride for a certain Customer, click on the Customer's label on the Map, and then click the 'Simulate Ride' button : you will be able to enter the destination, time and car type for the desired Ride. Afterwards, as you manually pass time, you will gradually see the steps of the Ride being completed, through the changes on the Map and the Console logs."
    			+ "\n\n - To attribute a mark to a Driver for a certain completed Ride, click on the Ride's row in the completed Rides List and enter an integer between 1 and 5. Beware ! You can only attribute a mark once for each Ride.";
    	actions.setText(actionsmsg);
    	
    	JTextArea env = new JTextArea();
		env.setEditable(false);
		env.setOpaque(false);
		env.setLineWrap(true);
		env.setWrapStyleWord(true);
		String statstitle = "Environment and statistics";
    	Border statsborder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), statstitle);
    	env.setBorder(statsborder);
    	String statsmsg = "The interface displays a lot of information about the ongoing myUber Environment :\n"
    			+ "\n\n - Time management : the Clock is displayed in the top-left corner of the screen. You can pass time 15mn at once using the '+15mn' button at the left of the Clock, or manually enter a number of minutes in the dedicated text field at the right of the Clock and then press the 'Go !' button. You cannot go back in time !"
    			+ "\n\n - Map and icons : the map displays all current online Drivers and Customers at their actual location, and some actions can be triggered by clicking on their labels (see the 'Actions' section). Pink cars represent Standard Cars, grey-blue ones are Berlines, and the yellow vans are... well, Vans. When a Driver's name is in green, it means he's on_duty ; orange means off_duty ; red means on_a_ride."
    			+ "\n\n - Console : in the top-right corner of the screen, the Console displays useful information as events occur in the myUber universe (typically, logs concerning the completion of Rides)."
    			+ "\n\n - Lists : in the bottom-right corner, 4 lists represent all the Customers, Drivers, Cars and completed Rides in the ongoing universe. You can sort these tables ((they will remain sorted until the next refresh) by choosing a sorting policy from the dropdown list just above them (by default, all lists are sorted by ID).";
    	env.setText(statsmsg);
    	
    	this.getContentPane().add(creation);
		this.getContentPane().add(actions);
		this.getContentPane().add(env);
		this.setVisible(true);
	}

}
