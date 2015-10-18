package com.theatre.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.theatre.annotation.Autowire;
import com.theatre.api.MovieUrlMap;
import com.theatre.api.Screen;
import com.theatre.application.ApplicationContext;
import com.theatre.services.theatre.TheatreService;

@Path("/theatre")
public class TheatreController extends RestController {

	@Autowire
	private TheatreService theatreService;

	@Override
	protected void init(ApplicationContext applicationContext) {
		theatreService = (TheatreService) applicationContext
				.get("theatreService");
	}

	@PUT
	@Path("/addmovietoscreen/")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addMovieToScreen(
			@QueryParam(value = "screenId") int screenId,
			@QueryParam(value = "movieId") int movieId) {
		try {
			Screen screen = theatreService.addMovieToScreen(movieId, screenId);
			return Response.ok().entity(screen).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/addmovieposter/{movieId}")
	@Consumes({ MediaType.MULTIPART_FORM_DATA,
			MediaType.APPLICATION_OCTET_STREAM })
	public Response addMoviePoster(@PathParam(value = "movieId") int movieId,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@HeaderParam("filename") String passedFileName) {
		OutputStream out = null;
		File file = null;
		try {
			String uploadUrl = servletContext
					.getInitParameter("imageUploadDirectory");
			String fileName;
			if (passedFileName == null || passedFileName.trim().length() == 0) {
				fileName = fileDetail.getFileName();
			} else {
				fileName = passedFileName;
			}
			String filePath = uploadUrl + fileDetail.getFileName();
			file = new File(filePath);
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(file);
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			theatreService.addPosterToScreen(movieId, filePath, fileName);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			if (file != null) {
				boolean deleted = file.delete();
				System.out.println("Deleted =  " + deleted);
			}
			return Response.serverError().build();
		} finally {
			try {
				uploadedInputStream.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
		}
	}

	@GET
	@Path("/getmovieposterdata/{movieId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMoviePoster(@PathParam(value = "movieId") int movieId,
			@HeaderParam("Accept") String acceptType) {
		try {
			List<MovieUrlMap> map = theatreService
					.fetchMoviePosterData(movieId);
			return Response.ok(map).type(acceptType).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getposter/{urlId}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM,
			MediaType.MULTIPART_FORM_DATA })
	public Response getPoster(@PathParam(value = "urlId") int urlId,
			@HeaderParam("Accept") String acceptType) {
		try {
			String fileUrl = theatreService.fetchPosterUrl(urlId);
			File file = new File(fileUrl);
			StreamingOutput stream = new FileStreamingOutput(file);
			return Response.ok(stream).type(acceptType).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("/deleteposter/{urlId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deletePoster(@PathParam(value = "urlId") int urlId,
			@HeaderParam("Accept") String acceptType) {
		try {
			String fileUrl = theatreService.fetchPosterUrl(urlId);
			theatreService.deletePosterUrl(urlId);
			File file = new File(fileUrl);
			file.delete();
			return Response.ok().type(acceptType).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	private class FileStreamingOutput implements StreamingOutput {

		private final File file;

		private FileStreamingOutput(File file) {
			this.file = file;
		}

		public void write(OutputStream output) throws IOException,
				WebApplicationException {
			FileInputStream input = new FileInputStream(file);
			System.out.println(file.getName());
			try {
				int read = 0;
				byte[] bytes = new byte[2048];
				while ((read = input.read(bytes)) != -1) {
					output.write(bytes, 0, read);
				}
			} finally {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			}
		}
	}
}
