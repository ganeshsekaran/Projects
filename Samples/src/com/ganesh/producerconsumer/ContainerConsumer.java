package com.ganesh.producerconsumer;

public class ContainerConsumer {

	private final int count;
	private final Thread[] threads;
	private boolean started = false;
	private ContainerProcessMointer moniter;
	private volatile Container container;

	private volatile int doneCount;

	private final Object lock;

	public ContainerConsumer(int count) {
		this.count = count;
		lock = new Object();
		threads = new Thread[count];
		doneCount = 0;
	}

	public void addProcessMointer(ContainerProcessMointer moniter) {
		this.moniter = moniter;
	}

	public void start() {
		if (started) {
			throw new RuntimeException("Cannot start consumer again");
		}
		for (int i = 0; i < count; i++) {
			Runner adder = new Runner();
			Thread t = new Thread(adder);
			threads[i] = t;
			t.start();
		}
		started = true;
	}

	public Container interrupt() {
		for (Thread t : threads) {
			t.interrupt();
		}
		Container tmpContainer = container;
		container = null;
		doneCount = 0;
		return tmpContainer;
	}

	public void process(Container container) {
		this.container = container;
		synchronized (lock) {
			doneCount = 0;
			lock.notifyAll();
		}
	}

	public boolean canProcess(int containerSize) {
		if (container == null || container.blockSize() == 0) {
			return true;
		} else {
			synchronized (lock) {
				int size = container.blockSize();

				if (containerSize < size) {
					return true;
				} else {
					return false;
				}
			}
		}

	}

	private void done() {
		doneCount++;
		System.out.println("doneCount : " + doneCount + " count : " + count);
		if (doneCount == count) {
			if (moniter != null) {
				moniter.processedContainer(container);
			}
			doneCount = 0;
		}
	}

	class Runner implements Runnable {

		public void run() {
			while (true) {
				runn();
			}
		}

		private void runn() {
			System.out.println(container);
			synchronized (lock) {
				while (container == null) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
			}

			synchronized (lock) {
				while (container.blockSize() == 0) {
					done();
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
			}

			synchronized (lock) {
				if (container.blockSize() > 0) {
					Block block = container.getBlock();
					System.out.println("Processed from containerId : "
							+ container.getId());
				}
			}
		}
	}
}
