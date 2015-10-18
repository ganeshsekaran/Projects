package com.jeet.service;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class SenondtagHandler extends BodyTagSupport {
	private String userName;
	private String password;

	public SenondtagHandler(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public SenondtagHandler() {
		// TODO Auto-generated constructor stub
	}

	public int doStartTag() {
		System.out.println("SenondtagHandler.doStartTag()");
		return EVAL_BODY_INCLUDE;
	}

	public int doAfterBody() {
		System.out.println("SenondtagHandler.doAfterBody()");
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		System.out.println("SenondtagHandler.doEndTag()");
		return EVAL_PAGE;

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

}
