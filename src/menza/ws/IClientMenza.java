package menza.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import menza.beans.Menucard;

import org.jboss.resteasy.client.ClientResponse;

public interface IClientMenza {
	/**
	 * return collection of all menucards
	 */
	@GET
    @Path("/read")
    @Produces("application/xml")
    @Consumes("application/xml")
    List<Menucard> readMenucards();


	/**
	 * create new menucard
     * @param date in format dd-MM-yyyy
     * @param foodname
     * @param weight
     * @param price
     * @param foodtype as STARTER, SOUP, LUNCH, SALAD, DESSERT, DRINK 
	 */
     @POST
     @Path("/create/{date}/{foodname}/{weight}/{price}/{foodtype}")
     @Consumes("application/xml")
     @Produces("text/plain")
     String createMenucard(@PathParam("date") String date, 
     				 @PathParam("foodname") String foodname,
     				 @PathParam("weight") Integer weight,
     				 @PathParam("price") Integer price,
     				 @PathParam("foodtype") String foodtype);
     
     /**
      * change existing menucard with menuid inside new object menu
      */
     @PUT
     @Path("/change/{id}/{date}/{foodname}/{weight}/{price}/{foodtype}")
     @Produces("application/xml")
     @Consumes("application/xml")
     String changeMenucard(@PathParam("id") String id,
						 			@PathParam("date") String date,
									@PathParam("foodname") String foodname,
									@PathParam("weight") Integer weight,
									@PathParam("price") Integer price,
									@PathParam("foodtype") String foodtype);

     /**
      * delete existing menucard
      * @param menuid
      */
     @DELETE
     @Path("/delete/{id}")
     @Produces("text/plain")
     @Consumes("text/plain")
     String deleteMenucard(@PathParam("id")String cislo);
//     ClientResponse<String> deleteMenucard(@PathParam("id")String cislo);

}
