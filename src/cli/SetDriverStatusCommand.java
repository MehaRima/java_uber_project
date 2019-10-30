package cli;

import java.util.ArrayList;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import helpers.DriverStatus;
import helpers.Environment;
import mainClasses.Driver;

/**
 * The command that allows the user to set a driver's status which can be 'offline','on_duty', 'on_a_ride' or 'off_duty'  
 * @author PC_Antoine
 *
 */
public class SetDriverStatusCommand {
	
	public static void setDriverStatusWithID(String driverID, String Driverstatus) throws CarAlreadyUsedException, InvalidDateException {
		
		ArrayList<Driver> listOfDriver = Environment.getInstance().getDriversList();
		int i = 0 ;
		int size = listOfDriver.size();
		while(i < size && listOfDriver.get(i).getID() != Integer.parseInt(driverID)) {
			i += 1;
		}
		
		
		if(i < size) {
			if (listOfDriver.get(i).getStatus() != DriverStatus.on_a_ride) {
				if(Driverstatus.equals("on_duty")) {
					listOfDriver.get(i).setStatus(DriverStatus.on_duty);
				}
				else if(Driverstatus.equals("off_duty")) {
					listOfDriver.get(i).setStatus(DriverStatus.off_duty);
				}
				else if(Driverstatus.equals("offline")) {
					listOfDriver.get(i).setStatus(DriverStatus.offline);
				}
			
				else {
					System.out.println("DriverStatus argument is not vailable one");
				}
			} else {
				System.out.println("You cannot change this Driver's status since he's on a ride.");
			}
		}
		
		
		else {
			System.out.println("DriverId not found");
			
		}
	}





}


	
