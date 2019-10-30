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
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Clock;
import helpers.Coordinates;
import helpers.Date;
import helpers.DriverStatus;
import helpers.Environment;
import helpers.RideStatus;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.StandardCar;
import mainClasses.UberBlack;
import mainClasses.UberX;

public class EnvironmentTest {

	/*
	 * Asserts that only one instance of Environment is ever created at a given time, and that another call of getInstance() returns the same unique Environment.
	 */
	@Test
	public void testGetInstance() throws InvalidDateException {
		Environment.wipeEnvironment();
		System.out.println("\ntestGetInstance");
		Environment env = Environment.getInstance();
		Environment env2 = Environment.getInstance();
		System.out.println(env2);
		env.getClock().getDate().setYear(2080);
		System.out.println(env);
		System.out.println(env2);
		assertTrue(env == env2);
	}

	/*
	 * Asserts that the wipeEnvironment method resets the Environment instance properly.
	 */
	@Test
	public void testWipeEnvironment() throws InvalidDateException {
		Environment.wipeEnvironment();
		System.out.println("\ntestWipeEnvironment");
		Environment env = Environment.getInstance();
		System.out.println(env);
		env.getClock().getDate().setYear(2080);
		System.out.println(env);
		Environment.wipeEnvironment();
		Environment env2 = Environment.getInstance();
		System.out.println(env2);
		assertTrue(env2.getClock().getDate().getYear() != 2080);
	}

