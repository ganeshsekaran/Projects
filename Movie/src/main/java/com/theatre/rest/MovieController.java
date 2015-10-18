package com.theatre.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.theatre.api.Movie;
import com.theatre.services.MovieService;

@Path("/movie")
public class MovieController {

	private final MovieService movieService;

	public MovieController() {
		movieService = MovieService.getInstance();
	}

	@GET
	@Path("/getmovies")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getMovies() {
		try {
			List<Movie> movies = movieService.fetchMovies();
			Movie[] movieArray;
			if (movies.size() > 0) {
				movieArray = new Movie[movies.size()];
				for (int i = 0; i < movies.size(); i++) {
					movieArray[i] = movies.get(i);
					System.out.println(movies.get(i).getName());
				}
			} else {
				movieArray = new Movie[0];
			}
			return Response.ok().entity(movieArray).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/addmovie")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addMovie(Movie movie) {
		try {
			movie = movieService.addMovie(movie);
			return Response.ok().entity(movie).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("/deletemovie/{movieId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteMovie(@PathParam(value = "movieId") int movieId) {
		try {
			Movie movie = movieService.deleteMovie(movieId);
			return Response.ok().entity(movie).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getmovie/{movieId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getMovie(@PathParam(value = "movieId") int movieId) {
		try {
			Movie movie = movieService.fetchMovie(movieId);
			return Response.ok().entity(movie).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/updatemovie")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateMovie(Movie movie) {
		try {
			movie = movieService.updateMovie(movie);
			return Response.ok().entity(movie).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getmoviePoster/{movieId}")
	@Produces({ MediaType.MULTIPART_FORM_DATA })
	public Response getMoviePoster(@PathParam(value = "movieId") int movieId) {
		try {
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
