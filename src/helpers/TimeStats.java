package helpers;

/**
 * It is a class used only by Driver object. It is not a Driver's attribute because it contains private information 
 * that shoud not be seen by not authorized users
 * @author PC_Antoine
 *
 */
public class TimeStats {
	
	/**
	 * The total amount of time spent on status 'On-duty'
	 */
	private int ondutyTime ;
	
	/**
	 * The total amount of time spent on status 'On going'
	 */
	private int on_a_rideTime ;
	
	/**
	 * The total amount of time spent on other status than 'Offline'
	 */
	private int offdutyTime;
	
	
	//Constructor
	
	public TimeStats(int ondutyTime, int on_a_rideTime, int offdutyTime) {
		this.ondutyTime = ondutyTime;
		this.on_a_rideTime = on_a_rideTime;
		this.offdutyTime = offdutyTime;
		
	}
	
	public String toString() {
		return "ondutyTime : " + this.ondutyTime + "\non_a_rideTime :" + this.on_a_rideTime  + "\n offdutyTime :" + this.offdutyTime;
	}


	public int getOndutyTime() {
		return this.ondutyTime;
	}


	public void setOndutyTime(int ondutyTime) {
		this.ondutyTime = ondutyTime;
	}


	public int getOn_a_RideTime() {
		return on_a_rideTime;
	}


	public void setOn_a_RideTime(int rideTime) {
		this.on_a_rideTime = rideTime;
	}
	
	public int getOffDutyTime() {
		return offdutyTime ; 
	}
	
	public void setOffDutyTime(int ongoingTime) {
		this.offdutyTime = ongoingTime ;
	}
	
	
	
	
	
	
	

}
