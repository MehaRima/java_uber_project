package exceptions;

/**
 * Thrown when a user of the CLI try to use a IDcar that has not been on file 
 * @author Antoine
 *
 */
public class NoExistingIDCarException  extends Exception {


	private static final long serialVersionUID = 7728592369764271726L;
	/**
	 * The error message describing this exception.
	 */
	private String message ;
	
	/**
	 * Creates a new NoExistingIDCarException
	 * @param message The error message describing this exception.
	 */
	public NoExistingIDCarException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}


	
	

}
