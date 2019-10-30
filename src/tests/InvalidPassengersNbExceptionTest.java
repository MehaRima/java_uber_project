package tests;

import org.junit.Test;

import exceptions.InvalidLocationException;
import exceptions.InvalidPassengersNbException;
import helpers.Coordinates;
import mainClasses.Customer;
import mainClasses.UberX;

public class InvalidPassengersNbExceptionTest {

	@Test
	public void testInvalidPassengersNbExceptionInt() throws InvalidLocationException {
		System.out.println("\ntestInvalidPassengersNbException");
		Customer testcustomer = new Customer("John", "Mills", "1234567812345678");
		System.out.println(testcustomer);
		UberX testride;
		try {
			testride = new UberX(testcustomer, Coordinates.randomCoord(), 5);
			System.out.println(testride);
		} catch (InvalidPassengersNbException e) {
			System.out.println(e.getMessage());
		}
	}

}
