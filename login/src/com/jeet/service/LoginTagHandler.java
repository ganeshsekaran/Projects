package com.jeet.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jeet.api.Login;


public class LoginTagHandler extends TagSupport {
	
	private String userName;
	
	private String password;

	public LoginTagHandler() {
	}
	

	public LoginTagHandler(String userName, String password) {
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

	public int doStartTag(){
		Login login = new Login(userName, password);
		login.addToDB();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag(){
		List<Login> list = new LoginService().getAllLogins();
		JspWriter out = pageContext.getOut();
		for(Login l: list){
			try {
				out.println(l.getUserName()+"   "+l.getPassword()+ "<br>");
			} catch (IOException e) {
				return SKIP_PAGE;
			}
		}
		return EVAL_PAGE;
	}
	
}
