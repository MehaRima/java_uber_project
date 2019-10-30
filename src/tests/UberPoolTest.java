package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import designPatterns.CustomerFactory;
import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Coordinates;
import helpers.DriverStatus;
import helpers.Environment;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;

public class UberPoolTest {
	
	// Asserts that when a Customer requests an UberPool ride, it is correctly added to the Environment's PoolRequest.
	@Test
	public void refreshWithCustomerBookTest() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException, NoMorePotentialsDriverException, RideRequestException, PoolRequestException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("refreshWithCustomerBookTest()");
		CustomerFactory  customerfactory = new CustomerFactory();
		DriverFactory driverfactory = new DriverFactory();
		StandardCarFactory standardCarFactory = new StandardCarFactory();
		Car car1 = standardCarFactory.createCar(0,2);
		Car car2 = standardCarFactory.createCar(8,2);
		Car car3 = standardCarFactory.createCar(3.6,2);
		
		Driver driver1 = driverfactory.createDriver("Jonh", "Watson", car1);
		Driver driver2 = driverfactory.createDriver("Karl", "Delanoix", car2);
		Driver driver3 = driverfactory.createDriver("Erwan", "Le DE", car3);
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		driver3.setStatus(DriverStatus.on_duty);
		
		
		Customer customer1 = customerfactory.createCustomer("Customer", "Uno");
		customer1.setLocation(new Coordinates(1,2));
		Ride[] simulateRides_customer1 = customer1.simulateRide(1, 10, 1);
		System.out.println(customer1.toString());
		customer1.bookRide(simulateRides_customer1, 4, 0);
		assertTrue(Environment.getInstance().getPoolRequest().getNumberOfRequests() == 1);
		System.out.println(Environment.getInstance().getPoolRequest().toString());
	}	
}
