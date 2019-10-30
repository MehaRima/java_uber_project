package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Clock;

public class ClockTest {

	/*
	 * Asserts that only one instance of Clock is ever created at a given time, and that another call of getInstance() returns the same unique Clock.
	 */
	@Test
	public void testGetInstance() throws InvalidDateException {
		Clock.wipeClock();
		System.out.println("\ntestGetInstance");
		Clock uberClock = Clock.getInstance();
		System.out.println(uberClock);
		uberClock.getDate().setYear(2080);
		System.out.println(uberClock);
		Clock uberClock2 = Clock.getInstance();
		System.out.println(uberClock2);
		assertTrue(uberClock == uberClock2);
	}
	
	/*
	 * Asserts that the wipeClock method resets the Clock instance properly.
	 */
	@Test
	public void testWipeclock() throws InvalidDateException {
		Clock.wipeClock();
		System.out.println("\ntestWipeClock");
		Clock clock = Clock.getInstance();
		System.out.println(clock);
		clock.getDate().setYear(2080);
		System.out.println(clock);
		Clock.wipeClock();
		clock = Clock.getInstance();
		assertTrue(clock.getDate().getYear() != 2080);
	}


	@Test
	public void testPassTime() throws InvalidDateException, CarAlreadyUsedException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Clock.wipeClock();
		System.out.println("\ntestPassTime");
		Clock clock = Clock.getInstance();
		System.out.println(clock.toString());
		clock.passTime(15);
		assertTrue(clock.getDate().getMinute() == 15);
		System.out.println(clock.toString());
		clock.passTime(2000);
		assertTrue(clock.getDate().getHour() == 21);
		System.out.println(clock.toString());
		clock.passTime(1000000);
		System.out.println(clock.toString());
	}
	
	/*
	 * Ensures that an exception is thrown when passTime() is given a negative input duration.
	 */
	@Test(expected = InvalidDateException.class)
	public void testPassNegativeTime() throws InvalidDateException, CarAlreadyUsedException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Clock.wipeClock();
		System.out.println("\ntestPassNegativeTime");
		Clock clock = Clock.getInstance();
		System.out.println(clock.toString());
		clock.passTime(-15);
	}
	
	/*
	 * Ensures that an exception is thrown when passTime() is given a duration that would exceed 2100.
	 */
	@Test
	public void testPassTooMuchTime() throws CarAlreadyUsedException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Clock.wipeClock();
		System.out.println("\ntestPassTooMuchTime");
		Clock clock = Clock.getInstance();
		System.out.println(clock.toString());
		try {
			clock.passTime(52560000);
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		}
		assertTrue(clock.getDate().getYear() == 2018);
		System.out.println(clock);
	}
	
	/*
	 * Asserts that the toString() method formats the Clock correctly.
	 */
	@Test
	public void testToString() throws InvalidDateException {
		Clock.wipeClock();
		System.out.println("\ntestToString");
		Clock clock = Clock.getInstance();
		System.out.println(clock.toString());
		clock.getDate().setDay(15);
		clock.getDate().setMonth(4);
		clock.getDate().setHour(8);
		clock.getDate().setMinute(17);
		System.out.println(clock.toString());
		assertTrue(clock.toString().equals("Current time : 08:17, 15/04/2018"));
	}
}