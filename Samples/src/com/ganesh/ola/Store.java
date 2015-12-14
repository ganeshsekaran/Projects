package com.ganesh.ola;

public final class Store {

	private static volatile Store instance;
	private final KeyValueStore<Integer, Object> integerKeyStore;
	private final KeyValueStore<Long, Object> longKeyStore;
	private final KeyValueStore<String, Object> stringKeyStore;

	private Store() {
		super();
		integerKeyStore = new KeyValueStore<Integer, Object>();
		longKeyStore = new KeyValueStore<Long, Object>();
		stringKeyStore = new KeyValueStore<String, Object>();

	}

	public static Store getInstance() {
		if (instance == null) {
			synchronized (Store.class) {
				if (instance == null) {
					instance = new Store();
				}
			}
		}
		return instance;
	}

	public Object put(Object key, Object value) {
		if (key instanceof Integer) {
			int k = ((Integer) key).intValue();
			return put(k, value);
		} else if (key instanceof Long) {
			long k = ((Long) key).longValue();
			return put(k, value);
		} else if (key instanceof String) {
			String k = (String) key;
			return put(k, value);
		} else {
			throw new RuntimeException("Not supported key");
		}
	}

	public Object put(int key, Object value) {
		return integerKeyStore.put(key, value);
	}

	public Object put(long key, Object value) {
		return longKeyStore.put(key, value);
	}

	public Object put(String key, Object value) {
		return stringKeyStore.put(key, value);
	}

	public Object get(Object key) {
		if (key instanceof Integer) {
			int k = ((Integer) key).intValue();
			return get(k);
		} else if (key instanceof Long) {
			long k = ((Long) key).longValue();
			return get(k);
		} else if (key instanceof String) {
			String k = (String) key;
			return get(k);
		} else {
			throw new RuntimeException("Not supported key");
		}
	}

	public Object get(int key) {
		return integerKeyStore.get(key);
	}

	public Object get(long key) {
		return longKeyStore.get(key);
	}

	public Object get(String key) {
		return stringKeyStore.get(key);
	}

	public Object delete(Object key) {
		if (key instanceof Integer) {
			int k = ((Integer) key).intValue();
			return delete(k);
		} else if (key instanceof Long) {
			long k = ((Long) key).longValue();
			return delete(k);
		} else if (key instanceof String) {
			String k = (String) key;
			return delete(k);
		} else {
			throw new RuntimeException("Not supported key");
		}
	}

	public Object delete(int key) {
		return integerKeyStore.remove(key);
	}

	public Object delete(long key) {
		return longKeyStore.remove(key);
	}

	public Object delete(String key) {
		return stringKeyStore.remove(key);
	}
}
