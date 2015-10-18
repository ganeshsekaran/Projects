package com.jeet.service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListenerImpl implements HttpSessionListener {

	public SessionListenerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("SessionListenerImpl.sessionCreated()"+ arg0.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("SessionListenerImpl.sessionDestroyed()" +arg0.getSession().getId());

	}

}
