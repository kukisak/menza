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
import menza.daos.MenucardDAO;

@Path("/menucards")
public class MenucardsWS implements IClientMenza 
{
	/**
	 * return all menucards
	 */
	@GET
    @Path("/read")
    @Produces("application/xml")
    @Consumes("application/xml")
    public List<Menucard>  readMenucards()
    {
		MenucardDAO mdao = new MenucardDAO();
		List<Menucard> menucards = mdao.getAll();
	    return menucards;
	}

	/**
	 * create new menucard - deprecated
	 * @param date
	 * @param foodname
	 * @param weight
	 * @param price
	 * @param foodtype
	 */
    @POST
    @Path("/{date}/{foodname}/{weight}/{price}/{foodtype}")
    @Consumes("application/xml")
    @Produces("text/plain")
    public void setMenucard(@PathParam("date") String date, 
    				 @PathParam("foodname") String foodname,
    				 @PathParam("weight") Integer weight,
    				 @PathParam("price") Integer price,
    				 @PathParam("foodtype") String foodtype)	
    {
		Menucard menu = new Menucard(date, foodname, weight, price, foodtype);
		MenucardDAO mdao = new MenucardDAO();
		mdao.save(menu);
	}
    /**
     * create new menucard - version 2
     * @param date
     * @param foodname
     * @param weight
     * @param price
     * @param foodtype
     */
    @POST
    @Path("/create/{date}/{foodname}/{weight}/{price}/{foodtype}")
    @Consumes("application/xml")
    @Produces("text/plain")
    public String createMenucard(@PathParam("date") String date, 
    				 @PathParam("foodname") String foodname,
    				 @PathParam("weight") Integer weight,
    				 @PathParam("price") Integer price,
    				 @PathParam("foodtype") String foodtype)	
    {
    	Menucard menu = null;
    	try {
    		menu = new Menucard(date, foodname, weight, price, foodtype);
    	} catch (Exception e) {
    		return "Illegal format of created menu";
    	}
		MenucardDAO mdao = new MenucardDAO();
		String status = mdao.save(menu);
		return status;
	}
    
    @PUT
    @Path("/change/{id}/{date}/{foodname}/{weight}/{price}/{foodtype}")
    @Produces("application/xml")
    @Consumes("application/xml")
    public String changeMenucard(@PathParam("id")String id,
				    			@PathParam("date") String date,
				    			@PathParam("foodname") String foodname,
				    			@PathParam("weight") Integer weight,
				    			@PathParam("price") Integer price,
				    			@PathParam("foodtype") String foodtype)
	
    {
    	Menucard menu = new Menucard();
    	try {
	    	menu.setMenucardID(id);
	    	menu.setDate(date);
	    	menu.setFoodname(foodname);
	    	menu.setWeight(weight);
	    	menu.setPrice(price);
	    	menu.setType(foodtype);
    	} catch(NumberFormatException e) {
    		return "Illegal format of menu with ID="+id;
    	}
		MenucardDAO mdao = new MenucardDAO();
		String status = mdao.changeMenucard(menu);
		return status;
    }
   
    @DELETE
    @Path("/delete/{id}")
    @Produces("text/plain")
    @Consumes("text/plain")
    public String deleteMenucard(@PathParam("id")String id)
    {
    	Menucard menu = new Menucard();
    	try{
    		menu.setMenucardID(id);
    	} catch(NumberFormatException e) {
    		return "Illegal number format of ID="+id;
    	}
		MenucardDAO mdao = new MenucardDAO();
		String status = mdao.deleteMenucard(menu);
      	return status;

    }

}
