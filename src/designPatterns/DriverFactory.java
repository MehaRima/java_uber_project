package designPatterns;
import helpers.Environment;
import helpers.TimeStats;
import mainClasses.*;

/**
 * The driver Factory class, allows the Client to instantiate a Driver without using the "new" operator.
 * Implements the Simple Factory design pattern.
 * @author Antoine
 */
public class DriverFactory {
	
	/**
	 * Creates a new Driver with the default status "offline", and adds them to the Environment's list.
	 * @param	name	The Driver's last name.
	 * @param	surname	The Driver's surname.
	 * @param	car		The Car that is owned by this Driver and that he will be using (it may be shared with other Drivers).
	 */
	public Driver createDriver(String name, String surname, Car car) {
		Driver driver = new Driver(name, surname, car);
		Environment.getInstance().getDriversList().add(driver);
		Environment.getInstance().getDriversStats().put(driver, new TimeStats(0,0,0)) ; 
		return driver;
	}
	
	/**
	 * Creates a new Driver with a generic name and surname, with the default status "offline", and adds them to the Environment's list.
	 * @param	car		The Car that is owned by this Driver and that he will be using (it may be shared with other Drivers).
	 */
	public Driver createDriver(Car car) {
		Driver driver = new Driver(car);
		Environment.getInstance().getDriversList().add(driver);
		Environment.getInstance().getDriversStats().put(driver, new TimeStats(0,0,0)) ; 
		return driver;
	}

}
