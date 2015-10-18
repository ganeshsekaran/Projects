package com.theatre.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.theatre.api.Movie;
import com.theatre.dao.DAOFactory;
import com.theatre.dao.Dao;
import com.theatre.dao.SqlQueryString;

public class MovieService implements Service{

	private static MovieService movieService;
	private final DAOFactory daoFactory;

	private MovieService() {
		daoFactory = DAOFactory.getInstance();
	}

	public synchronized static MovieService getInstance() {
		if (movieService == null) {
			movieService = new MovieService();
		}
		return movieService;
	}

	public Movie fetchMovie(int movieId) throws Exception {

		PreparedStatement selectStatement = null;
		ResultSet selectResult = null;
		Movie movie = null;
		/*try {
			Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
			selectStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_USING_ID_SELECT_QUERY);
			selectStatement.setInt(1, movieId);
			selectResult = selectStatement.executeQuery();

			if (selectResult.next()) {
				movie = constructMovie(selectResult);
			}
		} finally {
			if (selectStatement != null) {
				selectStatement.close();
			}

			if (selectResult != null) {
				selectResult.close();
			}
		}*/
		return movie;
	}

	public List<Movie> fetchMovies() throws Exception {
		List<Movie> movies = new ArrayList<Movie>();
		/*Statement statement = null;
		ResultSet result = null;
		try {
			Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
			statement = dao.getStatement();
			result = statement
					.executeQuery(SqlQueryString.MOVIE_ALL_SELECT_QUERY);

			while (result.next()) {
				Movie movie = constructMovie(result);
				movies.add(movie);
			}

		} finally {
			if (statement != null) {
				statement.close();
			}

			if (result != null) {
				result.close();
			}
		}*/
		return movies;
	}

	public Movie addMovie(Movie movie) throws Exception {
		if (movie.getMovieId() > 0) {
			throw new Exception();
		}

		/*Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		PreparedStatement insertStatement = null;
		ResultSet resultSet = null;
		int addedMovieId = -1;
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			insertStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_INSERT_QUERY);
			insertStatement.setString(1, movie.getName());
			insertStatement.setString(2, movie.getIsReleased() ? "Y" : "N");
			insertStatement.setString(3, movie.getIsRunning() ? "Y" : "N");
			int insertedRows = insertStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new Exception();
			}
			resultSet = insertStatement.getGeneratedKeys();
			if (resultSet.next()) {
				addedMovieId = resultSet.getInt(1);
			} else {
				throw new Exception();
			}

			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			if (insertStatement != null) {
				insertStatement.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		}*/

		//movie = fetchMovie(addedMovieId);
		if (movie == null) {
			throw new Exception();
		}

		return movie;
	}

	public Movie deleteMovie(int movieId) throws Exception {
		Movie movie = fetchMovie(movieId);
		/*if (movie == null) {
			throw new Exception();
		}

		Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		List<PreparedStatement> deleteStatements = new ArrayList<PreparedStatement>();
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			PreparedStatement deleteStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_DELETE_QUERY);
			deleteStatements.add(deleteStatement);
			deleteStatement.setInt(1, movieId);
			int count = deleteStatement.executeUpdate();
			if (count == 0) {
				throw new Exception();
			}

			deleteStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_SCREEN_MAP_DELETE_WITH_MOVIE_QUERY);
			deleteStatements.add(deleteStatement);
			deleteStatement.setInt(1, movieId);
			deleteStatement.executeUpdate();

			deleteStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_URL_MAP_WITH_MOVIE_DELETE_QUERY);
			deleteStatements.add(deleteStatement);
			deleteStatement.setInt(1, movieId);
			deleteStatement.executeUpdate();

			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			for (PreparedStatement deleteStatement : deleteStatements) {
				deleteStatement.close();
			}
		}*/
		return movie;
	}

	public Movie updateMovie(Movie movie) throws Exception {
		if (movie.getMovieId() < 1) {
			throw new Exception();
		}

		/*Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		PreparedStatement updateStatement = null;
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			updateStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_UPDATE_QUERY);
			updateStatement.setNString(1, movie.getName());
			updateStatement.setString(2, movie.getIsReleased() ? "Y" : "N");
			updateStatement.setString(3, movie.getIsRunning() ? "Y" : "N");
			updateStatement.setInt(4, movie.getMovieId());
			int count = updateStatement.executeUpdate();
			if (count == 0) {
				throw new Exception();
			}

			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			if (updateStatement != null) {
				updateStatement.close();
			}
		}
		movie = fetchMovie(movie.getMovieId());
		if (movie == null) {
			throw new Exception();
		}*/

		return movie;
	}

	private Movie constructMovie(ResultSet resultSet) throws Exception {
		Movie movie = new Movie();
		System.out.println(resultSet.getInt(1));
		System.out.println("MovieService.constructMovie()" + resultSet.getNString(2));
		movie.setMovieId(resultSet.getInt(1));
		movie.setName(resultSet.getNString(2));
		movie.setIsReleased("Y".equalsIgnoreCase(resultSet.getString(3)));
		movie.setIsRunning("Y".equalsIgnoreCase(resultSet.getString(4)));
		return movie;
	}
}
