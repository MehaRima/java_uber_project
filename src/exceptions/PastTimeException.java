package exceptions;

public class PastTimeException extends Exception {
	
	private static final long serialVersionUID = -5367469342267132495L;
	private String message;
	
	
	public PastTimeException(String message) {
		this.message = message ;
	}
	
	public String getMessage() {
		return this.message ;
	}

}
