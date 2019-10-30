package tests;

import org.junit.Test;

import exceptions.InvalidDateException;

public class InvalidDateExceptionTest {

	@Test
	public void testInvalidDateExceptionString() {
		System.out.println("\ntestInvalidDateExceptionString");
		InvalidDateException exc1 = new InvalidDateException("Error message test.");
		System.out.println(exc1.getMessage() + "\n");
	}

	@Test
	public void testInvalidDateException() {
		System.out.println("\ntestInvalidDateException");
		InvalidDateException exc1 = new InvalidDateException();
		System.out.println(exc1.getMessage());
		exc1.setMessage("Changing the displayed message.");
		System.out.println(exc1.getMessage());
	}

}