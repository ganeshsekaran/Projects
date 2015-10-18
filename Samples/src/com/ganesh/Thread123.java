package com.ganesh;

public class Thread123 {

	static volatile byte counter = 0;

	public static void main(String[] args) throws Exception {
		Thread123 thisObj = new Thread123();
		int size =2;
		for (int i = 0; i < size; i++) {
			String threadName = "Thread-" + i;
			Thread t = new Thread(thisObj.new Runner(size, i), threadName);
			t.start();
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
				int mod = Thread123.counter % threadCount;
				boolean canProcess = mod == threadNumber || mod == (-1*threadNumber);
				if (canProcess) {
					try {
						//Thread.sleep(10);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String currentThreadName = Thread.currentThread().getName();
					System.out.println(currentThreadName + " : "
							+ Thread123.counter);
					Thread123.counter++;
				}
			}
		}
	}
}
