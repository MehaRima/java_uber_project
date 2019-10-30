package mainClasses;

import helpers.Clock;
import helpers.Coordinates;
import helpers.Date;
import helpers.DriverStatus;
import helpers.Environment;
import helpers.RideStatus;
import helpers.Traffic;

import java.util.ArrayList;


import designPatterns.Observable;
import designPatterns.Observer;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.RideRequestException;

/**
 * Abstract class that represents a Ride, from its simulation and request to its completion.
 * @author Erwan
 *
 */
public abstract class Ride implements Observable {

	/**
	 * A counter that's incremented each time a new Ride is created : it is there to ensure each Ride has a unique ID.
	 */
	protected static int rideIDcounter = 0;
	
	/**
	 * An ID number that is used to represent the Ride : it is designed to be unique for each Ride.
	 */
	protected int ID;
	
	/**
	 * The Customer that simulated or booked this Ride.
	 */
	protected Customer associatedCustomer;
	
	/**
	 * The starting point of the ride (by default, the location of the Customer).
	 */
	protected Coordinates startingPoint;
	
	/**
	 * The destination desired by the Customer.
	 */
	protected Coordinates destination;
	
	/**
	 * The number of people who will board on this Ride.
	 */
	protected int passengersNb;
	
	/**
	 * Represents the current status of the Ride. It can be one of the following : simulated (it has just been used to compute prices but not booked yet), trashed (not selected by the Customer after computing
	 * the simulation), unconfirmed (booked but no Driver has yet accepted the Ride), cancelled (cancelled by the Customer before being confirmed by a Driver), confirmed (a Driver has accepted the Ride and is
	 * on his way to pick the Customer up), ongoing (the Customer is on board), completed (the Ride has successfully taken place).
	 */
	protected RideStatus status;
	
	/**
	 * The state of traffic at the simulation of the Ride.
	 */
	protected Traffic traffic;
	
	/**
	 * The simulated cost of this Ride.
	 */
	protected double price;
	
	/**
	 * The date and time when the Ride was booked by the associated Customer.
	 */
	protected Date bookingTime;
	
	/**
	 * The estimated duration (in minutes) of the Ride.
	 */
	protected int duration;
	
	/**
	 * If the Ride is confirmed, this is the Driver in charge of the Ride.
	 */
	protected Driver associatedDriver;
	
	/**
	 * If the Ride took place, this is the date and time when the Customer was picked up by the Driver.
	 */
	protected Date pickupTime;
	
	/**
	 * If the Ride took place, this is the date and time when the Customer was dropped at their destination ; if the Ride was cancelled, this is the date and time of the cancellation.
	 */
	protected Date endTime;
	
	/**
	 * If the Customer has marked the Driver after completion of the Ride, this is the mark given (integer from 1 to 5).
	 */
	protected int mark;
	
	/**
	 * When a Ride is waiting to be confirmed by a Driver, the list of possible Drivers is stored here, and the unconfirmed Ride becomes an Observable for the Drivers.
	 */
	protected ArrayList<Observer> observerDrivers;
	
	/**
	 * Boolean that switches to true when there is a change in the Ride status that requires the Observers to be notified.
	 */
	protected boolean changed;
	
	/**
	 * Allows a Customer to create a new "simulated" Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 */
	public Ride(Customer associatedCustomer, Coordinates destination, int passengersNb) {
		rideIDcounter += 1;
		this.ID = rideIDcounter;
		this.associatedCustomer = associatedCustomer;
		this.startingPoint = associatedCustomer.getLocation();
		this.bookingTime = Clock.getInstance().getDate();
		this.destination = destination;
		this.passengersNb = passengersNb;
		this.status = RideStatus.simulated;
		this.traffic = this.determineTrafficState();
		this.price = this.computePrice();
		this.duration = this.computeDuration(this.startingPoint, this.destination);
		this.changed = false;
		this.observerDrivers = new ArrayList<Observer>();		
	}
	
