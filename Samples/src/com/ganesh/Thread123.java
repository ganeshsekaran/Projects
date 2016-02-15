package com.ganesh;

import java.util.ArrayList;
import java.util.List;

public class Thread123 {

	// static volatile int counter = 0;
	Thread123() {
		Counter ctr = new Counter();
		list.add(ctr);
	}

	static volatile List<Counter> list = new ArrayList<Counter>();

	public static void main(String[] args) throws Exception {
		Thread123 thisObj = new Thread123();
		int size = 1;
		for (int i = 0; i < size; i++) {
			String threadName = "Thread-" + i;
			Thread t = new Thread(thisObj.new Runner(size, i), threadName);
			t.start();
		}
	}

	static class Counter {
		Integer count;

		Counter() {
			count = 0;
		}

		void countInc() {
			count++;
		}

		Integer getCount() {
			return count;
		}

	}

	class Runner implements Runnable {

		final int threadCount;
		final int threadNumber;

		public Runner(int threadCount, int threadNumber) {
			super();
			this.threadCount = threadCount;
			this.threadNumber = threadNumber;
		}

		@Override
		public void run() {
			while (true) {
				int mod = Thread123.list.get(0).getCount() % threadCount;
				// int mod = Thread123.counter % threadCount;
				boolean canProcess = mod == threadNumber
						|| mod == (-1 * threadNumber);
				if (canProcess) {
					try {
						// Thread.sleep(50);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String currentThreadName = Thread.currentThread().getName();
					System.out.println(currentThreadName + " : "
							+ Thread123.list.get(0).getCount());
					Thread123.list.get(0).countInc();
					// Thread123.counter++;
				}
			}
		}
	}
}
