package helpers;

import mainClasses.Driver;
import mainClasses.Ride;
import mainClasses.UberPool;
import mainClasses.Car;
import mainClasses.Customer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import designPatterns.BerlineFactory;
import designPatterns.CustomerFactory;
import designPatterns.DriverFactory;
import designPatterns.StandardCarFactory;
import designPatterns.VanFactory;
import exceptions.CarAlreadyUsedException;
import exceptions.InvalidDateException;
import exceptions.MarkAttributionException;
import exceptions.NoAskedForPoolRequestException;
import exceptions.NoMorePotentialsDriverException;
import exceptions.PoolRequestException;
import exceptions.RideRequestException;

/**
 * A class that stores the relevant informations about a myUber universe (list of drivers, customers, past rides, etc.) and that can compute relevant statistics about it. Applies the Singleton pattern : can only have one instance at a given time.
 * @author Erwan
 *
 */
public class Environment {

	private static Environment instance = null;

	/**
	 * The universe's StandardCarFactory.
	 */
	private StandardCarFactory SCFact;

	/**
	 * The universe's BerlineFactory.
	 */
	private BerlineFactory BFact;

	/**
	 * The universe's StandardCarFactory.
	 */
	private VanFactory VFact;

	/**
	 * The universe's CustomerFactory.
	 */
	private CustomerFactory CFact;

	/**
	 * The universe's DriverFactory.
	 */
	private DriverFactory DFact;

	/**
	 * Contains all the Drivers of the current simulated universe.
	 */
	private ArrayList<Driver> driversList;

	/**
	 * Contains all the Customers of the current simulated universe.
	 */
	private ArrayList<Customer> customersList;

	/**
	 * Contains all the Cars of the current simulated universe.
	 */
	private ArrayList<Car> carsList;

	/**
	 * Contains all the past completed Rides of the current simulated universe.
	 */
	private ArrayList<Ride> bookOfRides;

	/**
	 * Contains all the currently on-going Rides in the simulated universe.
	 */
	private ArrayList<Ride> onGoingRides;

	/**
	 * Contains all the currently on-going UberPool Rides in the simulated universe.
	 */
	private ArrayList<UberPool[]> onGoingUberPoolRides;

	/**
	 * A unique clock that allows for time management in the simulated universe.
	 */
	private Clock clock;


	/**
	 * Contains the time statistics for all the Driver of the environment
	 */
	private Map<Driver, TimeStats> driversStats;

	/**
	 * The implementation of a poolRequest that stores the current uberpool demands.
	 */
	private PoolRequest poolRequest;

	/**
	 * The list of the bookRides that have to be treated in the future
	 */
	private ArrayList<FutureBookRide> listOfFutureBookRide;



	/**
	 * Creates a new myUber Environment, initially with no population or past records. Can only be called through method getInstance to ensure unicity of the instance.
	 */
	private Environment() {
		this.SCFact = new StandardCarFactory();
		this.BFact = new BerlineFactory();
		this.VFact = new VanFactory();
		this.CFact = new CustomerFactory();
		this.DFact = new DriverFactory();
		this.driversList = new ArrayList<Driver>();
		this.customersList = new ArrayList<Customer>();
		this.carsList = new ArrayList<Car>();
		this.bookOfRides = new ArrayList<Ride>();
		this.onGoingRides = new ArrayList<Ride>();
		this.onGoingUberPoolRides = new ArrayList<UberPool[]>();
		this.clock = Clock.getInstance();
		this.driversStats  = new HashMap<Driver, TimeStats>();
		this.poolRequest = new PoolRequest();
		this.listOfFutureBookRide = new ArrayList<FutureBookRide>();
	}

	/**
	 * 
	 * @return instance The unique instance of the Environment class (a newly created one if none previously existed).
	 */
	public static Environment getInstance() {
		if (instance == null) {
			instance = new Environment();
		}
		return instance;
	}

