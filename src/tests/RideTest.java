package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import designPatterns.CustomerFactory;
import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.InvalidPassengersNbException;
import helpers.Clock;
import helpers.Coordinates;
import helpers.DriverStatus;
import helpers.Environment;
import helpers.RideStatus;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.UberX;


public class RideTest {
	
	/*
	 * Tests the determineTrafficState() method and helps verifiy that the probability distribution between 11h and 17h is respected.
	 */
	@Test
	public void testDetermineTrafficState11_17() throws InvalidPassengersNbException, InvalidLocationException {
		System.out.println("\ntestDetermineTrafficState11_17");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills");
		System.out.println(testcustomer);
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		System.out.println(Clock.getInstance());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
	}
	
	/*
	 * Tests the determineTrafficState() method and helps verifiy that the probability distribution between 17h and 22h is respected.
	 */
	@Test
	public void testDetermineTrafficState17_22() throws InvalidPassengersNbException, InvalidLocationException, InvalidDateException {
		System.out.println("\ntestDetermineTrafficState17_22");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills");
		System.out.println(testcustomer);
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		Clock.getInstance().getDate().setHour(20);
		System.out.println(Clock.getInstance());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
	}
	
	/*
	 * Tests the determineTrafficState() method and helps verifiy that the probability distribution between 22h and 7h is respected.
	 */
	@Test
	public void testDetermineTrafficState22_07() throws InvalidPassengersNbException, InvalidLocationException, InvalidDateException {
		System.out.println("\ntestDetermineTrafficState22_07");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills");
		System.out.println(testcustomer);
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		Clock.getInstance().getDate().setHour(3);
		System.out.println(Clock.getInstance());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
	}
	
	/*
	 * Tests the determineTrafficState() method and helps verifiy that the probability distribution between 7h and 11h is respected.
	 */
	@Test
	public void testDetermineTrafficState07_11() throws InvalidPassengersNbException, InvalidLocationException, InvalidDateException {
		System.out.println("\ntestDetermineTrafficState07_11");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills");
		System.out.println(testcustomer);
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		Clock.getInstance().getDate().setHour(9);
		System.out.println(Clock.getInstance());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
		System.out.println(testride.determineTrafficState());
	}
	
