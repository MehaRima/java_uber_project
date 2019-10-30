package tests;

import org.junit.Test;

import exceptions.InvalidLocationException;

public class InvalidLocationExceptionTest {

	@Test
	public void testInvalidLocationExceptionDouble() {
		System.out.println("\ntestInvalidLocationExceptionDouble");
		InvalidLocationException exc1 = new InvalidLocationException(56);
		System.out.println(exc1.getMessage());
	}

	@Test
	public void testInvalidLocationException() {
		System.out.println("\ntestInvalidLocationException");
		InvalidLocationException exc1 = new InvalidLocationException();
		System.out.println(exc1.getMessage());
	}

}
