package com.theatre.proxy.cache;

import java.util.HashMap;
import java.util.Map;

public final class CacheManager {

	private final Map<Integer, CacheRegion> regionMap;
	private static CacheManager region;

	private CacheManager() {
		regionMap = new HashMap<Integer, CacheRegion>();
	}

	public synchronized static CacheManager getInstance() {
		if (region == null) {
			region = new CacheManager();
		}
		return region;
	}

	public CacheRegion getCache(int region) {
		return regionMap.get(region);
	}

	public void removeCache(int region) {
		regionMap.remove(region);
	}

	void addCache(int region, CacheRegion value) {
		regionMap.put(region, value);
	}
}
