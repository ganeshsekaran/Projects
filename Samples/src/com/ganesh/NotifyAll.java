package com.ganesh;

public class NotifyAll {

	static volatile boolean check;
	Object lock;

	NotifyAll() {
		lock = new Object();
		check = true;
	}

	public static void main(String[] args) throws Exception {

		NotifyAll main = new NotifyAll();
		main.test();

	}

	void test() throws Exception {
		for (int i = 0; i < 5; i++) {
			Thread t = new Thread(new MyThread(), "Thread - " + (i + 1));
			t.start();
		}

		Thread.sleep(100);
		System.out.println("NotifyAll.test()**************************");

		check = false;

		synchronized (lock) {
			lock.notifyAll();
		}

	}

	class MyThread implements Runnable {

		@Override
		public void run() {

			synchronized (lock) {
				while (check) {
					try {
						System.out.println(Thread.currentThread().getName()
								+ " going to wait state");
						lock.wait();
						System.out.println("NotifyAll.MyThread.run()");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println(Thread.currentThread().getName()
					+ " comming out of wait state");
		}
	}
}
