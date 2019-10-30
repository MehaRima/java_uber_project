package mainClasses;
import helpers.*;
import exceptions.InvalidAvailableSeatsException;
import exceptions.InvalidLocationException;

import java.util.ArrayList;

import designPatterns.*;


/**
 * The Class that represents car objects in myUber application.
 */

public abstract class Car implements Observable {
	
	/**
	 * An alphanumeric ID  that is used to represent the Car : it is composed of the type of the car and its number.
	 */
	protected String ID; 
	
	/**
	 * The current GPS coordinates of the car 
	 */
	protected Coordinates location;
	
	/**
	 * The number of seats of the car
	 */
	protected int totalSeats;
	
	/**
	 * The number of current available seats of the car
	 */
	protected int availableSeats;
	
	/**
	 * The driver who is using the car. Null if the car is not used at the moment
	 */
	protected Driver currentDriver;
	
	/**
	 * Cause a car can be driven by severals drivers, it is the list of the potentials drivers of the car
	 */
	protected ArrayList<Observer> driversList;
	
	/**
	 * True if there has been a change of current Driver that still has not been noticed to the other potentials drivers 
	 */
	protected boolean currentDriverHasChanged;
	
	
	//Construsctors
	
	/**
	 * Creates a new Car at the given location.
	 * @param location The initial location of the Car.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public Car(Coordinates location) throws InvalidLocationException {
		this.location = location;
		this.currentDriverHasChanged = false;
		this.driversList = new ArrayList<Observer>();
	}
	
	/**
	 * Creates a new Car at a randomlocation.
	 * @throws InvalidLocationException
	 */
	public Car() throws InvalidLocationException {
		this.location = Coordinates.randomCoord();
		this.currentDriverHasChanged = false;
		this.driversList = new ArrayList<Observer>();
	}

	//Methods
	
	/**
	 * Returns detailed information about this Car.
	 * @return A String containing the relevant information for this Car.
	 */
	public String getInfo() {
		String drivedstate;
		if (this.currentDriver == null) {
			drivedstate = ". It is not currently being driven by anyone.";
		} else {
			drivedstate = ". It is currently being driven by " + this.currentDriver.toString() + ", who is " + this.currentDriver.getStatus().name() +".";
		}
		String message = this.ID +" is currently located at " + this.location + drivedstate;
		return message;
	}
	
	@Override
	public final String toString() {
		String s1 = this.ID;
		String[] parts = s1.split("[123456789]+");
		String type = parts[parts.length-1];
		String number = s1.replaceAll("\\D+", "");
		return type + " n°" + number;
	}
	/**
	 * Notifies the potential co-owners of the Car of the new Driver using it.
	 */
	@Override 
	public void notifyObservers() {
		if (this.currentDriverHasChanged) {
			for(Observer ob : driversList) {
				ob.update(this.currentDriver);
			}
		this.currentDriverHasChanged = false ;
		}
	}
	
	/**
	 * Marks a Driver as new co-owner of this Car and adds them to the list of Observers of this Car.
	 */
	@Override
	public void addObserver(Observer newdriver) {
		this.driversList.add(newdriver);
	}

	@Override
	public void removeObserver(Observer driver) {
		this.driversList.remove(driver);
	}
	
	@Override
	public void removeAllObservers() {
		this.driversList.clear();
	}
	
	
	
	//Getters & Setters
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Coordinates getLocation() {
		return location;
	}

	public void setLocation(Coordinates coordinates) {
		this.location = coordinates;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Modifies the current number of available seats in the car.
	 * @param availableSeats The new number (of int type) of available seats.
	 * @throws InvalidAvailableSeatsException Thrown if availableSeats is negative or over the totalSeats number.
	 */
	public void setAvailableSeats(int availableSeats) throws InvalidAvailableSeatsException {
		if (availableSeats > this.totalSeats || availableSeats < 0) {
			throw new InvalidAvailableSeatsException(availableSeats) ;
		}
		else {
		this.availableSeats = availableSeats;
		}
	}

	public Driver getCurrentDriver() {
		return currentDriver;
	}

	/**
	 * Changes the current Driver of the Car, and calls the notifyObservers() method to inform the eventual co-owners of the Car that its current Driver has changed.
	 * @param currentDriver
	 */
	public void setCurrentDriver(Driver currentDriver) {
		this.currentDriver = currentDriver;
		this.setCurrentDriverHasChanged(true);
		this.notifyObservers();
	}

	public ArrayList<Observer> getDriverslist() {
		return driversList;
	}

	public void setDriverslist(ArrayList<Observer> driverslist) {
		this.driversList = driverslist;
	}

	public boolean CurrentDriverHasChanged() {
		return currentDriverHasChanged;
	}

	public void setCurrentDriverHasChanged(boolean currentDriverHasChanged) {
		this.currentDriverHasChanged = currentDriverHasChanged;
	}
}
