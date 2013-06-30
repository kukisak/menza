package restaurant.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import restaurant.beans.Reservation;
import restaurant.beans.WSReservation;

public interface IRestaurant {
	@GET
    @Path("/")
    @Produces("application/xml")
    String doGet();
	
	@GET
    @Path("/")
    @Produces("application/xml")
    Reservation[] doGetRezervace();		
	
	@POST
    @Path("/")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response.Status postRezervace(WSReservation body);
		
	@DELETE
    @Path("/{id}")
    @Produces("application/xml")
    Response.Status deleteRezervace(@PathParam("id")int id);	
}
