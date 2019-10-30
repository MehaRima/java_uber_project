package tests;

import org.junit.Test;

import cli.AddCustomerCommand;
import cli.Ask4PriceCommand;
import exceptions.CustomerIDNotFoundException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class Ask4PriceCommandTest {

	@Test
	public void ask4PricesCommandTest() {
		System.out.println("\n ask4PricesCommandTest \n");
		Environment.wipeEnvironment();
		AddCustomerCommand.addCustomerNoName();
		try {
			System.out.println(Ask4PriceCommand.ask4PricesCommand("1", "0:0", "now"));
		} catch (NumberFormatException | InvalidLocationException | CustomerIDNotFoundException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(Ask4PriceCommand.ask4PricesCommand("1", "-15:0", "00/15/1/12/2018"));
		} catch (NumberFormatException | InvalidLocationException | CustomerIDNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
