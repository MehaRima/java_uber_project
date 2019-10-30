package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.CarAlreadyUsedException;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;

import helpers.Environment;

/**
 * Run this file through a terminal to start the Command Line Interface
 * @author Erwan
 *
 */
public class CLImain {

	public static void main(String[] args) throws NumberFormatException, InvalidLocationException, CustomerIDNotFoundException, CarAlreadyUsedException, InvalidDateException  {
		System.out.println("Welcome to Group 30's MyUber application ! Type 'help' for a detailed list of all available commands (beware, the commands are case-sensitive).\n");
		Scanner sc = new Scanner(System.in);

		String command = "";
		String line;
		String[] params;
		String invNbOfArgs = "The number of arguments that you entered for this command is invalid ! Type 'help' for a detailed list of all available commands.";
		String invArgs = "The argument(s) that you entered are invalid ! Type 'help' for a detailed list of all available commands.";
		String returnOfCommand = "";
		ArrayList<String> commandsOfInit = new ArrayList<String>();
		boolean inInitOrRunTest = false ;
		Environment.getInstance();


		while (!command.equals("exit")) {
			if(inInitOrRunTest) {
				System.out.println("Executing next command");
				if(commandsOfInit.isEmpty()) {
					inInitOrRunTest = false;
					line = "displayState" ; 
				}
				else {
					line = commandsOfInit.get(0);
					commandsOfInit.remove(0);
				}

			}
			else {
				System.out.print("\n\nEnter your command here : ");
				line = sc.nextLine();
			}
			params = line.split(" ");
			command = params[0];

			if (command.equals("help")) {
				returnOfCommand = "Available commands (you must not type the angular brackets when entering the arguments for the commands) :"
						+ "\n\n- init <filename.ini> : loads a pre-written configuration file and displays the results of its execution (<filename.ini> must be one of : my_uber.ini)."
						+ "\n\n- runtest <testScenarioN.txt> : loads a pre-written test scenario and displays the results of its execution (<testScenarioN.txt> must be one of : testScenario1.txt, testScenario2.txt)."
						+ "\n\n- setup <nStandardCars> <nBerlinCars> <nVanCars> <nCustomers> : sets up an universe containing the given numbers of each Car type and Customers and automatically creates an on_duty Driver for each Car (arguments must be integers)."
						+ "\n\n- addCustomer <customerName> <customerSurname> : creates a new Customer with given name and surname. Returns the detailed list of all Customers."
						+ "\n\n- addCustomer <> : creates a customer with automatically generated name and surname. Returns the detailed list of all Customers."
						+ "\n\n- addCarDriver <driverName> <driverSurname> <carType> : creates a new Car of the given type (one of 'standard', 'berline', 'van'), creates a new Driver with the given name and surname, and associates the Driver to the Car. Returns the detailed lists of all Cars and Drivers."
						+ "\n\n- addCarDriver <carType> : creates a new Car of the given type (one of 'standard', 'berline', 'van'), creates a new Driver with automatically generated name and surname, and associates the Driver to the Car. Returns the detailed lists of all Cars and Drivers."
						+ "\n\n- addDriver <driverName> <driverSurname> <carID> : creates a new Driver with given name and surname, and associates the Driver to the Car with the provided carID. Returns the detailed lists of all Cars and Drivers."
						+ "\n\n- addDriver <carID> : creates a new Driver with automatically generated name and surname, and associates the Driver to the Car with the provided carID. Returns the detailed lists of all Cars and Drivers."
						+ "\n\n- getTime <> : returns the ongoing universe's current date and time."
						+ "\n\n- passTime <nMinutes> : goes forward in time by the specified number of minutes (must be an integer), and returns the new time and date, as well as the eventual changes that occurred in the meanwhile."
						+ "\n\n- setDriverStatus <driverID> <status> : sets the status of the Driver with the given ID to the given status value (one of 'on_duty', 'off_duty', 'online', 'offline'). Returns the detailed list of all Drivers."
						+ "\n\n- moveCar <carID> <xPos> <yPos> : sets the position of the Car with the given ID at the given location. Returns the detailed list of all Cars."
						+ "\n\n- moveCustomer <customerID> <xPos> <yPos> : sets the position of the Customer with the given ID at the given location. Returns the detailed list of all Customers."
						+ "\n\n- ask4price <customerID> <destination> <date> : lets the Customer with the given ID receive a price estimation for all types of Rides, to the given destination with the format  'xPosition:yPosition' and at the given booking date (with the format 'minmin/hh/dd/mm/yyyy', or 'now')."
						+ "\n\n- simRide <customerID> <destination> <date> <rideType> <driverMark> : simulates the request and execution of a Ride for the given Customer, Ride type as an integer (1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool), destination and booking date (date is a date with the following format : 'minmin/hh/dd/mm/yyyy' or 'now' if it's immediate), and attributes the given mark to the Driver (integer between 1 and 5). Returns a detailed overview of the Ride."
						+ "\n\n- simRide_i <customerID> <destination> <date> : interactive version of the previous command. First returns the list of prices for each Ride type (you must then input the type of Ride you want as an integer : 1 = UberX, 2 = UberBlack, 3 = UberVan, 4 = UberPool) ; then lets the ride take place, and asks you for a mark (integer between 1 and 5)."
						+ "\n\n- displayState <> : displays the current state of the ongoing MyUber universe (detailed lists of all Customers, Cars, Drivers, ongoing and completed Rides)."
						+ "\n\n- displayDrivers <sortPolicy> : returns the list of all Drivers sorted in increasing order (where the sort policy is one of 'mostappreciated', 'mostoccupied')."
						+ "\n\n- displayCustomers <sortPolicy> : returns the list of all Customers sorted in increasing order (where the sort policy is one of 'mostfrequent', 'mostcharged')."
						+ "\n\n- totalCashed <> : returns the total amount of money cashed-in by all drivers in the ongoing MyUber universe."
						+ "\n\n- exit : clears the ongoing MyUber universe and exits the program.";
			} 

			else if(command.equals("init") || command.equals("runtest")) {
				if(params.length == 2) {
					inInitOrRunTest = true;
					try
					{
						File f = new File ("../src/eval/" + params[1]);
						FileReader fr = new FileReader (f);
						BufferedReader br = new BufferedReader (fr);

						try
						{
							String lineOfFile = br.readLine();
							while (lineOfFile != null)
							{
								commandsOfInit.add(lineOfFile);
								lineOfFile = br.readLine();
							}

							br.close();
							fr.close();

						}
						catch (IOException exception)
						{
							returnOfCommand = "Opening error :" + exception.getMessage();
						}
					}
					catch (FileNotFoundException exception)
					{
						returnOfCommand = "This init file has not been found";
					}
				}
				else {
					returnOfCommand = invNbOfArgs; 

				}
			}

			
			
			else if(command.equals("setup")) {
				if(params.length == 5) {
					returnOfCommand = SetupCommand.setup(params[1], params[2], params[3], params[4]);
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if (command.equals("addCustomer")) {
				if (params.length == 3) {
					AddCustomerCommand.addCustomerWithName(params[1], params[2]);
					returnOfCommand =  Environment.getInstance().getInfoCustomers();
				}
				else if (params.length == 1) {
					AddCustomerCommand.addCustomerNoName();
					returnOfCommand =  Environment.getInstance().getInfoCustomers();
				}
				else {
					returnOfCommand =invNbOfArgs;
				}
			} 


			else if (command.equals("addCarDriver")) {
				if (params.length == 4) {
					if (params[3].equals("standard") || params[3].equals("berline") || params[3].equals("van")) {
						AddCarDriverCommand.addCarDriverWithName(params[1], params[2], params[3]);
						returnOfCommand = Environment.getInstance().getInfoCars() +"\n \n" + Environment.getInstance().getInfoDrivers();
					}
					else {
						returnOfCommand = invArgs;
					}
				}
				else if (params.length == 2) {
					if (params[1].equals("standard") || params[1].equals("berline") || params[1].equals("van")) {
						AddCarDriverCommand.addCarDriverNoName(params[1]);
						returnOfCommand = Environment.getInstance().getInfoCars() +"\n \n" + Environment.getInstance().getInfoDrivers();
					}
					else {
						returnOfCommand =invArgs;
					}
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			} 

			else if(command.equals("addDriver")) {
				try{
					if(params.length == 4) {

						AddDriverCommand.addDriverWithName(params[1], params[2], params[3]);
						returnOfCommand = Environment.getInstance().getInfoCars() +"\n \n" + Environment.getInstance().getInfoDrivers();
					}

					else if (params.length == 2) {
						AddDriverCommand.addDriverNoName(params[1]);
						returnOfCommand = Environment.getInstance().getInfoCars() +"\n \n" + Environment.getInstance().getInfoDrivers();
					}

					else {
						returnOfCommand =invNbOfArgs;
					}
				}
				catch(Exception e) {
					returnOfCommand = e.getMessage();


				}

			}


			else if(command.equals("getTime")) {

				if(params.length == 1) {

					returnOfCommand = TimeCommands.getTimeCommand();
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if(command.equals("passTime")) {
				if(params.length == 2) {
					TimeCommands.passTimeCommand(Integer.valueOf(params[1]));
					returnOfCommand = Environment.getInstance().getClock().getDate().toString();
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}


			else if (command.equals("setDriverStatus")) {
				if(params.length == 3) {


					SetDriverStatusCommand.setDriverStatusWithID(params[1], params[2]);
					returnOfCommand = Environment.getInstance().getInfoDrivers();
				}

				else {
					returnOfCommand = invNbOfArgs ;
				}
			}
			else if(command.equals("moveCar")) {
				if(params.length == 4) {
					try {
						MoveCarCommand.moveCarCommand(params[1], params[2], params[3]);
						returnOfCommand = Environment.getInstance().getInfoCars();
					} catch (Exception e) {
						returnOfCommand = e.getMessage();
					}
				}

				else {
					returnOfCommand = invNbOfArgs;

				}
			}

			else if (command.equals("moveCustomer")) {
				if(params.length == 4) {

					MoveCustomerCommand.moveCustomerCommand(params[1], params[2], params[3]);
					returnOfCommand = Environment.getInstance().getInfoCustomers();
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if (command.equals("ask4price")) {
				if(params.length == 4) {
					try {
						returnOfCommand = Ask4PriceCommand.ask4PricesCommand(params[1], params[2], params[3]);
					}catch (Exception e) {
						returnOfCommand = e.getMessage();
					}
				}

				else {
					returnOfCommand = invNbOfArgs;
				}

			}

			else if(command.equals("simRide")) {
				if(params.length == 6) {
					try {
						returnOfCommand = SimRideCommand.simRideWithInfo(params[1], params[2], params[3], params[4], params[5]);
					} catch (Exception e) {
						returnOfCommand = e.getMessage();
					}
				}

				else {
					returnOfCommand = invNbOfArgs;
				}
			}


			else if(command.equals("simRide_i")) {
				if(params.length == 4) {
					try {
						returnOfCommand = SimRideCommand.simRide_i(params[1], params[2], params[3]);
					} catch (Exception e) {
						returnOfCommand = e.getMessage();
					}
				}
				else {
					returnOfCommand = invNbOfArgs;
				}

			}


			else if (command.equals("displayState")) {
				if (params.length == 1) {
					returnOfCommand = DisplayStateCommand.displayStateNoArgs();
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if(command.equals("displayDrivers")) {
				if (params.length == 2) {

					returnOfCommand = DisplayDriversCommand.displayDrivers(params[1]);
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if(command.equals("displayCustomers")) {
				if(params.length == 2) {

					returnOfCommand = DisplayCustomersCommand.displayCustomers(params[1]);
				}

				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if(command.equals("totalCashed")) {
				if(params.length == 1) {
					returnOfCommand = TotalCashedCommand.totalCashed();
				}
				else {
					returnOfCommand = invNbOfArgs;
				}
			}

			else if (command.equals("exit")) {
				returnOfCommand = "Thank you for using our MyUber application, we hope to see you back soon !\nAntoine and Erwan";
				Environment.wipeEnvironment();

			}


			else {
				returnOfCommand = "Invalid command ! Type 'help' for a detailed list of all available commands (beware, the commands are case-sensitive).";
			}


			System.out.println(returnOfCommand);

		}


		sc.close();
	}

}
