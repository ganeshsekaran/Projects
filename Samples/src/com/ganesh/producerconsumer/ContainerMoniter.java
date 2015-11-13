package com.ganesh.producerconsumer;

public class ContainerMoniter {

	private Lane lane;
	private ContainerConsumer consumer;
	private ContainerAdder adder;
	private ContainerIncomeMointer listener;
	private ContainerProcessMointer processMointer;

	ContainerMoniter(Lane lane, ContainerConsumer consumer, ContainerAdder adder) {
		listener = new ContainerIncomeMointerImpl();
		processMointer = new ContainerProcessMointerImpl();
		this.consumer = consumer;
		this.adder = adder;
		this.lane = lane;
	}

	public void mointer() {
		lane.addMointer(listener);
		consumer.addProcessMointer(processMointer);
	}

	class ContainerIncomeMointerImpl implements ContainerIncomeMointer {

		public void ContainerIncome(int count) {
			if (consumer.canProcess(count)) {
				Container container = consumer.interrupt();
				lane.returnContainer(container);
				container = lane.getContainerWithlessSize();
				consumer.process(container);
			}
		}
	}
	
	class ContainerProcessMointerImpl implements ContainerProcessMointer{

		public void processedContainer(Container container) {
			System.out.println("notified done");
			container = lane.getContainerWithlessSize();
			System.out.println(container);
			consumer.process(container);
			
		}
	}
}
