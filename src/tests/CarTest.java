package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidAvailableSeatsException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import helpers.DriverStatus;
import designPatterns.*;
import mainClasses.*;




public class CarTest {
	
	// ensures that an exception is thrown when the amount of available seats set for a certain car is not possible
	@Test(expected = InvalidAvailableSeatsException.class)
	public void invalidAvailableSeatsBerlineTest() throws InvalidAvailableSeatsException, InvalidLocationException {
		
		System.out.println("\nInvalidAvailableSeatsBerlineTest");
		CarFactory berlineFactory = new BerlineFactory();
		Berline berline01 = (Berline) berlineFactory.createCar(0, 0);
		System.out.println(berline01.toString());
		System.out.println(berline01.getAvailableSeats());
		berline01.setAvailableSeats(7);
		System.out.println(berline01.toString());
	} 

	// ensures that an exception is thrown when a Driver tries to go "on_duty" when their car is already used by another Driver
	@Test(expected = CarAlreadyUsedException.class)
	public void wrongCurrentDriverchange() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\nWrongCurrentDriverchangetest");
		CarFactory standardCarFactory = new StandardCarFactory() ;
		DriverFactory df = new DriverFactory();
		StandardCar cartest = (StandardCar) standardCarFactory.createCar(0, 0);
		Driver drivertest01 = df.createDriver("John", "Smith", cartest); 
		Driver drivertest02 = df.createDriver("Phill", "Lee", cartest);
		assertTrue(cartest.getDriverslist().size() == 2);
		drivertest01.setStatus(DriverStatus.on_duty);
		drivertest02.setStatus(DriverStatus.on_duty);
	}
	
	// ensures that a Driver can correctly go "on_duty" if the previous Driver to use their car has gone "offline"
	@Test
	public void rightCurrentDriverchange() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\nRightCurrentDriverchangetest");
		CarFactory standardCarFactory = new  StandardCarFactory() ;
		DriverFactory driverfactory = new DriverFactory();
		StandardCar cartest = (StandardCar) standardCarFactory.createCar(0, 0);
		Driver drivertest01 = driverfactory.createDriver("John", "Smith", cartest); 
		Driver drivertest02 = driverfactory.createDriver("Phill", "Lee", cartest); 
		drivertest01.setStatus(DriverStatus.on_duty);
		assertTrue(cartest.getCurrentDriver() == drivertest01);
		drivertest01.setStatus(DriverStatus.off_duty);
		drivertest01.setStatus(DriverStatus.offline);
		drivertest02.setStatus(DriverStatus.on_duty);
		assertTrue(cartest.getCurrentDriver() == drivertest02);
	}
	
	@Test
	public void getInfoTest() throws InvalidLocationException, CarAlreadyUsedException, InvalidDateException {
		System.out.println("\nGetInfoTest");
		CarFactory standardCarFactory = new  StandardCarFactory() ;
		DriverFactory driverfactory = new DriverFactory();
		StandardCar cartest = (StandardCar) standardCarFactory.createCar(0, 0);
		Driver drivertest01 = driverfactory.createDriver("John", "Smith", cartest);
		drivertest01.setStatus(DriverStatus.on_duty);
		System.out.println(cartest.getInfo());
		drivertest01.setStatus(DriverStatus.offline);
		System.out.println(cartest.getInfo());
		
	}
	
	
}
