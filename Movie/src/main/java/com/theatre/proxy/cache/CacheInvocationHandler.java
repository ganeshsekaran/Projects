package com.theatre.proxy.cache;

import java.lang.reflect.Method;

import com.theatre.annotation.Cacheable;
import com.theatre.proxy.ProxyInvocationHandler;

public class CacheInvocationHandler extends ProxyInvocationHandler {

	final CacheManager cacheManager;

	public CacheInvocationHandler(Object target) {
		super(target, null);
		this.cacheManager = CacheManager.getInstance();
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object value;
		Method targetMatchingMethod = getTargetMatchingMethod(method);

		if (targetMatchingMethod.isAnnotationPresent(Cacheable.class)) {
			int CacheRegionId = targetMatchingMethod.getAnnotation(
					Cacheable.class).cacheRegion();
			CacheRegion cache = cacheManager.getCache(CacheRegionId);
			if (cache == null) {
				cache = new CacheRegion();
				cacheManager.addCache(CacheRegionId, cache);
			}
			String key = getkey(method, args);
			value = cache.getValue(key);
			if (value == null) {
				System.out.println("Missed Cache value");
				value = method.invoke(target, args);
				cache.setValue(key, value);
			} else {
				System.out.println("Got from cache");
			}
		} else {
			value = method.invoke(target, args);
		}
		return value;
	}
}
