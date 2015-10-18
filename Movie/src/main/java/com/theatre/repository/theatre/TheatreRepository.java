package com.theatre.repository.theatre;

import java.util.List;
import java.util.Map;

import com.theatre.repository.Repository;
import com.theatre.repository.RowDataMapper;

public interface TheatreRepository extends Repository {

	public void addMovieToScreen(int movieId, int screenId);

	public void addPosterToScreen(int movieId, String filePath, String fileName);

	public List fetchMoviePosterData(int movieId, RowDataMapper dataMapper);

	public Map fetchPosterUrl(int urlId, RowDataMapper dataMapper);

	public void deletePosterUrl(int urlId);

}
