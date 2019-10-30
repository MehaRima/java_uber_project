package exceptions;

import mainClasses.*;

/**
 * Exception thrown when a driver tries to remove his status from 'offline' but his car is already used by another driver
 * @author Antoine
 *
 */
public class CarAlreadyUsedException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Driver otherDriver;
	
	
	public CarAlreadyUsedException(Driver otherDriver) {
		this.otherDriver = otherDriver ;
	}
	
	
	@Override
	public String getMessage() {
		
		return("You can not change your status because this other driver : " + otherDriver.toString() + "is using your car");
	}
}
