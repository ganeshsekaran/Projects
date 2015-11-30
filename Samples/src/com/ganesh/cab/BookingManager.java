package com.ganesh.cab;

import java.util.ArrayList;
import java.util.List;

final class BookingManager {

	private static volatile BookingManager instance;
	private final List<DriverRunnable> activeDriverList;
	private RequestQueue<RequestWrapper> requestQueue;
	private RequestQueue<RequestWrapper> futureRequestQueue;

	private BookingManager() {
		activeDriverList = new ArrayList<DriverRunnable>();
		requestQueue = new RequestQueue<RequestWrapper>();
		futureRequestQueue = new RequestQueue<RequestWrapper>();
	}

	static BookingManager getInstance() {
		if (instance == null) {
			synchronized (BookingManager.class) {
				if (instance == null) {
					instance = new BookingManager();
				}
			}
		}
		return instance;
	}

	boolean loginDriver(Driver driver) {
		synchronized (activeDriverList) {
			DriverRunnable runnable = new DriverRunnable(driver, requestQueue);
			Thread t = new Thread(runnable);
			t.start();
			activeDriverList.add(runnable);
		}

		return true;
	}

	boolean logoutDriver(Driver driver) {
		synchronized (activeDriverList) {
			activeDriverList.remove(driver);
		}
		return true;
	}

	BookingStatus processRequest(BookingRequest request) {
		BookingStatus status = new BookingStatus();
		status.setStatus(BookingStatus.Status.PENDING_CONFIRMATION);

		boolean cabsAvailable = false;
		synchronized (requestQueue) {
			for (DriverRunnable runnable : activeDriverList) {
				if (runnable.getStatus() == DriverRunnable.Status.WAITNG) {
					cabsAvailable = true;
					break;
				}
			}
		}

		if (!cabsAvailable) {
			status.setStatus(BookingStatus.Status.CAB_NOT_AVAILABLE);
		} else {
			status.setStatus(BookingStatus.Status.CONFIRMED);
			RequestWrapper wrapper = new RequestWrapper(request, status);
			synchronized (requestQueue) {
				requestQueue.enqueue(wrapper);
				requestQueue.notify();
			}
		}
		return status;
	}

	class RequestQueue<RequestWrapper> {
		volatile int size = 0;
		private List<RequestWrapper> list;

		RequestQueue() {
			list = new ArrayList<RequestWrapper>();
		}

		int size() {
			return size;
		}

		void enqueue(RequestWrapper wrapper) {
			list.add(wrapper);
			size++;
		}

		RequestWrapper dequeue() {
			size--;
			return list.remove(0);
		}
	}

	class RequestWrapper {
		private final BookingRequest request;
		private final BookingStatus status;

		RequestWrapper(BookingRequest request, BookingStatus status) {
			super();
			this.request = request;
			this.status = status;
		}

		BookingRequest getRequest() {
			return request;
		}

		BookingStatus getStatus() {
			return status;
		}
	}

	static class DriverRunnable implements Runnable {

		enum Status {
			RUNNING, WAITNG
		}

		private final RequestQueue<RequestWrapper> requestQueue;
		private final Driver driver;
		private volatile Status status;

		DriverRunnable(Driver driver, RequestQueue<RequestWrapper> requestQueue) {
			this.driver = driver;
			this.requestQueue = requestQueue;
		}

		@Override
		public void run() {
			while (true) {
				process();
			}
		}

		Status getStatus() {
			return status;
		}

		private void process() {
			
			RequestWrapper wrapper = null;
			synchronized (requestQueue) {
				while (requestQueue.size() == 0) {
					try {
						status = Status.WAITNG;
						System.out.println("Driver " + driver.getName() + " is wating");
						requestQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				status = Status.RUNNING;
				wrapper = requestQueue.dequeue();
				
			}
			
			/*RequestWrapper wrapper = null;
			synchronized (requestQueue) {
				status = Status.RUNNING;
				wrapper = requestQueue.dequeue();
			}*/
			
			if (wrapper != null) {
				
				processRequest(wrapper);
			}
		}

		private void processRequest(RequestWrapper wrapper) {
			BookingStatus status = wrapper.getStatus();
			BookingRequest request = wrapper.getRequest();
			status.setStatus(BookingStatus.Status.AT_SERVICE);
			request.setDriver(driver);
			
			System.out.println("Request of " + request.getUser().getName() + " is processing by " + driver.getName());
			
			try {
				Thread.sleep(request.getDistance() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Request of " + request.getUser().getName() + " is processed by " + driver.getName());
		}
	}
}
