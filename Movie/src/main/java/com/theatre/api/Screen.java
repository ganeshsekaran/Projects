package com.theatre.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Screen {

	private int screenId;
	private Seat[] seats;
	private Movie movie;
	private String name;
	private boolean isActive;

	public Screen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Screen(Seat[] seats, Movie movie, String name, boolean isActive) {
		this(0, seats, movie, name, isActive);
	}

	public Screen(int screenId, Seat[] seats, Movie movie, String name,
			boolean isActive) {
		super();
		this.seats = seats;
		this.movie = movie;
		this.name = name;
		this.isActive = isActive;
		this.screenId = screenId;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public void setSeats(Seat[] seats) {
		this.seats = seats;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
