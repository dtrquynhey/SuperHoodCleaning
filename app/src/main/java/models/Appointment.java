package models;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Appointment {
    private String customerId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ArrayList<String> staffs;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public ArrayList<String> getStaffs() {
		return staffs;
	}

	public void setStaffs(ArrayList<String> staffs) {
		this.staffs = staffs;
	}

	public Appointment(String customerId, LocalDate date, LocalTime startTime, LocalTime endTime, ArrayList<String> staffs) {
		this.customerId = customerId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.staffs = staffs;
	}
}
