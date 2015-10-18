package com.ganesh;

public class ThreadPool {

	private final LockableBlockingQueue<Runnable> queue;
	private final ThreadRunnableBean[] threads;

	private ThreadPool(int poolSize) {
		queue = new LockableBlockingQueue<Runnable>();
		threads = new ThreadRunnableBean[poolSize];
		initThreads(poolSize);
	}

	private final void initThreads(int poolSize) {
		for (int i = 0; i < poolSize; i++) {
			ThreadRunner runner = new ThreadRunner(queue);
			Thread thread = new Thread(runner);
			threads[i] = new ThreadRunnableBean(thread, runner);
		}
	}

	public synchronized static ThreadPool getThreadPool(int poolSize) {
		ThreadPool pool = new ThreadPool(poolSize);
		pool.start();
		return pool;
	}

	public void start() {
		for (ThreadRunnableBean bean : threads) {
			Thread thread = bean.getThread();
			thread.start();
		}
	}

	public void submit(Runnable runnable) {
		queue.enqueue(runnable);
		Object lock = queue.lock();
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void shutDown() {
		for (ThreadRunnableBean bean : threads) {
			Thread thread = bean.getThread();
			thread.interrupt();
		}
	}

	class LockableBlockingQueue<Runnable> {

		private final BlockingQueue<Runnable> queue;
		private final Object lock;

		LockableBlockingQueue(int size) {
			this.queue = new BlockingQueue<Runnable>(size);
			this.lock = new Object();
		}

		LockableBlockingQueue() {
			this.queue = new BlockingQueue<Runnable>();
			this.lock = new Object();
		}

		boolean enqueue(Runnable thread) {
			return queue.add(thread);
		}

		Runnable dequeue() {
			return queue.remove();
		}

		Runnable peak() {
			return queue.get();
		}

		int size() {
			return queue.size();
		}

		Object lock() {
			return lock;
		}
	}

	class ThreadRunner implements Runnable {

		final LockableBlockingQueue<Runnable> queue;
		final Object interruptLock;

		public ThreadRunner(LockableBlockingQueue<Runnable> queue) {
			this.queue = queue;
			this.interruptLock = new Object();
		}
		
		Object getInterruptLock(){
			return interruptLock;
		}

		public void run() {
			Object lock = queue.lock();

			try {
				while (true) {
					if(Thread.currentThread().isInterrupted()){
						break;
					}
					
					Runnable runnable = null;
					if (queue.size() > 0) {
						runnable = queue.dequeue();
					}

					if (runnable == null) {
						synchronized (lock) {
							lock.wait();
						}
					} else {
						runnable.run();
					}
				}
			} catch (InterruptedException e) {
				synchronized (interruptLock) {
					interruptLock.notify();
				}
				System.out.println(Thread.currentThread().getName()
						+ " Stopped");
			}
		}
	}

	class ThreadRunnableBean {
		private final Thread thread;
		private final Runnable runnable;

		ThreadRunnableBean(Thread thread, Runnable runnable) {
			this.thread = thread;
			this.runnable = runnable;
		}

		Thread getThread() {
			return thread;
		}

		Runnable getRunnable() {
			return runnable;
		}
	}
}
