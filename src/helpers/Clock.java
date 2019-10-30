package helpers;

import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.MarkAttributionException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;

/**
 * A clock that represents the fictive time of the myUber application (applies the Singleton pattern : can only have one instance at a given time). Has to be manually incremented through passTime method for time to pass.
 * @author Erwan
 *
 */
public class Clock {
	/**
	 * The only allowed instance of class Clock (because of Singleton design pattern).
	 */
	private static Clock instance = null;
	private Date date;
	
	/**
	 * Creates a Clock object initialized on 01/12/2018, 12.00pm. Can only be called through method getInstance to ensure unicity of the instance.
	 * @throws InvalidDateException Thrown if the initial Date for the Clock is invalid (or if it is after 2100 or before 2000).
	 */
	private Clock() {
		try {
			this.date = new Date(2018, 12, 1, 12, 0);
		} catch (InvalidDateException e) {
			e.getMessage();
		}
	}
	
	/**
	 * If no Clock instance exists yet, creates one initialized on 01/12/2018. Else, returns the already existing instance of Clock.
	 * @return instance The unique instance of the Clock class (a newly created one if none previously existed).
	 * @throws InvalidDateException Thrown if the initial Date for the Clock is invalid (or if it is after 2100 or before 2000).
	 */
	public static Clock getInstance() {
		if (instance == null) {
			instance = new Clock();
		}
		return instance;
	}
	
	/**
	 * Deletes the currently active Clock (if one exists).
	 */
	public static void wipeClock() {
		instance = null;
	}
	
	/**
	 * Allows to go forward in time by a certain number of minutes, and calls the Environment's refreshOnGoingRides() method to check if some Rides have advanced to the next step in their
	 * completion during the span of time that passed.
	 * @param minutes The number of minutes forward you want to go.
	 * @throws InvalidDateException Exception thrown if the minutes parameter entered by the user is negative, or if the user is trying to advance past 2100.
	 * @throws CarAlreadyUsedException 
	 * @throws NoMorePotentialsDriverException 
	 * @throws PoolRequestException 
	 * @throws RideRequestException 
	 * @throws NoAskedForPoolRequestException 
	 * @throws MarkAttributionException 
	 */
	public void passTime(int minutes) throws InvalidDateException, CarAlreadyUsedException, RideRequestException, PoolRequestException, NoMorePotentialsDriverException, NoAskedForPoolRequestException {
		Date newdate = new Date(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute());
		newdate.addTime(minutes);
		if (newdate.getYear() > 2100) {
			throw new InvalidDateException("You can't go so much far forward in time !");
		} else {
			this.setDate(newdate);
			try {
				Environment.getInstance().refreshOnGoingRides();
			} catch (MarkAttributionException e) {
				System.out.println(e.getMessage());
			}
			Environment.getInstance().refreshFutureBookRide();
		}
	}


	@Override
	public String toString() {
		return "Current time : " + this.date.toString();
	}

	//getters and setters
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