	/*
	 * Asserts that the refreshOnGoingRides() method is called properly when time is added to the Environment's clock, and that it modifies the ongoing Rides accordingly, as well as the
	 * concerned Customers and Drivers.
	 */
	@Test
	public void testRefreshOnGoingRides() throws InvalidLocationException, CarAlreadyUsedException, InvalidPassengersNbException, InvalidDateException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestRefreshOnGoingRides");
		System.out.println(Clock.getInstance());
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("John", "Mills", 0, 0);
		System.out.println(customer1 + ". Location : " + customer1.getLocation().toString());
		Customer customer2 = cf.createCustomer("Jack", "Brandt", -1, 2);
		System.out.println(customer2 + ". Location : " + customer2.getLocation().toString());
		Car car1 = scf.createCar(2, 0);
		Driver driver1 = df.createDriver("Paul", "Dubois", car1);
		driver1.setStatus(DriverStatus.on_duty);
		System.out.println(driver1 + ". Location : " + driver1.getCar().getLocation().toString());
		Car car2 = scf.createCar(0, 3);
		Driver driver2 = df.createDriver("Pierre", "Dupont", car2);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(driver2 + ". Location : " + driver2.getCar().getLocation().toString());
		Ride ride1 = new UberX(customer1, new Coordinates(-4, 0), 2);
		customer1.setOnGoingRide(ride1);
		System.out.println(ride1 + ". Destination : " + ride1.getDestination());
		Ride ride2 = new UberBlack(customer2, new Coordinates(-1, 5), 1);
		customer2.setOnGoingRide(ride2);
		System.out.println(ride2 + ". Destination : " + ride2.getDestination());
		ride1.setAssociatedDriver(driver1);
		ride2.setAssociatedDriver(driver2);
		ride1.setStatus(RideStatus.confirmed);
		ride2.setStatus(RideStatus.ongoing);
		driver2.setStatus(DriverStatus.on_a_ride);
		driver1.setStatus(DriverStatus.on_a_ride);
		Date clonedate1 = new Date(Clock.getInstance().getDate().getYear(), Clock.getInstance().getDate().getMonth(), Clock.getInstance().getDate().getDay(), Clock.getInstance().getDate().getHour(), Clock.getInstance().getDate().getMinute());
		Date clonedate2 = new Date(Clock.getInstance().getDate().getYear(), Clock.getInstance().getDate().getMonth(), Clock.getInstance().getDate().getDay(), Clock.getInstance().getDate().getHour(), Clock.getInstance().getDate().getMinute());
		Date clonedate3 = new Date(Clock.getInstance().getDate().getYear(), Clock.getInstance().getDate().getMonth(), Clock.getInstance().getDate().getDay(), Clock.getInstance().getDate().getHour(), Clock.getInstance().getDate().getMinute());
		clonedate1.addTime(10);
		clonedate2.addTime(15);
		clonedate3.addTime(25);
		ride1.setPickupTime(clonedate1);
		ride1.setEndTime(clonedate3);
		ride2.setPickupTime(clonedate1);
		ride2.setEndTime(clonedate2);
		System.out.println(ride1.getPickupTime());
		System.out.println(ride2.getEndTime());
		Environment.getInstance().getOnGoingRides().add(ride1);
		Environment.getInstance().getOnGoingRides().add(ride2);
		System.out.println(Clock.getInstance());
		Clock.getInstance().passTime(20);
		System.out.println(Clock.getInstance());
		assertTrue(Environment.getInstance().getBookOfRides().size() == 1);
		assertTrue(Environment.getInstance().getOnGoingRides().size() == 1);
		assertTrue(ride1.getStatus() == RideStatus.ongoing);
		assertTrue(ride2.getStatus() == RideStatus.completed);
		assertTrue(driver2.getStatus() == DriverStatus.on_duty);
		assertTrue(driver1.getStatus() == DriverStatus.on_a_ride);
		assertTrue(customer1.getOnGoingRide() == ride1);
		assertTrue(customer2.getOnGoingRide() == null);
		assertTrue(customer2.getLocation() == ride2.getDestination());
		assertTrue(driver2.getCar().getLocation() == ride2.getDestination());
		assertTrue(driver1.getCar().getLocation() == ride1.getStartingPoint());
	}

	@Test
	public void testGetBalanceCustomer() throws InvalidLocationException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestGetBalanceCustomer");
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("Antoine", "Miquel", 0, 0);
		Customer customer2 = cf.createCustomer("Erwan", "Le2", 0, 0);
		Customer customer3 = cf.createCustomer("Lucas", "Chelou", 0, 0);
		
		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);
		
		System.out.print(Environment.getInstance().getBalance(customer1));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(customer2));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(customer3));
		System.out.print("\n");
		
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		driver3.setStatus(DriverStatus.on_duty);
		
		
		customer1.bookRide(customer1.simulateRide(5, 5, 1), 1, 0);
		customer2.bookRide(customer2.simulateRide(0, 5, 1), 1, 0);
		customer3.bookRide(customer3.simulateRide(7, 5, 1), 1, 0);
		
		
		Clock.getInstance().passTime(10000);
		
		customer1.bookRide(customer1.simulateRide(4, 5, 1), 1, 0);
		
		Clock.getInstance().passTime(10000);
		
		System.out.print(Environment.getInstance().getBalance(customer1));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(customer2));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(customer3));
		System.out.print("\n");
			
	}

	@Test
	public void testGetBalanceDriver() throws InvalidLocationException, InvalidDateException, CarAlreadyUsedException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestGetBalanceDriver");
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("Antoine", "Miquel", 0, 0);
		Customer customer2 = cf.createCustomer("Erwan", "Le2", 0, 0);
		Customer customer3 = cf.createCustomer("Lucas", "Chelou", 0, 0);
		
		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);
		
		System.out.print(Environment.getInstance().getBalance(driver1));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(driver2));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(driver3));
		System.out.print("\n");
	
		
		driver1.setStatus(DriverStatus.on_duty);
					
		customer1.bookRide(customer1.simulateRide(5, 5, 1), 1, 0);
		driver2.setStatus(DriverStatus.on_duty);
		customer2.bookRide(customer2.simulateRide(0, 5, 1), 1, 0);
		driver3.setStatus(DriverStatus.on_duty);
		customer3.bookRide(customer3.simulateRide(7, 5, 1), 1, 0);
		
		
		Clock.getInstance().passTime(10000);
		
		customer1.bookRide(customer1.simulateRide(4, 5, 1), 1, 0);
		
		Clock.getInstance().passTime(10000);
		
		System.out.print(Environment.getInstance().getBalance(driver1));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(driver2));
		System.out.print("\n");
		System.out.print(Environment.getInstance().getBalance(driver3));
		System.out.print("\n");
	
	
	}

	@Test
	public void testGetSystemBalance() throws RideRequestException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestGetSystemBalance");
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("Antoine", "Miquel", 0, 0);
		Customer customer2 = cf.createCustomer("Erwan", "Le2", 0, 0);
		Customer customer3 = cf.createCustomer("Lucas", "Chelou", 0, 0);
		
		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);
		System.out.println(Environment.getInstance().getSystemBalance());		
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		driver3.setStatus(DriverStatus.on_duty);
		
		
		customer1.bookRide(customer1.simulateRide(5, 5, 1), 1, 0);
		customer2.bookRide(customer2.simulateRide(0, 5, 1), 1, 0);
		customer3.bookRide(customer3.simulateRide(7, 5, 1), 1, 0);
		
		
		Clock.getInstance().passTime(10000);
		
		customer1.bookRide(customer1.simulateRide(4, 5, 1), 1, 0);
		
		Clock.getInstance().passTime(10000);
		System.out.println(Environment.getInstance().getSystemBalance());
	}
	@Test
	public void testMostFreqCustomer() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestMostFreqCustomer");
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("Antoine", "Miquel", 0, 0);
		Customer customer2 = cf.createCustomer("Erwan", "Le2", 0, 0);
		Customer customer3 = cf.createCustomer("Lucas", "Chelou", 0, 0);
		
		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);	
		driver1.setStatus(DriverStatus.on_duty);
	
		customer1.bookRide(customer1.simulateRide(5, 5, 1), 1, 0);
		driver2.setStatus(DriverStatus.on_duty);
		customer2.bookRide(customer2.simulateRide(0, 5, 1), 1, 0);
		driver3.setStatus(DriverStatus.on_duty);
		customer3.bookRide(customer3.simulateRide(7, 5, 1), 1, 0);
		
		Clock.getInstance().passTime(10000);
		System.out.println(Environment.getInstance().mostFreqCustomer().toString());
		driver2.setStatus(DriverStatus.offline);
		driver3.setStatus(DriverStatus.offline);

		customer1.bookRide(customer1.simulateRide(4, 5, 1), 1, 0);
		driver2.setStatus(DriverStatus.on_duty);
		customer2.bookRide(customer2.simulateRide(10, 20, 1), 1, 0);
		Clock.getInstance().passTime(10000);
		System.out.println(customer1.getLocation());
		assertTrue(customer1.getLocation().getX() == 4 && customer1.getLocation().getY() == 5);
		assertTrue(customer2.getLocation().getX() == 10 && customer2.getLocation().getY() == 20);
		driver2.setStatus(DriverStatus.offline);
		customer1.bookRide(customer1.simulateRide(10, 0, 1), 1, 0);
		System.out.println(Environment.getInstance().mostFreqCustomer().toString());
	}
	
	@Test
	public void testLeastOccupied() throws RideRequestException, CarAlreadyUsedException, InvalidDateException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestLeastOccupiedDriver()");
		DriverFactory df = new DriverFactory();
		StandardCarFactory scf = new StandardCarFactory();

		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);	
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		driver3.setStatus(DriverStatus.on_duty);
		Clock.getInstance().passTime(100);
		System.out.println(Environment.getInstance().leastOccupiedDriver().toString());
		driver2.setStatus(DriverStatus.on_a_ride);
		Clock.getInstance().passTime(100);
		System.out.println(Environment.getInstance().leastOccupiedDriver().toString());
		driver3.setStatus(DriverStatus.on_a_ride);
		Clock.getInstance().passTime(100);
		System.out.println(Environment.getInstance().leastOccupiedDriver().toString());
		
	}
	
	@Test
	public void testMostChargedCustomer() throws RideRequestException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestMostChargedCustomer()");
		DriverFactory df = new DriverFactory();
		CustomerFactory cf = new CustomerFactory();
		StandardCarFactory scf = new StandardCarFactory();
		Customer customer1 = cf.createCustomer("Antoine", "Miquel", 0, 0);
		Customer customer2 = cf.createCustomer("Erwan", "Le2", 0, 0);
		Customer customer3 = cf.createCustomer("Lucas", "Chelou", 0, 0);
		
		StandardCar car1 = (StandardCar) scf.createCar(2, 2);
		StandardCar car2 = (StandardCar) scf.createCar(2, 2);
		StandardCar car3 = (StandardCar) scf.createCar(2, 2);
		Driver driver1 = df.createDriver("Pablo", "Picasso", car1);
		Driver driver2 = df.createDriver("Paul", "LeChemino", car2);
		Driver driver3 = df.createDriver("Malo", "Hervé", car3);	
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		driver3.setStatus(DriverStatus.on_duty);
		
		
		customer1.bookRide(customer1.simulateRide(5, 5, 1), 1, 0);
		customer2.bookRide(customer2.simulateRide(0, 5, 1), 1, 0);
		customer3.bookRide(customer3.simulateRide(7, 5, 1), 1, 0);
		
		
		Clock.getInstance().passTime(10000);
		System.out.println(Environment.getInstance().mostChargedCustomer());
		
		customer1.bookRide(customer1.simulateRide(4, 5, 1), 1, 0);
		customer2.bookRide(customer1.simulateRide(10, 20, 1), 1, 0);
	
		
		Clock.getInstance().passTime(10000);
		System.out.println(Environment.getInstance().mostChargedCustomer());
		
		
	}
	
	
	
	

}
