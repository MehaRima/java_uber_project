package mainClasses;

import helpers.Environment;
import helpers.PoolRequest;
import helpers.Date;
import java.util.ArrayList;

import designPatterns.Observer;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.RideRequestException;
import helpers.Clock;
import helpers.Coordinates;
import helpers.DriverStatus;
import helpers.RideStatus;

/**
 * Represents an Uber driver and stores most of the relevant information about them.
 * @author Erwan
 *
 */
public class Driver implements Observer {
	
	/**
	 * A counter that's incremented each time a new Driver is created : it is there to ensure each Driver has a unique ID.
	 */
	private static int driverIDcounter = 0;
	
	/**
	 * An ID number that is used to represent the Driver : it is designed to be unique for each Driver.
	 */
	private int ID;
	
	/**
	 * The Driver's last name.
	 */
	private String name;
	
	/**
	 * The Driver's surname.
	 */
	private String surname;
	
	/**
	 * Represents the Driver's current state. This attribute can take one of the following values : offline (i.e. not working), on_duty (i.e. ready to take a ride), on_a_ride (driving a customer somewhere) 
	 * or off_duty (i.e. working but on a pause, that is, not driving and not available to take a ride).
	 */
	private DriverStatus status;
	
	/**
	 * The car that belongs to the Driver. NOTE : it may be owned by several Drivers who take turns driving it.
	 */
	private Car car;
	
	/**
	 * Stores the Ride requests that have been sent to the Driver and that are still active : they will be dismissed once they have been either accepted or declined by the Driver.
	 */
	private ArrayList<Ride> onGoingRideRequests;
	
	/**
	 * Stores the eventual Ride(s) that the Driver is currently completing. There may be several Rides if the Ride type is UberPool.
	 */
	private ArrayList<Ride> onGoingRides;
	
	/**
	 * Stores the marks that have previously been given to the Driver by their Customers.
	 */
	private ArrayList<Integer> marks;
	
	
	/**
	 * Stores the Date when the Driver has changed his status for the last time. It is used to compute statistics
	 */
	private Date lastDateOfStatusChange;
	
	/**
	 * True if the driver is asked by the poolRequest
	 */
	private boolean isAskedForPoolRequest;
	
	/**
	 * If the driver is asked by the poolRequest he can found it in this attribute
	 */
	private PoolRequest onGoingPoolRequest;
	
	/**
	 * Creates a new Driver with the default status "offline".
	 * @param	name	The Driver's last name.
	 * @param	surname	The Driver's surname.
	 * @param	car		The Car that is owned by this Driver and that he will be using (it may be shared with other Drivers).
	 */
	public Driver(String name, String surname, Car car) {
		driverIDcounter += 1;
		this.ID = driverIDcounter;
		this.name = name;
		this.surname = surname;
		this.status = DriverStatus.offline;
		this.setCar(car);
		car.addObserver(this);
		this.onGoingRideRequests = new ArrayList<Ride>();
		this.onGoingRides = new ArrayList<Ride>();
		this.marks = new ArrayList<Integer>();
		this.lastDateOfStatusChange = Clock.getInstance().getDate();
		this.isAskedForPoolRequest = false ;
	}
	
	/**
	 * Creates a new Driver with a generic name and surname, with the default status "offline".
	 * @param	car		The Car that is owned by this Driver and that he will be using (it may be shared with other Drivers).
	 */
	public Driver(Car car) {
		driverIDcounter += 1;
		this.ID = driverIDcounter;
		this.name = "Driver" + this.ID + "Name";
		this.surname = "Driver" + this.ID + "Surname";
		this.status = DriverStatus.offline;
		this.setCar(car);
		car.addObserver(this);
		this.onGoingRideRequests = new ArrayList<Ride>();
		this.onGoingRides = new ArrayList<Ride>();
		this.marks = new ArrayList<Integer>();
		this.lastDateOfStatusChange = Clock.getInstance().getDate();
		this.isAskedForPoolRequest = false ;
	}

