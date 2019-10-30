package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import designPatterns.CustomerFactory;
import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.PoolRequest;
import mainClasses.Car;
import mainClasses.Customer;
import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.UberPool;

public class PoolRequestTest {

	/**
	 * To test if the computation of a route works
	 * @throws InvalidLocationException
	 */
	@Test
	public void poolRequestRouteTest() throws InvalidLocationException {
		System.out.println("poolRequestRouteTest()");
		CustomerFactory  customerfactory = new CustomerFactory();
		DriverFactory driverfactory = new DriverFactory();
		StandardCarFactory standardCarFactory = new StandardCarFactory();
		Car car1 = standardCarFactory.createCar(0,0);
		Car car2 = standardCarFactory.createCar(3,2);
		
		Driver driver1 = driverfactory.createDriver("Jonh", "Watson", car1);
		Driver driver2 = driverfactory.createDriver("Karl", "Delanoix", car2);
		
		Customer customer1 = customerfactory.createCustomer("Customer", "Uno");
		customer1.setLocation(new Coordinates(1,2));
		Customer customer2 = customerfactory.createCustomer("Customer", "Dos");
		customer2.setLocation(new Coordinates(2,2));
		Customer customer3 = customerfactory.createCustomer("Customer", "Tres");
		customer3.setLocation(new Coordinates(3.5,2));
		
		Ride[] simalteRides_customer1 = customer1.simulateRide(1, 10, 1);
		Ride[] simalteRides_customer2 = customer2.simulateRide(2, 10, 1);
		Ride[] simalteRides_customer3 =customer3.simulateRide(3.5, 10, 1);
		
		
		PoolRequest poolrequest = new PoolRequest();
		poolrequest.getUberPoolList()[0] = (UberPool) simalteRides_customer1[3];
		poolrequest.getUberPoolList()[1] = (UberPool) simalteRides_customer2[3];
		poolrequest.getUberPoolList()[2] = (UberPool) simalteRides_customer3[3];
		
		ArrayList<Coordinates> route1 = poolrequest.poolRequestRoute(driver1.getCar());
		ArrayList<Coordinates> route2 = poolrequest.poolRequestRoute(driver2.getCar());
		
		System.out.println("Route1");
		for( Coordinates cords : route1) {
			System.out.println(cords.toString());
		}
		System.out.println("Route2");
		for( Coordinates cords : route2) {
			System.out.println(cords.toString());
		};
}
	
	@Test
	public void poolRequestCostTest() throws InvalidLocationException {
		System.out.println("poolRequestCostTest()");
		CustomerFactory  customerfactory = new CustomerFactory();
		DriverFactory driverfactory = new DriverFactory();
		StandardCarFactory standardCarFactory = new StandardCarFactory();
		Car car1 = standardCarFactory.createCar(0,0);
		Car car2 = standardCarFactory.createCar(3,2);
		
		Driver driver1 = driverfactory.createDriver("Jonh", "Watson", car1);
		Driver driver2 = driverfactory.createDriver("Karl", "Delanoix", car2);
		
		Customer customer1 = customerfactory.createCustomer("Customer", "Uno");
		customer1.setLocation(new Coordinates(1,2));
		Customer customer2 = customerfactory.createCustomer("Customer", "Dos");
		customer2.setLocation(new Coordinates(2,2));
		Customer customer3 = customerfactory.createCustomer("Customer", "Tres");
		customer3.setLocation(new Coordinates(3.5,2));
		
		Ride[] simalteRides_customer1 = customer1.simulateRide(1, 10, 1);
		Ride[] simalteRides_customer2 = customer2.simulateRide(2, 10, 1);
		Ride[] simalteRides_customer3 =customer3.simulateRide(3.5, 10, 1);
		
		
		PoolRequest poolrequest = new PoolRequest();
		poolrequest.getUberPoolList()[0] = (UberPool) simalteRides_customer1[3];
		poolrequest.getUberPoolList()[1] = (UberPool) simalteRides_customer2[3];
		poolrequest.getUberPoolList()[2] = (UberPool) simalteRides_customer3[3];
		
		System.out.println(poolrequest.poolRequestCost(driver1.getCar()));
		assertTrue(poolrequest.poolRequestCost(driver2.getCar()) == 13.5);
	}
	
	
	
}
