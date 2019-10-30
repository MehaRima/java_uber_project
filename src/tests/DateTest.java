package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import exceptions.InvalidDateException;
import helpers.Date;

public class DateTest {

	/*
	 * Asserts that an exception is thrown if the Date given to the constructor is invalid (for example setting "month" to 13).
	 */
	@Test(expected = InvalidDateException.class)
	public void testDate() throws InvalidDateException {
		System.out.println("\ntestDateConstructor");
		Date date1 = new Date(2015, 10, 23, 15, 25);
		System.out.println(date1);
		Date date2 = new Date(2015, 13, 23, 15, 25);
		System.out.println(date2);
	}

	/*
	 * Asserts that the addTime() method functions properly and throws an exception if given a negative parameter.
	 */
	@Test(expected = InvalidDateException.class)
	public void testAddTime() throws InvalidDateException {
		System.out.println("\ntestAddTime");
		Date date1 = new Date(2015, 10, 23, 15, 25);
		System.out.println(date1);
		date1.addTime(20);
		assertTrue(date1.getMinute() == 45);
		System.out.println(date1);
		date1.addTime(102000);
		assertTrue(date1.getDay() == 1 && date1.getMonth() == 1 && date1.getYear() == 2016);
		System.out.println(date1);
		date1.addTime(-15);
		System.out.println(date1);
	}

	/*
	 * Asserts that the timeSpentSince() method functions properly and throws an exception if given an anterior Date.
	 */
	@Test(expected = InvalidDateException.class)
	public void testTimeSpentSince() throws InvalidDateException {
		System.out.println("\ntestTimeSpentSince");
		Date date1 = new Date(2015, 10, 23, 15, 25);
		System.out.println(date1);
		Date date2 = new Date(2015, 10, 23, 15, 43);
		System.out.println(date2);
		assertTrue(date2.timeSpentSince(date1) == 18);
		System.out.println(date2.timeSpentSince(date1));
		Date date3 = new Date(2015, 10, 23, 15, 25);
		date3.addTime(100000);
		System.out.println(date3);
		System.out.println(date1);
		assertTrue(date3.timeSpentSince(date1) == 100000);
		System.out.println(date3.timeSpentSince(date1));
		Date date4 = new Date(2015, 10, 23, 15, 10);
		System.out.println(date4);
		System.out.println(date4.timeSpentSince(date1));
	}

	/*
	 * Asserts that the isPosteriorTo() method functions properly, especially in the case where the two Dates are equal.
	 */
	@Test
	public void testIsPosteriorTo() throws InvalidDateException {
		System.out.println("\ntestIsPosteriorTo");
		Date date1 = new Date(2015, 10, 23, 15, 25);
		Date date2 = new Date(2015, 10, 26, 15, 25);
		Date date3 = new Date(2015, 5, 23, 15, 25);
		Date date4 = new Date(2015, 10, 23, 15, 25);
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(date3);
		System.out.println(date4);
		assertTrue(date2.isPosteriorTo(date1));
		assertFalse(date3.isPosteriorTo(date1));
		assertTrue(date4.isPosteriorTo(date1));
	}

	@Test
	public void testToString() throws InvalidDateException {
		System.out.println("\ntestToString");
		Date date1 = new Date(2015, 10, 23, 15, 25);
		System.out.println(date1.toString());
		Date date2 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date2.toString());
	}
	
	@Test(expected = InvalidDateException.class)
	public void testSetYear() throws InvalidDateException {
		System.out.println("\ntestSetYear");
		Date date1 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date1);
		date1.setYear(2005);
		System.out.println(date1);
		date1.setYear(2150);
		System.out.println(date1);
	}

	@Test(expected = InvalidDateException.class)
	public void testSetMonth() throws InvalidDateException {
		System.out.println("\ntestSetMonth");
		Date date1 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date1);
		date1.setMonth(6);
		System.out.println(date1);
		date1.setMonth(14);
		System.out.println(date1);
	}

	@Test(expected = InvalidDateException.class)
	public void testSetDay() throws InvalidDateException {
		System.out.println("\ntestSetDay");
		Date date1 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date1);
		date1.setDay(28);
		System.out.println(date1);
		date1.setDay(-5);
		System.out.println(date1);
		
	}

	@Test(expected = InvalidDateException.class)
	public void testSetHour() throws InvalidDateException {
		System.out.println("\ntestSetHour");
		Date date1 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date1);
		date1.setHour(19);
		System.out.println(date1);
		date1.setHour(25);
		System.out.println(date1);
	}

	@Test(expected = InvalidDateException.class)
	public void testSetMinute() throws InvalidDateException {
		System.out.println("\ntestSetMinute");
		Date date1 = new Date(2058, 1, 8, 5, 4);
		System.out.println(date1);
		date1.setMinute(48);
		System.out.println(date1);
		date1.setMinute(65);
		System.out.println(date1);
	}

}
