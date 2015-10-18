package com.ganesh;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadPoolMain {

	public static void main(String[] args) throws Exception {
		ThreadPool pool = ThreadPool.getThreadPool(10);
		UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Caught exception :( :(");
				System.out.println("Thread name : " + t.getName());
				System.out.println("Message : " + e.getMessage());
			}
		};
		Thread.currentThread().setUncaughtExceptionHandler(handler);
		new ThreadPoolMain().start(pool);
		throw new Exception("my exception");
	}

	void start(ThreadPool pool) throws Exception {
		// pool.start();

		for (int i = 0; i < 10; i++) {
			TestRunners runner = new TestRunners(i);
			pool.submit(runner);
		}
		// pool.shutDown();
	}

	class TestRunners implements Runnable {
		final int i;

		public TestRunners(int i) {
			this.i = i;
		}

		public void run() {
			System.out.println(i);

		}
	}
}
