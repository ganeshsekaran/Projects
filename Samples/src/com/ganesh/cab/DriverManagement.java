package com.ganesh.cab;

public class DriverManagement {

	private static volatile DriverManagement instance;
	private BookingManager bookingManager;

	private DriverManagement() {
		bookingManager = BookingManager.getInstance();
	}

	public static DriverManagement getInstance() {
		if (instance == null) {
			synchronized (DriverManagement.class) {
				if (instance == null) {
					instance = new DriverManagement();
				}
			}
		}
		return instance;
	}

	public boolean login(Driver driver) {
		boolean loggedIn = false;
		loggedIn = bookingManager.loginDriver(driver);
		return loggedIn;
	}

	public boolean logout(Driver driver) {
		boolean loggedOut = true;
		loggedOut = bookingManager.logoutDriver(driver);
		return loggedOut;
	}
}
