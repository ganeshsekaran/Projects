package com.ganesh;

public class ThreadCheck {

	public static void main(String[] args) {
		ThreadCheck main = new ThreadCheck();
		main.test();
	}
	
	private void test(){
		Thread t = new Thread(new Runner());
		t.run();
		t.start();
	}

	class Runner implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());

		}

	}

}
