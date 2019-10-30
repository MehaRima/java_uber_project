package cli;

import java.util.ArrayList;

import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.Environment;
import mainClasses.Customer;
/**
 * The command that allows the user to move a customer
 * @author PC_Antoine
 *
 */
public class MoveCustomerCommand {
	
	public static void moveCustomerCommand(String customerID, String xPos, String yPos) throws NumberFormatException, InvalidLocationException, CustomerIDNotFoundException {
		ArrayList<Customer> listOfCustomer = Environment.getInstance().getCustomersList() ;
		int i = 0 ;
		int size = listOfCustomer.size();
		while(i< size && listOfCustomer.get(i).getID() != Integer.parseInt(customerID)) {
			i += 1 ;
			}
		if(i<size) {
		 listOfCustomer.get(i).setLocation(new Coordinates(Double.parseDouble(xPos), Double.parseDouble(yPos)));
			
	}
		else {
			throw new CustomerIDNotFoundException("This ID does not exist");
		}
	}
	

}
