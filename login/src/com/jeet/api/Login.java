package com.jeet.api;

import com.jeet.db.DAO;

public class Login {

	private String userName;
	private String password;

	public Login() {
		// TODO Auto-generated constructor stub
	}

	public Login(final String userName, final String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addToDB() {
		try {
			System.out
					.println("-----------------------------------------------------");
			Login login = DAO.intsnace().getLoginDetail(userName);
			System.out.println(login.getUserName() + " " + login.getPassword()
					+ " already exist");
		} catch (Exception e) {
			try {
				DAO.intsnace().updateLogin(new Login(userName, password));
				System.out
						.println("SecondServlet.doGet(...).new Runnable() {...}.run()4444");
			} catch (Exception e1) {
			}
		}
	}
}
