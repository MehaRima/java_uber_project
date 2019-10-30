package designPatterns;
import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.Environment;
import mainClasses.*;

/**
 * Van Factory Class, allows the Client to instantiate a Van without using the "new" operator.
 * Implements the Factory Method design pattern.
 * @author Antoine
 *
 */
public class VanFactory extends CarFactory{
	
	@Override
	public Car createCar(double x_location, double y_location) throws InvalidLocationException {
		Van van = new Van(new Coordinates(x_location, y_location));
		Environment.getInstance().getCarsList().add(van);
		return van;
	}

	@Override
	public Car createCar() throws InvalidLocationException {
		Van van = new Van();
		Environment.getInstance().getCarsList().add(van);
		return van;
	}
	
	

}
