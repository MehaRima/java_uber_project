package cli;

import exceptions.InvalidLocationException;
import helpers.Environment;
/**
 * The command that allows the user to add a new customer to the environment
 * @author PC_Antoine
 *
 */
public class AddCustomerCommand {

	public static String addCustomerWithName(String name, String surname) {
		try {
			Environment.getInstance().getCFact().createCustomer(name, surname);
			return ("The new Customer has been created");
			
		} catch (InvalidLocationException e) {
			return e.getMessage();
		}
	}
	
	public static String addCustomerNoName() {
		try {
			Environment.getInstance().getCFact().createCustomer();
			return ("The new Customer has been created");
		} catch (InvalidLocationException e) {
			return (e.getMessage());
		}
	}
	
}
