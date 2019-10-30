package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.AddCustomerCommand;
import cli.DisplayCustomersCommand;
import cli.SetDriverStatusCommand;
import cli.SimRideCommand;
import cli.TimeCommands;
import exceptions.CarAlreadyUsedException;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PastTimeException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Environment;

public class DisplayCustomersCommandTest {

	@Test
	public void displayCustomersCommandTest() {
		System.out.println("\n DisplayCustomersCommandTest \n");
		Environment.wipeEnvironment();
		AddCustomerCommand.addCustomerNoName();
		AddCustomerCommand.addCustomerNoName();
		AddCarDriverCommand.addCarDriverNoName("standard");
		try {
			SetDriverStatusCommand.setDriverStatusWithID("1", "on_duty");
		} catch (CarAlreadyUsedException | InvalidDateException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			SimRideCommand.simRideWithInfo("1", "0:0", "now", "1", "4");
		} catch (NumberFormatException | CustomerIDNotFoundException | RideRequestException | PoolRequestException
				| NoMorePotentialsDriverException | InvalidLocationException | InvalidDateException | PastTimeException
				| CarAlreadyUsedException | NoAskedForPoolRequestException e) {
			System.out.println(e.getMessage());;
		}
		TimeCommands.passTimeCommand(100000);
		System.out.println("\n Mostfrequent : \n");
		System.out.println(DisplayCustomersCommand.displayCustomers("mostfrequent"));
		System.out.println("\n Mostcharged : \n");
		System.out.println(DisplayCustomersCommand.displayCustomers("mostcharged"));
		
		
	}

}
