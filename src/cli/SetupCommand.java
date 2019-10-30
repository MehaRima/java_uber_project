package cli;


import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import helpers.DriverStatus;
import helpers.Environment;

/**
 * A command that allows to add new cars of all the different types with new associated drivers and new customers
 * @author PC_Antoine
 *
 */
public class SetupCommand {
	
	public static String setup(String nStandard, String nBerline, String nVan, String nCustomers) throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		
		for(int i =0 ; i < Integer.parseInt(nStandard); i++) {
			Environment.getInstance().getDFact().createDriver(Environment.getInstance().getSCFact().createCar()).setStatus(DriverStatus.on_duty);
		}
		
		for(int i =0 ; i < Integer.parseInt(nBerline); i++ ) {
			Environment.getInstance().getDFact().createDriver(Environment.getInstance().getBFact().createCar()).setStatus(DriverStatus.on_duty);
		}
		
	
		for(int i =0 ; i < Integer.parseInt(nVan); i++) {
			Environment.getInstance().getDFact().createDriver(Environment.getInstance().getVFact().createCar()).setStatus(DriverStatus.on_duty);
		}
		
		for(int i =0 ; i < Integer.parseInt(nCustomers); i++) {
			Environment.getInstance().getCFact().createCustomer();
		}
		
		return "The setup is done, enter 'displayState' to see the refreshed Environment" ;
		
	}

}
