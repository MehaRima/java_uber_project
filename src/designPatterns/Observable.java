package designPatterns;

/**
 * A class can implement the Observable interface when it needs to notify certain Observers each time a certain change happens.
 * @author Erwan
 *
 */
public interface Observable {
	
	/**
	 * Adds a a new Observer to the list of Observers for this Observable.
	 * @param observer The new Observer to add.
	 */
	public void addObserver(Observer observer);
	
	/**
	 * Removes an Observer from the list of Observers.
	 * @param observer The Observer to remove.
	 */
	public void removeObserver(Observer observer);
	
	/**
	 * Clears the list of Observers for this Observable.
	 */
	public void removeAllObservers();
	
	/**
	 * Informs all Observers of a change in the Observable.
	 */
	public void notifyObservers();
}
