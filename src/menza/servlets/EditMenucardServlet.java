package menza.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menza.beans.Menucard;
import menza.beans.MenucardOrder;
import menza.enums.FoodType;
import menza.helpers.PMF;


/**
 * serve to manage data from forms for menucards
 */
public class EditMenucardServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(EditMenucardServlet.class.getName());

    /**
     * handle with POST data
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
    	/*
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String content = req.getParameter("content");
        Date date = new Date();
        */    	
    	//resp.setContentType("text/plain");
    	//resp.getWriter().println("Hello, oldie");

    	/**
    	 * detects current action based on action param 
    	 */
    	String action = req.getParameter("action");    	
    	if( action.equals("save")) saveMenucard(req, resp);
    	if( action.equals("delete")) deleteMenucard(req, resp);
    	if( action.equals("edit")) editMenucard(req, resp);
    	if( action.equals("new")) newMenucard(req, resp);
    	//if( action.equals("new")) resp.sendRedirect("/menucardEdit.jsp");
    	    	    	
    	/**
    	 * returns back to the jsp page
    	 */
        resp.sendRedirect("/index.jsp?site=menucardEdit");
    }
    
    /**
     * serves for saving new/edited menucard
     * @param req
     * @param resp
     * @throws IOException
     */
    private void saveMenucard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	PersistenceManager pm = PMF.getManager();    	    
    	try {    		
    		/**
    		 * get all params
    		 */
    		
    		Date date = new Date();			//default value is today
    		try {    			
    			String inputDate = req.getParameter("date");
    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    			date = df.parse(inputDate);
    		} catch (ParseException e) {    			    		
    		}	    	    		    		    		
	    	String foodname = req.getParameter("foodname");
	    	Integer weight	= Integer.parseInt(req.getParameter("weight"));
	    	Integer price 	= Integer.parseInt(req.getParameter("price"));    	    	
	    	FoodType type 	= getFoodType(req.getParameter("type"));
	    		    		 
	    	/**
	    	 * create new menucard in case of null menucardId from the form
	    	 */
	    	if(req.getParameter("menucardID").equals("null")){	    		
	    		Menucard menucard= new Menucard(date, foodname, weight, price, type);
	    		pm.makePersistent(menucard);
	    	}else {	    		
	    	/**
	    	 * edit the old one
	    	 */
		    	Long ID = Long.parseLong(req.getParameter("menucardID"));	    	
	    		Menucard menucard = pm.getObjectById(Menucard.class, ID);    		    		
	    		
	    		menucard.setDate(date);
	    		menucard.setFoodname(foodname);
	    		menucard.setWeight(weight);
	    		menucard.setPrice(price);  
	    		menucard.setType(type);
	    		pm.makePersistent(menucard);
	    	}	        	              
    	} catch (Exception e) {
    		//resp.sendRedirect("/menucardEdit.jsp");
    	} finally {
            pm.close();
        }
    }
    
    /**
     * find menucard in DB and delete it
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteMenucard(HttpServletRequest req, HttpServletResponse resp) throws IOException {    	        
    	PersistenceManager pm = PMF.getManager();    	
    	try {    		    		
    		Long ID = Long.parseLong(req.getParameter("menucardID"));
    		Menucard menucard = pm.getObjectById(Menucard.class, ID);    		    		    		
    		/**
    		 * delete all related orders
    		 */
    		Query query = pm.newQuery(MenucardOrder.class);    	        	   
    	    query.setFilter("menucardID == menucardIDParam");
    	    query.setOrdering("date");
    	    query.declareParameters("Long menucardIDParam");    	     	       	        	    
    	    try {
    	        List<MenucardOrder> results = (List<MenucardOrder>) query.execute(menucard.getID());    	        
    	        if (results.iterator().hasNext()) {    	        	
    	            for (MenucardOrder m : results) {
    	            	pm.deletePersistent(m);
    	            }
    	        } else {
    	            // ... no results ... nothing to delete
    	        }
    	    } finally {
    	        query.closeAll();
    	    }     	    
    	    pm.deletePersistent(menucard);    		
    	} catch (Exception e) {
    		//resp.sendRedirect("/menucardEdit.jsp");
    	} finally {
            pm.close();
        }	  
    }
    
    /**
     * redirect on the menucard page and pass ID in GET - will be shown in the form
     * @param req
     * @param resp
     * @throws IOException
     */
    private void editMenucard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	try {    		    		
    		resp.sendRedirect("/index.jsp?site=menucardEdit&menucardID="+req.getParameter("menucardID")+"");
    	} catch (Exception e) {
    		//resp.sendRedirect("/menucardEdit.jsp");
    	}
    }
    
    /**
     * redirect on the menucard page wihtou any params = new menucard
     */
    private void newMenucard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	try {
    		resp.sendRedirect("/index.jsp?site=menucardEdit");
    	}catch (Exception e) {
    		//resp.sendRedirect("/menucardEdit.jsp");
    	}
    }
        
    /**
     * support function for enum parsing
     * TODO move to the enum class
     * @param type
     * @return
     */
    private FoodType getFoodType(String type)
	{	
    	type = type.toLowerCase();
		if( type.equals("starter") )return FoodType.STARTER;
		if( type.equals("soup") ) 	return FoodType.SOUP;
		if( type.equals("lunch") ) 	return FoodType.LUNCH;
		if( type.equals("salad") ) 	return FoodType.SALAD;
		if( type.equals("dessert") )return FoodType.DESSERT;
		if( type.equals("drink") ) 	return FoodType.DRINK;
		return FoodType.LUNCH;		
	}
}