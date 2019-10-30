package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.DisplayStateCommand;
import cli.SetDriverStatusCommand;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import helpers.Environment;

public class SetDriverStatusCommandTest {

	@Test
	public void setDriverStatusCommandTest() {
		Environment.wipeEnvironment();
		System.out.println("\n setDriverStatusCommandTest \n");
		AddCarDriverCommand.addCarDriverNoName("standard");
		System.out.println(DisplayStateCommand.displayStateNoArgs());
		try {
			SetDriverStatusCommand.setDriverStatusWithID("1", "offduty");
		} catch (CarAlreadyUsedException | InvalidDateException e) {
			e.printStackTrace();
		}
		System.out.println(DisplayStateCommand.displayStateNoArgs());
	}

}
