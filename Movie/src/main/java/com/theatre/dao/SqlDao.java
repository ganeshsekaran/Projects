package com.theatre.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class SqlDao implements Dao {

	private final BasicDataSource dataSource;

	public SqlDao() {

		dataSource = new BasicDataSource();
		dataSource
				.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://INLTGSEKARAN;databaseName=THEATRE");
		dataSource.setUsername("sa");
		dataSource.setPassword("compaq1-2");
	}

	public DataSource getDataSource() {
		return dataSource;
	}
}
