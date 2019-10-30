package cli;

import helpers.Environment;
/**
 * The command that allows the user to retrieve different statistics on customers
 * 'mostfrequent' the customers are sorted by the number of ride they completed
 * 'mostcharged' the customers are sorted by the amount they have paid on my_Uber
 * @author PC_Antoine
 *
 */
public class DisplayCustomersCommand {
	
	public static String displayCustomers(String sortPolicy) {
		if(sortPolicy.equals("mostfrequent")) {
			return Environment.getInstance().mostFreqCustomer().toString();
		}
		
		else if(sortPolicy.equals("mostcharged")) {
			return Environment.getInstance().mostChargedCustomer().toString();
		}
		
		else {
			return "Invalid sortPolicy";
		}
	}

}
