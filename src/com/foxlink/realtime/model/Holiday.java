package com.foxlink.realtime.model;

public class Holiday {
	private String HolidayDate;
	private String HolidayType;
	
	
	
	
	public Holiday() {
		
	}
	
	public Holiday(String holidayDate) {
		HolidayDate = holidayDate;
	}
	
	public Holiday(String holidayDate, String holidayType) {
		HolidayDate = holidayDate;
		HolidayType = holidayType;
	}
	public String getHolidayDate() {
		return HolidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		HolidayDate = holidayDate;
	}
	public String getHolidayType() {
		return HolidayType;
	}
	public void setHolidayType(String holidayType) {
		HolidayType = holidayType;
	}

}
