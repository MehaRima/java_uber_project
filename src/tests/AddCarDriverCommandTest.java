package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import exceptions.InvalidCarTypeException;
import exceptions.InvalidLocationException;
import helpers.Environment;

public class AddCarDriverCommandTest {

	@Test
	public void addCarTest() {
		Environment.wipeEnvironment();
		System.out.println("\n addCarTest \n");
		try {
			AddCarDriverCommand.addCar("berline");
		} catch (InvalidLocationException | InvalidCarTypeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			AddCarDriverCommand.addCar("berline");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("van");
		} catch (InvalidLocationException | InvalidCarTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			AddCarDriverCommand.addCar("F1");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Environment.getInstance().getInfoCars());
	}

	@Test
	public void addCarDriverWithNameTest() {
		Environment.wipeEnvironment();
		System.out.println("\naddCarDriverWithNameTest\n");
		AddCarDriverCommand.addCarDriverWithName("Antoine", "Miquel", "berline");
		AddCarDriverCommand.addCarDriverWithName("Erwan", "DeLepineau", "van");
		AddCarDriverCommand.addCarDriverWithName("Malo", "Hervé", "standard");
		AddCarDriverCommand.addCarDriverWithName("Lewis", "Hamilton", "F1");
		System.out.println(Environment.getInstance().getInfoDrivers());
	}
	
	@Test
	public void addCarDriverNoNameTest() {
		Environment.wipeEnvironment();
		System.out.println("\naddCarDriverNoNameTest\n");
		AddCarDriverCommand.addCarDriverNoName("berline");
		AddCarDriverCommand.addCarDriverNoName("van");
		AddCarDriverCommand.addCarDriverNoName("standard");
		AddCarDriverCommand.addCarDriverNoName("F1");
		System.out.println(Environment.getInstance().getInfoDrivers());
		
	}

}
