package tests;

import org.junit.Test;

import cli.AddCustomerCommand;
import cli.DisplayStateCommand;
import cli.MoveCustomerCommand;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class MoveCustomerCommandTest {

	@Test
	public void moveCustomerCommandTest() {
		Environment.wipeEnvironment();
		System.out.println("\n MoveCustomerCommandTest \n");
		AddCustomerCommand.addCustomerWithName("Juste", "LeBlanc");
		try {
			MoveCustomerCommand.moveCustomerCommand("1", "0", "-50");
		} catch (NumberFormatException | InvalidLocationException | CustomerIDNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(DisplayStateCommand.displayStateNoArgs());
	}

}
