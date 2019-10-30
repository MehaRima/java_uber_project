package exceptions;

/**
 * Exception thrown when a user sets the number of avaible seats higher than the number of seats in a car
 * @author Antoine
 *
 */
public class InvalidAvailableSeatsException extends Exception {
	private static final long serialVersionUID = 1L;
	private int availableSeats;
	
	/**
	 * Instantiates an InvalidAvailableSeatsException.
	 * @param availableSeats The number of seats that triggered the exception.
	 */
	public InvalidAvailableSeatsException(int availableSeats) {
		this.availableSeats = availableSeats ;
	}

	@Override
	public String getMessage() {
		
		return("You set availableSeats at" + availableSeats + " which is higher than the number of the seats of the car");
	}

}

	