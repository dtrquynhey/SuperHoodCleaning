package models;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Appointment {
    private String customerId;
    private String date;
    private String startTime;
    private String endTime;
    private ArrayList<String> staffs;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ArrayList<String> getStaffs() {
		return staffs;
	}

	public void setStaffs(ArrayList<String> staffs) {
		this.staffs = staffs;
	}

	public Appointment(String customerId, String date, String startTime, String endTime, ArrayList<String> staffs) {
		this.customerId = customerId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.staffs = staffs;
	}
}
