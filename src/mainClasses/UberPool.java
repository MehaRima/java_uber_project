package mainClasses;

import java.util.ArrayList;

import exceptions.InvalidDateException;
import exceptions.InvalidPassengersNbException;
import helpers.Clock;
import helpers.Coordinates;
import helpers.Date;
import helpers.Traffic;

/**
 * Represents an UberPool Ride (shared Uber ride in a standard car), from its simulation and request to its completion. Extends the Ride superclass.
 * @author Antoine
 *
 */
public class UberPool extends Ride {
	
	/**
	 * Creates a new "simulated" UberBlack Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 3 included.
	 */
	public UberPool(Customer associatedCustomer, Coordinates destination, int passengersNb) throws InvalidPassengersNbException {
		super(associatedCustomer, destination, passengersNb);
		if (passengersNb < 1 || passengersNb > 3) {
			throw new InvalidPassengersNbException(this);
		}
		this.duration = 0; //In fact the duration of UberPool Ride can't be estimated until the others customers who will share the ride are known
	}
	
	
	/**
	 * Creates a new "simulated" UberBlack Ride.
	 * @param	associatedCustomer	The Customer who is simulating/booking the Ride.
	 * @param	startingPoint		The starting point of the ride (by default, the location of the Customer).
	 * @param	destination			The destination desired by the Customer.
	 * @param	passengersNb		The number of passengers who will board on this Ride : must be an integer between 1 and 3 included.
	 * @param   hour                The hour of the simulation
	 */
	public UberPool(Customer associatedCustomer, Coordinates destination, int passengersNb, int hour) throws InvalidPassengersNbException {
		super(associatedCustomer, destination, passengersNb,hour);
		if (passengersNb < 1 || passengersNb > 3) {
			throw new InvalidPassengersNbException(this);
		}
		this.duration = 0; //In fact the duration of UberPool Ride can't be estimated until the others customers who will share the ride are known
	}


	//Methods
	/**
	 * Computes the pickup date when the ride is a UberPool.
	 * @param car The car that will pickup the Customer.
	 * @param route The path that will be followed to complete this UberPool's poolRequests.
	 * @return The date and time at which the Customer will be picked up.
	 * @throws InvalidDateException
	 */
	public Date computePoolPickUpTime(Car car, ArrayList<Coordinates> route) throws InvalidDateException {
		Date currentDate = Clock.getInstance().getDate();
		Date pickupDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay(), currentDate.getHour(), currentDate.getMinute());
		pickupDate.addTime(this.computeDuration(car.getLocation(), route.get(0)));
		int i = 0;
		while(route.get(i) != this.startingPoint) {
			pickupDate.addTime(this.computeDuration(route.get(i), route.get(i+1)));
			i += 1;
		}
		return pickupDate;
	}
	
	
	/**
	 * Computes the drop-off date when the ride is a UberPool.
	 * @param car The car that is picking up the Customer.
	 * @param route The path that is followed to complete this UberPool's poolRequests.
	 * @return The date and time at which the Customer will be dropped-off.
	 * @throws InvalidDateException
	 */
	public Date computePoolEndTime(Car car, ArrayList<Coordinates> route) throws InvalidDateException {
		Date currentDate = Clock.getInstance().getDate();
		Date endDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay(), currentDate.getHour(), currentDate.getMinute());
		endDate.addTime(this.computeDuration(this.getAssociatedDriver().getCar().getLocation(), route.get(0)));
		
		int i = 0;
		while(route.get(i) != this.destination) {
			endDate.addTime(this.computeDuration(route.get(i), route.get(i+1)));
			i += 1;
		}
		return endDate;
	}
	
	/**
	 * Computes the duration of the Customer's UberPool Ride (which depends on the other Customers in the Ride).
	 * @param car The car that will pickup the Customer.
	 * @param route The path that will be followed to complete this UberPool's poolRequests.
	 * @return The time between pick-up and drop-off for the Customer.
	 * @throws InvalidDateException
	 */
	public int computePoolDuration(Car car, ArrayList<Coordinates> route) throws InvalidDateException {
		int duration = 0 ;
		int i = 0;
		while(route.get(i) != this.startingPoint) {
			i += 1;
		}
		while(route.get(i) != this.destination) {
			duration += this.computeDuration(route.get(i),  route.get(i+1));
			i += 1;
		}
		return duration ;
	}
	
	
	@Override
	public double computePrice() {
		double trafficrate;
		double basicrate;
		if (this.traffic == Traffic.medium) {
			trafficrate = 1.1;
		} else if (this.traffic == Traffic.heavy) {
			trafficrate = 1.2;
		} else {
			trafficrate = 1;
		}
		double length = this.startingPoint.distance(this.destination);
		if (length < 5) {
			basicrate = 2.4; 
		} else if (length >= 5 & length < 10) {
			basicrate = 3;
		} else if (length >= 10 & length < 20) {
			basicrate = 1.3;
		} else {
			basicrate = 1.1;
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
