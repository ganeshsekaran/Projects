package com.theatre.application;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.theatre.application.ApplicationServletContextListener.ApplicationContextImpl;
import com.theatre.dao.SqlQueryString;
import com.theatre.dao.SqlServerSqlQueryString;
import com.theatre.repository.RepositoryFactory;
import com.theatre.repository.theatre.TheatreRepository;
import com.theatre.services.ServiceFactory;
import com.theatre.services.theatre.TheatreService;

class ApplicationConfiguration {

	final ApplicationContextImpl applicationContext;

	public ApplicationConfiguration(ApplicationContextImpl applicationContext) {
		this.applicationContext = applicationContext;
	}

	void initializeApplicationContext() {

		// 1. Initialize the data source.
		initializeDataSource();

		// 2. Initialize transaction manager.
		initializeTransactionManager();

		// 3. Initialize Sql Query strings.
		initializeSqlString();

		// 4. Initialize Repositories.
		initializeRepositories();

		// 5. Initialize Services.
		initializeServices();
	}

	void deInitializeApplicationContext() {
		System.out.println("Application is cleared");
		applicationContext.clear();
	}

	private void initializeDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource
				.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://INLTGSEKARAN;databaseName=THEATRE");
		dataSource.setUsername("sa");
		dataSource.setPassword("compaq1-2");
		applicationContext.add("dataSource", dataSource);
		System.out.println("dataSource is initialized");
	}

	private void initializeTransactionManager() {
		DataSource dataSource = (DataSource) applicationContext
				.get("dataSource");
		PlatformTransactionManager transactionManger = new DataSourceTransactionManager(
				dataSource);
		applicationContext.add("transactionManger", transactionManger);
		System.out.println("transactionManger is initialized");
	}

	private void initializeSqlString() {
		SqlQueryString sqlString = new SqlServerSqlQueryString();
		applicationContext.add("sqlString", sqlString);
		System.out.println("sqlString is initialized");
	}

	private void initializeRepositories() {
		try {
			TheatreRepository theatreRepository = (TheatreRepository) RepositoryFactory
					.getRepository(RepositoryFactory.REPOSITORY_TYPE_THEATRE,
							applicationContext);
			applicationContext.add("theatreRepository", theatreRepository);
			System.out.println("theatreRepository is initialized");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void initializeServices() {
		TheatreService theatreService = (TheatreService) ServiceFactory
				.getService(ServiceFactory.TYPE_THEATRE, applicationContext);
		applicationContext.add("theatreService", theatreService);
		System.out.println("theatreService is initialized");
	}
}
