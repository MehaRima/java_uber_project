package cli;

import java.util.ArrayList;

import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import helpers.Environment;
import mainClasses.Customer;
/**
 * The command that allows the user to ask for the price for all Uber type ride for a given time
 * @author PC_Antoine
 *
 */
public class Ask4PriceCommand {
	
	public static String ask4PricesCommand(String customerID, String destination, String time) throws NumberFormatException, InvalidLocationException, CustomerIDNotFoundException {
		ArrayList<Customer> listOfCustomer = Environment.getInstance().getCustomersList() ;
		int i = 0 ;
		int size = listOfCustomer.size();
		while(i< size && listOfCustomer.get(i).getID() != Integer.parseInt(customerID)) {
			i += 1 ;
			}
		if(i<size) {
			Customer cust = listOfCustomer.get(i);
			int hour ;
			if(time.equals("now")) {
				hour = Environment.getInstance().getClock().getDate().getHour();
			}
			else {
				String[] separatedTime = time.split("/");
				hour = Integer.parseInt(separatedTime[1]) ;
			}
			String[] separatedDestination = destination.split(":");
			double[] listOfPrice = cust.askForPrice(Double.parseDouble(separatedDestination[0]),Double.parseDouble(separatedDestination[1]), 1, hour);
			String message = "" ;
			message += "\nRide fares from " + cust.getLocation() + " to (" + separatedDestination[0] +", " + separatedDestination[1] + ") at " + hour + "h :" +
			"\n UberX fare : " + listOfPrice[0] + "\n UberBlack fare : " + listOfPrice[1] + "\n UberVan fare : " + listOfPrice[2] + "\n UberPool fare : " + listOfPrice[3];
			return(message);
		}
		else {
			throw new CustomerIDNotFoundException("This ID does not exist");
		}
	}
		
	}


