package com.theatre.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MovieUrlMap {
	
	private int urlId;
	private int movieId;
	private String movieName;
	private String fileName;
	
	public MovieUrlMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieUrlMap(int urlId, int movieId, String movieName, String fileName) {
		super();
		this.urlId = urlId;
		this.movieId = movieId;
		this.movieName = movieName;
		this.fileName = fileName;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