	/**
	 * Deletes the currently active Environment (if one exists) and wipes its Clock.
	 */
	public static void wipeEnvironment() {
		instance = null;
		Clock.wipeClock();
	}


	public void removeFutureBookingFromARide(Ride ride) {
		FutureBookRide fbr = null ;
		for(FutureBookRide efbr : Environment.getInstance().getListOfFutureBookRide()) {
			if(efbr.getSimulatedRides()[efbr.getTypeOfRide()-1] == ride) {
				fbr = efbr;
			}
		}

		Environment.getInstance().getListOfFutureBookRide().remove(fbr);
	}

	/**
	 * When time has passed through the Clock's passTime(int), this method checks if the onGoingRides have advanced to a next step in their completion :
	 * for instance, if enough time has passed for a formerly on-going Ride to be completed, this method makes the necessary changes to the Ride, Customer, Driver and Environment
	 * instances concerned.
	 * @throws InvalidDateException 
	 * @throws CarAlreadyUsedException 
	 * @throws MarkAttributionException 
	 */
	public void refreshOnGoingRides() throws InvalidDateException, CarAlreadyUsedException, MarkAttributionException {
		ArrayList<Ride> toRemove = new ArrayList<Ride>();
		for (Ride ride : this.onGoingRides) {
			if (ride.getStatus() == RideStatus.confirmed || ride.getStatus() == RideStatus.ongoing) {
				if (this.getClock().getDate().isPosteriorTo(ride.getEndTime())) {
					ride.getAssociatedDriver().getCar().setLocation(ride.getDestination());
					ride.getAssociatedCustomer().setLocation(ride.getDestination());
					ride.setStatus(RideStatus.completed);
					ride.getAssociatedDriver().setStatus(DriverStatus.on_duty);
					ride.getAssociatedCustomer().setOnGoingRide(null);
					String msg = ride.getAssociatedCustomer().toString() + " has completed the following Ride : " + ride.toString() +". They can now give a mark to the Driver using the Ride's ID if they wish.";
					ride.getAssociatedCustomer().getMailBox().add(msg);
					System.out.println(msg);
					Environment.getInstance().getBookOfRides().add(ride);
					if (ride.getMark() != 0) {
						ride.getAssociatedDriver().getMarks().add(ride.getMark());
					}
					toRemove.add(ride);
				} else if (this.getClock().getDate().isPosteriorTo(ride.getPickupTime()) && ride.getStatus() == RideStatus.confirmed) {
					ride.setStatus(RideStatus.ongoing);
					ride.getAssociatedDriver().getCar().setLocation(ride.getStartingPoint());
					String msg = ride.getAssociatedCustomer().toString() + " has been picked up by " + ride.getAssociatedDriver().toString() + " at " + ride.getPickupTime() + " at " + ride.getStartingPoint()+
							". They will be dropped off at " + ride.getEndTime() + " at " + ride.getDestination() + ".";
					ride.getAssociatedCustomer().getMailBox().add(msg);
					System.out.println(msg);
				}
			}
		}
		ArrayList<UberPool[]> poolsToRemove = new ArrayList<UberPool[]>();
		for (UberPool[] pool : this.onGoingUberPoolRides) { // in the case of UberPool, the status of the Driver must only be changed if all Customers have been dropped-off, and his location must be changed to the last one reached
			Coordinates lastLocationReached = pool[0].getAssociatedDriver().getCar().getLocation();
			Date lastEventDate = pool[0].getBookingTime();
			boolean allRidesCompleted = true;
			for (UberPool ride : pool) {
				if (ride.getStatus() == RideStatus.confirmed || ride.getStatus() == RideStatus.ongoing) {
					if (this.getClock().getDate().isPosteriorTo(ride.getEndTime())) {
						if (ride.getEndTime().isPosteriorTo(lastEventDate)) {
							lastEventDate = ride.getEndTime();
							lastLocationReached = ride.getDestination();
						}
						ride.getAssociatedCustomer().setLocation(ride.getDestination());
						ride.setStatus(RideStatus.completed);
						ride.getAssociatedDriver().setStatus(DriverStatus.on_duty);
						System.out.println(ride.getAssociatedDriver().getStatus());
						ride.getAssociatedCustomer().setOnGoingRide(null);
						String msg = ride.getAssociatedCustomer().toString() + " has completed the following Ride dsklmfiqhs : " + ride.toString() +".";
						ride.getAssociatedCustomer().getMailBox().add(msg);
						System.out.println(msg);
						Environment.getInstance().getBookOfRides().add(ride);
						if (ride.getMark() != 0) {
							ride.getAssociatedCustomer().markRide(ride, ride.getMark());
						}
						toRemove.add(ride);
					} else if (this.getClock().getDate().isPosteriorTo(ride.getPickupTime()) && ride.getStatus() == RideStatus.confirmed) {
						if (ride.getPickupTime().isPosteriorTo(lastEventDate)) {
							lastEventDate = ride.getPickupTime();
							lastLocationReached = ride.getStartingPoint();
						}
						ride.setStatus(RideStatus.ongoing);
						ride.getAssociatedDriver().getCar().setLocation(ride.getStartingPoint());
						String msg = ride.getAssociatedCustomer().toString() + " has been picked up by " + ride.getAssociatedDriver().toString() + " at " + ride.getPickupTime() + " at " + ride.getStartingPoint()+
								". They will be dropped off at " + ride.getEndTime() + " at " + ride.getDestination() + ".";
						ride.getAssociatedCustomer().getMailBox().add(msg);
						System.out.println(msg);
						allRidesCompleted = false;
					} else {
						allRidesCompleted = false;
					}
				}
			}
			pool[0].getAssociatedDriver().getCar().setLocation(lastLocationReached);
			if (allRidesCompleted) {
				pool[0].getAssociatedDriver().setStatus(DriverStatus.on_duty);
				poolsToRemove.add(pool);
			}
		}
		for (Ride r : toRemove) {
			Environment.getInstance().getOnGoingRides().remove(r);
		}
		
		for (UberPool[] p : poolsToRemove) {
			Environment.getInstance().getOnGoingUberPoolRides().remove(p);
		}

	}

