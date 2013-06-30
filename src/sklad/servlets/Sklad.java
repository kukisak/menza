package sklad.servlets;

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

import sklad.beans.Polozka;
import sklad.ws.ISklad;


public class Sklad extends HttpServlet 
{
    private static final long serialVersionUID = -1295528418027195806L;

    /**
     * handle with POST data
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	resp.setContentType("text/plain");    	
    	/**
    	 * detects current action based on action param 
    	 */
    	String action = req.getParameter("action");    	
    	if( action.equals("export")) export(req, resp);
    	if( action.equals("ciselnik_get")) ciselnikGet(req, resp);
    	if( action.equals("ciselnik_put")) ciselnikPut(req, resp);
    	if( action.equals("ciselnik_delete")) ciselnikDelete(req, resp);
    	if( action.equals("put_nasklad")) putNasklad(req, resp);
    	if( action.equals("put_vysklad")) putVysklad(req, resp);
    	    	    	
    	/**
    	 * returns back to the jsp page
    	 */
        //resp.sendRedirect("/index.jsp?site=menucardEdit");
    }
    
    private void export(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	try
    	{
    		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	        ClientExecutor clientExecutor = new URLConnectionClientExecutor();
	        ISklad client = ProxyFactory.create(
	                ISklad.class, 
	                "http://fit-mdw-ws10-103-6.appspot.com/resources/tym5_product_manager/tym5_product_manager/zamestnanec/export/174001", 
	                clientExecutor);	        	        	
	        Polozka[] polozky = client.doGet();	    
	        for(int i= 0; i < polozky.length; i++)
	        {
	        	resp.getWriter().println(polozky[i].toString());	
	        }							        	            		    
    	}
    	catch (Exception e)
    	{ 
    		//e.printStackTrace(resp.getWriter());
    	}
    }
    
    private void ciselnikGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	try
    	{
    		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	        ClientExecutor clientExecutor = new URLConnectionClientExecutor();
	        ISklad client = ProxyFactory.create(
	                ISklad.class, 
	                "http://fit-mdw-ws10-103-6.appspot.com/resources/tym5_product_manager/tym5_product_manager/zamestnanec/ciselnik/174001", 
	                clientExecutor);	        
	        Polozka[] polozky = client.doGet();	    
	        for(int i= 0; i < polozky.length; i++)
	        {
	        	resp.getWriter().println(polozky[i].toString());	
	        }	            		    	
    	}
    	catch (Exception e)
    	{    
    		//e.printStackTrace(resp.getWriter());
    	}
    }
    
    private void ciselnikPut(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {    	
    	try
    	{      		    		
    		Polozka p = new Polozka();
            p.setPLU(Integer.parseInt(req.getParameter("PLU")));
            p.setNazev(req.getParameter("nazev"));
            p.setCena(Integer.parseInt(req.getParameter("cena")));   		
    		
    		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	        ClientExecutor clientExecutor = new URLConnectionClientExecutor();	        
	        ISklad client = ProxyFactory.create(
	                ISklad.class, 
	                "http://fit-mdw-ws10-103-6.appspot.com/resources/tym5_product_manager/tym5_product_manager/zamestnanec/ciselnik/174001", 
	                clientExecutor);	        
	        Response.Status s = client.putPolozka(p);
	        
	        resp.getWriter().println(s);
    	}
    	catch (Exception e)
    	{    		
    		//e.printStackTrace(resp.getWriter());
    	}
    	finally
    	{
    		resp.sendRedirect("/index.jsp?site=sklad");
    	}
    }
    
    private void ciselnikDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	try
    	{
    		int PLU = Integer.parseInt(req.getParameter("PLU"));
    		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	        ClientExecutor clientExecutor = new URLConnectionClientExecutor();	        
	        ISklad client = ProxyFactory.create(
	                ISklad.class, 
	                "http://fit-mdw-ws10-103-6.appspot.com/resources/tym5_product_manager/tym5_product_manager/zamestnanec/ciselnik/174001", 
	                clientExecutor);	        
	        Response.Status s = client.deletePolozka(PLU);
	        resp.getWriter().println(s);
    	}
    	catch (Exception e)
    	{
    		//e.printStackTrace(resp.getWriter());    		
    	}
    	finally
    	{
    		resp.sendRedirect("/index.jsp?site=sklad");
    	}
    }
    
    private void putNasklad(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	try
    	{
    	}
    	catch (Exception e)
    	{
    	}
    }
    
    private void putVysklad(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	try
    	{
    	}
    	catch (Exception e)
    	{
    	}
    }
}
