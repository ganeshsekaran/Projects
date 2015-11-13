package com.ganesh.producerconsumer;

import java.util.Random;

public class ContainerAdder {

	private final Lane lane;
	private final int count;

	public ContainerAdder(Lane lane, int count) {
		this.lane = lane;
		this.count = count;
	}

	public void start() {
		for (int i = 0; i < count; i++) {
			Runner adder = new Runner();
			Thread t = new Thread(adder);
			t.start();
		}
	}

	class Runner implements Runnable {

		public void run() {
			Random random = new Random();

			while (true) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int rand = random.nextInt(5);
				Container container = new Container();
				for (int i = 0; i < rand + 1; i++) {
					container.addBlock(new Block());
				}
				System.out.println("Added Container with Id "
						+ container.getId() + " size : " + (rand + 1));
				lane.addContainer(container);
			}
		}
	}
}