	/**
	 * Allows a Customer to create a new "simulated" Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param   hour                The hour of the simulation 
	 */
	public Ride(Customer associatedCustomer, Coordinates destination, int passengersNb, int hour) {
		rideIDcounter += 1;
		this.ID = rideIDcounter;
		this.associatedCustomer = associatedCustomer;
		this.startingPoint = associatedCustomer.getLocation();
		this.bookingTime = Clock.getInstance().getDate();
		this.destination = destination;
		this.passengersNb = passengersNb;
		this.status = RideStatus.simulated;
		this.traffic = this.determineTrafficStateWithHour(hour);
		this.price = this.computePrice();
		this.duration = this.computeDuration(this.startingPoint, this.destination);
		this.changed = false;
		this.observerDrivers = new ArrayList<Observer>();		
	}

	/**
	 * Computes the price of the simulated Ride : the price depends on the type of Ride selected, the length of the ride, the traffic conditions...
	 * @return The price of the simulated Ride.
	 */
	public abstract double computePrice();
	
	/**
	 * Computest the predicted duration of the Ride, depending on the traffic state.
	 * @param startingPoint Coordinates representing the starting point of the drive which duration you want to compute.
	 * @param destination Coordinates representing the destination of the drive which duration you want to compute.
	 * @return An integer representing the duration in minutes.
	 */
	public int computeDuration(Coordinates startingPoint, Coordinates destination) {
		double length = startingPoint.distance(destination);
		double speed;
		if (this.traffic == Traffic.low) {
			speed = 0.25;
		} else if (this.traffic == Traffic.medium) {
			speed = 0.125;
		} else {
			speed = 0.05;
		}
		int duration = (int) (length/speed);
		return duration;
	}
	
