package exceptions;

/**
 * Exception that is thrown when a Customer is trying to attribute a mark for a Ride but certain conditions are not met.
 * @author Erwan
 *
 */
public class MarkAttributionException extends Exception {
	private static final long serialVersionUID = -6441638709906776134L;
	
	private String message;

	/**
	 * Instantiates a MarkAttributionException.
	 * @param mark The given mark that triggered the exception.
	 */
	public MarkAttributionException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	

}
