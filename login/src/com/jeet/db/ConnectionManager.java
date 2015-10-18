package com.jeet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectionManager {

	private Connection con;

	public ConnectionManager() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	private void init() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		con = DriverManager.getConnection("jdbc:sqlserver://INLTGSEKARAN;databaseName=USER",
				"sa", "compaq1-2");
	}


	Connection connection() {
		return con;
	}
	
	Statement statement(){
		try {
			return con.createStatement();
		} catch (SQLException e) {
		}
		return null;
	}

}