	/**
	 * When time has passed through the Clock's passTime(int), this method checks if time has come for a Ride in the ListOfFutureBookRide to be booked.
	 * @throws RideRequestException
	 * @throws PoolRequestException
	 * @throws NoMorePotentialsDriverException
	 * @throws CarAlreadyUsedException
	 * @throws InvalidDateException
	 * @throws NoAskedForPoolRequestException 
	 */
	public void refreshFutureBookRide() throws RideRequestException, PoolRequestException, NoMorePotentialsDriverException, CarAlreadyUsedException, InvalidDateException, NoAskedForPoolRequestException {
		for(FutureBookRide futureBookRide : Environment.getInstance().getListOfFutureBookRide()) {
			if(Clock.getInstance().getDate().isPosteriorTo(futureBookRide.getDateOfBegining())) {
				futureBookRide.getCustomer().bookRide(futureBookRide.getSimulatedRides(), futureBookRide.getTypeOfRide(), futureBookRide.getDriverMark());
				Environment.getInstance().getListOfFutureBookRide().remove(futureBookRide);
			}
		}

	}

	/**
	 * Computes the total number of rides completed, total money spent and total time spent on Uber rides for a certain customer.
	 * @param customer The customer you want to get statistics of.
	 * @return String containing the statistics about the customer.
	 * @throws InvalidDateException Thrown if an error occurs while computing the total time spent on rides.
	 */
	public String getBalance(Customer customer) throws InvalidDateException {
		int nbRides = 0;	
		double charges = 0;
		double timeSpent = 0;
		for (Ride ride : this.bookOfRides) {
			if (ride.getAssociatedCustomer() == customer) {
				nbRides ++;
				charges += ride.getPrice();
				timeSpent += ride.getEndTime().timeSpentSince(ride.getPickupTime());
			}
		}
		return customer.toString() + " has taken " + nbRides + " rides, for a total of " + timeSpent + " minutes spent and " + charges + "€ paid.";
	}