	/**
	 * Computes the pickup time of this Ride once it has been accepted by a Driver, using the Driver's current location and the state of traffic through computeDuration() method.
	 * @return Date representing the estimated pickup time of this Ride.
	 * @throws InvalidDateException
	 */
	public Date computePickUpTime() throws InvalidDateException {
		Date currentDate = Clock.getInstance().getDate();
		Date pickupDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay(), currentDate.getHour(), currentDate.getMinute());
		pickupDate.addTime(this.computeDuration(this.getAssociatedDriver().getCar().getLocation(), this.startingPoint));
		return pickupDate;
	}
	
	/**
	 * Computes the end time of this Ride once the Customer has been picked up, by adding the Ride duration to the pickup time.
	 * @return Date representing the estimated end time of this Ride.
	 * @throws InvalidDateException
	 */
	public Date computeEndTime() throws InvalidDateException {
		Date pickupDate = this.pickupTime;
		Date endDate = new Date(pickupDate.getYear(), pickupDate.getMonth(), pickupDate.getDay(), pickupDate.getHour(), pickupDate.getMinute());
		endDate.addTime(this.duration);
		return endDate;
	}


	/**
	 * Returns the current traffic state, based on both the current time of the day and a randomness factor.
	 * @return The state of the traffic (one of Traffic.low, Traffic.medium and Traffic.heavy).
	 */
	public Traffic determineTrafficState() {
		double rand = Math.random();
		if (this.bookingTime.getHour() >= 7 & this.bookingTime.getHour() < 11 ) {
			if (rand < 0.05) {
				return Traffic.low;
			} else if (rand >= 0.05 & rand < 0.25) {
				return Traffic.medium;
			} else {
				return Traffic.heavy;
			}
		}
		else if (this.bookingTime.getHour() >= 11 & this.bookingTime.getHour() < 17 ) {
			if (rand < 0.15) {
				return Traffic.heavy;
			} else if (rand >= 0.15 & rand < 0.30) {
				return Traffic.low;
			} else {
				return Traffic.medium;
			}
		}
		else if (this.bookingTime.getHour() >= 17 & this.bookingTime.getHour() < 22 ) {
			if (rand < 0.01) {
				return Traffic.low;
			} else if (rand >= 0.01 & rand < 0.05) {
				return Traffic.medium;
			} else {
				return Traffic.heavy;
			}
		}
		else {
			if (rand < 0.01) {
				return Traffic.heavy;
			} else if (rand >= 0.01 & rand < 0.05) {
				return Traffic.medium;
			} else {
				return Traffic.low;
			}
		}
	}
	
	/**
	 * Returns the estimated traffic state, based on both the time given via 'hour' of the day and a randomness factor.
	 * @return The state of the traffic (one of Traffic.low, Traffic.medium and Traffic.heavy).
	 */
	public Traffic determineTrafficStateWithHour(int hour) {
		double rand = Math.random();
		if (hour >= 7 & hour < 11 ) {
			if (rand < 0.05) {
				return Traffic.low;
			} else if (rand >= 0.05 & rand < 0.25) {
				return Traffic.medium;
			} else {
				return Traffic.heavy;
			}
		}
		else if (hour >= 11 & hour < 17 ) {
			if (rand < 0.15) {
				return Traffic.heavy;
			} else if (rand >= 0.15 & rand < 0.30) {
				return Traffic.low;
			} else {
				return Traffic.medium;
			}
		}
		else if (hour >= 17 & hour < 22 ) {
			if (rand < 0.01) {
				return Traffic.low;
			} else if (rand >= 0.01 & rand < 0.05) {
				return Traffic.medium;
			} else {
				return Traffic.heavy;
			}
		}
		else {
			if (rand < 0.01) {
				return Traffic.heavy;
			} else if (rand >= 0.01 & rand < 0.05) {
				return Traffic.medium;
			} else {
				return Traffic.low;
			}
		}
	}
	
	
	
	
	/**
	 * Method used to get the required type of Car (Standard, Berline or Van) for each type of Ride.
	 * @return The type of Car that is required for this Ride.
	 */
	@SuppressWarnings("rawtypes")
	public abstract Class getCarType();
	
	/**
	 * Sends a request to available Drivers in the area (< d = 5km) matching the criteria of the Ride, and adds them to the list of Observers for the Ride.
	 * Requested Drivers have to be "on-duty", they have to be less than 5km far from the Customer booking the Ride, their Car needs to match the type of the Ride, and they need to have a sufficient amount
	 * of available seats (for UberPool only).
	 */
	public void oldrequestDrivers() {
		int d = 5;
		for (Driver driver : Environment.getInstance().getDriversList()) {
			if (driver.getStatus() == DriverStatus.on_duty) {
				if (driver.getCar().getClass() == this.getCarType() ) {
					if (this.startingPoint.distance(driver.getCar().getLocation()) < d ) {
						this.addObserver((Observer) driver);
					}
				}
			}
		}
	}
	
	/**
	 * Requests the nearest Driver near the starting point of the Ride that is being booked. Return false if there is not available driver
	 * @return
	 * @throws RideRequestException
	 * @throws CarAlreadyUsedException
	 * @throws InvalidDateException
	 */
	public boolean requestNearestDriver() throws RideRequestException, CarAlreadyUsedException, InvalidDateException {
		double distance = 100000 ;
		//Because we work on 100*100 square area this distance is unattainable 
		Driver nearestDriver = null;
		for (Driver driver : Environment.getInstance().getDriversList()) {
			if (driver.getStatus() == DriverStatus.on_duty) {
				if (driver.getCar().getClass() == this.getCarType()) {
					if (this.startingPoint.distance(driver.getCar().getLocation()) < distance) {
						distance = this.startingPoint.distance(driver.getCar().getLocation());
						nearestDriver = driver ;
					}
					
				}
			}
			
		}
		
		if(nearestDriver == null) {
			return false;
		}
		else {
			this.addObserver(nearestDriver);
			nearestDriver.acceptRide(this);
			return true;
		}
	}
	
	@Override
	public void addObserver(Observer observer) {
		this.observerDrivers.add(observer);
		((Driver) observer).getOnGoingRideRequests().add(this);
	}
	
	@Override
	public void removeObserver(Observer observer) {
		this.observerDrivers.remove(observer);
		((Driver) observer).getOnGoingRideRequests().remove(this);
	}
	
	@Override
	public void removeAllObservers() {
		for (Observer o : this.observerDrivers) {
			((Driver) o).getOnGoingRideRequests().remove(this);
		}
		this.observerDrivers.clear();
	}
	
	@Override
	public void notifyObservers() {
		if (this.changed) {
			for (Observer o : observerDrivers) {
				o.update(this);
			}
			this.changed = false;
		}
	}
	
	/**
	 * Return a string that contains relevant information about the ride.
	 * @return
	 */
	public String getInfo() {
		String info = "";
		if (this.getAssociatedDriver() != null) {
		info += "\n **Ride Info** \n" +
				"\n Associated Customer : \n" + this.getAssociatedCustomer().toString() + "\n Associated Driver :\n" + this.getAssociatedDriver().toString() +
				"\n Associated Car :\n" + this.getAssociatedDriver().getCar().getID() + "\n TimeDeparture : \n" + this.getPickupTime() + 
				"\n TimeArrival : \n" + this.getEndTime() + "\n Cost of ride : \n" + this.getPrice();
		} else {
			info += "Information for this Ride is not available for now.";
		}
		return info ;
	}
	
	
	@Override
	public String toString() {
		String s1 = this.getClass().toString();
		String[] parts = s1.split("\\.");
		String type = parts[parts.length-1];
		return type + " ride n°" + this.ID;
	}
	
	//getters
	
	public int getID() {
		return ID;
	}

	public Customer getAssociatedCustomer() {
		return associatedCustomer;
	}

	public Coordinates getStartingPoint() {
		return startingPoint;
	}

	public Coordinates getDestination() {
		return destination;
	}

	public int getPassengersNb() {
		return passengersNb;
	}

	public RideStatus getStatus() {
		return status;
	}

	public Traffic getTraffic() {
		return traffic;
	}
	
	public double getPrice() {
		return price;
	}

	public int getDuration() {
		return duration;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public Driver getAssociatedDriver() {
		return associatedDriver;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public int getMark() {
		return mark;
	}

	public ArrayList<Observer> getObserverDrivers() {
		return observerDrivers;
	}

	public boolean isChanged() {
		return changed;
	}

	
	//setters
	
	public void setAssociatedCustomer(Customer associatedCustomer) {
		this.associatedCustomer = associatedCustomer;
	}

	public void setStartingPoint(double x, double y) throws InvalidLocationException {
		this.startingPoint = new Coordinates(x, y);
	}

	public void setDestination(double x, double y) throws InvalidLocationException {
		this.destination = new Coordinates(x, y);
	}

	public void setPassengersNb(int passengersNb) {
		this.passengersNb = passengersNb;
	}

	/**
	 * When the status of the ride is changed, the boolean attribute "changed" is set to true to notify eventual Observers of the modification.
	 * If the status is "confirmed" or "cancelled", clears the list of Observers for this Ride.
	 * If the status is "completed", adds this Ride to the Environment's BookOfRides.
	 * @param status Must be one of : simulated, trashed, unconfirmed, cancelled, confirmed, ongoing, completed.
	 * @throws CarAlreadyUsedException 
	 */
	public void setStatus(RideStatus status) {
		this.status = status;
		this.changed = true;
		this.notifyObservers();
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTraffic(Traffic traffic) {
		this.traffic = traffic;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public void setAssociatedDriver(Driver associatedDriver) {
		this.associatedDriver = associatedDriver;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void setObserverDrivers(ArrayList<Observer> observerDrivers) {
		this.observerDrivers = observerDrivers;
	}
	
	
}