	@Override
	public void update(Object obj) {
		if (obj instanceof Ride) {
			Ride ride = (Ride) obj;
			if (ride.getStatus() == RideStatus.unconfirmed) {
				System.out.println("A new Ride request has arrived for " + this.toString() + " : " + ride.toString() + "\nIt can be found in the Driver's onGoingRideRequests, and can be accepted or declined.");
			} else if (ride.getStatus() == RideStatus.cancelled) {
				System.out.println(ride.toString() + " has been cancelled before confirmation by its associated Customer. The request has been removed from " + this.toString() + "'s OnGoingRideRequests.");
			} else if (ride.getStatus() == RideStatus.confirmed) {
				System.out.println(ride.toString() + " has been accepted by a Driver. The request has been removed from " + this.toString() + "'s OnGoingRideRequests.");
			}
		} else if (obj instanceof Driver || obj == null) {
			Driver newdriver = (Driver) obj;
			if(newdriver == null) {
				System.out.println(this.getCar().toString() + " is not used anymore."); 
			}
			else {
				System.out.println(this.getCar().toString() + " is now used by the driver : " + newdriver.toString());
					
			}
		}
	}
	
	/**
	 * If the suggestedRide is in the onGoingRideRequests of the Driver, accepts the request (changing the ride status to "confirmed") and sets the Driver's status to on_a_ride, clearing other
	 * eventual Ride requests.
	 * @param suggestedRide A ride request in the onGoingRideRequests list.
	 * @throws RideRequestException Thrown if the input "suggestedRide" is not acceptable by this Driver.
	 * @throws CarAlreadyUsedException 
	 * @throws InvalidDateException 
	 */
	public void acceptRide(Ride suggestedRide) throws RideRequestException, CarAlreadyUsedException, InvalidDateException {
		if (this.onGoingRideRequests.contains(suggestedRide)) {
			if (suggestedRide.getStatus() == RideStatus.unconfirmed) {
				suggestedRide.setStatus(RideStatus.confirmed);
				suggestedRide.removeAllObservers();
				suggestedRide.setAssociatedDriver(this);
				this.setStatus(DriverStatus.on_a_ride);
				suggestedRide.setPickupTime(suggestedRide.computePickUpTime());
				suggestedRide.setEndTime(suggestedRide.computeEndTime());
				String msg = this.toString() + " has accepted to complete " + suggestedRide.toString() + ". The Driver is going to pick up " + suggestedRide.getAssociatedCustomer().toString()
						+ " at "+ suggestedRide.startingPoint + " at " + suggestedRide.pickupTime.toString() +".";
				System.out.println(msg);
				suggestedRide.associatedCustomer.getMailBox().add(msg);
			} else {
				throw new RideRequestException(suggestedRide.toString() + " is not or no longer available for confirmation.");
			}
		} else {
			throw new RideRequestException("You cannot accept " + suggestedRide.toString() + " is not in your OnGoingRideRequests.");
		}
	}
	
	/**
	 * If the suggestedRide is in the onGoingRideRequests of the Driver, declines the request.
	 * @param suggestedRide A ride request in the onGoingRideRequests list.
	 * @throws RideRequestException Thrown if suggestedRide isn't in the onGoingRideRequests list.
	 */
	public void declineRide(Ride suggestedRide) throws RideRequestException {
		if (this.onGoingRideRequests.contains(suggestedRide) && suggestedRide.getStatus() == RideStatus.unconfirmed) {
			suggestedRide.removeObserver(this);
		} else {
			throw new RideRequestException("You cannot decline " + suggestedRide.toString() + " because it is not in your OnGoingRideRequests or it is not unconfirmed anymore.");
		}
	}
	
