package mainClasses;

import exceptions.InvalidPassengersNbException;
import helpers.Coordinates;
import helpers.Traffic;

/**
 * Represents an UberBlack Ride (luxury Uber ride), from its simulation and request to its completion. Extends the Ride superclass.
 * @author Erwan
 *
 */
public class UberBlack extends Ride {

	/**
	 * Creates a new "simulated" UberBlack Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 4 included.
	 * @throws InvalidPassengersNbException Thrown if the input passengersNb is invalid.
	 */
	public UberBlack(Customer associatedCustomer, Coordinates destination, int passengersNb) throws InvalidPassengersNbException {
		super(associatedCustomer, destination, passengersNb);
		if (passengersNb < 1 || passengersNb > 4) {
			throw new InvalidPassengersNbException(this);
		}
	}
	
	/**
	 * Creates a new "simulated" UberBlack Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 4 included.
	 * @param   hour                The hour of simulation
	 * @throws InvalidPassengersNbException Thrown if the input passengersNb is invalid.
	 */
	public UberBlack(Customer associatedCustomer, Coordinates destination, int passengersNb,int hour) throws InvalidPassengersNbException {
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
			trafficrate = 1.3;
		} else if (this.traffic == Traffic.heavy) {
			trafficrate = 1.6;
		} else {
			trafficrate = 1;
		}
		double length = this.startingPoint.distance(this.destination);
		if (length < 5) {
			basicrate = 6.2; 
		} else if (length >= 5 & length < 10) {
			basicrate = 5.5;
		} else if (length >= 10 & length < 20) {
			basicrate = 3.25;
		} else {
			basicrate = 2.6;
		}
		int roundprice = (int) (basicrate*length*trafficrate*100);
		double hundredprice = (double) roundprice;
		return hundredprice/100;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getCarType() {
		return Berline.class;
	}

}
