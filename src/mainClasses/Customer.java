package mainClasses;

import java.util.ArrayList;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.InvalidPassengersNbException;
import exceptions.MarkAttributionException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.RideStatus;
import helpers.Coordinates;
import helpers.Date;
import helpers.Environment;
import helpers.FutureBookRide;

/**
 * Represents an individual Uber Customer.
 * @author Erwan
 *
 */
public class Customer {

	/**
	 * A counter that's incremented each time a new Ride is created : it is there to ensure each Ride has a unique ID.
	 */
	private static int customerIDcounter;

	/**
	 * An ID number that is used to represent the Customer : it is designed to be unique for each Customer.
	 */
	private int ID;

	/**
	 * The Customer's last name.
	 */
	private String name;

	/**
	 * The Customer's surname.
	 */
	private String surname;

	/**
	 * The Customer's credit card number (16 digits).
	 */
	private long creditCard;

	/**
	 * The current location of the Customer.
	 */
	private Coordinates location;

	/**
	 * If it exists, stores the ongoing/currently booked Ride of the Customer
	 */
	private Ride onGoingRide;

	/**
	 * A List that contains the messages sent to the Customer by the Uber application (e.g. Ride confirmation notifications).
	 */
	private ArrayList<String> mailBox;

	/**
	 * Creates a Customer with a random starting location.
	 * @param name String representing the last name of the Customer.
	 * @param surname String representing the first name of the Customer.
	 * @param creditCard String representing the credit card number of the Customer.
	 * @throws InvalidLocationException
	 */
	public Customer(String name, String surname, String creditCard) throws InvalidLocationException {
		customerIDcounter += 1;
		this.ID = customerIDcounter;
		this.name = name;
		this.surname = surname;
		this.creditCard = Long.parseLong(creditCard);
		this.location = Coordinates.randomCoord();
		this.mailBox = new ArrayList<String>();
		this.onGoingRide = null; 
	}

	/**
	 * Creates a Customer with a generic name and surname, as well as a random starting location.
	 * @param creditCard String representing the credit card number of the Customer.
	 * @throws InvalidLocationException
	 */
	public Customer(String creditCard) throws InvalidLocationException {
		customerIDcounter += 1;
		this.ID = customerIDcounter;
		this.name = "Customer" + this.ID + "Name";
		this.surname = "Customer" + this.ID + "Surname";
		this.creditCard = Long.parseLong(creditCard);
		this.location = Coordinates.randomCoord();
		this.mailBox = new ArrayList<String>();
		this.onGoingRide = null; 
	}

	/**
	 * Creates a Customer with the given starting location
	 * @param name String representing the last name of the Customer.
	 * @param surname String representing the first name of the Customer.
	 * @param creditCard String representing the credit card number of the Customer.
	 * @param x_location Double representing the x coordinate of the location of the Customer.
	 * @param y_location Double representing the y coordinate of the location of the Customer.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public Customer(String name, String surname, String creditCard, double x_location, double y_location) throws InvalidLocationException {
		customerIDcounter += 1;
		this.ID = customerIDcounter;
		this.name = name;
		this.surname = surname;
		this.creditCard = Long.parseLong(creditCard);
		this.location = new Coordinates(x_location, y_location);
		this.mailBox = new ArrayList<String>();
		this.onGoingRide = null;
	}

	/**
	 * Takes a destination and a number of passengers as inputs from the Customer, and returns a simulation for each type of Ride, as an Array of Rides.
	 * The first element of the Array is the simulated UberX Ride, the second is UberBlack, the third is UberVan and the fourth is UberPool.
	 * @param destination The destination of the simulated Ride.
	 * @param passengersNb The number of passengers that wish to board on the Ride.
	 * @return Array of size 4, containing the simulated UberX, UberBlack, UberVan and UberPool for the destination given by the Customer.
	 * @throws InvalidLocationException Thrown if the given destination is invalid (out of the [-50, 50]² boundaries).
	 */
	public Ride[] simulateRide(double x_destination, double y_destination, int passengersNb) throws InvalidLocationException {
		Coordinates destination = new Coordinates(x_destination, y_destination);
		Ride[] rideSimulations = new Ride[4];
		try {
			rideSimulations[0] = new UberX(this, destination, passengersNb);
		} catch (InvalidPassengersNbException e) {
			System.out.println(e.getMessage());
		}
		try {
			rideSimulations[1] = new UberBlack(this, destination, passengersNb);
		} catch (InvalidPassengersNbException e) {
			System.out.println(e.getMessage());
		}
		try {
			rideSimulations[2] = new UberVan(this, destination, passengersNb);
		} catch (InvalidPassengersNbException e) {
			System.out.println(e.getMessage());
		}
		try {
			rideSimulations[3] = new UberPool(this, destination, passengersNb);
		} catch (InvalidPassengersNbException e) {
			System.out.println(e.getMessage());
		}
		return rideSimulations;
	}


