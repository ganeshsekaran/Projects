package com.jeet.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.jeet.api.Login;
import com.jeet.db.DAO;

public class LoginService {

	public LoginService() {
		// TODO Auto-generated constructor stub
	}

	public boolean addToDB(final String userName, final String password) {
		Executor exe = Executors.newFixedThreadPool(5);
		exe.execute(new Runnable() {
			@Override
			public void run() {
				try {
					System.out
							.println("-----------------------------------------------------");
					Login login = DAO.intsnace().getLoginDetail(userName);
					System.out.println(login.getUserName() + " "
							+ login.getPassword() + " already exist Wait");
					Thread.currentThread().sleep(5000);
				} catch (Exception e) {
					try {
						Thread.currentThread().sleep(5000);
						DAO.intsnace().updateLogin(new Login(userName, password));
						System.out
								.println("SecondServlet.doGet(...).new Runnable() {...}.run() 3333");
					} catch (Exception e1) {
					}
				}
				
			}
		});
		return true;
	}

	public List<Login> getAllLogins() {
		try {
			return DAO.intsnace().getAllLoginDetails();
		} catch (SQLException e) {
			System.out.println("LoginService.getAllLogins() EX");
		}
		return new ArrayList<Login>();
	}

}
