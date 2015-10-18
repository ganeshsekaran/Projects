/**
 * 
 */
package com.theatre.dao;

import java.util.HashMap;

/**
 * @author ganeshs
 *
 */
public final class DAOFactory {

	public final static int TYPE_SQL_SERVER = 1;

	private static DAOFactory factory;
	private final HashMap<Integer, Dao> daoMap;

	private DAOFactory() {
		daoMap = new HashMap<Integer, Dao>();
	}

	public static synchronized DAOFactory getInstance() {
		if (factory == null) {
			factory = new DAOFactory();
		}
		return factory;
	}

	public synchronized Dao getDAO(int daoType) {
		Dao dao = daoMap.get(daoType);
		if (dao == null) {
			dao = getDao(daoType);
			daoMap.put(daoType, dao);
			//SqlQueryString.init(daoType);
		}
		return dao;
	}

	private Dao getDao(int type){
		Dao dao;
		switch (type) {
		case TYPE_SQL_SERVER:
			dao = new SqlDao();
			break;
		default:
			dao = null;
		}

		return dao;
	}
}