	/**
	 * Takes a destination and a number of passengers as inputs from the Customer, and returns a simulation of prices for each type of Ride.
	 * @param destination The destination of the simulated Ride.
	 * @param passengersNb The number of passengers that wish to board on the Ride.
	 * @return Array of size 4, containing the simulated UberX, UberBlack, UberVan and UberPool for the destination given by the Customer.
	 * @throws InvalidLocationException Thrown if the given destination is invalid (out of the [-50, 50]² boundaries).
	 */
	public double[] askForPrice(double x_destination, double y_destination, int passengersNb, int hour) throws InvalidLocationException {
		Ride[] rideSimulations = this.simulateRide(x_destination, y_destination, passengersNb);
		double[] prices = new double[4];
		for(int i = 0; i < 4; i++) {
			prices[i] = rideSimulations[i].getPrice();
		}

		return prices;
	}



	/**
	 * After simulation of Rides for a certain destination and number of passengers (through method simulateRide), allows to book one of the simulated Rides.
	 * @param rideSimulations The simulation of Rides obtained through simulateRide.
	 * @param typeOfRide 1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool
	 * @param mark The future mark that will be attributed for the Driver (if not already known, set to 0).
	 * @throws RideRequestException Thrown if the wanted Ride is not technically possible (invalid passengersNb), or if it is no longer a "simulated" Ride.
	 * @throws PoolRequestException 
	 * @throws NoMorePotentialsDriverException 
	 * @throws InvalidDateException 
	 * @throws CarAlreadyUsedException 
	 * @throws NoAskedForPoolRequestException 
	 */
	public void bookRide(Ride[] rideSimulations, int typeOfRide, int mark) throws RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {

		if (this.onGoingRide == null) {
			if(typeOfRide == 1 || typeOfRide == 2 || typeOfRide == 3 || typeOfRide == 4) {
				if (rideSimulations[typeOfRide-1] != null) {
					Ride wantedRide = rideSimulations[typeOfRide-1];
					wantedRide.setMark(mark);
					if (wantedRide.getStatus() == RideStatus.simulated) {
						for (Ride r : rideSimulations) {
							if (r != wantedRide && r!= null) {
								r.setStatus(RideStatus.trashed);
							}
						}
						wantedRide.setBookingTime(Environment.getInstance().getClock().getDate());

						if(typeOfRide == 4) {
							Ride[] poolRequest = Environment.getInstance().getPoolRequest().getUberPoolList();
							int i = 0;
							while(i<3 & poolRequest[i] != null) {
								i +=1;
								if( i == 3) break;
							}
							if(i<3) {
								this.onGoingRide = wantedRide;
								System.out.println(wantedRide.toString() + " has been booked by " + this.toString());
								poolRequest[i] = wantedRide;
								wantedRide.setStatus(RideStatus.unconfirmed);
								Environment.getInstance().getOnGoingRides().add(wantedRide);
								Environment.getInstance().getPoolRequest().refreshCostTreatment();
							}
							else {
								throw new PoolRequestException("Error : there are too many customers trying to book a UberPool Ride at the moment, please re-try later");
							}
						}
						else {
							System.out.println(wantedRide.toString() + " has been booked by " + this.toString());
							wantedRide.setStatus(RideStatus.unconfirmed);
							if(wantedRide.requestNearestDriver()) {
								this.onGoingRide = wantedRide;
								Environment.getInstance().getOnGoingRides().add(wantedRide);
							}
							else {
								wantedRide.setStatus(RideStatus.cancelled);
								throw new RideRequestException("No driver ready for ride for the moment, please retry later");
							}
						}	
					} else {
						throw new RideRequestException("Error : the Ride you are trying to book is not available anymore (it may have been trashed or completed already).");
					}
				} else {
					throw new RideRequestException("Error : the Ride you are trying to book is not available for this number of passengers.");
				}
			}
			else {
				throw new RideRequestException("Error : this is an invalid type of Ride. (1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool)");
			}
		} else {
			throw new RideRequestException("Error : you are trying to book a Ride while already having an ongoing Ride.");
		}

	}


