package cli;
import java.util.ArrayList;

import exceptions.NoExistingIDCarException;
import  helpers.*;
import mainClasses.*;

/**
 * The command that allows the user to add a new driver with an already existing car.
 * @author PC_Antoine
 *
 */
public class AddDriverCommand {
	
	
	public static void addDriverWithName(String name , String surname, String carID) throws NoExistingIDCarException {
		ArrayList<Car> listOfCars = Environment.getInstance().getCarsList() ;
		Car driverCar = null ;
		int i = 0 ;
		int size = listOfCars.size();
		while(i< size && !listOfCars.get(i).getID().equals(carID)) {
			i += 1 ;
			}
		if(i<size) {
			driverCar = listOfCars.get(i);
			Environment.getInstance().getDFact().createDriver(name, surname, driverCar);
	}
		else {
			throw new NoExistingIDCarException("\n This is a non-existing car ID \n");
		}
	}
	
	
	public static void addDriverNoName(String carID) throws NoExistingIDCarException {
		ArrayList<Car> listOfCars = Environment.getInstance().getCarsList() ;
		Car driverCar = null ;
		int i = 0 ;
		int size = listOfCars.size();
		while(i< size && !listOfCars.get(i).getID().equals(carID)) {
			i += 1 ;
			}
		if(i<size) {
			driverCar = listOfCars.get(i);
			Environment.getInstance().getDFact().createDriver(driverCar);
	}
		else {
			throw new NoExistingIDCarException("\n This is a non-existing car ID\n ");
		}
	}
	
	
}
