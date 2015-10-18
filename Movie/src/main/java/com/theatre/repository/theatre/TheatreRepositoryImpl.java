package com.theatre.repository.theatre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.theatre.annotation.Cacheable;
import com.theatre.dao.SqlQueryString;
import com.theatre.proxy.cache.CacheRegionConstants;
import com.theatre.repository.RowDataMapper;

public class TheatreRepositoryImpl implements TheatreRepository {

	private final JdbcTemplate template;
	private final PlatformTransactionManager transactionManger;

	public TheatreRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
		this.transactionManger = new DataSourceTransactionManager(dataSource);
	}

	public void addMovieToScreen(final int movieId, final int screenId) {
		PreparedStatementCreator creater = new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement insertStatement = con.prepareStatement(
						SqlQueryString.MOVIE_SCREEN_MAP_INSERT_QUERY,
						Statement.RETURN_GENERATED_KEYS);
				insertStatement.setInt(1, movieId);
				insertStatement.setInt(2, screenId);
				return insertStatement;
			}
		};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(creater, keyHolder);
	}

	public void addPosterToScreen(int movieId, String filePath, String fileName) {
		template.update(SqlQueryString.MOVIE_URL_MAP_INSERT_QUERY, movieId,
				fileName, filePath);
	}

	@Cacheable(cacheRegion = CacheRegionConstants.CACHE_REGION_MOVIE)
	@Transactional
	public List<?> fetchMoviePosterData(int movieId,
			final RowDataMapper dataMapper) {
		RowMapper<?> rowMapper = new RowMapper<Object>() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return dataMapper.mapRow(rs, rowNum);
			}
		};
		return template.query(
				SqlQueryString.MOVIE_URL_MAP_WITH_MOVIE_DISTICT_SELECT_QUERY,
				rowMapper, movieId);

	}

	@Cacheable(cacheRegion = CacheRegionConstants.CACHE_REGION_MOVIE)
	public Map fetchPosterUrl(int urlId, final RowDataMapper dataMapper) {
		RowMapper<?> rowMapper = new RowMapper<Object>() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return dataMapper.mapRow(rs, rowNum);
			}
		};
		return template.queryForMap(SqlQueryString.MOVIE_URL_SELECT_QUERY,
				rowMapper, urlId);
	}

	public void deletePosterUrl(int urlId) {
		template.update(SqlQueryString.MOVIE_URL_MAP_WITH_URLID_DELETE_QUERY,
				urlId);

	}
}
