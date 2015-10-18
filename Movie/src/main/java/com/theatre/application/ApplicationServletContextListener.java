package com.theatre.application;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationServletContextListener implements
		ServletContextListener {

	ApplicationConfiguration config;

	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContextImpl applicationContext = new ApplicationContextImpl();
		config = new ApplicationConfiguration(applicationContext);
		new Application(applicationContext);
		config.initializeApplicationContext();
		sce.getServletContext().setAttribute("applicationContext",
				applicationContext);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		config.deInitializeApplicationContext();
		sce.getServletContext().removeAttribute("applicationContext");
	}

	class ApplicationContextImpl implements ApplicationContext {

		private final Map<String, Object> applicationContext;

		ApplicationContextImpl() {
			applicationContext = new HashMap<String, Object>();
		}

		public Object get(String key) {
			return applicationContext.get(key);
		}

		void add(String key, Object value) {
			applicationContext.put(key, value);
		}

		void clear() {
			applicationContext.clear();
		}

	}
}
