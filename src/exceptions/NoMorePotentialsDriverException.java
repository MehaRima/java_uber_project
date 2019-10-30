package exceptions;

/**
 * Thrown when there is no driver for the poolRequest
 * @author PC_Antoine
 *
 */
public class NoMorePotentialsDriverException extends Exception {
	
	private static final long serialVersionUID = 4209985009656397499L;
	/**
	 * The error message describing this exception.
	 */
	private String message ;
	
	/**
	 * Creates a new NoMorePotentialsDriverException
	 * @param message The error message describing this exception.
	 */
	public NoMorePotentialsDriverException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	

}
