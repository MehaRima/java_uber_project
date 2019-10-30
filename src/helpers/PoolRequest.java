package helpers;

import mainClasses.UberPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import mainClasses.Car;
import mainClasses.Driver;
import mainClasses.StandardCar;

/**
 * A class that implements the merge of 2 or 3 UberPool requests
 * 
 * @author Antoine
 *
 */
public class PoolRequest {

	/**
	 * A list that contains up to 3 UberPool Ride requests. Its length is 3.
	 */
	private UberPool[] uberPoolList;

	/**
	 * The potentialsDrivers sorted. The drivers are sorted from the one with the
	 * minimum 'cost' to the one with the maximum 'cost'
	 */
	private ArrayList<Driver> sortedPotentialsDrivers;

	/**
	 * A list that contains all the potential drivers that have been asked for the
	 * ride i.e who are 'on_duty' and in a range of 5 km from one of the customer of
	 * uberPoolList
	 */
	private ArrayList<Driver> potentialsDrivers;

	/**
	 * This is the current driver who has to choose if he wants to accept the
	 * poolRequest
	 */
	private Driver currentObserverDriver;

	// Constructor

	public PoolRequest() {
		this.uberPoolList = new UberPool[3];
		this.sortedPotentialsDrivers = new ArrayList<Driver>();
		this.potentialsDrivers = new ArrayList<Driver>();
	}

	// Methods

	public void refreshCostTreatment() throws NoMorePotentialsDriverException, NoAskedForPoolRequestException, InvalidDateException, CarAlreadyUsedException {

		int nb_ride = 0;
		int total_nb_passengers = 0;
		while (nb_ride < 3 && this.uberPoolList[nb_ride] != null) {
			total_nb_passengers += this.uberPoolList[nb_ride].getPassengersNb();
			nb_ride += 1;
		}
		if (nb_ride > 1) {
			ArrayList<Coordinates> pickups_coords = new ArrayList<Coordinates>();
			for (int i = 0; i < nb_ride; i++) {
				pickups_coords.add(this.uberPoolList[i].getStartingPoint());
			}
			
			for (Driver driver : Environment.getInstance().getDriversList()) {
				if (driver.getStatus() == DriverStatus.on_duty && driver.getCar() instanceof StandardCar
						&& driver.getCar().getAvailableSeats() >= total_nb_passengers) {
					this.potentialsDrivers.add(driver);
										
				}
			}
			

			Map<Driver, Double> mapOfCost = new HashMap<Driver, Double>();
			for (Driver driver : this.potentialsDrivers) {
				mapOfCost.put(driver, this.poolRequestCost(driver.getCar()));
			}
			Map<Driver, Double> sortedMapOfCost = Environment.sortByValueDriverDouble(mapOfCost);
			Set<Driver> sortedSet = sortedMapOfCost.keySet();
			this.sortedPotentialsDrivers = new ArrayList<Driver>(sortedSet);
			if (this.currentObserverDriver != null) {
				this.currentObserverDriver.setIsAskedForPoolRequest(false);
			}
			this.notifyNextDriver();
		}
		else {
			if(this.currentObserverDriver != null) {
				this.currentObserverDriver.setIsAskedForPoolRequest(false);
				this.currentObserverDriver = null;
			}
		}

	}

	/**
	 * Return the 'cost' for a car to accept a poolRequest. It is used to choose the
	 * best car to fulfill a poolRequest
	 * 
	 * @param car
	 * @param poolRequest
	 * @return
	 */
	public double poolRequestCost(Car car) {
		ArrayList<Coordinates> sortedCoords = new ArrayList<Coordinates>();
		ArrayList<Coordinates> pickupCoords = new ArrayList<Coordinates>();
		ArrayList<Coordinates> dropCoords = new ArrayList<Coordinates>();
		for (UberPool uberpool : uberPoolList) {
			if (uberpool != null) {
				pickupCoords.add(uberpool.getStartingPoint());
				dropCoords.add(uberpool.getDestination());

			}
		}
		Coordinates nextCoords = car.getLocation().nearest(pickupCoords);
		sortedCoords.add(nextCoords);
		pickupCoords.remove(nextCoords);
		while (!pickupCoords.isEmpty()) {
			nextCoords = nextCoords.nearest(pickupCoords);
			sortedCoords.add(nextCoords);
			pickupCoords.remove(nextCoords);

		}
		while (!dropCoords.isEmpty()) {
			nextCoords = nextCoords.nearest(dropCoords);
			sortedCoords.add(nextCoords);
			dropCoords.remove(nextCoords);
		}

		double cost = 0;

		Coordinates lastCoords = car.getLocation();
		for (Coordinates c : sortedCoords) {
			cost += lastCoords.distance(c);
			lastCoords = c;
		}

		return cost;
	}

