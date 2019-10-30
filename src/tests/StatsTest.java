package tests;

import org.junit.Test;

import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.InvalidLocationException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;
import helpers.Clock;
import helpers.DriverStatus;
import helpers.Environment;
import mainClasses.Driver;
import mainClasses.StandardCar;

public class StatsTest {

	@Test
	public void testTimeStats() throws RideRequestException, CarAlreadyUsedException, InvalidDateException, PoolRequestException, NoMorePotentialsDriverException, InvalidLocationException, NoAskedForPoolRequestException {
		Environment.wipeEnvironment();
		System.out.println("\ntestTimeStats()");
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
		driver1.setStatus(DriverStatus.off_duty);
		driver2.setStatus(DriverStatus.off_duty);
		driver3.setStatus(DriverStatus.off_duty);
		System.out.println(Environment.getInstance().leastOccupiedDriver().toString());
		
		Clock.getInstance().passTime(100);
		driver1.setStatus(DriverStatus.on_duty);
		driver2.setStatus(DriverStatus.on_duty);
		System.out.println(Environment.getInstance().leastOccupiedDriver().toString());
	}
	
	

}
