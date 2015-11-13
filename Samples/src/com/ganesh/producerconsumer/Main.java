package com.ganesh.producerconsumer;

public class Main {

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		Lane lane = Lane.getInstance();
		main.start(lane);
	}

	private void start(Lane lane) throws Exception {

		ContainerAdder adder = new ContainerAdder(lane, 2);
		ContainerConsumer consumer = new ContainerConsumer(1);
		ContainerMoniter mointer = new ContainerMoniter(lane, consumer, adder);
		mointer.mointer();
		adder.start();
		consumer.start();

	}
}
