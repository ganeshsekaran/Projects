package com.theatre.proxy.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheRegion {

	private final Map<String, Object> cache;

	public CacheRegion() {
		cache = new HashMap<String, Object>();
	}

	public synchronized Object getValue(String key) {
		return cache.get(key);
	}

	public synchronized void clear() {
		cache.clear();
	}

	synchronized void setValue(String key, Object value) {
		cache.put(key, value);
	}
}
