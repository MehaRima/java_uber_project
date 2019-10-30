package exceptions;

/**
 * Exception thrown when an invalid Clock date is given to the program.
 * @author Erwan
 *
 */
public class InvalidDateException extends Exception {
	
	private static final long serialVersionUID = 4255456442812868343L;
	
	/**
	 * The error message describing this exception.
	 */
	private String message;
	
	/**
	 * Instantiates a InvalidDateException with the given error message.
	 * @param message The error message describing this exception.
	 */
	public InvalidDateException(String message) {
		this.message = message;
	}
	
	/**
	 * Instantiates a InvalidDateException with "Invalid date !" as the error message.
	 */
	public InvalidDateException() {
		this.message = "Invalid date !";
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * Changes the error message that is associated with this instance of InvalidDateException.
	 * @param message The error message describing this exception.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
}