	/**
	 * Computes the total number of rides completed, total money earned, on-duty rate of driving and rate of activity for a certain driver.
	 * @param driver The driver you want to get statistics of.
	 * @return String containing the statistics about the driver.
	 */
	public String getBalance(Driver driver) {
		int nbRides = 0;
		double cashed = 0;
		TimeStats timestats = this.driversStats.get(driver);
		int on_duty_time = timestats.getOndutyTime();
		int off_duty_time = timestats.getOffDutyTime();
		int on_a_ride_time = timestats.getOn_a_RideTime();
		double rateOfDriving = (double) on_a_ride_time / on_duty_time;
		double rateOfActivity = (double) (on_duty_time + on_a_ride_time) / (on_duty_time + on_a_ride_time + off_duty_time);
		for (Ride ride : this.bookOfRides) {
			if (ride.getAssociatedDriver() == driver) {
				nbRides ++;
				cashed += ride.getPrice();
			}
		}
		return driver.toString() + " has completed " + nbRides + " rides, for a total of " + cashed + " euros earned. His/her on-duty rate of driving is " + rateOfDriving +
				"% and his/her rate of activity is " + rateOfActivity + "%.";
	}

	/**
	 * Computes global statistics about the myUber universe that is running : total number of Rides completed and total money cashed in by the Drivers.
	 * @return String containing the statistics about the universe.
	 */
	public String getSystemBalance() {
		int totalRides = this.bookOfRides.size();
		double totalCashed = 0;
		for (Ride ride : this.bookOfRides) {
			totalCashed += ride.getPrice();
		}
		return "In this myUber universe, a total of " + totalRides + " rides have been completed so far, for a whopping amount of " + totalCashed +" euros cashed in.";
	}


	/**
	 * Returns the list of all Customers with their relevant information
	 * @return
	 */
	public String getInfoCustomers() {
		String s = "\n Customers List : \n";
		for (Customer c : Environment.getInstance().getCustomersList()) {
			s +="\n- " + c.getInfo();
		}
		return s ;
	}

	/**
	 * Returns the list of all Drivers with their relevant information
	 * @return
	 */
	public String getInfoDrivers() {
		String s = "\n Drivers List \n";
		for (Driver d : Environment.getInstance().getDriversList()) {
			s += "\n- " + d.getInfo();
		}
		return s ;
	}

	/**
	 * Returns the list of all Cars with their relevant information
	 * @return
	 */
	public String getInfoCars() {
		String s = "\n Cars List \n";
		for (Car c : Environment.getInstance().getCarsList()) {
			s += "\n- " + c.getInfo();
		}
		return s ;
	}
	/**
	 * Computes the total numbers of ride for all the customers.
	 * @return a sorted Map to the Customer with the more completed rides to the lowest. 
	 */
	public Map<Customer, Integer> mostFreqCustomer() {
		Map<Customer, Integer> freqStats = new HashMap<Customer, Integer>() ;
		for(Customer customer : customersList) {
			freqStats.put(customer, 0 );
		}

		for (Ride ride : bookOfRides) {
			freqStats.put(ride.getAssociatedCustomer(),freqStats.get(ride.getAssociatedCustomer()) + 1);
		}

		Map<Customer, Integer> sortedMap = reverseSortByValue(freqStats)	;	

		return sortedMap ;		
	}


	/**
	 * Computes the total numbers of ride for all the customers.
	 * @returna sorted Map to the Customer with the highest amount of charges to the lowest.
	 */
	public Map<Customer, Double> mostChargedCustomer(){
		Map<Customer, Double> chargesStats = new HashMap<Customer, Double>() ;
		for(Customer customer : customersList) {
			chargesStats.put(customer, (double) 0 );
		}

		for (Ride ride : bookOfRides) {
			chargesStats.put(ride.getAssociatedCustomer(),chargesStats.get(ride.getAssociatedCustomer()) + ride.getPrice());
		}

		Map<Customer, Double> sortedMap = reverseSortByValueCustomerDouble(chargesStats)	;	

		return sortedMap ;		
	}


