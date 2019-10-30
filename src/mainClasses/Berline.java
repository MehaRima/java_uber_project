package mainClasses;
import exceptions.InvalidLocationException;
import helpers.Coordinates;

/**
 * The Berline car class.
 * @author Antoine
 *
 */

public class Berline extends Car {
	
	/**
	 *  A counter that's incremented each time a new Berline is created : it is there to ensure each Berline has a unique ID.
	 */
	private static int berlineIDcounter = 0;
	
	
	//Constructors
	
	/**
	 * Creates a new Berline car.
	 * A Berline has 4 total seats
	 * @param location The initial location of the Car.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public Berline(Coordinates location) throws InvalidLocationException {
		super(location);
		this.totalSeats = 4;
		this.availableSeats = 4;
		berlineIDcounter +=1 ;
		setID("Berline" + berlineIDcounter);
		
	}
	
	/**
	 * Creates a new Berline car at a random location.
	 * A Berline has 4 total seats
	 * @throws InvalidLocationException
	 */
	public Berline() throws InvalidLocationException {
		super();
		this.totalSeats = 4;
		this.availableSeats = 4;
		berlineIDcounter +=1 ;
		setID("Berline" + berlineIDcounter);
		
	}
		
}