	public void declinePoolRequest() throws NoMorePotentialsDriverException, NoAskedForPoolRequestException, InvalidDateException, CarAlreadyUsedException {
		if(this.isAskedForPoolRequest) {
			this.isAskedForPoolRequest = false ;
			Environment.getInstance().getPoolRequest().notifyNextDriver();
			
		}
		else {
			throw new NoAskedForPoolRequestException("Error : you are not requested for a UberPool Ride");
		}
	}
	
	
	/**
	 * If the Driver is asked for pool request, it accepts the request
	 * @throws NoAskedForPoolRequestException
	 * @throws InvalidDateException 
	 * @throws CarAlreadyUsedException 
	 */
	public void acceptPoolRequest() throws NoAskedForPoolRequestException, InvalidDateException, CarAlreadyUsedException {
		if(this.isAskedForPoolRequest) {
			this.setStatus(DriverStatus.on_a_ride);
			ArrayList<Coordinates> route = this.onGoingPoolRequest.poolRequestRoute(this.getCar());
			for(UberPool uberpoolride : this.onGoingPoolRequest.getUberPoolList()) {
				if(uberpoolride != null) {
					uberpoolride.setStatus(RideStatus.confirmed);
					uberpoolride.setAssociatedDriver(this);
					uberpoolride.setPickupTime(uberpoolride.computePoolPickUpTime(this.car, route));
					uberpoolride.setEndTime(uberpoolride.computePoolEndTime(this.car, route));
					String msg = this.toString() + " has accepted to complete " + uberpoolride.toString() + ". The Driver is going to pick up " + uberpoolride.getAssociatedCustomer().toString()
							+ " at "+ uberpoolride.startingPoint + " at " + uberpoolride.pickupTime.toString() +".";
					System.out.println(msg);
					uberpoolride.associatedCustomer.getMailBox().add(msg);
				}
			}
			Environment.getInstance().getOnGoingUberPoolRides().add(this.onGoingPoolRequest.getUberPoolList());
			this.onGoingPoolRequest = null ;
			this.isAskedForPoolRequest = false;
			Environment.getInstance().setPoolRequest(new PoolRequest());
			
			
		}
		else {
			throw new NoAskedForPoolRequestException("You are not requested for a poolRequest !");
		}
	}
	
	/**
	 * Displays all the relevant information about the Driver.
	 * @return String containing the relevant information.
	 */
	public String getInfo() {
		int evals = this.getMarks().size();
		double avgmark = this.getAvgMark();
		String message;
		if (this.status == DriverStatus.offline) {
			message = "Driver n°" + this.ID + " is " + this.surname + " " + this.name + ", who is currently offline. His/her average mark is " + avgmark + "/5 on a total of " 
		+ evals + " Customer evaluations.";
		} else {
			message = "Driver n°" + this.ID + " is " + this.surname + " " + this.name + ", currently " + this.status.name() + ", driving the " + this.getCar().toString() + " located at " 
		+ this.getCar().getLocation() + ". His/her average mark is " + avgmark + "/5 on a total of " + evals + " Customer evaluations.";
		}
		return message;
	}
	
	public double getAvgMark() {
		double avgmark = 0;
		int evals = this.marks.size();
		for (int mark : this.marks) {
			avgmark += mark;
		}
		if (evals > 0) {
			avgmark /= evals;
		}
		int temp = (int) (avgmark*10);
		double tempd = (double) temp;
		avgmark = tempd/10;
		return avgmark;
	}
	
	@Override
	public String toString() {
		return this.surname + " " + this.name +" (Driver n°" + this.ID + ")";
	}
	
	//getters
	
	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public DriverStatus getStatus() {
		return status;
	}

	public Car getCar() {
		return car;
	}

	public ArrayList<Ride> getOnGoingRideRequests() {
		return onGoingRideRequests;
	}

	public ArrayList<Ride> getOnGoingRides() {
		return onGoingRides;
	}

	public ArrayList<Integer> getMarks() {
		return marks;
	}
	
	public Date getLastDateOfStatusChange() {
		return lastDateOfStatusChange ;
	}
	
	public boolean getIsAskedForPoolRequest() {
		return this.isAskedForPoolRequest ;
	}

	//setters
	
