package com.ganesh.ola;

import java.lang.reflect.Field;
import java.util.HashMap;

import sun.misc.Unsafe;

/**
 * Thread safe Key Value Store.
 * 
 * @author GaneshS
 *
 * @param <K>
 * @param <V>
 */
public class KeyValueStore<K, V> {

	private final int noOfMaps;
	private final double salt;
	private final HashMapObj<K, V>[] hashMapObjArray;

	public KeyValueStore() {
		this(16);
	}

	public KeyValueStore(final int count) {
		noOfMaps = count;
		salt = 0.37;
		hashMapObjArray = createHashMapObj(count);
	}

	private HashMapObj<K, V>[] createHashMapObj(int count) {
		HashMapObj<K, V>[] hashMapObjArray = new HashMapObj[count];
		for (int i = 0; i < count; i++) {
			Object lock = new Object();
			HashMapObj<K, V> hashtableObj = new HashMapObj<K, V>(lock);
			hashMapObjArray[i] = hashtableObj;
		}
		return hashMapObjArray;
	}

	private int hash(K key) {
		int hashKey = key.hashCode();
		double fraction = (hashKey * salt);
		double fractionKey = fraction % 1;
		int index = (int) (fractionKey * noOfMaps);
		return index;
	}

	private HashMapObj<K, V> getHashMapObj(K key) {
		int index = hash(key);
		HashMapObj<K, V> hashMapObj = hashMapObjArray[index];
		return hashMapObj;
	}

	public V get(K key) {
		// First get the hash map object from the index.
		HashMapObj<K, V> hashMapObj = getHashMapObj(key);

		// if (hashMapObj.isLocked()) {
		// System.out.println("Get waiting");
		// }

		// Synchronize on the hash map and retrieve the content;
		synchronized (hashMapObj.getLockObject()) {
			return hashMapObj.get(key);
		}
	}

	public V[] get(K[] keys) {
		V[] values = (V[]) new Object[keys.length];

		int i = 0;
		for (K key : keys) {
			V value = get(key);
			values[i++] = value;
		}
		return values;
	}

	public V put(K key, V value) {
		// First get the hash map object from the index.
		HashMapObj<K, V> hashMapObj = getHashMapObj(key);

		// if (hashMapObj.isLocked()) {
		// System.out.println("Put waiting");
		// }

		// Synchronize on the hash map and put the content;
		synchronized (hashMapObj.getLockObject()) {
			return hashMapObj.put(key, value);
		}
	}

	public V[] put(K[] keys, V[] values) {
		V[] retValues = (V[]) new Object[keys.length];

		int i = 0;
		for (K key : keys) {
			V value = values[i];
			values[i++] = put(key, value);
		}
		return retValues;
	}

	public V remove(K key) {
		// First get the hash map object from the index.
		HashMapObj<K, V> hashMapObj = getHashMapObj(key);

		// if (hashMapObj.isLocked()) {
		// System.out.println("Remove waiting");
		// }

		// Synchronize on the hash map and delete the content;
		synchronized (hashMapObj.getLockObject()) {
			return hashMapObj.remove(key);
		}
	}

	public boolean containsKey(K key) {
		// First get the hash map object from the index.
		HashMapObj<K, V> hashMapObj = getHashMapObj(key);

		// if (hashMapObj.isLocked()) {
		// System.out.println("Contains key waiting");
		// }

		synchronized (hashMapObj.getLockObject()) {
			return hashMapObj.containsKey(key);
		}
	}

	public boolean containsValue(V value) {
		boolean contains = false;
		synchronized (this) {
			for (HashMapObj<K, V> hashMapObj : hashMapObjArray) {
				if (contains) {
					break;
				} else {
					contains = hashMapObj.containsValue(value);
				}
			}
		}
		return contains;
	}

	public int size() {
		int totalSize = 0;
		for (int i = 0; i < noOfMaps; i++) {
			HashMapObj<K, V> hashtableObj = hashMapObjArray[i];
			totalSize = totalSize + hashtableObj.size();
		}
		return totalSize;
	}

	public void printMapStatus() {
		int totalSize = 0;
		for (int i = 0; i < noOfMaps; i++) {
			HashMapObj<K, V> hashtableObj = hashMapObjArray[i];
			int size = hashtableObj.size();
			System.out.println("Index : " + i + " size : " + size);
			totalSize = totalSize + size;
		}
		System.out.println("Total size : " + totalSize);
	}

	/**
	 * Static inner class for storing the key values.
	 * 
	 * @author GaneshS
	 *
	 * @param <K>
	 * @param <V>
	 */
	static class HashMapObj<K, V> {
		private final Object lock;
		private final Unsafe unsafe;
		private int size;

		private volatile HashMap<K, V> table;

		HashMapObj(Object lock) {
			table = new HashMap<K, V>();
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

		Object getLockObject() {
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

		V put(K key, V value) {
			try {
				//Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			V retValue = table.put(key, value);
			if (retValue == null) {
				size++;
			}
			return retValue;
		}

		V get(K key) {
			try {
				//Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return table.get(key);
		}

		V remove(K key) {
			try {
				//Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			V value = table.remove(key);
			if (value != null) {
				size--;
			}
			return value;
		}

		int size() {
			return size;
		}

		boolean containsKey(K key) {
			try {
				//Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return table.containsKey(key);
		}

		boolean containsValue(V value) {
			return table.containsValue(value);
		}
	}
}
