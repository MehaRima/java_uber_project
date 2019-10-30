package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.MarkAttributionException;

public class MarkOutOfRangeExceptionTest {

	@Test
	public void testGetMessage() {
		System.out.println("\ntestGetMessage");
		MarkAttributionException exc = new MarkAttributionException("Test Error message.");
		System.out.println(exc.getMessage());
		assertTrue(exc.getMessage().equals("Test Error message."));
	}

}
