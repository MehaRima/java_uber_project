package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import designPatterns.CustomerFactory;
import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.InvalidPassengersNbException;
import exceptions.MarkAttributionException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Clock;
import helpers.Coordinates;
import helpers.DriverStatus;
import helpers.Environment;
import helpers.RideStatus;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.StandardCar;
import mainClasses.UberX;

public class CustomerTest {

	/*
	 * Ensures that the simulateRide() function of Customer functions properly when given a correct input destination (that is, it computes the right price for each type of possible Ride
	 * w.r.t. the given number of passengers), and throws an exception when given an invalid destination.
	 */
	@Test(expected = InvalidLocationException.class)
	public void testSimulateRide() throws InvalidLocationException, InvalidDateException {
		System.out.println("\ntestSimulateRide");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Clock.getInstance().getDate().setHour(10);
		System.out.println("\n" + Clock.getInstance().toString());
		Ride[] simulations = testcustomer.simulateRide(3.01, 4, 3);
		assertTrue(simulations[0] != null && simulations[1] != null && simulations[2] != null && simulations[3] != null);
		for (Ride r : simulations) {
			if (r != null) {
			System.out.println(r + " : " + r.getPrice() + "€ in " + r.getTraffic().name() + " traffic (" + r.getDuration() + "mn).");
			}
		}
		Clock.getInstance().getDate().setHour(21);
		System.out.println("\n" + Clock.getInstance().toString());
		Ride[] simulations2 = testcustomer.simulateRide(-10, -5, 5);
		assertTrue(simulations2[0] == null && simulations2[1] == null && simulations2[2] != null && simulations2[3] == null);
		for (Ride r : simulations2) {
			if (r != null) {
			System.out.println(r + " : " + r.getPrice() + "€ in " + r.getTraffic().name() + " traffic (" + r.getDuration() + "mn).");
			}
		}
		Clock.getInstance().getDate().setHour(5);
		System.out.println("\n" + Clock.getInstance().toString());
		Ride[] simulations3 = testcustomer.simulateRide(51, 22, 5);
		for (Ride r : simulations3) {
			if (r != null) {
			System.out.println(r + " : " + r.getPrice() + "€ in " + r.getTraffic().name() + " traffic (" + r.getDuration() + "mn).");
			}
		}
	}

