package com.ganesh;

import java.util.ArrayList;

public class BlockingQueue<T> {

	private final int size;
	private final ArrayList<T> list;
	private final Object lock;
	private final Object fullLock;
	private final Object emptyLock;

	public BlockingQueue(int size) {
		this.size = size;
		this.list = new ArrayList<T>();
		this.lock = new Object();
		this.fullLock = new Object();
		this.emptyLock = new Object();
	}

	public BlockingQueue() {
		this(Integer.MAX_VALUE);
	}

	public boolean add(T data) {
		boolean added = false;
		synchronized (fullLock) {
			try {
				int listSize = list.size();
				while (listSize == size) {
					// System.out.println("Add : " + listSize);
					// System.out.println("Add Wating thread : " +
					// Thread.currentThread().getName());
					fullLock.wait();
					listSize = list.size();
				}
				added = list.add(data);
				// System.out.println("Added : " + data);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		synchronized (emptyLock) {
			emptyLock.notify();
		}
		return added;
	}

	public boolean remove(T data) {
		boolean removed = false;
		synchronized (lock) {
			try {
				int listSize = list.size();
				while (listSize == 0) {
					lock.wait();
				}
				removed = list.remove(data);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// lock.notifyAll();
			}
		}
		synchronized (fullLock) {
			fullLock.notify();
		}
		return removed;
	}

	public T remove() {
		T removed = null;
		synchronized (emptyLock) {
			try {
				int listSize = list.size();
				while (listSize == 0) {
					// System.out.println("Remove : " + listSize);
					// System.out.println("Remove Wating thread : "
					// + Thread.currentThread().getName());
					emptyLock.wait();
					listSize = list.size();
				}
				removed = list.remove(0);
				// System.out.println("Removed : " + removed);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
		synchronized (fullLock) {
			fullLock.notify();
		}
		return removed;
	}

	public T get(int index) {
		synchronized (fullLock) {
			synchronized (emptyLock) {
				T data = list.get(index);
				return data;
			}
		}
	}

	public T get() {
		return get(0);
	}

	public T getLast() {
		int size = size();
		return get(size - 1);
	}

	public int size() {
		synchronized (emptyLock) {
			synchronized (fullLock) {
				int size = list.size();
				return size;
			}
		}
	}
}
