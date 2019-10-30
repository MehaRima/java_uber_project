package tests;

import org.junit.Test;

import cli.AddCustomerCommand;
import helpers.Environment;

public class AddCustomerCommandTest {

	@Test
	public void  addCustomerWithNameTest() {
		Environment.wipeEnvironment();
		System.out.println("\n addCustomerWithNameTest \n");
		AddCustomerCommand.addCustomerWithName("Pierre", "DeLaCroix");
		AddCustomerCommand.addCustomerWithName("Juste", "");
		System.out.println(Environment.getInstance().getInfoCustomers());
	}
	
	@Test
	public void  addCustomerNoNameTest() {
		Environment.wipeEnvironment();
		System.out.println("\n addCustomerNoNameTest \n");
		AddCustomerCommand.addCustomerNoName();
		AddCustomerCommand.addCustomerNoName();
		System.out.println(Environment.getInstance().getInfoCustomers());
	}

}
