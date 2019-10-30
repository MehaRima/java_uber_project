package mainClasses;
import exceptions.InvalidLocationException;
import helpers.Coordinates;

/**
 * The Van car class.
 * @author Antoine
 *
 */
public class Van extends Car {
	/**
	 *  A counter that's incremented each time a new Van is created : it is there to ensure each standard car has a unique ID.
	 */
	private static int vanIDcounter = 0;
	
	
	//Concstructors
	/**
	 * Creates a new Van car.
	 * A Van has 6 total seats.
	 * @param location The initial location of the Car.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public Van(Coordinates location) throws InvalidLocationException {
		super(location);
		this.totalSeats = 4;
		this.availableSeats = 4;
		vanIDcounter +=1 ;
		setID("Van" + vanIDcounter);
		
	}
	
	/**
	 * Creates a new Van car at a random location.
	 * A Van has 6 total seats.
	 * @throws InvalidLocationException
	 */
	public Van() throws InvalidLocationException {
		super();
		this.totalSeats = 4;
		this.availableSeats = 4;
		vanIDcounter +=1 ;
		setID("Van" + vanIDcounter);
		
	}
	
}
