package cli;

import java.util.ArrayList;

import exceptions.InvalidLocationException;
import helpers.Coordinates;
import helpers.Environment;
import mainClasses.Car;

/**
 * The command that allows a user to move a car. 
 * @author PC_Antoine
 *
 */
public class MoveCarCommand {
	
	public static void moveCarCommand(String carID, String xPos, String yPos) throws NumberFormatException, InvalidLocationException {
		ArrayList<Car> listOfCars = Environment.getInstance().getCarsList() ;
		int i = 0 ;
		int size = listOfCars.size();
		while(i< size && !listOfCars.get(i).getID().equals(carID)) {
			i += 1 ;
			}
		if(i<size) {
			listOfCars.get(i).setLocation(new Coordinates(Double.parseDouble(xPos), Double.parseDouble(yPos)));
		}
		else {
			System.out.print("This is a none-existing car ID");
		}
	}
		
	}


