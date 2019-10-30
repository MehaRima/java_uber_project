package cli;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.CarAlreadyUsedException;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.MarkAttributionException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PastTimeException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Clock;
import helpers.Date;
import helpers.Environment;
import mainClasses.Customer;
import mainClasses.Ride;

/**
 * The command that simulates a Ride. If there is a vacant driver, he is automatically elected to be the associated driver. 
 * This command does not pass the time, so the user has to pass the time after this command with 'passTime'
 * @author Antoine
 *
 */
public class SimRideCommand {

	
	public static String simRideWithInfo(String customerID, String destination, String time, String rideType, String mark) throws CustomerIDNotFoundException, NumberFormatException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, InvalidDateException, PastTimeException, CarAlreadyUsedException, NoAskedForPoolRequestException {
		ArrayList<Customer> listOfCustomer = Environment.getInstance().getCustomersList() ;
		int i = 0 ;
		int size = listOfCustomer.size();
		while(i< size && listOfCustomer.get(i).getID() != Integer.parseInt(customerID)) {
			i += 1 ;
		}
		if(i < size) {
			String[] separatedDestination = destination.split(":");
			if(time.equals("now")) {
				String message = "" ;
				listOfCustomer.get(i).bookRide(listOfCustomer.get(i).simulateRide(Double.parseDouble(separatedDestination[0]), Double.parseDouble(separatedDestination[1]), 1), Integer.parseInt(rideType), Integer.parseInt(mark));
				message += listOfCustomer.get(i).getOnGoingRide().getInfo(); 
				return(message);
			}
			else {
				String[] separatedTime = time.split("/");
				Date date = new Date(Integer.parseInt(separatedTime[4]), Integer.parseInt(separatedTime[3]), Integer.parseInt(separatedTime[2]), Integer.parseInt(separatedTime[1]), Integer.parseInt(separatedTime[0]));
				if(date.isPosteriorTo(Clock.getInstance().getDate())) {
					String message = listOfCustomer.get(i).toString() + " has booked a ride for : " + date.toString() + ".";

					listOfCustomer.get(i).makeFutureBookRide(listOfCustomer.get(i).simulateRide(Double.parseDouble(separatedDestination[0]), Double.parseDouble(separatedDestination[1]),  1), Integer.parseInt(rideType), date, Integer.valueOf(mark));

					return(message);
				}

				else {
					throw new PastTimeException("You can't book a ride in the past !") ;
				}


			}
		}

		else {
			throw new CustomerIDNotFoundException("This ID does not exist");
		}
	}
	@SuppressWarnings("resource")
	public static String simRide_i(String customerID, String destination, String time) throws CustomerIDNotFoundException, NumberFormatException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, InvalidDateException, PastTimeException, CarAlreadyUsedException, NoAskedForPoolRequestException {

		System.out.println(Ask4PriceCommand.ask4PricesCommand(customerID, destination, time));
		System.out.println("Please choose the type of Ride you want (1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool)");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String typeRide = scan.nextLine();
		
		if(time.equals("now")) {

		}
		else {
			String[] separated_date = time.split("/");

			Date future_date = new Date(Integer.parseInt(separated_date[4]),Integer.parseInt(separated_date[3]),Integer.parseInt(separated_date[2]),Integer.parseInt(separated_date[1]),Integer.parseInt(separated_date[0]));
			Environment.getInstance().getClock().passTime(future_date.timeSpentSince(Environment.getInstance().getClock().getDate()));
		}

		ArrayList<Customer> listOfCustomer = Environment.getInstance().getCustomersList() ;
		int i = 0 ;
		int size = listOfCustomer.size();
		while(i< size && listOfCustomer.get(i).getID() != Integer.parseInt(customerID)) {
			i += 1 ;
		}
		if(i < size) {
			String[] separatedDestination = destination.split(":");
			try{
				listOfCustomer.get(i).bookRide(listOfCustomer.get(i).simulateRide(Double.parseDouble(separatedDestination[0]), Double.parseDouble(separatedDestination[1]), 1), Integer.parseInt(typeRide), 0);
			}
			catch(Exception e) {
				return e.getMessage();
			}
			if(listOfCustomer.get(i).getOnGoingRide() == null) {
				return "End of the SimRide-i because of a lack of Driver" ;
			}
			Ride ride = listOfCustomer.get(i).getOnGoingRide();
			Environment.getInstance().getClock().passTime(listOfCustomer.get(i).getOnGoingRide().getEndTime().timeSpentSince(Environment.getInstance().getClock().getDate()));
			System.out.println("Choose the mark for the driver : ");
			String mark = scan.nextLine();
			try {
				listOfCustomer.get(i).markRide(ride, Integer.parseInt(mark));
			} catch (MarkAttributionException e) {
				e.printStackTrace();
			}
			return ride.getInfo();

		}

		else {
			throw new CustomerIDNotFoundException("This ID does not exist");
		}
	}


}
