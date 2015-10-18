package com.theatre.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {

	private int movieId;
	private String name;
	private boolean isReleased;
	private boolean isRunning;

	public Movie() {
		super();
	}

	public Movie(String name, boolean isReleased, boolean isRunning) {
		this(0, name, isReleased, isRunning);
	}

	public Movie(int movieId, String name, boolean isReleased, boolean isRunning) {
		super();
		this.movieId = movieId;
		this.name = name;
		this.isReleased = isReleased;
		this.isRunning = isRunning;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsReleased() {
		return isReleased;
	}

	public void setIsReleased(boolean isReleased) {
		this.isReleased = isReleased;
	}

	public boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
