package com.ganesh.ola;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

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
	private final TimerClass timerClass;

	public KeyValueStore() {
		this(16);
	}

	public KeyValueStore(final int count) {
		this.noOfMaps = count;
		this.salt = 0.37;
		this.hashMapObjArray = createHashMapObj(count);
		this.timerClass = new TimerClass(5000);
		timerClass.start();
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

	public V put(K key, V value, long ttl) {
		// First get the hash map object from the index.
		HashMapObj<K, V> hashMapObj = getHashMapObj(key);

		// if (hashMapObj.isLocked()) {
		// System.out.println("Put waiting");
		// }

		// Synchronize on the hash map and put the content;
		synchronized (hashMapObj.getLockObject()) {
			return hashMapObj.put(key, value, ttl);
		}
	}

	public V put(K key, V value) {
		return put(key, value, -1);
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

	class TimerClass {
		private final Timer timer;
		private final long delay;

		public TimerClass(long delay) {
			timer = new Timer(true);
			this.delay = delay;
		}

		void start() {
			timer.schedule(new TimerTaksClass(), delay);
		}

		class TimerTaksClass extends TimerTask {

			public void run() {
				removeExpiredEntry(System.currentTimeMillis());
				start();
			}
		}

		private void removeExpiredEntry(long currentTime) {
			for (HashMapObj<K, V> hashMapObj : hashMapObjArray) {
				synchronized (hashMapObj) {
					HashMap<K, EntryValue<V, Long>> map = hashMapObj.getMap();

					Collection<EntryValue<V, Long>> collections = map.values();
					Iterator<EntryValue<V, Long>> itr = collections.iterator();

					while (itr.hasNext()) {
						EntryValue<V, Long> entry = itr.next();
						long currentTTL = entry.ttl;
						if (currentTTL != -1 && entry.ttl < currentTime) {
							itr.remove();
						}
					}
				}
			}
		}
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

		private volatile HashMap<K, EntryValue<V, Long>> table;

		HashMapObj(Object lock) {
			table = new HashMap<K, EntryValue<V, Long>>();
			this.lock = lock;
			this.unsafe = getUnsafe();
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

		HashMap<K, EntryValue<V, Long>> getMap() {
			return table;
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

		V put(K key, V value, long ttl) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ttl != -1) {
				ttl = System.currentTimeMillis() + ttl;
			}
			EntryValue<V, Long> valueEntry = new EntryValue<V, Long>(value, ttl);
			EntryValue<V, Long> ret = table.put(key, valueEntry);
			if (ret == null) {
				return null;
			}
			return ret.value;
		}

		V get(K key) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EntryValue<V, Long> ret = table.get(key);
			if (ret == null) {
				return null;
			}
			return ret.value;
		}

		V remove(K key) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EntryValue<V, Long> ret = table.remove(key);
			if (ret != null) {
				return ret.value;
			}
			return null;
		}

		int size() {
			return table.size();
		}

		boolean containsKey(K key) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return table.containsKey(key);
		}

		boolean containsValue(V value) {
			EntryValue<V, Long> entryValue = new EntryValue<V, Long>(value, -1);
			return table.containsValue(entryValue);
		}
	}

	static class EntryValue<V, Long> {
		volatile V value;
		final long ttl;

		EntryValue(V value, long ttl) {
			this.value = value;
			this.ttl = ttl;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (!(obj instanceof EntryValue)) {
				return false;
			}

			return ((EntryValue<V, Long>) obj).value.equals(value);

		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}
	}
}
