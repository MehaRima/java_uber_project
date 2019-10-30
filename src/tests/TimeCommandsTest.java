package tests;

import org.junit.Test;

import cli.TimeCommands;
import helpers.Environment;

public class TimeCommandsTest {

	@Test
	public void timeCommandsTest() {
		Environment.wipeEnvironment();
		System.out.println("\n timeCommandsTest \n");
		System.out.println(TimeCommands.getTimeCommand());
		TimeCommands.passTimeCommand(60);
		System.out.println(TimeCommands.getTimeCommand());
		TimeCommands.passTimeCommand(1440);
		System.out.println(TimeCommands.getTimeCommand());
		
		
	}

}
