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

import menza.beans.Reservation;
import menza.helpers.PMF;



/**
 * serve to manage data from forms for reservations
 */
public class EditReservationServlet extends HttpServlet 
{
    private static final long serialVersionUID = -5540133221357082604L;
    private static final Logger log = Logger.getLogger(EditReservationServlet.class.getName());
    private static final Integer Max_count = 50;			// maximum amount of seats

    /**
     * handle with POST data
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
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
        if( action.equals("save")) saveReservation(req, resp);
        if( action.equals("delete")) deleteReservation(req, resp);
        if( action.equals("edit")) editReservation(req, resp);
        if( action.equals("new")) newReservation(req, resp);

        /**
         * returns back to the jsp page
         */
        resp.sendRedirect("/index.jsp?site=reservationEdit");
    }

    /**
     * serves for saving new/edited reservation
     * @param req
     * @param resp
     * @throws IOException
     */
    private void saveReservation(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        PersistenceManager pm = PMF.getManager();    	    
        try 
        {    		
            /**
             * get all params
             */

            Date dateStart = new Date();			//default value is today
            try 
            {    			
                String inputDateStart = req.getParameter("dateStart");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy-H:mm");
                dateStart = df.parse(inputDateStart);
            } 
            catch (ParseException e) 
            {    			    		
            }

            Date dateEnd= new Date();			//default value is today
            try 
            {    			
                String inputDateEnd = req.getParameter("dateEnd");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy-H:mm");
                dateEnd = df.parse(inputDateEnd);
            } 
            catch (ParseException e) 
            {    			    		
            }

            Long userID 	= Long.parseLong(req.getParameter("userID"));    			    	
            Integer price 	= Integer.parseInt(req.getParameter("price"));
            Integer count 	= Integer.parseInt(req.getParameter("count"));    	    		

            Query query = pm.newQuery(Reservation.class);
            //query.setFilter("dateStart.before(dateStartParam)");    	    
            //query.setFilter("dateEnd.after(dateEndParam)");
            query.setOrdering("count");
            //query.declareParameters("Date dateStartParam, Date dateEndParam");
            //query.declareParameters("Date dateStartParam");
            try
            {
                //List<Reservation> results = (List<Reservation>) query.execute(dateStart, dateEnd);
                List<Reservation> results = (List<Reservation>) query.execute(); 
                int countEngaged = 0;
                if (results.iterator().hasNext()) 
                {    	        	    	        	
                    for (Reservation resOrder : results) 
                    {    	            	    	            	
                        if(	( dateStart.before(resOrder.getDateStart()) && dateEnd.after(resOrder.getDateEnd()) ) ||
                            ( dateStart.after(resOrder.getDateStart()) && dateStart.before(resOrder.getDateEnd()) )
                          )
                        {
                            countEngaged += resOrder.getCount();    	            		
                        }
                    }
                }    	 

                if ( countEngaged > Max_count ) 
                {
                    resp.sendRedirect("/index.jsp?site=reservationEdit&problem=noSpace");
                } 
                else if(count > Max_count) 
                {
                    resp.sendRedirect("/index.jsp?site=reservationEdit&problem=noSpace");
                } 
                else if(dateStart.before(dateEnd)) 
                {
                    resp.sendRedirect("/index.jsp?site=reservationEdit&problem=revDate");
                }
                else //if(count < Max_count && dateStart.before(dateEnd)) 
                {    	        	
                    //if((count <= Max_count)&&(dateStart.before(dateEnd))){
                    /**
                     * create new reservation in case of null reservationId from the form
                     */    	        	
                    if(req.getParameter("reservationID").equals("null"))
                    {	    		
                        Reservation reservation= new Reservation(userID, dateStart, dateEnd, price, count);
                        pm.makePersistent(reservation);
                    }
                    else 
                    {	    		
                        /**
                         * edit the old one
                         */
                        Long ID = Long.parseLong(req.getParameter("reservationID"));	    	
                        Reservation reservation = pm.getObjectById(Reservation.class, ID);    		    		

                        reservation.setDateStart(dateStart);
                        reservation.setDateEnd(dateEnd);
                        reservation.setUserID(userID);
                        reservation.setPrice(price);  	
                        reservation.setCount(count);
                        pm.makePersistent(reservation);
                    }
                }
            } 
            finally 
            {
                query.closeAll();
            }

        } 
        catch (Exception e) 
        {
            //resp.sendRedirect("/reservationEdit.jsp");
        } 
        finally 
        {
            pm.close();
        }
    }

    /**
     * find reservation in DB and delete it
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteReservation(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {    	        
        PersistenceManager pm = PMF.getManager();    	
        try 
        {    		    		
            Long ID = Long.parseLong(req.getParameter("reservationID"));
            Reservation reservation = pm.getObjectById(Reservation.class, ID);    		    		    		    	    
            pm.deletePersistent(reservation);    		
        } 
        catch (Exception e) 
        {
            //resp.sendRedirect("/reservationEdit.jsp");
        } 
        finally 
        {
            pm.close();
        }	  
    }

    /**
     * redirect on the reservation page and pass ID in GET - will be shown in the form
     * @param req
     * @param resp
     * @throws IOException
     */
    private void editReservation(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try 
        {    		    		
            resp.sendRedirect("/index.jsp?site=reservationEdit&reservationID="+req.getParameter("reservationID")+"");
        } 
        catch (Exception e) 
        {
            //resp.sendRedirect("/reservationEdit.jsp");
        }
    }

    /**
     * redirect on the reservation page wihtou any params = new reservation
     */
    private void newReservation(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try 
        {
            resp.sendRedirect("/index.jsp?site=reservationEdit");
        }
        catch (Exception e) 
        {
            //resp.sendRedirect("/reservationEdit.jsp");
        }
    }

}