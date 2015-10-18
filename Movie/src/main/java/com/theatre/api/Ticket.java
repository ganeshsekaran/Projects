package com.theatre.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ticket {

	private User user;
	private Screen screen;
	private Seat[] seats;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(User user, Screen screen, Seat[] seats) {
		super();
		this.user = user;
		this.screen = screen;
		this.seats = seats;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public void setSeats(Seat[] seats) {
		this.seats = seats;
	}

}
