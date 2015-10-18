package com.theatre.services;

import com.theatre.application.ApplicationContext;
import com.theatre.proxy.ProxyCreator;
import com.theatre.services.theatre.TheatreServiceImpl;

public final class ServiceFactory {

	public static final int TYPE_SCREEN = 1;
	public static final int TYPE_MOVIE = 2;
	public static final int TYPE_THEATRE = 3;

	public static Object getService(int type,
			ApplicationContext applicationContext) {

		Object target;
		switch (type) {
		case TYPE_THEATRE:
			Service service = new TheatreServiceImpl(applicationContext);
			target = ProxyCreator.createProxy(service);
			break;
		default:
			target = null;
			break;
		}
		return target;
	}
}
