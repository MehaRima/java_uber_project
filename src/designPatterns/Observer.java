package designPatterns;

/**
 * A class can implement the Observer interface when it wants to be informed of changes in observable objects.
 * @author Erwan
 *
 */
public interface Observer {
	
	/**
	 * This method is called whenever the observed object is changed. 
	 * @param obj The change passed on by the Observable's notifyObservers() method.
	 */
	public void update(Object obj);
		
}
