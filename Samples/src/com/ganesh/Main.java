package com.ganesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Main {

	private static int count;
	private static final int QUEUE_SIZE = 100000;
	private static final int QUEUE_ADD_SIZE = 120000;
	private static final int QUEUE_DELETE_SIZE = 500000;

	private static final int MAP_CACHE_SIZE = 16;
	private static final int MAP_ADD_SIZE = 100000;

	public static void main(String[] args) throws Exception {
		count = 1;
		BlockingQueue<String> queue = new BlockingQueue<String>(QUEUE_SIZE);
		ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<Object, Object>(
				MAP_CACHE_SIZE);
		Main main = new Main();
		try{
		main.testConcurrentMap(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	void testConcurrentMap(final ConcurrentHashMap<Object, Object> map)
			throws Exception {
		final HashMap<Object, Object> table = new HashMap<Object, Object>();
		final Hashtable<Object, Object> missedTable = new Hashtable<Object, Object>();
		Set<Integer> indexSet = new HashSet<Integer>();
		final List<Thread> threads = new ArrayList<Thread>();
		final List<Float> putTimeTaken = new ArrayList<Float>();
		final List<Float> readTimeTaken = new ArrayList<Float>();

		for (int i = 0; i < MAP_ADD_SIZE; i++) {
			Object key = new Object();
			Object value = new Object();
			table.put(key, value);
		}

		long startTime = System.currentTimeMillis();
		Set<Object> keys = table.keySet();
		for (Object key : keys) {
			Object value = table.get(key);
			Thread t = new Thread(new MapAdder(map, key, value, indexSet,
					putTimeTaken));
			threads.add(t);
			t.start();
		}

		for (Object key : keys) {
			Object value = table.get(key);
			Thread t = new Thread(new MapReader(map, key, value, missedTable,
					readTimeTaken));
			threads.add(t);
			t.start();
		}

		// System.out.println("First Missed table size : " +
		// missedTable.size());

		for (Thread thread : threads) {
			thread.join();
		}
		long endTime = System.currentTimeMillis();
		float totalTime = (endTime - startTime) / 1000;
		System.out.println("Total time = " + totalTime + " seconds");

		float totalputTime = 0;
		for (Float putTime : putTimeTaken) {
			totalputTime = totalputTime + putTime;
		}
		System.out.println("Total put time : " + totalputTime);

		float totalReadTime = 0;
		for (Float readTime : readTimeTaken) {
			totalReadTime = totalReadTime + readTime;
		}
		System.out.println("Total Read time : " + totalReadTime);

		// System.out.println("Missed table size : " + missedTable.size());
		// map.printMapStatus();
	}

	void testBlockingQueue(final BlockingQueue<String> queue) throws Exception {
		for (int i = 0; i < QUEUE_DELETE_SIZE; i++) {
			Thread t = new Thread(new Remover(queue), ("RT :" + (i + 1)));
			t.start();
		}

		for (int i = 0; i < QUEUE_ADD_SIZE; i++) {
			Thread t = new Thread(new Adder(queue), ("AT :" + (i + 1)));
			t.start();
			t.join();
		}

		int size = queue.size();
		System.out
				.println("Size should be "
						+ (QUEUE_ADD_SIZE - QUEUE_DELETE_SIZE)
						+ " and it is : " + size);
	}

	class Adder implements Runnable {
		final BlockingQueue<String> queue;

		Adder(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			queue.add("String" + count++);
		}
	}

	class Remover implements Runnable {
		final BlockingQueue<String> queue;

		Remover(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			queue.remove();
		}
	}

	class MapAdder implements Runnable {
		final ConcurrentHashMap<Object, Object> map;
		final Object key;
		final Object value;
		final Set<Integer> indexSet;
		final List<Float> putTimeTaken;

		public MapAdder(ConcurrentHashMap<Object, Object> map, Object key,
				Object value, Set<Integer> indexSet, List<Float> putTimeTaken) {
			this.map = map;
			this.key = key;
			this.value = value;
			this.indexSet = indexSet;
			this.putTimeTaken = putTimeTaken;
		}

		@Override
		public void run() {
			int index = map.getIndex(key);
			indexSet.add(index);
			long startTime = System.currentTimeMillis();
			map.put(key, value);
			long endTime = System.currentTimeMillis();
			float totalTime = (endTime - startTime) / 1000;
			putTimeTaken.add(totalTime);
		}
	}

	class MapReader implements Runnable {
		final ConcurrentHashMap<Object, Object> map;
		final Object key;
		final Object actualValue;
		final Hashtable<Object, Object> missedTable;
		final List<Float> readTimeTaken;

		MapReader(ConcurrentHashMap<Object, Object> map, Object key,
				Object actualValue, Hashtable<Object, Object> missedTable,
				List<Float> readTimeTaken) {
			this.map = map;
			this.key = key;
			this.actualValue = actualValue;
			this.missedTable = missedTable;
			this.readTimeTaken = readTimeTaken;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			Object value = map.get(key);
			long endTime = System.currentTimeMillis();
			float totalTime = (endTime - startTime) / 1000;
			if (!actualValue.equals(value)) {
				// System.out.println("missed read");
				missedTable.put(key, actualValue);
			} else {
				readTimeTaken.add(totalTime);
			}
		}
	}
}
