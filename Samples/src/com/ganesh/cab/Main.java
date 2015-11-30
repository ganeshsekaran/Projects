package com.ganesh.cab;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

	private static volatile int userId = 0;

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		DriverApp driverApp = new DriverApp();
		main.start(driverApp);
	}

	void start(DriverApp driverApp) throws Exception {

		// Add five drivers;
		driverApp.addDriver(5);

		Map<Integer, User> userMap = new HashMap<Integer, User>();
		// create 10 users.
		for (int i = 0; i < 10; i++) {
			userId++;
			User user = new UserImpl("User " + userId, userId);
			userMap.put(userId, user);
		}

		// Wait for 5 secs
		Thread.sleep(2000);

		// Let User 1 create a booking request
		BookingRequest request = new BookingRequest(userMap.get(1));
		request.setDistance(20);
		request.setStartingPlace(Place.PLACE1);
		request.setEndingPlace(Place.PLACE2);

		BookingStatus bookingStatus = BookingManager.getInstance()
				.processRequest(request);
		System.out.println(bookingStatus.getStatus());

		// Create 4 more request
		Random random = new Random();
		for (int i = 2; i < 6; i++) {
			request = new BookingRequest(userMap.get(i));
			request.setDistance(20);
			request.setStartingPlace(Place.PLACE1);
			request.setEndingPlace(Place.PLACE2);

			bookingStatus = BookingManager.getInstance()
					.processRequest(request);
			System.out.println(bookingStatus.getStatus());
		}
		
		//Thread.sleep(3000);

		// Create 4 more request
		random = new Random();
		for (int i = 6; i < 8; i++) {
			request = new BookingRequest(userMap.get(i));
			request.setDistance(random.nextInt(10));
			request.setStartingPlace(Place.PLACE1);
			request.setEndingPlace(Place.PLACE2);

			bookingStatus = BookingManager.getInstance()
					.processRequest(request);
			System.out.println(bookingStatus.getStatus());
		}

	}

	class UserImpl implements User {

		private final String name;
		private final int id;

		public UserImpl(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getId() {
			return id;
		}
	}
}
