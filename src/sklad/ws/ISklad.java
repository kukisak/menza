package sklad.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import sklad.beans.Polozka;

public interface ISklad {
	@GET
    @Path("/")
    @Produces("application/xml")
    Polozka[] doGet();
	
	@PUT
    @Path("/")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response.Status putPolozka(Polozka body);
	
	@DELETE
    @Path("/{PLU}")
    @Produces("application/xml")
    Response.Status deletePolozka(@PathParam("PLU")int PLU);
	
}
