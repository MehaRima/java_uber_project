package cli;

import exceptions.InvalidDateException;
import helpers.Environment;
/**
 * The command that allows the user to retrieve different statistics on drivers
 * 'mostappreciated' the drivers are sorted by the average of marks of their competed ride
 * 'mostoccupied' the drivers are sorted by the ratio of occupation which is  on_duty_time / (on_duty_time + on_a_ride_time)
 * @author PC_Antoine
 *
 */
public class DisplayDriversCommand {
	
	public static String displayDrivers(String sortPolicy) throws InvalidDateException {
		if(sortPolicy.equals("mostappreciated")) {
			return Environment.getInstance().mostAppreciatedDriver().toString();
		}
		else if(sortPolicy.equals("mostoccupied")) {
			return Environment.getInstance().leastOccupiedDriver().toString();
		}
		
		else {
			return "Invalid sortPolicy" ; 
		}
		
	}

}
