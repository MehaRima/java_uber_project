package tests;

import org.junit.Test;

import cli.SetDriverStatusCommand;
import cli.SetupCommand;
import cli.SimRideCommand;
import cli.TimeCommands;
import cli.TotalCashedCommand;
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

public class TotalCashedCommandTest {

	@Test
	public void totalCashedCommandTest() {
		Environment.wipeEnvironment();
		System.out.println("\n TotalCashedCommandTest \n");
		try {
			SetupCommand.setup("1", "0", "0", "1");
		} catch (InvalidLocationException | CarAlreadyUsedException | InvalidDateException e) {
			e.printStackTrace();
		}
		try {
			SetDriverStatusCommand.setDriverStatusWithID("1", "on_duty");
		} catch (CarAlreadyUsedException | InvalidDateException e) {
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
		
		TimeCommands.passTimeCommand(1000);
		System.out.println(TotalCashedCommand.totalCashed());
	}

}
