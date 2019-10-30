package exceptions;

/**
 * Exception that is thrown when an invalid location is given to the program.
 * @author Erwan
 *
 */
public class InvalidLocationException extends Exception {
	
	private static final long serialVersionUID = -2989693967675396647L;
	
	/**
	 * The error message describing this exception.
	 */
	private String message;
	
	/**
	 * Instantiates a SetTimeException with the given error message.
	 * @param message The error message describing this exception.
	 */
	public InvalidLocationException(double coord) {
		this.message = "Invalid location : one of the coordinates you entered (" + coord +") is out of the boundaries of the city ([-50, 50]²).";
	}
	
	/**
	 * Instantiates a SetTimeException with "Invalid date !" as the error message.
	 */
	public InvalidLocationException() {
		this.message = "Invalid location !";
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
