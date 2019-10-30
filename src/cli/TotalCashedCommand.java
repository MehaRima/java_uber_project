package cli;

import helpers.Environment;

public class TotalCashedCommand {
	
	public static String totalCashed() {
		
		return Environment.getInstance().getSystemBalance();
	}

}
