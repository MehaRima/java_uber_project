package exceptions;

public class NoAskedForPoolRequestException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4641168321570614805L;
	/**
	 * The error message describing this exception.
	 */
	private String message ;
	
	/**
	 * Creates a new PoolRequestException
	 * @param message The error message describing this exception.
	 */
	public NoAskedForPoolRequestException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}


}
