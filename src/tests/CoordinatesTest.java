package tests;

import org.junit.Test;

import exceptions.InvalidLocationException;
import helpers.Coordinates;

public class CoordinatesTest {

	@Test(expected = InvalidLocationException.class)
	public void testCoordinatesWrongX() throws InvalidLocationException {
		System.out.println("\ntestCoordinatesWrongX");
		Coordinates loc1 = new Coordinates(-12.65, 36.1);
		System.out.println(loc1.toString());
		Coordinates loc2 = new Coordinates(51, 2.36);
		System.out.println(loc2.toString());
	}
	
	@Test(expected = InvalidLocationException.class)
	public void testCoordinatesWrongY() throws InvalidLocationException {
		System.out.println("\ntestCoordinatesWrongY");
		Coordinates loc1 = new Coordinates(-12.65, 36.1);
		System.out.println(loc1.toString());
		Coordinates loc2 = new Coordinates(48.85, -50.01);
		System.out.println(loc2.toString());
	}

	@Test
	public void testRandomCoord() throws InvalidLocationException {
		System.out.println("\ntestRandomCoord");
		Coordinates loc1 = Coordinates.randomCoord();
		System.out.println(loc1);
		Coordinates loc2 = Coordinates.randomCoord();
		System.out.println(loc2);
		Coordinates loc3 = Coordinates.randomCoord();
		System.out.println(loc3);
		Coordinates loc4 = Coordinates.randomCoord();
		System.out.println(loc4);
		Coordinates loc5 = Coordinates.randomCoord();
		System.out.println(loc5);
		Coordinates loc6 = Coordinates.randomCoord();
		System.out.println(loc6);
		Coordinates loc7 = Coordinates.randomCoord();
		System.out.println(loc7);
		Coordinates loc8 = Coordinates.randomCoord();
		System.out.println(loc8);
		Coordinates loc9 = Coordinates.randomCoord();
		System.out.println(loc9);
	}
	
	@Test
	public void testDistance() throws InvalidLocationException {
		System.out.println("\ntestDistance");
		Coordinates loc1 = Coordinates.randomCoord();
		System.out.println(loc1);
		Coordinates loc2 = Coordinates.randomCoord();
		System.out.println(loc2);
		System.out.println(loc1.distance(loc2));
		Coordinates loc3 = Coordinates.randomCoord();
		System.out.println(loc3);
		Coordinates loc4 = Coordinates.randomCoord();
		System.out.println(loc4);
		System.out.println(loc3.distance(loc4));
		Coordinates loc5 = new Coordinates(10, -40);
		System.out.println(loc5);
		Coordinates loc6 = new Coordinates(10, +40);
		System.out.println(loc6);
		System.out.println(loc5.distance(loc6));
	}

	@Test
	public void testEqualsObject() throws InvalidLocationException {
		System.out.println("\ntestEqualsObject");
		Coordinates loc1 = new Coordinates(48.84, 2.32);
		Coordinates loc2 = new Coordinates(48.84, 2.32);
		Coordinates loc3 = new Coordinates(48.85, -11.25);
		System.out.println(loc1.equals(loc2));
		System.out.println(loc1.equals(loc3));
	}

	@Test(expected = InvalidLocationException.class)
	public void testSetX() throws InvalidLocationException {
		System.out.println("\ntestSetX");
		Coordinates loc = Coordinates.randomCoord();
		System.out.println(loc.toString());
		loc.setX(41.25);
		System.out.println(loc.toString());
		loc.setX(-50.23);
		System.out.println(loc.toString());
	}

	@Test(expected = InvalidLocationException.class)
	public void testSetY() throws InvalidLocationException {
		System.out.println("\ntestSetY");
		Coordinates loc = Coordinates.randomCoord();
		System.out.println(loc.toString());
		loc.setY(2.36);
		System.out.println(loc.toString());
		loc.setY(650);
		System.out.println(loc.toString());
	}

}
