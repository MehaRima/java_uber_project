package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.AddCustomerCommand;
import cli.DisplayStateCommand;
import exceptions.InvalidCarTypeException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class DisplayStateCommandTest {

	@Test
	public void displayStateCommandTest() {
		System.out.println("\n displayStateCommandTest \n");
		Environment.wipeEnvironment();
		AddCustomerCommand.addCustomerNoName();
		AddCustomerCommand.addCustomerNoName();
		AddCustomerCommand.addCustomerNoName();
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			e.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("berline");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			e.printStackTrace();
		}
		System.out.println(DisplayStateCommand.displayStateNoArgs());
		
	}

}
