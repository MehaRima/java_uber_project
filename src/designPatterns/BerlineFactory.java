package designPatterns;
import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.Environment;
import mainClasses.*;

/**
 * Berline Factory Class, allows the Client to instantiate a Berline without using the "new" operator.
 * Implements the Factory Method design pattern.
 * @author Antoine
 *
 */
public class BerlineFactory extends CarFactory {
	
	@Override
	public Car createCar(double x_location, double y_location) throws InvalidLocationException {
		Berline berline = new Berline(new Coordinates(x_location, y_location));
		Environment.getInstance().getCarsList().add(berline);
		return berline;
	}

	@Override
	public Car createCar() throws InvalidLocationException {
		Berline berline = new Berline();
		Environment.getInstance().getCarsList().add(berline);
		return berline;
	}
	
	

}
