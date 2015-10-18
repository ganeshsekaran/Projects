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

import com.theatre.api.Screen;
import com.theatre.services.ScreenService;

@Path("/screen")
public class ScreenController {

	private final ScreenService screenService;

	public ScreenController() {
		screenService = ScreenService.getInstance();
	}

	@PUT
	@Path("/addscreen")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addScreen(Screen screen) {
		try {
			screen = screenService.addScreen(screen);
			return Response.ok().entity(screen).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getscreens")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getScreens() {
		try {
			List<Screen> screens = screenService.fetchScreens();
			Screen[] screenArray;
			if (screens.size() > 0) {
				screenArray = new Screen[screens.size()];
				for (int i = 0; i < screens.size(); i++) {
					screenArray[i] = screens.get(i);
				}
			} else {
				screenArray = new Screen[0];
			}
			return Response.ok().entity(screenArray).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/updatescreen")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateScreen(Screen screen) {
		try {
			screen = screenService.updateScreen(screen);
			return Response.ok().entity(screen).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("/deletescreen/{screenId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteScreen(@PathParam(value = "screenId") int screenId) {
		try {
			System.out.println("Delete");
			Screen screen = screenService.deleteScreen(screenId);
			return Response.ok().entity(screen).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getscreen/{screenId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getScreen(@PathParam(value = "screenId") int screenId) {
		try {
			Screen screen = screenService.fetchScreen(screenId);
			return Response.ok().entity(screen).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
