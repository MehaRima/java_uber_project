package designPatterns;
import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.Environment;
import mainClasses.*;

/**
 * Standard Car Factory Class, allows the Client to instantiate a StandardCar without using the "new" operator.
 * Implements the Factory Method design pattern.
 * @author Antoine
 *
 */
public class StandardCarFactory extends CarFactory{
	
	@Override
	public Car createCar(double x_location, double y_location) throws InvalidLocationException {
		StandardCar standard = new StandardCar(new Coordinates(x_location, y_location));
		Environment.getInstance().getCarsList().add(standard);
		return standard;
	}

	@Override
	public Car createCar() throws InvalidLocationException {
		StandardCar standard = new StandardCar();
		Environment.getInstance().getCarsList().add(standard);
		return standard;
	}
	
	
}