	/**
	 * Return the route the car has to follow to complete the poolRequest
	 * 
	 * @param car
	 * @return
	 */
	public ArrayList<Coordinates> poolRequestRoute(Car car) {
		ArrayList<Coordinates> sortedCoords = new ArrayList<Coordinates>();
		ArrayList<Coordinates> pickupCoords = new ArrayList<Coordinates>();
		ArrayList<Coordinates> dropCoords = new ArrayList<Coordinates>();
		for (UberPool uberpool : uberPoolList) {
			if (uberpool != null) {
				pickupCoords.add(uberpool.getStartingPoint());
				dropCoords.add(uberpool.getDestination());

			}
		}
		Coordinates nextCoords = car.getLocation().nearest(pickupCoords);
		sortedCoords.add(nextCoords);
		pickupCoords.remove(nextCoords);
		while (!pickupCoords.isEmpty()) {
			nextCoords = nextCoords.nearest(pickupCoords);
			sortedCoords.add(nextCoords);
			pickupCoords.remove(nextCoords);

		}
		while (!dropCoords.isEmpty()) {
			nextCoords = nextCoords.nearest(dropCoords);
			sortedCoords.add(nextCoords);
			dropCoords.remove(nextCoords);
		}
		return sortedCoords;
	}

	/**
	 * Notify the next closest Driver that there is an available PoolRequest.
	 * 
	 * @throws NoMorePotentialsDriverException
	 * @throws CarAlreadyUsedException 
	 * @throws InvalidDateException 
	 * @throws NoAskedForPoolRequestException 
	 */
	public void notifyNextDriver() throws NoMorePotentialsDriverException, NoAskedForPoolRequestException, InvalidDateException, CarAlreadyUsedException {
		if (this.sortedPotentialsDrivers.size() > 0) {
			Driver nextDriver = sortedPotentialsDrivers.get(0);
			nextDriver.setIsAskedForPoolRequest(true);
			this.currentObserverDriver = nextDriver;
			this.sortedPotentialsDrivers.remove(nextDriver);
			nextDriver.acceptPoolRequest();
			//With the current model a driver has to accept any drive request
		} else {
			for(UberPool up :this.uberPoolList) {
				up.getAssociatedCustomer().setOnGoingRide(null);
				up.setStatus(RideStatus.cancelled);
				Environment.getInstance().removeFutureBookingFromARide(up);
			}
			
			this.uberPoolList = new UberPool[3];
			
			throw new NoMorePotentialsDriverException("There is no available driver");
		}
	}

	public String toString() {
		String s = "";
		s += "The poolRequest is composed of the uberPool requests : \n";
		for (UberPool ride : this.uberPoolList) {
			if (ride != null) {
				s += ride.toString() + "\n **** \n";
			}

		}
		if (this.currentObserverDriver != null) {
			s += "The current driver to be asked for the poolRequest is  : \n" + this.currentObserverDriver.toString();
		} else {
			s += "There is not current driver to be asked for the poolRequest";
		}

		s += "\nThese are the next drivers to be asked : \n";
		for (Driver driver : this.sortedPotentialsDrivers) {
			s += driver.toString() + "\n";
		}

		return s;
	}

	// Getters & Setters
	public UberPool[] getUberPoolList() {
		return uberPoolList;
	}
	
	public int getNumberOfRequests() {
		int nb = 0;
		for (UberPool u : this.uberPoolList) {
			if (u != null) {
				nb += 1;
			}
		}
		return nb;
	}

	public void setUberPoolList(UberPool[] uberPoolList) {
		this.uberPoolList = uberPoolList;
	}

	public ArrayList<Driver> getSortedPotentialsDrivers() {
		return sortedPotentialsDrivers;
	}

	public void setSortedPotentialsDrivers(ArrayList<Driver> sortedPotentialsDrivers) {
		this.sortedPotentialsDrivers = sortedPotentialsDrivers;
	}
	
	public Driver getCurrentObserverDriver() {
		return this.currentObserverDriver;
	}
	
	public void setCurrentObserverDriver(Driver newDriver) {
		this.currentObserverDriver = newDriver;
	}

}
