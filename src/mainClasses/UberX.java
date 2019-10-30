package mainClasses;

import exceptions.InvalidPassengersNbException;
import helpers.Coordinates;
import helpers.Traffic;

/**
 * Represents an UberX Ride (standard non-shared Uber ride), from its simulation and request to its completion. Extends the Ride superclass.
 * @author Erwan
 *
 */
public class UberX extends Ride {

	/**
	 * Creates a new "simulated" UberX Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 4 included.
	 */
	public UberX(Customer associatedCustomer, Coordinates destination, int passengersNb) throws InvalidPassengersNbException {
		super(associatedCustomer, destination, passengersNb);
		if (passengersNb < 1 || passengersNb > 4) {
			throw new InvalidPassengersNbException(this);
		}
	}
	
	/**
	 * Creates a new "simulated" UberX Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 4 included.
	 * @param   hour                The hour of the simulation
	 */
	public UberX(Customer associatedCustomer, Coordinates destination, int passengersNb, int hour) throws InvalidPassengersNbException {
		super(associatedCustomer, destination, passengersNb, hour);
		if (passengersNb < 1 || passengersNb > 4) {
			throw new InvalidPassengersNbException(this);
		}
	}
	
	

	@Override
	public double computePrice() {
		double trafficrate;
		double basicrate;
		if (this.traffic == Traffic.medium) {
			trafficrate = 1.1;
		} else if (this.traffic == Traffic.heavy) {
			trafficrate = 1.5;
		} else {
			trafficrate = 1;
		}
		double length = this.startingPoint.distance(this.destination);
		if (length < 5) {
			basicrate = 3.3; 
		} else if (length >= 5 & length < 10) {
			basicrate = 4.2;
		} else if (length >= 10 & length < 20) {
			basicrate = 1.91;
		} else {
			basicrate = 1.5;
		}
		int roundprice = (int) (basicrate*length*trafficrate*100);
		double hundredprice = (double) roundprice;
		return hundredprice/100;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getCarType() {
		return StandardCar.class;
	}

}
