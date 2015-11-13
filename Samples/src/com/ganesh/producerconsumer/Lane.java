package com.ganesh.producerconsumer;

import java.util.ArrayList;
import java.util.List;

import com.ganesh.BlockingQueue;

public enum Lane {

	INSTANCE(20);

	private final BlockingQueue<Container> queue;
	private List<ContainerIncomeMointer> mointers;
	private Object addLock;

	private Lane(int count) {
		queue = new BlockingQueue<Container>(count);
		mointers = new ArrayList<ContainerIncomeMointer>();
		addLock = new Object();
	}

	public static Lane getInstance() {
		return INSTANCE;
	}

	public void addMointer(ContainerIncomeMointer mointer) {
		mointers.add(mointer);
	}

	public void removeMointer(ContainerIncomeMointer mointer) {
		mointers.remove(mointer);
	}

	public Container getContainerWithlessSize() {
		return queue.remove();
	}

	public void addContainer(Container container) {
		synchronized (addLock) {
			returnContainer(container);
			int size = container.blockSize();
			for (ContainerIncomeMointer mointer : mointers) {
				mointer.ContainerIncome(size);
			}
		}
	}

	public void returnContainer(Container container) {
		queue.add(container);
	}
}
