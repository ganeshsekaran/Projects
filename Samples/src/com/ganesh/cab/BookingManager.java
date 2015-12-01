package com.ganesh.cab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class BookingManager {

	private static volatile BookingManager instance;
	private final Map<Driver, DriverRunnable> activeDriverList;
	private RequestQueue requestQueue;
	private RequestQueue futureRequestQueue;

	private BookingManager() {
		activeDriverList = new ConcurrentHashMap<Driver, DriverRunnable>();
		requestQueue = new RequestQueue();
		futureRequestQueue = new RequestQueue();
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
		if (activeDriverList.containsKey(driver)) {
			return false;
		}
		DriverRunnable runnable = new DriverRunnable(driver, requestQueue);
		Thread t = new Thread(runnable);
		t.start();
		activeDriverList.put(driver, runnable);
		return true;
	}

	boolean logoutDriver(Driver driver) {

		DriverRunnable runnable = activeDriverList.get(driver);
		if (runnable == null) {
			return false;
		}
		System.out.println(runnable.getStatus());
		if (runnable.getStatus() == DriverRunnable.Status.RUNNING) {
			Object lock = runnable.getCompletedLock();
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		runnable.interrupt();
		activeDriverList.remove(driver);
		return true;
	}

	BookingStatus processRequest(BookingRequest request) {
		BookingStatus status = new BookingStatus(request);
		status.setStatus(BookingStatus.Status.PENDING_CONFIRMATION);

		boolean cabsAvailable = false;
		for (DriverRunnable runnable : activeDriverList.values()) {
			if (runnable.getStatus() == DriverRunnable.Status.WAITNG) {
				cabsAvailable = true;
				break;
			}
		}

		if (!cabsAvailable) {
			status.setStatus(BookingStatus.Status.CAB_NOT_AVAILABLE);
		} else {
			RequestWrapper wrapper = new RequestWrapper(request, status);
			synchronized (requestQueue) {
				requestQueue.enqueue(wrapper);
				requestQueue.notify();
				status.setStatus(BookingStatus.Status.CONFIRMED);
			}
		}
		return status;
	}

	void cancelRequest(BookingRequest request) {
		synchronized (requestQueue) {
			RequestWrapper wrapper = requestQueue.getWrapper(request);
			if (wrapper != null) {
				DriverRunnable runnable = wrapper.getRunnable();

				if (runnable == null) {
					requestQueue.remove(wrapper);
				} else {

					DriverRunnable.Status status = runnable.getStatus();
					if (status.equals(DriverRunnable.Status.WAITNG)) {

					}
				}
			}
		}
	}

	class RequestQueue {
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

		boolean contains(BookingRequest request) {
			boolean contains = false;
			for (RequestWrapper wrapper : list) {
				BookingRequest rqst = wrapper.getRequest();
				if (rqst.equals(request)) {
					contains = true;
					break;
				}
			}
			return contains;
		}

		RequestWrapper getWrapper(BookingRequest request) {
			RequestWrapper retWrapper = null;
			for (RequestWrapper wrapper : list) {
				BookingRequest rqst = wrapper.getRequest();
				if (rqst.equals(request)) {
					retWrapper = wrapper;
					break;
				}
			}
			return retWrapper;
		}

		void remove(RequestWrapper wrapper) {
			list.remove(wrapper);
		}

		void remove(BookingRequest request) {
			for (RequestWrapper wrapper : list) {
				BookingRequest rqst = wrapper.getRequest();
				if (rqst.equals(request)) {
					list.remove(request);
					break;
				}
			}
		}
	}

	class RequestWrapper {
		private final BookingRequest request;
		private final BookingStatus status;
		private final Object lock;
		private DriverRunnable runnable;

		RequestWrapper(BookingRequest request, BookingStatus status) {
			super();
			this.request = request;
			this.status = status;
			this.lock = new Object();
		}

		BookingRequest getRequest() {
			return request;
		}

		BookingStatus getStatus() {
			return status;
		}

		Object getLockObj() {
			return lock;
		}

		DriverRunnable getRunnable() {
			return runnable;
		}
	}

	static class DriverRunnable implements Runnable {

		enum Status {
			RUNNING, WAITNG
		}

		private final RequestQueue requestQueue;
		private final Driver driver;
		private volatile Status status;
		private final Object completedLock;
		private final Object interrupedLock;
		private Thread thread;

		DriverRunnable(Driver driver, RequestQueue requestQueue) {
			this.driver = driver;
			this.requestQueue = requestQueue;
			this.completedLock = new Object();
			this.interrupedLock = new Object();
		}

		@Override
		public void run() {
			thread = Thread.currentThread();
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					synchronized (interrupedLock) {
						interrupedLock.notifyAll();
					}
					break;
				}
				process();
			}
		}

		Status getStatus() {
			return status;
		}

		void interrupt() {
			if (thread != null) {
				thread.interrupt();
			} else {
				interrupedLock.notifyAll();
			}
		}

		Object getCompletedLock() {
			return completedLock;
		}

		Object getInteruppedLock() {
			return interrupedLock;
		}

		private void process() {

			synchronized (requestQueue) {
				while (requestQueue.size() == 0) {
					if (Thread.currentThread().isInterrupted()) {
						break;
					}
					try {
						status = Status.WAITNG;
						System.out.println(driver.getName() + " is wating");
						requestQueue.wait();
					} catch (InterruptedException e) {
						System.out.println("Interruped Driver : "
								+ driver.getName());
					}
				}
			}

			RequestWrapper wrapper = null;
			if (!Thread.currentThread().isInterrupted()) {
				synchronized (requestQueue) {
					status = Status.RUNNING;
					wrapper = requestQueue.dequeue();
				}
				if (wrapper != null) {
					wrapper.runnable = this;
					processRequest(wrapper);
					synchronized (completedLock) {
						completedLock.notifyAll();
					}
				}
			} else {
				synchronized (interrupedLock) {
					interrupedLock.notifyAll();
				}
			}
		}

		private void processRequest(RequestWrapper wrapper) {
			BookingStatus status = wrapper.getStatus();
			BookingRequest request = wrapper.getRequest();
			status.setStatus(BookingStatus.Status.AT_SERVICE);
			request.setDriver(driver);

			System.out.println("Request of " + request.getUser().getName()
					+ " is processing by " + driver.getName());

			try {
				Thread.sleep(request.getDistance() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			status.setStatus(BookingStatus.Status.COMPLETED);
			synchronized (status) {
				status.notify();
			}
			System.out.println("Request of " + request.getUser().getName()
					+ " is processed by " + driver.getName());
		}
	}
}
