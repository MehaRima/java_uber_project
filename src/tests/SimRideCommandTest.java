package tests;

import org.junit.Test;

import cli.DisplayStateCommand;
import cli.SetDriverStatusCommand;
import cli.SetupCommand;
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

public class SimRideCommandTest {

	@Test
	public void simRideCommandTest() {
		Environment.wipeEnvironment();
		System.out.println("\n SimRideCommandTest \n");
		try {
			SetupCommand.setup("1", "0", "0", "1");
		} catch (InvalidLocationException | CarAlreadyUsedException | InvalidDateException e) {
			e.printStackTrace();
		}
		try {
			SimRideCommand.simRideWithInfo("1", "0:0", "00/13/1/12/2018", "1", "4");
		} catch (NumberFormatException | CustomerIDNotFoundException | RideRequestException | PoolRequestException
				| NoMorePotentialsDriverException | InvalidLocationException | InvalidDateException | PastTimeException
				| CarAlreadyUsedException | NoAskedForPoolRequestException e) {
			e.printStackTrace();
		}
		TimeCommands.passTimeCommand(60);
		try {
			SetDriverStatusCommand.setDriverStatusWithID("1", "on_duty");
		} catch (CarAlreadyUsedException | InvalidDateException e) {
			e.printStackTrace();
		}
		try {
			SimRideCommand.simRideWithInfo("1", "0:0", "now", "1", "4");
		} catch (NumberFormatException | CustomerIDNotFoundException | RideRequestException | PoolRequestException
				| NoMorePotentialsDriverException | InvalidLocationException | InvalidDateException | PastTimeException
				| CarAlreadyUsedException | NoAskedForPoolRequestException e) {
			e.printStackTrace();
		}
		TimeCommands.passTimeCommand(1000);
		System.out.println(DisplayStateCommand.displayStateNoArgs());
	}

}
