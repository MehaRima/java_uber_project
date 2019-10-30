package exceptions;

/**
 * Exception thrown when to much customer (more than 3) want to book a UberPool in the same time.
 * @author Antoine
 */
public class PoolRequestException extends Exception {
	
	private static final long serialVersionUID = -1846831637391612887L;
	/**
	 * The error message describing this exception.
	 */
	private String message ;
	
	/**
	 * Creates a new PoolRequestException
	 * @param message The error message describing this exception.
	 */
	public PoolRequestException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
