package helpers;

import mainClasses.Customer;
import mainClasses.Ride;

/**
 * A class that represents bookRide in the future. It's object are stored in the Environment listOfFuturBookRide
 * @author Antoine
 *
 */
public class FutureBookRide {
	/**
	 * List of the simulatedRides
	 */
	private Ride[] simulatedRides ;
	
	/**
	 * typeOfRide 1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool
	 */
	private int typeOfRide ;
	
	/**
	 * The time at which the RideBooking must occur 
	 */
	private Date dateOfBegining;
	
	
	/**
	 * The customer that made this 'FutureBookRide'
	 */
	private Customer customer;
	
	/**
	 * The mark of the future Driver
	 */
	private int driverMark;
	
	//Constructor
	
	public FutureBookRide(Ride[] simulatedRides, int typeOfRide, Date dateOfBegining, Customer customer, int mark) {
		this.simulatedRides = simulatedRides ;
		this.typeOfRide = typeOfRide ;
		this.dateOfBegining = dateOfBegining;
		this.customer = customer ;
		this.driverMark = mark ;
		
	}


	public Ride[] getSimulatedRides() {
		return simulatedRides;
	}


	public void setSimulatedRides(Ride[] simulatedRides) {
		this.simulatedRides = simulatedRides;
	}


	public int getTypeOfRide() {
		return typeOfRide;
	}


	public void setTypeOfRide(int typeOfRide) {
		this.typeOfRide = typeOfRide;
	}


	public Date getDateOfBegining() {
		return dateOfBegining;
	}


	public void setDateOfBegining(Date dateOfBegining) {
		this.dateOfBegining = dateOfBegining;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	public int getDriverMark() {
		return driverMark;
	}


	public void setDriverMark(int driverMark) {
		this.driverMark = driverMark;
	}

}
