package com.ganesh.cab;

class DriverApp {

	private static volatile int driverId = 0;
	private static volatile int cabId = 0;

	public void addDriver(int count) {

		for (int i = 0; i < count; i++) {
			cabId++;
			driverId++;
			Cab cab = new CabImpl("KA-01-MK-3427 --- " + cabId);
			String driverName = "Driver " + driverId;
			Driver driver = new DriverImpl(cab, driverName, driverId);

			Runnable adder = new Adder(driver, cab);
			Thread t = new Thread(adder);
			t.start();
		}
	}

	class Adder implements Runnable {

		private final Cab cab;
		private final Driver driver;

		public Adder(Driver driver, Cab cab) {
			this.driver = driver;
			this.cab = cab;
		}

		@Override
		public void run() {
			DriverManagement.getInstance().login(driver);
		}
	}

	class CabImpl implements Cab {

		private final String number;

		public CabImpl(String number) {
			this.number = number;
		}

		public String getCabNumber() {
			return number;
		}
	}

	class DriverImpl implements Driver {

		private final Cab cab;
		private final String name;
		private final int id;

		DriverImpl(Cab cab, String name, int id) {
			this.cab = cab;
			this.name = name;
			this.id = id;
		}

		public int getId() {
			return id;
		}

		@Override
		public Cab getCab() {
			return cab;
		}

		@Override
		public String getName() {
			return name;
		}
	}

}
