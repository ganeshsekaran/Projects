package com.theatre.dao;

public abstract class SqlQueryString {

	public static String SCREEN_ALL_SELECT_QUERY;
	public static String SCREEN_ALL_WITH_MOVIE_SELECT_QUERY;
	public static String SCREEN_USING_ID_SELECT_QUERY;
	public static String SCREEN_USING_ID_WITH_MOVIE_SELECT_QUERY;
	public static String SCREEN_INSERT_QUERY;
	public static String SCREEN_UPDATE_QUERY;
	public static String SCREEN_DELETE_QUERY;

	public static String MOVIE_SCREEN_MAP_INSERT_QUERY;
	public static String MOVIE_SCREEN_MAP_DELETE_WITH_SCREEN_QUERY;
	public static String MOVIE_SCREEN_MAP_DELETE_WITH_MOVIE_QUERY;

	public static String MOVIE_ALL_SELECT_QUERY;
	public static String MOVIE_USING_ID_SELECT_QUERY;
	public static String MOVIE_INSERT_QUERY;
	public static String MOVIE_DELETE_QUERY;
	public static String MOVIE_UPDATE_QUERY;

	public static String MOVIE_URL_MAP_WITH_MOVIE_DELETE_QUERY;
	public static String MOVIE_URL_MAP_WITH_URLID_DELETE_QUERY;
	public static String MOVIE_URL_MAP_INSERT_QUERY;
	public static String MOVIE_URL_MAP_WITH_MOVIE_DISTICT_SELECT_QUERY;
	public static String MOVIE_URL_SELECT_QUERY;

	SqlQueryString() {
		initializeQueryString();
	}

	abstract void initializeQueryString();
}
