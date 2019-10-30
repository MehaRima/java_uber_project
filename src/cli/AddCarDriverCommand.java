package cli;

import exceptions.InvalidCarTypeException;
import exceptions.InvalidLocationException;
import helpers.Environment;
import mainClasses.Car;

public class AddCarDriverCommand {
	
	public static Car addCar(String carType) throws InvalidLocationException, InvalidCarTypeException {
		
		Car newcar;
		if (carType.equals("standard")) {
			newcar = Environment.getInstance().getSCFact().createCar();
		}
		else if (carType.equals("berline")) {
			newcar = Environment.getInstance().getBFact().createCar();
		}
		else  if (carType.equals("van")){
			newcar = Environment.getInstance().getVFact().createCar();
		}
		else {
			throw new InvalidCarTypeException( carType + " is not a valid car type");
		}
		return newcar;
	}

	
	public static void addCarDriverWithName(String name, String surname, String carType) {
		try {
			Car newcar = addCar(carType);
			Environment.getInstance().getDFact().createDriver(name, surname, newcar);
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addCarDriverNoName(String carType) {
		try {
			Car newcar = addCar(carType);
			Environment.getInstance().getDFact().createDriver(newcar);
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			System.out.println(e.getMessage());
		}
	}
}
