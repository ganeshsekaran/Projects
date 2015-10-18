package com.theatre.services.theatre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.theatre.annotation.Autowire;
import com.theatre.api.MovieUrlMap;
import com.theatre.api.Screen;
import com.theatre.application.ApplicationContext;
import com.theatre.repository.RowDataMapper;
import com.theatre.repository.theatre.TheatreRepository;

public class TheatreServiceImpl implements TheatreService {

	@Autowire
	private final TheatreRepository theatreRepository;

	@Autowire
	private final ApplicationContext applicationContext;

	public TheatreServiceImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		theatreRepository = (TheatreRepository) applicationContext
				.get("theatreRepository");
	}

	public Screen addMovieToScreen(int movieId, int screenId) {
		theatreRepository.addMovieToScreen(movieId, screenId);
		// Screen screen = ScreenService.getInstance().fetchScreen(screenId);
		return null;
	}

	@Transactional
	public void addPosterToScreen(int movieId, String filePath, String fileName) {
		theatreRepository.addPosterToScreen(movieId, filePath, fileName);
	}

	public List<MovieUrlMap> fetchMoviePosterData(int movieId) {
		RowDataMapper mapper = new RowDataMapper<MovieUrlMap>() {

			public MovieUrlMap mapRow(ResultSet resultSet, int rowNum) {
				MovieUrlMap map = new MovieUrlMap();
				try {
					map.setUrlId(resultSet.getInt("URL_ID"));
					map.setMovieId(resultSet.getInt("MOVIE_ID"));
					map.setMovieName(resultSet.getNString("MOVIE_NAME"));
					map.setFileName(resultSet.getNString("FILE_NAME"));
				} catch (SQLException e) {
					throw new RuntimeException();
				}
				return map;
			}
		};
		List<MovieUrlMap> movieUrlMap = theatreRepository.fetchMoviePosterData(
				movieId, mapper);
		return movieUrlMap;
	}

	public String fetchPosterUrl(int urlId) {
		PreparedStatement selectStatement = null;
		ResultSet resultSet = null;
		String url = null;
		/*
		 * try { Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		 * selectStatement = dao
		 * .getPreparedStatement(SqlQueryString.MOVIE_URL_SELECT_QUERY);
		 * selectStatement.setInt(1, urlId); resultSet =
		 * selectStatement.executeQuery(); if (resultSet.next()) { url =
		 * resultSet.getNString("IMAGE_URL"); }
		 * 
		 * } catch (Exception e) { throw e; } finally { if (selectStatement !=
		 * null) { selectStatement.close(); }
		 * 
		 * if (resultSet != null) { resultSet.close(); } }
		 * 
		 * if (url == null) { throw new Exception(); }
		 */
		return url;
	}

	public void deletePosterUrl(int urlId) {
		/*
		 * Dao dao = daoFactory.getDAO(DAOFactory.TYPE_SQL_SERVER);
		 * PreparedStatement deleteStatement = null; Connection connection =
		 * dao.getConnection(); connection.setAutoCommit(false); try {
		 * deleteStatement = dao .getPreparedStatement(SqlQueryString.
		 * MOVIE_URL_MAP_WITH_URLID_DELETE_QUERY); deleteStatement.setInt(1,
		 * urlId); int deletedRows = deleteStatement.executeUpdate();
		 * 
		 * if (deletedRows == 0) { throw new Exception(); } connection.commit();
		 * } catch (Exception e) { connection.rollback(); throw e; } finally {
		 * connection.setAutoCommit(true); if (deleteStatement != null) {
		 * deleteStatement.close(); } }
		 */
	}
}
