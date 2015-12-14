package com.ganesh.ola;

import java.util.ArrayList;
import java.util.List;

public class KeyValueStoreMain {

	public static void main(String[] args) throws Exception {
		KeyValueStoreMain main = new KeyValueStoreMain();
		KeyValueStore<Object, Object> map = new KeyValueStore<Object, Object>();
		System.out.println("Starting the test :)");
		main.test(map);
	}

	private void test(KeyValueStore<Object, Object> map) throws Exception {
		int count = 1000;

		List<Object> keys = new ArrayList<Object>();
		List<Object> values = new ArrayList<Object>();
		Thread[] adderThreads = new Thread[count];
		for (int i = 0; i < count; i++) {
			Adder adder = new Adder(map, keys, values);
			Thread t = new Thread(adder);
			adderThreads[i] = t;
			t.start();
		}

		for (Thread t : adderThreads) {
			t.join();
		}

		map.printMapStatus();
		Thread.sleep(1000);

		Thread[] containsKeyThreads = new Thread[count];
		List<Boolean> containsKey = new ArrayList<Boolean>();
		int i = 0;
		for (Object k : keys) {
			ContainsKey contains = new ContainsKey(map, k, containsKey);
			Thread t = new Thread(contains);
			containsKeyThreads[i++] = t;
			t.start();
		}

		for (Thread t : containsKeyThreads) {
			t.join();
		}

		boolean containsAllKeys = true;
		for (boolean contains : containsKey) {
			containsAllKeys = containsAllKeys && contains;
			if (!contains) {
				break;
			}
		}
		System.out.println("Maps contains all added Keys : " + containsAllKeys);

		Thread[] containsValueThreads = new Thread[count];
		List<Boolean> containsValue = new ArrayList<Boolean>();
		i = 0;
		for (Object v : values) {
			ContainsValue contains = new ContainsValue(map, v, containsValue);
			Thread t = new Thread(contains);
			containsValueThreads[i++] = t;
			t.start();
		}

		for (Thread t : containsValueThreads) {
			t.join();
		}

		boolean containsAllValues = true;
		for (boolean contains : containsValue) {
			containsAllValues = containsAllValues && contains;
			if (!contains) {
				break;
			}
		}
		System.out.println("Maps contains all added Values : "
				+ containsAllValues);

		Thread[] getterThreads = new Thread[count];
		List<Object> getValues = new ArrayList<Object>();
		i = 0;
		for (Object k : keys) {
			Getter getter = new Getter(map, k, getValues);
			Thread t = new Thread(getter);
			getterThreads[i++] = t;
			t.start();
		}

		for (Thread t : getterThreads) {
			t.join();
		}

		boolean gotAllValues = true;

		if (values.size() != getValues.size()) {
			gotAllValues = false;
		}

		if (gotAllValues) {
			for (Object gotValues : getValues) {
				gotAllValues = values.contains(gotValues);
				if (!gotAllValues) {
					break;
				}
			}
		}
		System.out.println("Got all added Values : " + gotAllValues);
		
		
		Thread[] deleteThreads = new Thread[count];
		List<Object> deleteValues = new ArrayList<Object>();
		i = 0;
		for (Object k : keys) {
			Deleter deleter = new Deleter(map, k, deleteValues);
			Thread t = new Thread(deleter);
			deleteThreads[i++] = t;
			t.start();
		}

		for (Thread t : deleteThreads) {
			t.join();
		}

		boolean deletedAllValues = true;

		if (values.size() != deleteValues.size()) {
			deletedAllValues = false;
		}

		if (deletedAllValues) {
			for (Object deleteValue : deleteValues) {
				deletedAllValues = values.contains(deleteValue);
				if (!deletedAllValues) {
					break;
				}
			}
		}
		System.out.println("Deleted all added Values : " + deletedAllValues);
		
		map.printMapStatus();
		
		
		System.out.println("Adding data using multi add api");
		
		map.put(keys.toArray(), values.toArray());
		map.printMapStatus();
		
		System.out.println("Getting all data using multi get api");
		
		Object[] allValues = map.get(keys.toArray());
		
		boolean allVals = true;
		if(allValues.length != values.size()){
			allVals = false;
		}
		
		if (allVals) {
			for (Object allVal : allValues) {
				allVals = values.contains(allVal);
				if (!allVals) {
					break;
				}
			}
		}
		System.out.println("Got all added Values : " + allVals);
	}
	
	class Deleter implements Runnable {

		private final KeyValueStore<Object, Object> map;
		private final List<Object> values;
		private final Object key;

		Deleter(KeyValueStore<Object, Object> map, Object key,
				List<Object> values) {
			this.map = map;
			this.key = key;
			this.values = values;
		}

		public void run() {
			Object value = map.remove(key);
			synchronized (values) {
				values.add(value);
			}
		}
	}

	class Getter implements Runnable {

		private final KeyValueStore<Object, Object> map;
		private final List<Object> values;
		private final Object key;

		Getter(KeyValueStore<Object, Object> map, Object key,
				List<Object> values) {
			this.map = map;
			this.key = key;
			this.values = values;
		}

		public void run() {
			Object value = map.get(key);
			synchronized (values) {
				values.add(value);
			}
		}
	}

	class ContainsKey implements Runnable {

		private final KeyValueStore<Object, Object> map;
		private final List<Boolean> contains;
		private final Object key;

		ContainsKey(KeyValueStore<Object, Object> map, Object key,
				List<Boolean> contains) {
			this.map = map;
			this.key = key;
			this.contains = contains;
		}

		public void run() {
			boolean containsKey = map.containsKey(key);
			synchronized (contains) {
				contains.add(containsKey);
			}
		}
	}

	class ContainsValue implements Runnable {

		private final KeyValueStore<Object, Object> map;
		private final List<Boolean> contains;
		private final Object value;

		ContainsValue(KeyValueStore<Object, Object> map, Object value,
				List<Boolean> contains) {
			this.map = map;
			this.value = value;
			this.contains = contains;
		}

		public void run() {
			boolean containsValue = map.containsValue(value);
			synchronized (contains) {
				contains.add(containsValue);
			}
		}
	}

	class Adder implements Runnable {

		private final KeyValueStore<Object, Object> map;
		private final List<Object> keys;
		private final List<Object> values;

		Adder(KeyValueStore<Object, Object> map, List<Object> keys,
				List<Object> values) {
			this.map = map;
			this.keys = keys;
			this.values = values;
		}

		public void run() {
			Object k = new Object();
			Object v = new Object();
			map.put(k, v);
			
			
			synchronized (keys) {
				keys.add(k);
				values.add(v);
			}
		}
	}
}