	/*
	 * Asserts that the computeDuration() function returns the right duration depending on the traffic state.
	 */
	@Test
	public void testComputeDuration() throws InvalidPassengersNbException, InvalidLocationException {
		System.out.println("\ntestDetermineTrafficState07_11");
		Environment.wipeEnvironment();
		CustomerFactory cf = new CustomerFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer);
		Ride testride = new UberX(testcustomer, new Coordinates(15, 0), 2);
		System.out.println(testride);
		System.out.println(testride.getDuration() +"mn in " + testride.getTraffic().name() + " traffic.");
		assertTrue(testride.getDuration() == testride.computeDuration(testride.getStartingPoint(), testride.getDestination()));
		assertTrue(testride.getDuration() == 60 || testride.getDuration() == 120 || testride.getDuration() == 300);
		
	}
	
	/*
	 * Asserts that the computePickUpTime() method provides the expected result.
	 */
	@Test
	public void testComputePickUpTime() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestComputePickUpTime");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setPickupTime(testride.computePickUpTime());
		System.out.println(Clock.getInstance().toString());
		assertTrue(testride.getPickupTime().timeSpentSince(Clock.getInstance().getDate()) == 20 || testride.getPickupTime().timeSpentSince(Clock.getInstance().getDate()) == 40 || testride.getPickupTime().timeSpentSince(Clock.getInstance().getDate()) == 100 );
		System.out.println(testride + " estimated pickup time : " + testride.getPickupTime() + " in " + testride.getTraffic().name() + " traffic.");
	}
	
	/*
	 * Asserts that the computeEndTime() method provides the expected result.
	 */
	@Test
	public void testComputeEndTime() throws InvalidLocationException, InvalidPassengersNbException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestComputeEndTime");
		Environment.wipeEnvironment();
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer testcustomer = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(testcustomer + ". Location : " + testcustomer.getLocation());
		Car car1 = scf.createCar(0, 5);
		Driver driver1 = df.createDriver("Pierre", "Dupont", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, new Coordinates(10, 0), 2);
		System.out.println(testride + ". Destination : " + testride.getDestination());
		testride.setAssociatedDriver(driver1);
		testride.setPickupTime(testride.computePickUpTime());
		testride.setEndTime(testride.computeEndTime());
		System.out.println("Pickup time : " + testride.getPickupTime());
		System.out.println(testride + " estimated end time : " + testride.getEndTime() + " in " + testride.getTraffic().name() + " traffic.");
		assertTrue(testride.getEndTime().timeSpentSince(testride.getPickupTime()) == 40 || testride.getEndTime().timeSpentSince(testride.getPickupTime()) == 80 || testride.getEndTime().timeSpentSince(testride.getPickupTime()) == 200 );
		
	}

	/*
	 * Tests the addObserver() method by creating a Customer, 3 Drivers with a StandardCar each, and a test UberX Ride, and then adding 2 of the Drivers to the list of Observers for the test Ride.
	 * Asserts that 2 Observers have been added to the ObserversList at the end and that the Drivers' OnGoingRideRequests has been updated.
	 */
	@Test
	public void testAddObserver() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, InvalidDateException {
		System.out.println("\ntestAddObserver");
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
		Car car3 = scf.createCar(8, 3);
		Driver driver3 = df.createDriver("Jacques", "Dumont", car3);
		driver3.setStatus(DriverStatus.on_duty);
		System.out.println(driver3 + ". Location : " + driver3.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		System.out.println(testride.getObserverDrivers());
		testride.addObserver(driver1);
		System.out.println(testride.getObserverDrivers());
		assertTrue(testride.getObserverDrivers().size() == 1);
		assertTrue(driver1.getOnGoingRideRequests().size() == 1);
		testride.addObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		assertTrue(testride.getObserverDrivers().size() == 2);
	}

	/*
	 * Tests the removeObserver() method by creating a Customer, 3 Drivers with a StandardCar each, and a test UberX Ride, adding 2 of the Drivers to the list of Observers for the test Ride,
	 * and then removing them one by one. Asserts that the Observers list is empty at the end and that the Drivers' OnGoingRideRequests has been updated.
	 */
	@Test
	public void testRemoveObserver() throws InvalidPassengersNbException, InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestRemoveObserver");
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
		Car car3 = scf.createCar(8, 3);
		Driver driver3 = df.createDriver("Jacques", "Dumont", car3);
		driver3.setStatus(DriverStatus.on_duty);
		System.out.println(driver3 + ". Location : " + driver3.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		System.out.println(testride.getObserverDrivers());
		testride.addObserver(driver1);
		System.out.println(testride.getObserverDrivers());
		testride.addObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		testride.removeObserver(driver1);
		System.out.println(testride.getObserverDrivers());
		assertTrue(testride.getObserverDrivers().size() == 1);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0);
		testride.removeObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		assertTrue(testride.getObserverDrivers().size() == 0);
	}

	/*
	 * Tests the removeAllObservers() method by creating a Customer, 3 Drivers with a StandardCar each, and a test UberX Ride, adding 2 of the Drivers to the list of Observers for the test Ride,
	 * and then removing them all at once. Asserts that the Observers list is empty at the end and that the Drivers' OnGoingRideRequests has been updated.
	 */
	@Test
	public void testRemoveAllObservers() throws InvalidPassengersNbException, InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\ntestRemoveAllObservers");
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
		Car car3 = scf.createCar(8, 3);
		Driver driver3 = df.createDriver("Jacques", "Dumont", car3);
		driver3.setStatus(DriverStatus.on_duty);
		System.out.println(driver3 + ". Location : " + driver3.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		System.out.println(testride);
		System.out.println(testride.getObserverDrivers());
		testride.addObserver(driver1);
		System.out.println(testride.getObserverDrivers());
		testride.addObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		testride.removeAllObservers();
		System.out.println(testride.getObserverDrivers());
		assertTrue(testride.getObserverDrivers().size() == 0);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0);
	}

	/*
	 * Tests the notifyObservers() method by creating a Customer, 3 Drivers with a StandardCar each, and a test UberX Ride, adding 2 of the Drivers to the list of Observers for the test Ride,
	 * and then asserts the Drivers are notified when the status of the test Ride changes to confirmed or cancelled.
	 */
	@Test
	public void testNotifyObserversViaSetStatus() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, InvalidDateException {
		System.out.println("\ntestNotifyObserversViaSetStatus");
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
		Car car3 = scf.createCar(8, 3);
		Driver driver3 = df.createDriver("Jacques", "Dumont", car3);
		driver3.setStatus(DriverStatus.on_duty);
		System.out.println(driver3 + ". Location : " + driver3.getCar().getLocation().toString());
		Ride testride = new UberX(testcustomer, Coordinates.randomCoord(), 2);
		testride.setStatus(RideStatus.unconfirmed);
		System.out.println(testride + ". Status : " + testride.getStatus().name());
		testride.addObserver(driver1);
		testride.addObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		testride.setStatus(RideStatus.confirmed);
		System.out.println(testride + ". Status : " + testride.getStatus().name());
		testride.removeAllObservers();
		assertTrue(testride.getObserverDrivers().size() == 0);
		assertTrue(driver1.getOnGoingRideRequests().size() == 0 && driver2.getOnGoingRideRequests().size() == 0);
		testride.setStatus(RideStatus.unconfirmed);
		System.out.println(testride + ". Status : " + testride.getStatus().name());
		testride.addObserver(driver1);
		testride.addObserver(driver2);
		System.out.println(testride.getObserverDrivers());
		testride.setStatus(RideStatus.cancelled);
		System.out.println(testride + ". Status : " + testride.getStatus().name());
		testride.setStatus(RideStatus.completed);
		System.out.println(testride + ". Status : " + testride.getStatus().name());
		
	}
	
	


}
