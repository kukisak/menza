package restaurant.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.URLConnectionClientExecutor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import restaurant.beans.Reservation;
import restaurant.beans.WSReservation;
import restaurant.ws.IRestaurant;

public class Restaurant extends HttpServlet 
{
    private static final long serialVersionUID = 369547190410658453L;

    /**
     * handle with POST data
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        resp.setContentType("text/plain");    	
        /**
         * detects current action based on action param 
         */
        String action = req.getParameter("action");    	
        if( action.equals("get_rezervace")) getRezervace(req, resp);    
        if( action.equals("post_rezervace")) postRezervace(req, resp);
        if( action.equals("delete_rezervace")) deleteRezervace(req, resp);

        /**
         * returns back to the jsp page
         */
        //resp.sendRedirect("/index.jsp?site=menucardEdit");
    }

    private void getRezervace(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try
        {
            RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
            ClientExecutor clientExecutor = new URLConnectionClientExecutor();
            IRestaurant client = ProxyFactory.create(
                    IRestaurant.class, 
                    "http://fit-mdw-ws10-106-1.appspot.com/ws/reservations/", 
                    clientExecutor);
            Reservation[] rezervace = client.doGetRezervace();		        
            for(int i= 0; i < rezervace.length; i++)
            {
                resp.getWriter().println(rezervace[i].toString());	
            }	
        }
        catch (Exception e)
        { 
            //e.printStackTrace(resp.getWriter());
        }
    }    

    private void postRezervace(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try
        {    		
            WSReservation r = new WSReservation();
            r.setDesription(req.getParameter("description"));
            r.setParticipantsNumber(Integer.parseInt(req.getParameter("participantsNumber")));
            r.setReservationDate(req.getParameter("reservationDate"));
            r.setReservationTime(req.getParameter("reservationTime"));
            r.setTitle(req.getParameter("title"));
            r.setUserId(req.getParameter("userId"));

            resp.getWriter().println(r);

            RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
            ClientExecutor clientExecutor = new URLConnectionClientExecutor();	        
            IRestaurant client = ProxyFactory.create(
                    IRestaurant.class, 
                    "http://fit-mdw-ws10-106-1.appspot.com/ws/reservations/newReservation", 
                    clientExecutor);	        	        	     	        
            Response.Status s = client.postRezervace(r);
            resp.getWriter().println(s);
        }
        catch (Exception e)
        {
            //e.printStackTrace(resp.getWriter());
        }
        finally
        {
            //resp.sendRedirect("/index.jsp?site=restaurant");
        }
    }

    private void deleteRezervace(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try
        {
            int deleteID = Integer.parseInt(req.getParameter("deleteID"));    		
            RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
            ClientExecutor clientExecutor = new URLConnectionClientExecutor();	        
            IRestaurant client = ProxyFactory.create(
                    IRestaurant.class, 
                    "http://fit-mdw-ws10-106-1.appspot.com/ws/reservations/delete", 
                    clientExecutor);	        
            Response.Status s = client.deleteRezervace(deleteID);

            resp.getWriter().println(s);
        }catch (Exception e){
            e.printStackTrace(resp.getWriter());
        }finally{
            //resp.sendRedirect("/index.jsp?site=restaurant");
        }
    }

}
