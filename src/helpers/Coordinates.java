package helpers;

import java.util.ArrayList;

import exceptions.InvalidLocationException;

/**
 * Class that provides an easy way to represent locations with x and y coordinates.
 * @author Erwan
 *
 */
public class Coordinates {
	
	/**
	 * Horizontal coordinate of the location.
	 */
	private double x;
	
	/**
	 * Vertical coordinate of the location.
	 */
	private double y;

	/**
	 * Create a new Point with the coordinates x and y (both x and y must be between -50 and 50).
	 * @param x	Horizontal coordinate of the location.
	 * @param y Vertical coordinate of the location.
	 * @throws InvalidLocationException Thrown if x or y are out of the boundaries mentioned above.
	 */
	public Coordinates(double x, double y) throws InvalidLocationException {
		if (x >= -50 & x <= 50 & y >= -50 & y <= 50) {
			this.x = x;
			this.y = y;
		} else {
			throw new InvalidLocationException(x);
		}
	}

	/**
	 * Static method that computes a random location in a square area of side 100, centered on (0, 0).
	 * @return The GPS Coordinates of a pseudo-random location in Paris.
	 * @throws InvalidLocationException
	 */
	public static Coordinates randomCoord() throws InvalidLocationException {
		double x = -50 + 100*Math.random();
		double y = -50 + 100*Math.random();
		return new Coordinates(x, y);
		
	}
	
	/**
	 * Computes the distance between the location that calls the method and another location given as a parameter.
	 * @param otherLocation
	 * @return The distance between the 2 locations.
	 */
	public double distance(Coordinates otherLocation) {
		double d = Math.sqrt( Math.pow(this.x - otherLocation.getX(), 2) + Math.pow(this.y - otherLocation.getY(), 2) );
		return d;
	}
	
	
	
	/**
	 * Return the nearest Coordinate to the current instance among a list of Coordinates
	 * @param list The list of Coordinates
	 * @return The nearest Coordinates
	 */
	public Coordinates nearest(ArrayList<Coordinates> list) {
		Coordinates nearest = list.get(0);
		double min_distance = this.distance(nearest);
		for(Coordinates coord : list) {
			double distance = this.distance(coord);
			if(distance < min_distance) {
				nearest = coord;
				min_distance = distance;
			}
		}
	
		return nearest ;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Coordinates.class) {
			Coordinates objp = (Coordinates) obj;
			if (this.getX() == objp.getX() && this.getY() == objp.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31*result + Double.valueOf(this.x).hashCode();
		result = 31*result + Double.valueOf(this.y).hashCode();
		return result;
	}
	
	/**
	 * Returns the coordinates of a given point using the format (x, y).
	 * @return String indicating the x and y coordinates.
	 */
	@Override
	public String toString() {
		int temp = (int) (this.x*100);
		double tempd = (double) temp;
		double x_round = tempd/100;
		temp = (int) (this.y*100);
		tempd = (double) temp;
		double y_round = tempd/100;
		String s = "(" + x_round + ", " + y_round +")";
		return s;
	}

	//getters and setters
	public double getX() {
		return x;
	}

	/**
	 * Changes the current x coordinate of the location to the entered value (must be between -50 and 50).
	 * @param x New horizontal coordinate desired.
	 * @throws InvalidLocationException Thrown if the entered coordinate is out of the boundaries mentioned above.
	 */
	public void setX(double x) throws InvalidLocationException {
		if (x >= -50 & x <= 50) {
			this.x = x;
		} else {
			throw new InvalidLocationException(x);
		}
	}

	public double getY() {
		return y;
	}

	/**
	 * Changes the current y coordinate of the location to the entered value (must be between -50 and 50).
	 * @param y New vertical coordinate desired.
	 * @throws InvalidLocationException Thrown if the entered coordinate is out of the boundaries mentioned above.
	 */
	public void setY(double y) throws InvalidLocationException {
		if (y >= -50 & y <= 50) {
			this.y = y;
		} else {
			throw new InvalidLocationException(y);
		}
	}
}