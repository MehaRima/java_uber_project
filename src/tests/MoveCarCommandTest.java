package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.DisplayStateCommand;
import cli.MoveCarCommand;
import exceptions.InvalidCarTypeException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class MoveCarCommandTest {

	@Test
	public void moveCarCommandTest() {
		System.out.println("MoveCarCommandTest");
		Environment.wipeEnvironment();
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			e.printStackTrace();
		}
		try {
			MoveCarCommand.moveCarCommand("StandardCar1", "0", "10");
		} catch (NumberFormatException | InvalidLocationException e) {
			e.printStackTrace();
		}
		System.out.println(DisplayStateCommand.displayStateNoArgs());
		
	}

}
