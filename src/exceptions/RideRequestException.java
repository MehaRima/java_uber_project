package exceptions;

/**
 * Exception thrown when an error occurs during the Ride request process (booking/confirmation/cancellation).
 * @author Erwan
 *
 */
public class RideRequestException extends Exception {
	
	private static final long serialVersionUID = 5256587206802201771L;
	
	/**
	 * The error message describing this exception.
	 */
	private String message;
	
	/**
	 * Creates a new RideRequestException
	 * @param message The error message describing this exception.
	 */
	public RideRequestException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
