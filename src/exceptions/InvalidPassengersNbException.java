package exceptions;

import mainClasses.Ride;

/**
 * Exception that is thrown when a Customer is trying to book a Ride for too many passengers with regards to the available number of seats.
 * @author Erwan
 *
 */
public class InvalidPassengersNbException extends Exception {
	
	private static final long serialVersionUID = 4886120910531651596L;
	/**
	 * The Ride that threw the exception.
	 */
	private Ride ride;
	
	/**
	 * Create a new InvalidPassengersNbException.
	 * @param passengersNb Number of passengers entered by the Customer.
	 */
	public InvalidPassengersNbException(Ride ride) {
		this.ride = ride;
	}
	
	@Override
	public String getMessage() {
		return ("The number of passengers (" + ride.getPassengersNb() +") you entered is not available for this type of Ride (" + ride.getClass() + ").");
	}
}
