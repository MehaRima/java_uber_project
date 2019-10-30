package helpers;

import exceptions.InvalidDateException;

/**
 * Provides an easy way to store dates (hour, minute, day, month, year).
 * @author Erwan
 *
 */
public class Date {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	/**
	 * Create a Date with the chosen year, month, day, hour and minute.
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @throws InvalidDateException Thrown if the input month, day, hour or minute are not valid, or if the year input is not between 2000 and 2100.
	 */
	public Date(int year, int month, int day, int hour, int minute) throws InvalidDateException {
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
		this.setHour(hour);
		this.setMinute(minute);
	}

	/**
	 * Allows to add a certain number of minutes to the date.
	 * @param minutes The number of minutes you want to add.
	 * @throws InvalidDateException Thrown if you try to add a negative number of minutes.
	 */
	public void addTime(int minutes) throws InvalidDateException{
		if (minutes >= 0) {
			int totalMinutes = this.minute + minutes;
			if (totalMinutes > 59) {
				int totalHours = this.hour + totalMinutes/60;
				this.minute = totalMinutes%60;
				if (totalHours > 23) {
					int totalDays = this.day + totalHours/24;
					this.hour = totalHours%24;
					if (totalDays > 31) {
						int totalMonths = this.month + totalDays/31;
						if(totalDays%31 ==0) {
							totalMonths -= 1;
							this.day = 31;
						} else {
							this.day = totalDays%31;
						}
						if (totalMonths > 12) {
							int totalYears = this.year + totalMonths/12;
							if(totalMonths%12 == 0) {
								totalYears -= 1;
								this.month = 12;
							} else {
								this.month = totalMonths%12;
							}
							this.year = totalYears;
						} else {
							this.month = totalMonths;
						}
					} else {
						this.day = totalDays;
					}
				} else {
					this.hour = totalHours;
				}
			} else {
				this.minute = totalMinutes;
			}
		} else {
			throw new InvalidDateException("You can't go back in time !");
		}
	}

	/**
	 * Computes the difference in time between two dates.
	 * @param beginDate The beginning date of the duration you want to compute.
	 * @return The number of minutes between the beginning date and the ending date.
	 * @throws InvalidDateException Thrown if the ending date (the one calling the method) is anterior to the beginning date beginDate.
	 */
	public int timeSpentSince(Date beginDate) throws InvalidDateException {
		int diffYears = this.year - beginDate.getYear();
		int diffMonths = this.month - beginDate.getMonth();
		int diffDays = this.day - beginDate.getDay();
		int diffHours = this.hour - beginDate.getHour();
		int diffMinutes = (this.minute - beginDate.getMinute()) + 60*(diffHours + 24*(diffDays + 31*(diffMonths + 12*diffYears)));
		if (diffMinutes < 0) {
			throw new InvalidDateException("The beginning date is posterior to the ending date !");
		} else {
			return diffMinutes;
		}
		
	}
	
	/**
	 * Returns a boolean indicating if the Date that calls the method is posterior to the Date passed as argument.
	 * @param argdate The Date you want to compare the method-calling Date to.
	 * @return true if the Date that calls the method is posterior or equal to the Date passed as argument, false otherwise.
	 */
	public boolean isPosteriorTo(Date argdate) {
		try {
			this.timeSpentSince(argdate);
		} catch (InvalidDateException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Date.class) {
			Date objp = (Date) obj;
			if (this.year == objp.getYear() && this.month == objp.getMonth() && this.day == objp.getDay() && this.hour == objp.getHour() && this.minute == objp.getMinute()) {
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
		result = 31*result + this.minute;
		System.out.println(result);
		result = 31*result + this.hour;
		System.out.println(result);
		result = 31*result + this.day;
		System.out.println(result);
		result = 31*result + this.month;
		System.out.println(result);
		result = 31*result + this.year;
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		Date date;
		try {
			date = new Date(2015, 11, 26, 14, 32);
			System.out.println(date.hashCode());
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Returns a representation of the date as a String.
	 * @return A string representation of the date under the "hh:mm, dd/mm/yyyy" format.
	 */
	@Override
	public String toString() {
		String dispyear = "" + this.year;
		String dispmonth = "" + this.month;
		String dispday = "" + this.day;
		String disphour = "" + this.hour;
		String dispminute = "" + this.minute;
		if (this.month < 10) {
			dispmonth = "0" + dispmonth;
		}
		if (this.day < 10) {
			dispday = "0" + dispday;
		}
		if (this.hour < 10) {
			disphour = "0" + disphour;
		}
		if (this.minute < 10) {
			dispminute = "0" + dispminute;
		}
		return disphour + ":" + dispminute + ", " + dispday +"/" + dispmonth +"/" + dispyear;
	}


	//getters and setters

	public int getYear() {
		return year;
	}

	/**
	 * Changes the current date's year to the value of the year parameter (has to be between 2000 and 2100 included).
	 * @param year Desired year for the date.
	 * @throws InvalidDateException Thrown if the entered year is under 2000 or over 2100.
	 */
	public void setYear(int year) throws InvalidDateException {
		if (year <= 2100 & year >= 2000) {
			this.year = year;
		} else {
			throw new InvalidDateException("Please enter a realistic year for the date (between 2000 and 2100) !");
		}
	}

	public int getMonth() {
		return month;
	}

	/**
	 * Changes the current date's month to the value of the month parameter (has to be between 1 and 12 included).
	 * @param month Desired month for the date.
	 * @throws InvalidDateException Thrown if the entered month is not between 1 and 12.
	 */
	public void setMonth(int month) throws InvalidDateException {
		if (month <= 12 & month >= 1) {
			this.month = month;
		} else {
			throw new InvalidDateException("Please enter a realistic month for the date (between 1 and 12) !");
		}
	}

	public int getDay() {
		return day;
	}

	/**
	 * Changes the current date's day to the value of the day parameter (has to be between 1 and 31 included).
	 * @param day Desired day for the date.
	 * @throws InvalidDateException Thrown if the entered day is not between 1 and 31.
	 */
	public void setDay(int day) throws InvalidDateException {
		if (day <= 31 & day >= 1) {
			this.day = day;
		} else {
			throw new InvalidDateException("Please enter a realistic day for the date (between 1 and 31) !");
		}
	}

	public int getHour() {
		return hour;
	}

	/**
	 * Changes the current date's hour to the value of the hour parameter (has to be between 0 and 23 included).
	 * @param hour Desired hour for the date.
	 * @throws InvalidDateException Thrown if the entered hour is not between 0 and 23.
	 */
	public void setHour(int hour) throws InvalidDateException {
		if (hour < 24 & hour >= 0) {
			this.hour = hour;
		} else {
			throw new InvalidDateException("Please enter a realistic hour for the date (between 0 and 23) !");
		}
	}

	public int getMinute() {
		return minute;
	}

	/**
	 * Changes the current date's minute to the value of the minute parameter (has to be between 0 and 59 included).
	 * @param minute Desired minute for the date.
	 * @throws InvalidDateException Thrown if the entered minute is not between 0 and 59.
	 */
	public void setMinute(int minute) throws InvalidDateException {
		if (minute < 60 & minute >= 0) {
			this.minute = minute;
		} else {
			throw new InvalidDateException("Please enter a realistic minute for the date (between 0 and 59) !");
		}
	}

}
