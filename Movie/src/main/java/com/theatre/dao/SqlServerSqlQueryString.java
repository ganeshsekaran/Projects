package com.theatre.dao;

public class SqlServerSqlQueryString extends SqlQueryString {

	@Override
	protected void initializeQueryString() {
		SCREEN_ALL_SELECT_QUERY = "select * from TH_SCREEN";
		SCREEN_ALL_WITH_MOVIE_SELECT_QUERY = "select screen.SCREEN_ID, screen.SCREEN_NAME, screen.IS_ACTIVE, "
				+ "movie.MOVIE_ID, movie.MOVIE_NAME, movie.IS_RELEASED, movie.IS_RUNNING "
				+ "from TH_SCREEN screen "
				+ "left join TH_MOVIE_SCREEN_MAP screenmoviemap on screen.SCREEN_ID = screenmoviemap.SCREEN_ID "
				+ "left join TH_MOVIE movie on screenmoviemap.MOVIE_ID = movie.MOVIE_ID";

		SCREEN_USING_ID_SELECT_QUERY = "select * from TH_SCREEN where SCREEN_ID = ?";
		SCREEN_USING_ID_WITH_MOVIE_SELECT_QUERY = "select screen.SCREEN_ID, screen.SCREEN_NAME, screen.IS_ACTIVE, "
				+ "movie.MOVIE_ID, movie.MOVIE_NAME, movie.IS_RELEASED, movie.IS_RUNNING "
				+ "from TH_SCREEN screen "
				+ "left join TH_MOVIE_SCREEN_MAP screenmoviemap on screen.SCREEN_ID = screenmoviemap.SCREEN_ID "
				+ "left join TH_MOVIE movie on screenmoviemap.MOVIE_ID = movie.MOVIE_ID "
				+ "where screen.SCREEN_ID = ?";
		SCREEN_INSERT_QUERY = "insert into TH_SCREEN values (?,?)";
		SCREEN_UPDATE_QUERY = "update TH_SCREEN set SCREEN_NAME = ?, IS_ACTIVE = ? where SCREEN_ID = ?";
		SCREEN_DELETE_QUERY = "delete from TH_SCREEN where SCREEN_ID = ?";

		MOVIE_SCREEN_MAP_INSERT_QUERY = "insert into TH_MOVIE_SCREEN_MAP values (?,?)";
		MOVIE_SCREEN_MAP_DELETE_WITH_SCREEN_QUERY = "delete from TH_MOVIE_SCREEN_MAP where SCREEN_ID = ?";
		MOVIE_SCREEN_MAP_DELETE_WITH_MOVIE_QUERY = "delete from TH_MOVIE_SCREEN_MAP where MOVIE_ID = ?";

		MOVIE_ALL_SELECT_QUERY = "select * from TH_MOVIE";
		MOVIE_USING_ID_SELECT_QUERY = "select * from TH_MOVIE where MOVIE_ID = ?";
		MOVIE_INSERT_QUERY = "insert into TH_MOVIE values (?,?,?)";
		MOVIE_DELETE_QUERY = "delete from TH_MOVIE where MOVIE_ID = ?";
		MOVIE_UPDATE_QUERY = "update TH_MOVIE set MOVIE_NAME = ?, IS_RELEASED = ?, IS_RUNNING = ? where MOVIE_ID = ?";

		MOVIE_URL_MAP_WITH_MOVIE_DELETE_QUERY = "delete from TH_MOVIE_URL_MAP where MOVIE_ID = ?";
		MOVIE_URL_MAP_WITH_URLID_DELETE_QUERY = "delete from TH_MOVIE_URL_MAP where URL_ID = ?";
		MOVIE_URL_MAP_INSERT_QUERY = "insert into TH_MOVIE_URL_MAP values (?,?,?)";
		MOVIE_URL_MAP_WITH_MOVIE_DISTICT_SELECT_QUERY = "select distinct map.URL_ID, movie.MOVIE_ID, movie.MOVIE_NAME, map.FILE_NAME, "
				+ "map.IMAGE_URL from TH_MOVIE_URL_MAP map join TH_MOVIE movie on movie.MOVIE_ID = map.MOVIE_ID where map.MOVIE_ID = ?";
		MOVIE_URL_SELECT_QUERY = "select map.IMAGE_URL from TH_MOVIE_URL_MAP map where map.URL_ID = ? ";
	}

}
