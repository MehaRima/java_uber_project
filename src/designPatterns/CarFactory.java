package designPatterns;
import exceptions.InvalidLocationException;
import mainClasses.*;

/**
 * CarFactory class, allows the Client to instantiate a Car without using the "new" operator.
 * Implements the Factory Method design pattern.
 * The user has to choose the type of factory they want to use depending on the type of car they want to create.
 * @author Antoine
 *
 */

public abstract class CarFactory  {
	
	/**
	 * Creates a new Car at the given location and adds it to the Environment's list.
	 * @param x_location The x coordinate of the initial location of the Car.
	 * @param y_location The y coordinate of the initial location of the Car.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public abstract Car createCar(double x_location, double y_location) throws InvalidLocationException;
	
	/**
	 * Creates a new Car at a random location and adds it to the Environment's list.
	 * @throws InvalidLocationException
	 */
	public abstract Car createCar() throws InvalidLocationException;
	
	

	
}
