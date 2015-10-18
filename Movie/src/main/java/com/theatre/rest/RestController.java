package com.theatre.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import com.theatre.application.ApplicationContext;

public abstract class RestController {

	protected ServletContext servletContext;

	@Context
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
		ApplicationContext applicationContext = (ApplicationContext) context.getAttribute("applicationContext");
		init(applicationContext);
	}
	
	protected abstract void init(ApplicationContext applicationContext);
	
	
}
