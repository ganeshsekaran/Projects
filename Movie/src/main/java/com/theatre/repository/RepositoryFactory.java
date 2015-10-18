package com.theatre.repository;

import javax.sql.DataSource;

import com.theatre.application.ApplicationContext;
import com.theatre.proxy.ProxyCreator;
import com.theatre.repository.theatre.TheatreRepositoryImpl;

public final class RepositoryFactory {

	public static final int REPOSITORY_TYPE_THEATRE = 1;

	public static Object getRepository(int type,
			ApplicationContext applicationContext) {
		DataSource dataSource = (DataSource) applicationContext
				.get("dataSource");
		Object target;
		switch (type) {
		case REPOSITORY_TYPE_THEATRE:
			Object repository = new TheatreRepositoryImpl(dataSource);
			target = ProxyCreator.createProxy(repository);
			break;
		default:
			target = null;
		}

		return target;
	}
}
