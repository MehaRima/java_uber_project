package tests;

import org.junit.Test;

import cli.DisplayStateCommand;
import cli.SetupCommand;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class SetupCommandTest {

	@Test
	public void setupCommandTest() {
		Environment.wipeEnvironment();
		System.out.println("\n SetupCommandTest \n");
		try {
			SetupCommand.setup("1", "2", "3", "4");
		} catch (InvalidLocationException | CarAlreadyUsedException | InvalidDateException e) {
			e.printStackTrace();
		}
		System.out.println(DisplayStateCommand.displayStateNoArgs());
	}

}