	/**
	 * Computes the mean of marks for all the Drivers
	 * @return a sorted Map to the Driver the more appriciated to the less
	 */
	public Map<Driver, Double> mostAppreciatedDriver(){
		Map<Driver, Double> marks = new HashMap<Driver, Double>();
		for(Driver driver : this.driversList) {

			marks.put(driver, driver.getAvgMark());
		}
		Map<Driver, Double> sortedMap =  reverseSortByValueDriverDouble(marks);
		return sortedMap;
	}



	/**
	 * Retun a Map of Drivers sorted on thier occupation rate (i.e time spent 'on_duty' over time spent 'on_duty' or 'on_a_ride'
	 * @return
	 * @throws InvalidDateException 
	 */
	public Map<Driver, Double> leastOccupiedDriver() {

		Map<Driver, Double> map = new HashMap<Driver, Double>();

		for(Driver driver : this.driversList) {
			try {
				driver.refreshTimeStats();
			} catch (InvalidDateException e) {
				e.printStackTrace();
			}
			TimeStats timestats = this.driversStats.get(driver);
			int on_duty_time = timestats.getOndutyTime();
			int on_a_ride_time = timestats.getOn_a_RideTime();
			if(on_duty_time == 0 ) {
				map.put(driver, (double) 0);
			}
			else{
				map.put(driver, (double) on_duty_time / (on_duty_time + on_a_ride_time));
			}
		}

		Map<Driver, Double> sortedMap = sortByValueDriverDouble(map);
		return sortedMap;

	}


	/**
	 * Sorts Map<Customer, Integer> on Integers with a reverse order. It is used in "mostFreqCustomer()"
	 * @param unsortMap The initial unsortedMap
	 * @return A new sorted Map
	 */

