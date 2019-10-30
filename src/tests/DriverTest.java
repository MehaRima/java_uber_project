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
import exceptions.RideRequestException;
import helpers.Coordinates;
import helpers.DriverStatus;
import helpers.Environment;
import helpers.RideStatus;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.UberX;

public class DriverTest {

	/*
	 * Tests the Observers pattern's update() method when triggered by a Ride request by creating a test Ride request, and passing it as the argument of a Driver's update() method
	 * with various statuses.
	 */
	@Test
	public void testUpdateRideRequest() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, InvalidDateException {
		System.out.println("\ntestUpdateRideRequest");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		testride.setStatus(RideStatus.unconfirmed);
		System.out.println(testride.toString() + ". Status : " + testride.getStatus().name());
		driver1.update(testride);
		testride.setStatus(RideStatus.confirmed);
		System.out.println(testride.toString() + ". Status : " + testride.getStatus().name());
		driver1.update(testride);
		testride.setStatus(RideStatus.cancelled);
		System.out.println(testride.toString() + ". Status : " + testride.getStatus().name());
		driver1.update(testride);
	}

	/*
	 * Tests the Observers pattern's update() method when triggered by a change in the currentDriver of the Driver's car.
	 */
	@Test
	public void testUpdateDriverChange() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException {
		System.out.println("\ntestUpdateDriverChange");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Driver driver2 = df.createDriver("Paul", "Dubois", car1);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
		driver1.update(driver2);
		driver2.update(null);
	}
	
	/*
	 * Asserts that the acceptRide() method throws an exception when a Driver is trying to accept a Ride that is not in their OnGoingRideRequests list.
	 */
	@Test(expected = RideRequestException.class)
	public void testAcceptRideNotInOnGoingRR() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, RideRequestException, InvalidDateException {
		System.out.println("\ntestAcceptRideNotInOnGoingRR");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		System.out.println(driver1.getOnGoingRideRequests());
		driver1.acceptRide(testride);
	}

	/*
	 * Asserts that the acceptRide() method throws an exception when a Driver is trying to accept a Ride that's not "unconfirmed" (after acceptation by another Driver), and that after
	 * acceptation of a Ride : the Ride is deleted from all Observer's onGoingRideRequests, the Ride's Observers list is cleared and each Observer's status is updated accordingly.
	 */ 
	@Test(expected = RideRequestException.class)
	public void testAcceptRideAlreadyConfirmed() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, RideRequestException, InvalidDateException {
		System.out.println("\ntestAcceptRideAlreadyConfirmed");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Car car2 = scf.createCar(2, 0);
		Driver driver2 = df.createDriver("Paul", "Dubois", car2);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		testride.addObserver(driver1);
		testride.addObserver(driver2);
		testride.setStatus(RideStatus.unconfirmed);
		driver1.acceptRide(testride);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0);
		assertTrue(driver1.getStatus() == DriverStatus.on_a_ride);
		assertTrue(driver2.getStatus() == DriverStatus.on_duty);
		assertTrue(driver2.getOnGoingRideRequests().size() == 0);
		assertTrue(testride.getObserverDrivers().size() == 0);
		driver2.acceptRide(testride);
	}
	
	/*
	 * Sends a Ride request to a Driver, and first asserts that is is added only to their list of OnGoingRideRequests and that the Driver is added to the Observers list for the Ride.
	 * Then, when the Driver declines the Ride, asserts that it is removed from their OnGoingRideRequests and that the Driver is removed from the Observers list.
	 * Finally, ensures that a Driver that did not receive the Ride request cannot decline it.
	 */
	@Test(expected = RideRequestException.class)
	public void testDeclineRideNotInOnGoingRR() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, RideRequestException, InvalidDateException {
		System.out.println("\ntestDeclineRideNotInOnGoingRR");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Car car2 = scf.createCar(2, 0);
		Driver driver2 = df.createDriver("Paul", "Dubois", car2);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		testride.addObserver(driver1);
		testride.setStatus(RideStatus.unconfirmed);
		assertTrue(driver2.getOnGoingRideRequests().size() == 0);
		assertTrue(testride.getObserverDrivers().size() == 1);
		driver1.declineRide(testride);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0);
		assertTrue(testride.getObserverDrivers().size() == 0);
		driver2.declineRide(testride);
	}
	
	/*
	 * Ensures that an exception is thrown when a Driver is trying to decline a Ride that is not unconfirmed anymore.
	 */
	@Test(expected = RideRequestException.class)
	public void testDeclineRideNotUnconfirmed() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, RideRequestException, InvalidDateException {
		System.out.println("\ntestDeclineRideNotUnconfirmed");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Car car2 = scf.createCar(2, 0);
		Driver driver2 = df.createDriver("Paul", "Dubois", car2);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		testride.setStatus(RideStatus.confirmed);
		testride.addObserver(driver1);
		driver1.declineRide(testride);
	}

	/*
	 * Asserts that when a driver sets their status on "on_duty", they become the currentDriver of their Car if it is not used, and that it throws an exception otherwise.
	 */
	@Test(expected  = CarAlreadyUsedException.class)
	public void testSetStatusAlreadyUsed() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestSetStatusAlreadyUsed");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		driver1.setStatus(DriverStatus.on_duty);
		assertTrue(car1.getCurrentDriver() == driver1);
		Driver driver2 = df.createDriver("Paul", "Dubois", car1);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
	}
	
	/*
	 * Asserts that when a Driver sets their status on other than "on_duty", it automatically declines their OnGoingRideRequests, and if the Driver sets their status to "offline",
	 * the currentDriver of their Car becomes null.
	 */
	@Test
	public void testSetStatusWithOnGoingRR() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, InvalidDateException {
		System.out.println("\ntestSetStatusWithOnGoingRR");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation().toString());
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		driver1.setStatus(DriverStatus.on_duty);
		assertTrue(car1.getCurrentDriver() == driver1);
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		testride.addObserver(driver1);
		assertTrue(driver1.getOnGoingRideRequests().size() == 1);
		driver1.setStatus(DriverStatus.off_duty);
		assertTrue(car1.getCurrentDriver() == driver1);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0);
		assertTrue(testride.getObserverDrivers().size() == 0);
		driver1.setStatus(DriverStatus.offline);
		assertTrue(car1.getCurrentDriver() == null);
	}
	
	@Test
	public void testGetInfo() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestSetStatusWithOnGoingRR");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Car car1 = scf.createCar(0, 3);
		Driver driver1 = df.createDriver("Dupont", "Pierre", car1);
		System.out.println(driver1.getInfo());
		driver1.setStatus(DriverStatus.on_duty);
		driver1.getMarks().add(4);
		driver1.getMarks().add(3);
		driver1.getMarks().add(4);
		System.out.println(driver1.getInfo());
	}

}