	/**
	 * Add a new FutureBookRide to the listOfFutureBookRide of the Environment.
	 * @param rideSimulations The rideSimulations in which the user picked the Ride to book.
	 * @param typeOfRide The type of Ride represented by an integer (1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool).
	 * @param date The date at which the Ride must take place.
	 * @param mark The future mark that will be attributed for the Driver (if not already known, set to 0) .
	 */
	public void makeFutureBookRide(Ride[] rideSimulations, int typeOfRide, Date date, int mark) {
		Environment.getInstance().getListOfFutureBookRide().add(new FutureBookRide(rideSimulations, typeOfRide, date, this, mark));
	}

	/**
	 * If it exists and is still in an "unconfirmed" state, cancels the current onGoingRide.
	 * @throws RideRequestException Thrown if the onGoingRide doesn't exist, or if it is not anymore in an "unconfirmed" state.
	 */
	public void cancelRide() throws RideRequestException {
		if (this.onGoingRide != null) {
			if (this.onGoingRide.getStatus() == RideStatus.unconfirmed) {
				this.onGoingRide.setStatus(RideStatus.cancelled);
				this.onGoingRide.removeAllObservers();
				System.out.println("You successfully cancelled " + this.onGoingRide.toString() + " before its confirmation.");
				this.onGoingRide = null;
			} else {
				throw new RideRequestException("You cannot cancel " + this.onGoingRide.toString() + " because it is not 'unconfirmed' anymore.");
			}
		} else {
			throw new RideRequestException("You have no ongoing Ride to cancel !");
		}
	}

	/**
	 * Allows the Customer to attribute a mark to a Driver for a completed Ride, from 1 to 5.
	 * @param completedRide A Ride previously completed by the Customer.
	 * @param mark The mark, an integer from 1 to 5, that the Customer wishes to attribute.
	 * @throws MarkAttributionException Thrown if the input mark is not an integer between 1 and 5.
	 */
	public void markRide(Ride completedRide, int mark) throws MarkAttributionException {
		if (mark < 6 && mark > 0) {
			if (completedRide.getStatus() == RideStatus.completed && completedRide.getAssociatedCustomer() == this) {
				if (completedRide.getMark() == 0) {
					completedRide.setMark(mark);
					completedRide.getAssociatedDriver().getMarks().add(mark);
				} else {
					throw new MarkAttributionException("You have already attributed a mark for this Ride.");
				}
			} else {
				throw new MarkAttributionException("This Ride is not eligible for grading for you.");
			}
		} else {
			throw new MarkAttributionException("The mark you attributed (" + mark + ") is invalid. Please enter a mark between 1 and 5 included.");
		}
	}

	/**
	 * Returns detailed information about this Customer.
	 * @return A String containing the relevant information for this Customer.
	 */
	public String getInfo() {
		String status;
		if (this.onGoingRide == null) {
			status = ". He/She is not currently on a Ride.";
		} else {
			status = ". He/She is currently on a(n) " + this.onGoingRide.getStatus().name() + " Ride (" + this.onGoingRide.toString() + ").";
		}
		String message = "Customer n°" + this.ID + " is " + this.surname + " " + this.name + ", who is currently located at " + this.location + status + " His/her credit card number is... a secret ;-)";
		return message;
	}

	@Override
	public String toString() {
		return this.surname + " " + this.name +" (Customer n°" + this.ID + ")";
	}

	//getters

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public long getCreditCard() {
		return creditCard;
	}

	public Coordinates getLocation() {
		return location;
	}

	public Ride getOnGoingRide() {
		return onGoingRide;
	}

	public ArrayList<String> getMailBox() {
		return mailBox;
	}

	//setters

	public void setCreditCard(long creditCard) {
		this.creditCard = creditCard;
	}

	public void setLocation(Coordinates coordinates) {
		this.location = coordinates;
	}

	public void setOnGoingRide(Ride onGoingRide) {
		this.onGoingRide = onGoingRide;
	}

	public void setMailBox(ArrayList<String> mailBox) {
		this.mailBox = mailBox;
	}



}
