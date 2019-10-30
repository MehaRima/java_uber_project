package exceptions;

/**
 * Thrown when a user try to add a car with an invalid type.
 * @author Antoine
 *
 */
public class InvalidCarTypeException extends Exception {
	

	private static final long serialVersionUID = 7904074419865441531L;
	private String message ;
	
	public InvalidCarTypeException(String message) {
		this.message = message ;
	}
	
	
	@Override
	public String getMessage() {
		
		return(this.message);
	}

}
