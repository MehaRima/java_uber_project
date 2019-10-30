package gui.mainview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.eventlisteners.ClickOnCustomerListener;
import gui.eventlisteners.OpenMoveCarOrCustDialogListener;
import helpers.DriverStatus;
import helpers.Environment;
import mainClasses.Berline;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.StandardCar;

/**
 * Represents the map of the simulated universe, and displays the Customers and Drivers at their actual location.
 * @author Erwan
 *
 */
public class UberMap extends JPanel {

	private static final long serialVersionUID = -8012698191576951108L;
	
	private Image mapBg;
	
	private Image custIcon;
	
	private Image scarIcon;
	
	private Image berlineIcon;
	
	private Image vanIcon;
	
	private int xpos;
	
	private int ypos;
	
	public UberMap() throws IOException {
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2.5), (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2));
		this.setLayout(null);
		Image unsized = ImageIO.read(new File("src/resources/paris_map_alpha.png"));
		this.mapBg = unsized.getScaledInstance((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2.6), (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2.6), Image.SCALE_SMOOTH);
		this.custIcon = ImageIO.read(new File("src/resources/icons8_Walking_48px.png"));
		this.scarIcon = ImageIO.read(new File("src/resources/icons8_Car_48px.png"));
		this.berlineIcon = ImageIO.read(new File("src/resources/icons8_Sedan_48px.png"));
		this.vanIcon = ImageIO.read(new File("src/resources/icons8_Shuttle_48px.png"));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		xpos = (int) (0.5*(this.getWidth() - mapBg.getWidth(this) - custIcon.getWidth(this)));
		ypos = (int) (mapBg.getHeight(this) - 0.5*custIcon.getHeight(this));
	    super.paintComponent(g);

	    // Draw the background image.
	    g.drawImage(mapBg, (this.getWidth() - mapBg.getWidth(this))/2, 0, this);
	    
	    // Draw the Customer's icons
    	
    	
	    for (Customer c : Environment.getInstance().getCustomersList()) {
	    	g.drawImage(custIcon, xpos + this.getIconPosition(c)[0], ypos - this.getIconPosition(c)[1], this);
	    }
	    
	    for (Driver d : Environment.getInstance().getDriversList()) {
	    	if (d.getStatus() != DriverStatus.offline) {
	    		if (d.getCar().getClass() == StandardCar.class) {
	    			g.drawImage(scarIcon, xpos + this.getIconPosition(d)[0], ypos - this.getIconPosition(d)[1], this);
	    		} else if (d.getCar().getClass() == Berline.class) {
	    			g.drawImage(berlineIcon, xpos + this.getIconPosition(d)[0], ypos - this.getIconPosition(d)[1], this);
	    		} else {
	    			g.drawImage(vanIcon, xpos + this.getIconPosition(d)[0], ypos - this.getIconPosition(d)[1], this);
	    		}
	    	}
	    }
    	
	  }
	
	/**
	 * Adds the clickable labels under the Customers/Cars icons on the UberMap.
	 */
	public void addLabels() {
		xpos = (int) (0.5*(this.getPreferredSize().getWidth() - mapBg.getWidth(this)));
		ypos = (int) (mapBg.getHeight(this));
		for (Customer c : Environment.getInstance().getCustomersList()) {
			JLabel label = new JLabel("Customer " + c.getID());
	    	label.setFont(new Font("Arial", Font.BOLD, 12));
	    	Dimension size = label.getPreferredSize();
	    	this.add(label);
	    	label.setBounds(xpos + this.getIconPosition(c)[0] - 10, ypos - this.getIconPosition(c)[1] + 20, (int) size.getWidth(), (int) size.getHeight());
	    	label.addMouseListener(new ClickOnCustomerListener(c));
		}
		for (Driver d : Environment.getInstance().getDriversList()) {
			if (d.getStatus() != DriverStatus.offline) {
				JLabel label = new JLabel("Driver " + d.getID());
		    	label.setFont(new Font("Arial", Font.BOLD, 13));
		    	if (d.getStatus() == DriverStatus.on_duty) {
		    		label.setForeground(new Color(16, 135, 26));
		    	} else if (d.getStatus() == DriverStatus.off_duty) {
		    		label.setForeground(new Color(237, 119, 2));
		    	} else {
		    		label.setForeground(new Color(220, 5, 5));
		    	}
		    	Dimension size = label.getPreferredSize();
		    	this.add(label);
		    	label.setBounds(xpos + this.getIconPosition(d)[0] - 2, ypos - this.getIconPosition(d)[1] + 15, (int) size.getWidth(), (int) size.getHeight());
		    	label.addMouseListener(new OpenMoveCarOrCustDialogListener(d.getCar()));
			}
		}
	}
	
	/**
	 * Gives the relative position on the image map of the icon representing a Customer or Driver.
	 * @param o The Driver or Customer you want to represent.
	 * @return Array containing 2 ints : x position and y position
	 */
	public int[] getIconPosition(Object o) {
		int[] pos = new int[2];
		double x = 0;
		double y = 0;
		if (o instanceof Customer) {
			Customer c = (Customer) o;
			x = c.getLocation().getX();
			y = c.getLocation().getY();
			
		} else if (o instanceof Driver) {
			Driver d = (Driver) o;
			x = d.getCar().getLocation().getX();
			y = d.getCar().getLocation().getY();
		} else {
			System.out.println("Trying to position icon for invalid type of object.");
		}
		x = (x+50)/100; //normalizing x
		y = (y+50)/100; //normalizing y
		x = mapBg.getWidth(this)*x;
		y = mapBg.getHeight(this)*y;
		pos[0] = (int) x;
		pos[1] = (int) y;
		return pos;
	}
	
}
