package com.ganesh;

import java.lang.reflect.Field;
import java.util.Hashtable;

import sun.misc.Unsafe;

public class ConcurrentHashMap<K, V> {

	private final int noOfMaps;
	private final HashtableObj<K, V>[] hashtableObjArray;
	private final double salt;

	public ConcurrentHashMap() {
		this(16);
	}

	public ConcurrentHashMap(final int count) {
		noOfMaps = count;
		salt = 0.37;
		hashtableObjArray = createHashtableObj(count);

	}

	public V get(K key) {
		int index = getIndex(key);
		HashtableObj<K, V> hashtableObj = hashtableObjArray[index];
		/*if (hashtableObj.isLocked()) {
			System.out.println("Get waiting");
		}*/
		synchronized (hashtableObj.getLockObject()) {
			return (V) hashtableObj.get(key);
		}
	}

	public void put(K key, V value) {
		int index = getIndex(key);
		HashtableObj<K, V> hashtableObj = hashtableObjArray[index];
		/*if (hashtableObj.isLocked()) {
			System.out.println("Put waiting");
		}*/
		synchronized (hashtableObj.getLockObject()) {
			hashtableObj.put(key, value);
		}
	}

	public void printMapStatus() {
		int totalSize = 0;
		for (int i = 0; i < noOfMaps; i++) {
			HashtableObj<K, V> hashtableObj = hashtableObjArray[i];
			int size = hashtableObj.size();
			System.out.println("Index : " + i + " size : " + size);
			totalSize = totalSize + size;
		}
		System.out.println("Total size : " + totalSize);
	}

	public int getIndex(K key) {
		int hashKey = key.hashCode();
		double fraction = (hashKey * salt);
		double fractionKey = fraction % 1;
		int index = (int) (fractionKey * noOfMaps);
		return index;
	}

	private HashtableObj<K, V>[] createHashtableObj(int count) {
		HashtableObj<K, V>[] hashtableObjArray = new HashtableObj[count];
		for (int i = 0; i < count; i++) {
			Object lock = new Object();
			HashtableObj<K, V> hashtableObj = new HashtableObj<K, V>(lock);
			hashtableObjArray[i] = hashtableObj;
		}
		return hashtableObjArray;
	}

	static class HashtableObj<K, V> {
		private final Hashtable<K, V> table;
		private final Object lock;
		private final Unsafe unsafe;
		private long size;

		HashtableObj(Object lock) {
			table = new Hashtable<K, V>();
			this.lock = lock;
			this.unsafe = getUnsafe();
			this.size = 0;
		}

		private Unsafe getUnsafe() {
			try {
				Field field = sun.misc.Unsafe.class
						.getDeclaredField("theUnsafe");
				field.setAccessible(true);
				return (Unsafe) field.get(null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		boolean isCurrentThreadHoldsLock() {
			return Thread.holdsLock(lock);
		}
		
		Object getLockObject(){
			return lock;
		}

		boolean tryLock() {
			boolean locked = false;
			locked = unsafe.tryMonitorEnter(lock);
			return locked;
		}

		boolean isLocked() {
			boolean isLocked = !tryLock();
			if (!isLocked) {
				unsafe.monitorExit(lock);
			}
			return isLocked;
		}

		boolean releaseLock() {
			boolean released = false;
			if (isCurrentThreadHoldsLock()) {
				unsafe.monitorExit(lock);
			}
			return released;
		}

		void put(K key, V value) {
			table.put(key, value);
			size++;
		}

		V get(K key) {
			return table.get(key);
		}

		int size() {
			return table.size();
		}
	}
}