	private static Map<Customer, Integer> reverseSortByValue(Map<Customer, Integer> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Customer, Integer>> list =
				new LinkedList<Map.Entry<Customer, Integer>>(unsortMap.entrySet());

		// 2. Sort list with Collections.sort() with a reverse order. 
		Collections.sort(list, new Comparator<Map.Entry<Customer, Integer>>() {
			public int compare(Map.Entry<Customer, Integer> o1,
					Map.Entry<Customer, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<Customer, Integer> sortedMap = new LinkedHashMap<Customer, Integer>();
		for (Map.Entry<Customer, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Sorts Map<Customer, double > on doubles values with a reverse order. It is used in "mostChargedCustomer()"
	 * @param unsortMap the initial unsorted Map
	 * @return a new sorted Map
	 */
	private static Map<Customer, Double> reverseSortByValueCustomerDouble(Map<Customer, Double> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Customer, Double>> list =
				new LinkedList<Map.Entry<Customer, Double>>(unsortMap.entrySet());

		// 2. Sort list with Collections.sort() with a reverse order. 
		Collections.sort(list, new Comparator<Map.Entry<Customer, Double>>() {
			public int compare(Map.Entry<Customer, Double> o1,
					Map.Entry<Customer, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<Customer, Double> sortedMap = new LinkedHashMap<Customer, Double>();
		for (Map.Entry<Customer, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Sorts Map<Driver, Double> on doubles values with a reverse order. It is used in "mostAppreciateDriver"
	 * @param unsortMap
	 * @return
	 */
	private static Map<Driver, Double> reverseSortByValueDriverDouble(Map<Driver, Double> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Driver, Double>> list =
				new LinkedList<Map.Entry<Driver, Double>>(unsortMap.entrySet());

		// 2. Sort list with Collections.sort() with a reverse order. 
		Collections.sort(list, new Comparator<Map.Entry<Driver, Double>>() {
			public int compare(Map.Entry<Driver, Double> o1,
					Map.Entry<Driver, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<Driver, Double> sortedMap = new LinkedHashMap<Driver, Double>();
		for (Map.Entry<Driver, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Return a new Map sorted on Double with a normal order. It is used in 'leastOccupiedDriver'
	 * @param unsortMap
	 * @return
	 */
	public static Map<Driver, Double> sortByValueDriverDouble(Map<Driver, Double> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Driver, Double>> list =
				new LinkedList<Map.Entry<Driver, Double>>(unsortMap.entrySet());

		// 2. Sort list with Collections.sort() with a reverse order. 
		Collections.sort(list, new Comparator<Map.Entry<Driver, Double>>() {
			public int compare(Map.Entry<Driver, Double> o1,
					Map.Entry<Driver, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<Driver, Double> sortedMap = new LinkedHashMap<Driver, Double>();
		for (Map.Entry<Driver, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}




	@Override
	public String toString() {
		return "This myUber universe has a total of " + this.customersList.size() +" customers, " + this.driversList.size() + " Drivers and " + this.carsList.size() +" cars, and a whopping " 
				+ this.bookOfRides.size() + " rides have been completed so far." + " The virtual time and date of the universe is " + this.getClock().toString() +".";
	}

	// getters and setters

	public ArrayList<Driver> getDriversList() {
		return driversList;
	}

	public ArrayList<Customer> getCustomersList() {
		return customersList;
	}

	public ArrayList<Car> getCarsList() {
		return carsList;
	}

	public ArrayList<Ride> getBookOfRides() {
		return bookOfRides;
	}

	public ArrayList<Ride> getOnGoingRides() {
		return onGoingRides;
	}

	public ArrayList<UberPool[]> getOnGoingUberPoolRides() {
		return onGoingUberPoolRides;
	}

	public Clock getClock() {
		return clock;
	}

	public Map<Driver, TimeStats> getDriversStats(){
		return driversStats ;
	}

	public PoolRequest getPoolRequest() {
		return poolRequest;
	}

	public void setDriversList(ArrayList<Driver> driversList) {
		this.driversList = driversList;
	}

	public void setCustomersList(ArrayList<Customer> customersList) {
		this.customersList = customersList;
	}

	public void setCarsList(ArrayList<Car> carsList) {
		this.carsList = carsList;
	}

	public void setBookOfRides(ArrayList<Ride> bookOfRides) {
		this.bookOfRides = bookOfRides;
	}

	public void setOnGoingRides(ArrayList<Ride> onGoingRides) {
		this.onGoingRides = onGoingRides;
	}

	public void setOnGoingUberPoolRides(ArrayList<UberPool[]> onGoingUberPoolRides) {
		this.onGoingUberPoolRides = onGoingUberPoolRides;
	}

	public void setDriversStats(Map<Driver, TimeStats> driversStats) {
		this.driversStats = driversStats;
	}

	public void setPoolRequest(PoolRequest poolRequest) {
		this.poolRequest = poolRequest;
	}

	public void setListOfFutureBookRide(ArrayList<FutureBookRide> listOfFutureBookRide) {
		this.listOfFutureBookRide = listOfFutureBookRide;
	}


	public StandardCarFactory getSCFact() {
		return SCFact;
	}

	public void setSCFact(StandardCarFactory sCFact) {
		SCFact = sCFact;
	}

	public BerlineFactory getBFact() {
		return BFact;
	}

	public void setBFact(BerlineFactory bFact) {
		BFact = bFact;
	}

	public VanFactory getVFact() {
		return VFact;
	}

	public void setVFact(VanFactory vFact) {
		VFact = vFact;
	}

	public CustomerFactory getCFact() {
		return CFact;
	}

	public void setCFact(CustomerFactory cFact) {
		CFact = cFact;
	}

	public DriverFactory getDFact() {
		return DFact;
	}

	public void setDFact(DriverFactory dFact) {
		DFact = dFact;
	}

	public ArrayList<FutureBookRide> getListOfFutureBookRide() {
		return listOfFutureBookRide;
	}





}
