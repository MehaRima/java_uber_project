package tests;

import org.junit.Test;

import cli.AddCarDriverCommand;
import cli.AddDriverCommand;
import exceptions.InvalidCarTypeException;
import exceptions.InvalidLocationException;
import exceptions.NoExistingIDCarException;
import helpers.Environment;

public class AddDriverCommandTest {

	@Test
	public void addDriverWithNameTest() {
		System.out.println("\n addDriverWithNameTest \n");
		Environment.wipeEnvironment();
		try {
			AddCarDriverCommand.addCar("berline");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			e.printStackTrace();
		}
		try {
			AddDriverCommand.addDriverWithName("Borris", "Grinchon", "Berline1");
		} catch (NoExistingIDCarException e) {
			e.printStackTrace();
		}
		System.out.println(Environment.getInstance().getInfoDrivers());
		
	}
	
	@Test
	public void addDriverNoNameTest() {
		System.out.println("\n addDriverNoNameTest \n");
		Environment.wipeEnvironment();
		try {
			AddCarDriverCommand.addCar("standard");
		} catch (InvalidLocationException | InvalidCarTypeException e) {
			e.printStackTrace();
		}
		try {
			AddDriverCommand.addDriverNoName("StandardCar1");
		} catch (NoExistingIDCarException e) {
			e.printStackTrace();
		}
		System.out.println(Environment.getInstance().getInfoDrivers());
		
	}
	

}
