package designPatterns;
import exceptions.InvalidLocationException;
import helpers.Environment;
import mainClasses.*;

/**
 * The Customer Factory class.
 * @author Antoine
 *
 */
public class CustomerFactory {
	
	/**
	 * Creates a Customer with a random starting location, and adds them to the Environment's list.
	 * @param name String representing the last name of the Customer.
	 * @param surname String representing the first name of the Customer.
	 * @throws InvalidLocationException
	 */
	public Customer createCustomer(String name, String surname) throws InvalidLocationException {
		String rand1 = Double.toString(Math.random());
		String rand2 = Double.toString(Math.random());
		rand1 = rand1.split("\\.")[1].substring(0, 8);
		rand2 = rand2.split("\\.")[1].substring(0, 8);
		String creditCard = rand1 + rand2;
		Customer cust = new Customer(name, surname, creditCard);
		Environment.getInstance().getCustomersList().add(cust);
		return cust;
	}
	
	/**
	 * Creates a Customer with the given starting location, and adds them to the Environment's list.
	 * @param name String representing the last name of the Customer.
	 * @param surname String representing the first name of the Customer.
	 * @param x_location Double representing the x coordinate of the location of the Customer.
	 * @param y_location Double representing the y coordinate of the location of the Customer.
	 * @throws InvalidLocationException Thrown if the given location is invalid (out of the [-50, 50]² boundaries).
	 */
	public Customer createCustomer(String name, String surname, double x_location, double y_location) throws InvalidLocationException {
		String rand1 = Double.toString(Math.random());
		String rand2 = Double.toString(Math.random());
		rand1 = rand1.split("\\.")[1].substring(0, 8);
		rand2 = rand2.split("\\.")[1].substring(0, 8);
		String creditCard = rand1 + rand2;
		Customer cust = new Customer(name, surname, creditCard, x_location, y_location);
		Environment.getInstance().getCustomersList().add(cust);
		return cust;
	}
	
	/**
	 * Creates a Customer with a generic name and surname, as well as a random starting location, and adds them to the Environment's list.
	 * @throws InvalidLocationException
	 */
	public Customer createCustomer() throws InvalidLocationException {
		String rand1 = Double.toString(Math.random());
		String rand2 = Double.toString(Math.random());
		rand1 = rand1.split("\\.")[1].substring(0, 8);
		rand2 = rand2.split("\\.")[1].substring(0, 8);
		String creditCard = rand1 + rand2;
		Customer cust = new Customer(creditCard);
		Environment.getInstance().getCustomersList().add(cust);
		return cust;
	}
	

}
