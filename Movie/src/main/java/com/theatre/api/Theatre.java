package com.theatre.api;

import java.util.List;

public interface Theatre {

	public List<Screen> fetchScreens() throws Exception;

	public Screen updateScreen(Screen screen) throws Exception;

	public Screen deleteScreen(int screenId) throws Exception;

	public Screen addScreen(Screen screen) throws Exception;

	public Screen fetchScreen(int screenId) throws Exception;

	public List<Movie> fetchMovies() throws Exception;

	public Movie fetchMovie(int movieId) throws Exception;

	public Movie updateMovie(Movie movie) throws Exception;

	public Movie deleteMovie(int movieId) throws Exception;

	public Movie addMovie(Movie movie) throws Exception;

	public Screen addMovieToScreen(int movieId, int screenId) throws Exception;

	public Seat[] fetchSeats(Screen screen);

	public Seat[] fetchSeats(Movie movie);

	public Ticket bookSeats(Seat[] seats);

	public User[] getUsers(Screen screen);

	public User getUser(Ticket ticket);

}
