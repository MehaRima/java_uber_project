package exceptions;

public class CustomerIDNotFoundException extends Exception {

	
	private static final long serialVersionUID = -4817958923911952403L;
	private String message;
	
	public  CustomerIDNotFoundException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message ;
	}
}
