package com.theatre.application;

public class Application {

	private static ApplicationContext applicationContext;

	public Application(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
