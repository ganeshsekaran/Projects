package com.jeet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jeet.api.Login;


public class DAO {
	private static DAO instance;
	private ConnectionManager conMgr;

	private DAO() {
		conMgr = new ConnectionManager();
	}

	public static DAO intsnace() {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}

	public void createTable() {
		Statement stm = conMgr.statement();
		try {
			stm.execute("create table login (name varchar(50), password varchar(20))");
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int updateLogin(Login login) throws SQLException{
		Connection con = conMgr.connection();
		PreparedStatement pStm = con.prepareStatement("insert into user_info values (?,?)");
		pStm.setString(1, login.getUserName());
		pStm.setString(2, login.getPassword());
		return pStm.executeUpdate();
	}
	
	public Login getLoginDetail(String userName) throws SQLException{
		Connection con = conMgr.connection();
		PreparedStatement pStm = con.prepareStatement("select * from user_info where user_name=?");
		pStm.setString(1, userName);
		ResultSet rs =  pStm.executeQuery();
		Login login = new Login();
		if( rs.next()){
			login.setUserName(rs.getString(2));
			login.setPassword(rs.getString(3));
		}else{
			System.out.println("DAO.getLoginDetail() RUNTIME EX");
			throw new RuntimeException();
		}
		return login;
	}
	
	public List<Login> getAllLoginDetails() throws SQLException{
		List<Login> list = new ArrayList<Login>();
		Connection con = conMgr.connection();
		PreparedStatement p  = con.prepareStatement("select * from user_info");
		ResultSet rs = p.executeQuery();
		while(rs.next()){
			list.add(new Login(rs.getString(2), rs.getString(3)));
		}
		return list;
	}
	
}
