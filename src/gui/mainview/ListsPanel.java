package gui.mainview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import gui.eventlisteners.DriversListSelectionListener;
import gui.eventlisteners.RidesListSelectionListener;
import helpers.Environment;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;

/**
 * Contains and displays the lists of Cars, Customers, Drivers and Rides of the ongoing universe.
 * @author Erwan
 */
public class ListsPanel extends JPanel {

	private static final long serialVersionUID = -5442618047063508796L;

	private SortableScrollList custList;
	
	private SortableScrollList drivList;
	
	private SortableScrollList carsList;
	
	private SortableScrollList rideList;
	
	
	/**
	 * Constructor to get sorted Lists (with the given sortPolicy) in the Lists Panel.
	 * @param custSortPolicy
	 * @param drivSortPolicy
	 */
	public ListsPanel(String custSortPolicy, String drivSortPolicy) {
		this.setLayout(new GridLayout(2, 3, 30, 10));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/(5/3)), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(7/4))));

		String[] custColumns = {"ID", "Surname", "Name", "Location", "Ongoing Ride"};
		String custTopLabel = "List of all Customers. Sort by ";
		String[] custSortPolicies = {"ID", "Most Frequent", "Most Charged"};
		this.custList = new SortableScrollList(getCustomersData(custSortPolicy), custColumns, custTopLabel, custSortPolicies);
		this.custList.getTable().getColumnModel().getColumn(0).setMaxWidth(30);
		

		String[] carsColumns = {"Type and ID", "Location", "Current Driver", "Nb of co-owners"};
		String carsTopLabel = "List of all Cars. Sort by ";
		String[] carSortPolicies = {"Type and ID"};
		this.carsList = new SortableScrollList(getCarsData(), carsColumns, carsTopLabel, carSortPolicies);
    	
		
		String[] drivColumns = {"ID", "Surname", "Name", "Status", "Car", "Avg Mark"};
		String drivTopLabel = "List of all Drivers. Sort by ";
		String[] drivSortPolicies = {"ID", "Most Appreciated", "Most Occupied"};
		this.drivList = new SortableScrollList(getDriversData(drivSortPolicy), drivColumns, drivTopLabel, drivSortPolicies);
		this.drivList.getTable().getColumnModel().getColumn(0).setMaxWidth(30);
		this.drivList.getTable().getSelectionModel().addListSelectionListener(new DriversListSelectionListener(drivList.getTable()));
    	
    	
		String[] rideColumns = {"ID", "Customer", "Driver", "Price", "Ending Time", "Mark"};
		String rideTopLabel = "List of all completed Rides. Sort by ";
		String[] rideSortPolicies = {"ID"};
		this.rideList = new SortableScrollList(getRidesData(), rideColumns, rideTopLabel, rideSortPolicies);
		this.rideList.getTable().getColumnModel().getColumn(0).setMaxWidth(30);
		this.rideList.getTable().getColumnModel().getColumn(5).setMaxWidth(60);
		this.rideList.getTable().getSelectionModel().addListSelectionListener(new RidesListSelectionListener(rideList.getTable()));
		
		this.add(this.custList);
		this.add(this.drivList);
		this.add(this.carsList);
		this.add(this.rideList);

	}
	
	/**
	 * Constructor to get unsorted Lists in the Lists Panel.
	 * @param custSortPolicy
	 * @param drivSortPolicy
	 */
	public ListsPanel() {
		this("ID", "ID");
	}
	
	
	/**
	 * Returns the Customers data in the forme of an array of Object arrays, using the desired sorting policy, so as to display this data in the table.
	 * @return Object[][] containing the sorted Customers data.
	 */
	public static Object[][] getCustomersData(String custSortPolicy) {
		ArrayList<Customer> sortedList = new ArrayList<Customer>();
		if (custSortPolicy.equals("Most Frequent")) {
			sortedList.addAll(Environment.getInstance().mostFreqCustomer().keySet());
		} else if (custSortPolicy.equals("Most Charged")) {
			sortedList.addAll(Environment.getInstance().mostChargedCustomer().keySet());
		} else {
			sortedList = Environment.getInstance().getCustomersList();
		}
		int nbCust = sortedList.size();
		Object[][] data = new Object[nbCust][5];
		for (int i = 0; i < nbCust; i++) {
			Customer c = sortedList.get(i);
			data[i][0] = c.getID();
			data[i][1] = c.getSurname();
			data[i][2] = c.getName();
			data[i][3] = c.getLocation();
			if (c.getOnGoingRide() != null) {
				data[i][4] = "Ride n°" + c.getOnGoingRide().getID();
			} else {
				data[i][4] = "";
			}
		}
		return data;
	}
	
	/**
	 * Returns the Drivers data in the form of an array of Object arrays, using the desired sorting policy, so as to display this data in the table.
	 * @return Object[][] containing the sorted Drivers data.
	 */
	public static Object[][] getDriversData(String drivSortPolicy) {
		ArrayList<Driver> sortedList = new ArrayList<Driver>();
		if (drivSortPolicy.equals("Most Appreciated")) {
			sortedList.addAll(Environment.getInstance().mostAppreciatedDriver().keySet());
		} else if (drivSortPolicy.equals("Most Occupied")) {
			sortedList.addAll(Environment.getInstance().leastOccupiedDriver().keySet());
		} else {
			sortedList = Environment.getInstance().getDriversList();
		}
		int nbDrivers = sortedList.size();
		Object[][] data = new Object[nbDrivers][6];
		for (int i = 0; i < nbDrivers; i++) {
			Driver d = sortedList.get(i);
			data[i][0] = d.getID();
			data[i][1] = d.getSurname();
			data[i][2] = d.getName();
			data[i][3] = d.getStatus().name();
			data[i][4] = d.getCar().getID();
			if (d.getAvgMark() == 0) {
				data[i][5] = "";
			} else {
				data[i][5] = d.getAvgMark();
			}
			
		}
		return data;
	}
	
	/**
	 * Returns the Cars data in the forme of an array of Object arrays, so as to display this data in the table.
	 * @return Object[][] containing the Cars data.
	 */
	public static Object[][] getCarsData() {
		int nbCars = Environment.getInstance().getCarsList().size();
		Object[][] data = new Object[nbCars][4];
		for (int i = 0; i < nbCars; i++) {
			Car c = Environment.getInstance().getCarsList().get(i);
			data[i][0] = c.getID();
			data[i][1] = c.getLocation();
			data[i][2] = c.getCurrentDriver();
			data[i][3] = c.getDriverslist().size();
		}
		return data;
	}
	
	/**
	 * Returns the completed Rides data in the forme of an array of Object arrays, so as to display this data in the table.
	 * @return Object[][] containing the completed Rides data.
	 */
	public static Object[][] getRidesData() {
		int nbRides = Environment.getInstance().getBookOfRides().size();
		Object[][] data = new Object[nbRides][6];
		for (int i = 0; i < nbRides; i++) {
			Ride r = Environment.getInstance().getBookOfRides().get(i);
			data[i][0] = r.getID();
			data[i][1] = r.getAssociatedCustomer();
			data[i][2] = r.getAssociatedDriver();
			data[i][3] = r.getPrice();
			data[i][4] = r.getEndTime();
			data[i][5] = r.getMark();
		}
		return data;
	}

	public SortableScrollList getCustList() {
		return custList;
	}

	public void setCustList(SortableScrollList custList) {
		this.custList = custList;
	}

	public SortableScrollList getDrivList() {
		return drivList;
	}

	public void setDrivList(SortableScrollList drivList) {
		this.drivList = drivList;
	}

	public SortableScrollList getCarsList() {
		return carsList;
	}

	public void setCarsList(SortableScrollList carsList) {
		this.carsList = carsList;
	}

	public SortableScrollList getRideList() {
		return rideList;
	}

	public void setRideList(SortableScrollList rideList) {
		this.rideList = rideList;
	}

	
	
}
