package com.ganesh.cab;

public class BookingRequest {

	private final User user;
	private int status;
	private Driver driver;
	
	private Place startingPlace;
	private Place endingPlace;
	
	private int distance;
	
	public BookingRequest(User user) {
		super();
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver= driver;
	}

	public Place getStartingPlace() {
		return startingPlace;
	}

	public void setStartingPlace(Place startingPlace) {
		this.startingPlace = startingPlace;
	}

	public Place getEndingPlace() {
		return endingPlace;
	}

	public void setEndingPlace(Place endingPlace) {
		this.endingPlace = endingPlace;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public User getUser() {
		return user;
	}	
}
