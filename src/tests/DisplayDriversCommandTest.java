package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.AddCustomerCommand;
import cli.DisplayDriversCommand;
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

public class DisplayDriversCommandTest {

	@Test
	public void displayDriversCommandTest() {
		System.out.println("\n displayDriversCommandTest \n");
		Environment.wipeEnvironment();
		AddCustomerCommand.addCustomerNoName();
		AddCarDriverCommand.addCarDriverNoName("standard");
		AddCarDriverCommand.addCarDriverNoName("standard");
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
		TimeCommands.passTimeCommand(5000);
		try {
			SetDriverStatusCommand.setDriverStatusWithID("2", "on_duty");
		} catch (CarAlreadyUsedException | InvalidDateException e1) {
			System.out.println(e1.getMessage());
		}
		TimeCommands.passTimeCommand(5000);
		System.out.println("\n Mostappreciated : \n");
		try {
			System.out.println(DisplayDriversCommand.displayDrivers("mostappreciated"));
		} catch (InvalidDateException e) {
			e.printStackTrace();
		}
		System.out.println("\n Mostoccupied: \n");
		try {
			System.out.println(DisplayDriversCommand.displayDrivers("mostoccupied"));
		} catch (InvalidDateException e) {
			e.printStackTrace();
		}
		
	}

}
