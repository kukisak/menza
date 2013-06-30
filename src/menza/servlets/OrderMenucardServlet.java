package menza.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menza.beans.MenucardOrder;
import menza.helpers.PMF;


/**
 * serve to manage data from forms for menucards
 */
public class OrderMenucardServlet extends HttpServlet {
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
    	if( action.equals("order")) orderMenucard(req, resp);
    	if( action.equals("cancel")) cancelMenucard(req, resp);
    	    	    	    
    	/**
    	 * returns back to the jsp page
    	 */
        resp.sendRedirect("/index.jsp?site=menucardOrder");
    }
    
    /**
     * creates new order or reorder older one
     * @param req
     * @param resp
     * @throws IOException
     */
    private void orderMenucard (HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	PersistenceManager pm = PMF.getManager();    	
    	try {    		
    		/**
    		 * get all params
    		 */    		    		
    		Date date = new Date();			//default value is today
    		Long userID = Long.parseLong(req.getParameter("userID"));
    		Long menucardID = Long.parseLong(req.getParameter("menucardID"));
    		int count = Integer.parseInt(req.getParameter("count"));    		    		  	    		
    		
    		/**
    		 * check if there is already an order
    		 */
    		Query query = pm.newQuery(MenucardOrder.class);
    	    query.setFilter("userID == userIDParam");    	    
    	    query.setFilter("menucardID == menucardIDParam");
    	    query.setOrdering("date");
    	    query.declareParameters("Long userIDParam, Long menucardIDParam");    	     	       	        	   
    	    try {
    	        List<MenucardOrder> results = (List<MenucardOrder>) query.execute(userID, menucardID);    	        
    	        if (results.iterator().hasNext()) {    	        	
    	            for (MenucardOrder menuOrder : results) {
    	            	/**
    	        		 * remake order
    	        		 */
    	            	menuOrder.setCount(count);
    	            	menuOrder.setDate(date);
    	                pm.makePersistent(menuOrder);
    	            }
    	        } else {
    	        	/**
    	    		 * make new order
    	    		 */
    	        	MenucardOrder menuOrder = new MenucardOrder(date, userID, menucardID, count);
    	        	pm.makePersistent(menuOrder);
    	        }
    	    } finally {
    	        query.closeAll();
    	    }    	    
    	} catch (Exception e) {
    		//resp.sendRedirect("/menucardOrder.jsp");
    	} finally {
            pm.close();
        }
    	
    }
    
    /**
     * delete order
     * @param req
     * @param resp
     * @throws IOException
     */
    private void cancelMenucard (HttpServletRequest req, HttpServletResponse resp) throws IOException {    	
    	PersistenceManager pm = PMF.getManager();    	
    	try {    		    		
    		Long userID = Long.parseLong(req.getParameter("userID"));
    		Long menucardID = Long.parseLong(req.getParameter("menucardID"));
    		
    		/**
    		 * get all orders
    		 */
    		Query query = pm.newQuery(MenucardOrder.class);
    	    query.setFilter("userID == userIDParam");    	    
    	    query.setFilter("menucardID == menucardIDParam");
    	    query.setOrdering("date");
    	    query.declareParameters("Long userIDParam, Long menucardIDParam");    	     	       	        	    
    	    try {
    	        List<MenucardOrder> results = (List<MenucardOrder>) query.execute(userID, menucardID);    	        
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
    	} catch (Exception e) {
    		//resp.sendRedirect("/menucardEdit.jsp");
    	} finally {
            pm.close();
        }	    	
    }
    
    
}