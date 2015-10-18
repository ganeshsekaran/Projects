package com.theatre.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.theatre.api.Movie;
import com.theatre.api.Screen;
import com.theatre.dao.DAOFactory;
import com.theatre.dao.Dao;
import com.theatre.dao.SqlQueryString;

public class ScreenService implements Service{

	private static ScreenService screenService;
	private final DAOFactory daoFactory;

	private ScreenService() {
		daoFactory = DAOFactory.getInstance();
	}

	public synchronized static ScreenService getInstance() {
		if (screenService == null) {
			screenService = new ScreenService();
		}
		return screenService;
	}

	public List<Screen> fetchScreens() throws Exception {
		return getScreens(SqlQueryString.SCREEN_ALL_WITH_MOVIE_SELECT_QUERY);
	}

	public Screen addScreen(Screen screen) throws Exception {
		/*Movie movie = screen.getMovie();
		if (screen.getScreenId() > 0) {
			throw new Exception();
		}

		if (movie != null && movie.getMovieId() > 0) {
			throw new Exception();
		}

		List<PreparedStatement> insertStatements = new ArrayList<PreparedStatement>();
		List<ResultSet> resultSets = new ArrayList<ResultSet>();
		int addedScreenId = -1;
		Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			PreparedStatement insertStatement = dao
					.getPreparedStatement(SqlQueryString.SCREEN_INSERT_QUERY);
			insertStatements.add(insertStatement);
			insertStatement.setString(1, screen.getName());
			insertStatement.setString(2, screen.getIsActive() ? "Y" : "N");
			int insertedRows = insertStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new Exception();
			}
			ResultSet resultSet = insertStatement.getGeneratedKeys();
			resultSets.add(resultSet);
			if (resultSet.next()) {
				addedScreenId = resultSet.getInt(1);
			} else {
				throw new Exception();
			}

			if (movie != null) {
				insertStatement = dao
						.getPreparedStatement(SqlQueryString.MOVIE_INSERT_QUERY);
				insertStatements.add(insertStatement);
				insertStatement.setString(1, movie.getName());
				insertStatement.setString(2, movie.getIsReleased() ? "Y" : "N");
				insertStatement.setString(3, movie.getIsRunning() ? "Y" : "N");
				insertedRows = insertStatement.executeUpdate();

				if (insertedRows == 0) {
					throw new Exception();
				}

				int addedMovieId = -1;
				resultSet = insertStatement.getGeneratedKeys();
				resultSets.add(resultSet);
				if (resultSet.next()) {
					addedMovieId = resultSet.getInt(1);
				} else {
					throw new Exception();
				}

				insertStatement = dao
						.getPreparedStatement(SqlQueryString.MOVIE_SCREEN_MAP_INSERT_QUERY);
				insertStatements.add(insertStatement);
				insertStatement.setInt(1, addedMovieId);
				insertStatement.setInt(2, addedScreenId);
				insertedRows = insertStatement.executeUpdate();

				if (insertedRows == 0) {
					throw new Exception();
				}
			}
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			for (PreparedStatement insertStatement : insertStatements) {
				insertStatement.close();
			}
			for (ResultSet resultSet : resultSets) {
				resultSet.close();
			}
		}

		screen = fetchScreen(addedScreenId);
		if (screen == null) {
			throw new Exception();
		}*/

		return screen;
	}

	public Screen updateScreen(Screen screen) throws Exception {

		/*if (screen.getScreenId() < 1 || screen.getMovie() != null
				|| screen.getSeats() != null) {
			throw new Exception();
		}

		Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		PreparedStatement updateStatement = null;
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			updateStatement = dao
					.getPreparedStatement(SqlQueryString.SCREEN_UPDATE_QUERY);
			updateStatement.setNString(1, screen.getName());
			updateStatement.setString(2, screen.getIsActive() ? "Y" : "N");
			updateStatement.setInt(3, screen.getScreenId());

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

		screen = fetchScreen(screen.getScreenId());
		if (screen == null) {
			throw new Exception();
		}*/

		return screen;
	}

	public Screen fetchScreen(int screenId) throws Exception {
		Screen screen = null;
		List<Screen> screens = getScreens(
				SqlQueryString.SCREEN_USING_ID_WITH_MOVIE_SELECT_QUERY,
				screenId);

		if (screens.size() > 0) {
			screen = screens.get(0);
		}
		return screen;
	}

	public Screen deleteScreen(int screenId) throws Exception {
		Screen screen = fetchScreen(screenId);
		/*if (screen == null) {
			throw new Exception();
		}

		Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		List<PreparedStatement> deleteStatements = new ArrayList<PreparedStatement>();
		Connection connection = dao.getConnection();
		connection.setAutoCommit(false);
		try {
			PreparedStatement deleteStatement = dao
					.getPreparedStatement(SqlQueryString.SCREEN_DELETE_QUERY);
			deleteStatements.add(deleteStatement);
			deleteStatement.setInt(1, screenId);
			int count = deleteStatement.executeUpdate();
			if (count == 0) {
				throw new Exception();
			}

			deleteStatement = dao
					.getPreparedStatement(SqlQueryString.MOVIE_SCREEN_MAP_DELETE_WITH_SCREEN_QUERY);
			deleteStatements.add(deleteStatement);
			deleteStatement.setInt(1, screenId);
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
		}

		screen.setMovie(null);
		screen.setSeats(null);*/
		return screen;
	}

	private List<Screen> getScreens(String query) throws Exception {
		return getScreens(query, -1);
	}

	private List<Screen> getScreens(String query, int screenId)
			throws Exception {
		List<Screen> screens = new ArrayList<Screen>();
		/*PreparedStatement statement = null;
		ResultSet result = null;
		try {

			Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
			statement = dao.getPreparedStatement(query);
			if (screenId > 0) {
				statement.setInt(1, screenId);
			}
			result = statement.executeQuery();

			if (query.equalsIgnoreCase(SqlQueryString.SCREEN_ALL_SELECT_QUERY)
					|| query.equalsIgnoreCase(SqlQueryString.SCREEN_USING_ID_SELECT_QUERY)) {
				while (result.next()) {
					Screen screen = new Screen();
					screen.setScreenId(result.getInt(1));
					screen.setName(result.getNString(2));
					screen.setIsActive("Y".equalsIgnoreCase(result.getString(6)));
					screens.add(screen);
				}
			} else if (query
					.equalsIgnoreCase(SqlQueryString.SCREEN_ALL_WITH_MOVIE_SELECT_QUERY)
					|| query.equalsIgnoreCase(SqlQueryString.SCREEN_USING_ID_WITH_MOVIE_SELECT_QUERY)) {
				while (result.next()) {
					Screen screen = new Screen();
					screen.setScreenId(result.getInt(1));
					screen.setName(result.getNString(2));
					screen.setIsActive(result.getString(3)
							.equalsIgnoreCase("Y"));

					Movie movie;
					int movieId = result.getInt(4);
					if (movieId == 0) {
						movie = null;
					} else {
						movie = new Movie();
						movie.setMovieId(movieId);
						movie.setName(result.getNString(5));
						movie.setIsReleased("Y".equalsIgnoreCase(result
								.getString(6)));
						movie.setIsRunning("Y".equalsIgnoreCase(result
								.getString(7)));
					}
					screen.setMovie(movie);
					screens.add(screen);
				}
			} else {
				throw new Exception("UnSupported select");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (result != null) {
				result.close();
			}
		}*/
		return screens;
	}
}
