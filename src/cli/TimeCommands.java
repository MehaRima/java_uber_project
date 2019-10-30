package cli;


import helpers.Environment;

public class TimeCommands {
	
	public static String getTimeCommand() {
		return (Environment.getInstance().getClock().getDate().toString());
	}
	
	
	public static void passTimeCommand(int minutes) {
		try {
			Environment.getInstance().getClock().passTime(minutes);
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		}
	}
}
