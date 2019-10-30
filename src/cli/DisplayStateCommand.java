package cli;

import helpers.Environment;

/**
 * The command that displays relevant information on all of the customers, drivers and cars of the environment
 * @author PC_Antoine
 *
 */
public class DisplayStateCommand {

	public static String displayStateNoArgs() {
		
		String message = "";
		message += Environment.getInstance().getInfoCustomers();
		message += "\n  \n";
		message += Environment.getInstance().getInfoDrivers();
		message += "\n \n";
		message += Environment.getInstance().getInfoCars();
		
		return message ;
		
	}
	
}
