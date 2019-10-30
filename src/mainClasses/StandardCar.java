package mainClasses;
import exceptions.InvalidLocationException;
import helpers.Coordinates;

/**
 * The StandardCar class
 * @author Antoine
 *
 */
public class StandardCar extends Car {

	/**
	 *  A counter that's incremented each time a new StandardCar is created : it is there to ensure each StandardCar has a unique ID.
	 */
	private static int standardCarIDcounter = 0;
	
	
	//ConcstructorsS
	
	/**
	 * Creates a new StandardCar.
	 * A StandardCar has 4 total seats
	 * @param location The initial location of the Car.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public StandardCar(Coordinates location) throws InvalidLocationException {
		super(location);
		this.totalSeats = 4;
		this.availableSeats = 4 ;
		standardCarIDcounter +=1 ;
		setID("StandardCar" + standardCarIDcounter);
		
	}
	
	/**
	 * Creates a new StandardCar at a random location.
	 * A StandardCar has 4 total seats
	 * @throws InvalidLocationException
	 */
	public StandardCar() throws InvalidLocationException {
		super();
		this.totalSeats = 4;
		this.availableSeats = 4 ;
		standardCarIDcounter +=1 ;
		setID("StandardCar" + standardCarIDcounter);
		
	}

}
