package com.theatre.services.theatre;

import java.util.List;

import com.theatre.api.MovieUrlMap;
import com.theatre.api.Screen;
import com.theatre.services.Service;

public interface TheatreService extends Service{
	
	public Screen addMovieToScreen(int movieId, int screenId);
	
	public void addPosterToScreen(int movieId, String filePath, String fileName);
	
	public List<MovieUrlMap> fetchMoviePosterData(int movieId);
	
	public String fetchPosterUrl(int urlId);
	
	public void deletePosterUrl(int urlId);

}