	/*
	 * Ensures that a Customer cannot book a "null" Ride simulation, that is a Ride that is not compatible with the number of passengers wanted.
	 */
	@Test(expected = RideRequestException.class)
	public void testBookRideNull() throws InvalidLocationException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		System.out.println("\ntestBookRideNull");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Ride[] simulations = testcustomer.simulateRide(-10, -5, 5);
		testcustomer.bookRide(simulations, 1, 0);
	}

	/*
	 * Ensures that a Customer cannot book a Ride while already being on one.
	 */
	@Test(expected = RideRequestException.class)
	public void testBookRideAlreadyOngoing() throws InvalidLocationException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		System.out.println("\ntestBookRideAlreadyOngoing");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		DriverFactory df = new DriverFactory();
		StandardCarFactory scf = new StandardCarFactory();
		StandardCar testStandardCar = (StandardCar) scf.createCar();
		Driver testdriver = df.createDriver(testStandardCar);
		testdriver.setStatus(DriverStatus.on_duty);
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Ride[] simulations = testcustomer.simulateRide(-10, -5, 3);
		testcustomer.bookRide(simulations, 1, 0);
		assertTrue(testcustomer.getOnGoingRide() == simulations[0]);
		System.out.println("Ongoing ride : " + testcustomer.getOnGoingRide());
		testcustomer.bookRide(simulations, 2, 0);
	}
	
	/*
	 * Ensures that a Customer cannot book a Ride once it is not "simulated" anymore, for instance once it has been trashed.
	 */
	@Test(expected = RideRequestException.class)
	public void testBookRideTrashed() throws InvalidLocationException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		System.out.println("\ntestBookRideTrashed");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		DriverFactory df = new DriverFactory();
		StandardCarFactory scf = new StandardCarFactory();
		StandardCar testStandardCar = (StandardCar) scf.createCar();
		Driver testdriver = df.createDriver(testStandardCar);
		testdriver.setStatus(DriverStatus.on_duty);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Ride[] simulations = testcustomer.simulateRide(-10, -5, 3);
		testcustomer.bookRide(simulations, 1, 0);
		assertTrue(testcustomer.getOnGoingRide() == simulations[0]);
		assertTrue(simulations[2].getStatus() == RideStatus.trashed);
		testcustomer.setOnGoingRide(null);
		testcustomer.bookRide(simulations, 2, 0);
	}
	
	/*
	 * Ensures that an exception is thrown when a Customer tries to cancel a Ride without having booked one.
	 */
	@Test(expected = RideRequestException.class)
	public void testCancelRideNoRide() throws InvalidLocationException, RideRequestException {
		System.out.println("\ntestCancelRideNoRide");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		assertTrue(testcustomer.getOnGoingRide() == null);
		testcustomer.cancelRide();
	}
	
	

	/*
	 * Asserts that the markRide method() functions normally when the right conditions concerning the completed Ride are met. Asserts that the mark is forwarded onto the Driver.
	 */
	@Test
	public void testMarkRide() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, MarkAttributionException, InvalidDateException {
		System.out.println("\ntestMarkRide");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(customer1 + ". Location : " + customer1.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(customer1, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setStatus(RideStatus.completed);
		assertTrue(testride.getAssociatedCustomer() == customer1);
		assertTrue(testride.getAssociatedDriver() == driver1);
		customer1.markRide(testride, 3);
		assertTrue(testride.getMark() == 3);
		assertTrue(driver1.getMarks().size() == 1);
	}
	
	/*
	 * Ensures that an exception is thrown when a mark out of bounds is attributed (for example 6).
	 */
	@Test(expected = MarkAttributionException.class)
	public void testMarkRideInvalidMark() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, MarkAttributionException, InvalidDateException {
		System.out.println("\ntestMarkRideInvalidMark");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(customer1 + ". Location : " + customer1.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(customer1, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setStatus(RideStatus.completed);
		assertTrue(testride.getAssociatedCustomer() == customer1);
		assertTrue(testride.getAssociatedDriver() == driver1);
		customer1.markRide(testride, 6);
	}
	
	/*
	 * Ensures that an exception is thrown when a Customer tries to double-mark a Ride.
	 */
	@Test(expected = MarkAttributionException.class)
	public void testMarkRideDoubleMark() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, MarkAttributionException, InvalidDateException {
		System.out.println("\ntestMarkRideDoubleMark");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(customer1 + ". Location : " + customer1.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(customer1, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setStatus(RideStatus.completed);
		assertTrue(testride.getAssociatedCustomer() == customer1);
		assertTrue(testride.getAssociatedDriver() == driver1);
		customer1.markRide(testride, 4);
		customer1.markRide(testride, 3);
	}
	
	/*
	 * Ensures that an exception is thrown when a Customer tries to mark a Ride that is not eligible for grading (for example an ongoing Ride).
	 */
	@Test(expected = MarkAttributionException.class)
	public void testMarkRideOngoing() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, MarkAttributionException, InvalidDateException {
		System.out.println("\ntestMarkRideOngoing");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(customer1 + ". Location : " + customer1.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(customer1, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setStatus(RideStatus.ongoing);
		assertTrue(testride.getAssociatedCustomer() == customer1);
		assertTrue(testride.getAssociatedDriver() == driver1);
		customer1.markRide(testride, 4);
	}
	
	@Test
	public void testGetInfo() throws InvalidLocationException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		System.out.println("\ntestGetInfo");
		Environment.wipeEnvironment();
		CustomerFactory cf = Environment.getInstance().getCFact();
		Customer customer1 = cf.createCustomer("Mills", "John");
		System.out.println(customer1.getInfo());
		customer1.simulateRide(0, 0, 2);
		System.out.println(customer1.getInfo());
	}
}