	/**
	 * If a Driver becomes offline, set their Car's 'CurrentDriver' attribute to null. If the Driver has ongoing Ride requests, declines them if he's not "on_duty" anymore.
	 * @param status Must be one of : offline, on_duty, on_a_ride, off_duty.
	 * @throws CarAlreadyUsedException Thrown if the Driver is trying to get back online but their Car is already used.
	 * @throws InvalidDateException 
	 * @throws RideRequestException
	 */
	public void setStatus(DriverStatus status) throws CarAlreadyUsedException, InvalidDateException {
		
		//Retrieving information time on this Driver
		
		
	
		
		if(this.status == DriverStatus.on_duty) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_ongointime = Environment.getInstance().getDriversStats().get(this).getOndutyTime();
			Environment.getInstance().getDriversStats().get(this).setOndutyTime(previous_ongointime + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
		else if(this.status == DriverStatus.on_a_ride) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_on_a_ride = Environment.getInstance().getDriversStats().get(this).getOn_a_RideTime();
			Environment.getInstance().getDriversStats().get(this).setOn_a_RideTime(previous_on_a_ride + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
		else if(this.status == DriverStatus.off_duty) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_off_duty = Environment.getInstance().getDriversStats().get(this).getOffDutyTime();
			Environment.getInstance().getDriversStats().get(this).setOffDutyTime(previous_off_duty + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
		
		
		if (this.car.getCurrentDriver() == this ) {
			if (status == DriverStatus.offline ) {
				this.car.setCurrentDriver(null);
			}
			if (status != DriverStatus.on_duty) {
				ArrayList<Ride> toRemove = new ArrayList<Ride>(); //intermediate List that stores the Ride requests to remove for this Driver to avoid ConcurrentModificationException
				for (Ride ride : this.onGoingRideRequests) {
				    toRemove.add(ride);
				}
				for (Ride ride : toRemove) {
					ride.removeObserver(this);
				}
				
			}
			this.status = status;
		} else if (this.car.getCurrentDriver() != null ) {
			throw new CarAlreadyUsedException(this.car.getCurrentDriver());
		} else {
			if (status != DriverStatus.offline) {
				this.car.setCurrentDriver(this);
			}
			this.status = status;
		}
			
		
		
		
		
	}
	
	public void refreshTimeStats() throws InvalidDateException {
		
		if(this.status == DriverStatus.on_duty) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_ongointime = Environment.getInstance().getDriversStats().get(this).getOndutyTime();
			Environment.getInstance().getDriversStats().get(this).setOndutyTime(previous_ongointime + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
		else if(this.status == DriverStatus.on_a_ride) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_on_a_ride = Environment.getInstance().getDriversStats().get(this).getOn_a_RideTime();
			Environment.getInstance().getDriversStats().get(this).setOn_a_RideTime(previous_on_a_ride + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
		else if(this.status == DriverStatus.off_duty) {
			Date currentTime = Clock.getInstance().getDate();
			int timetoadd = currentTime.timeSpentSince(this.lastDateOfStatusChange);
			int previous_off_duty = Environment.getInstance().getDriversStats().get(this).getOffDutyTime();
			Environment.getInstance().getDriversStats().get(this).setOffDutyTime(previous_off_duty + timetoadd);
			this.setLastDateOfStatusChange(currentTime);
		}
		
	}
	
	/**
	 * Marks this Driver as (co-)owner of the given Car, and adds them to the list of Observers of the Car.
	 * @param car The car that the Driver (co-)owns.
	 */
	public void setCar(Car car) {
		this.car = car;
	}

	public void setOnGoingRideRequests(ArrayList<Ride> onGoingRideRequests) {
		this.onGoingRideRequests = onGoingRideRequests;
	}

	public void setOnGoingRides(ArrayList<Ride> onGoingRides) {
		this.onGoingRides = onGoingRides;
	}

	public void setMarks(ArrayList<Integer> marks) {
		this.marks = marks;
	}
	
	public void setLastDateOfStatusChange(Date date) {
		this.lastDateOfStatusChange = date;
	}
	
	public void setIsAskedForPoolRequest(boolean bool) {
		this.isAskedForPoolRequest = bool;
		if(bool) {
			this.onGoingPoolRequest = Environment.getInstance().getPoolRequest();
			System.out.println("A new poolRequest has arrived for : " + this.toString() + "\nIt can be found in 'onGoingPoolRequest' and can be accepted or declined");
			
		}
	}
}
