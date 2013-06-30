<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="menza.helpers.*" %>
<%@ page import="menza.enums.*"%>
<%@ page import="menza.daos.MenzaUserDAO" %>

<%!
	private PersistenceManager pm = null;
	private MenzaUserDAO menzaUserDAO = null;

	@Override
	public void jspInit()
	{
	    pm = PMF.getManager();
	}
	@Override
	public void jspDestroy()
	{
	    pm.close();
	}
	protected PersistenceManager getPm()
	{
	    return this.pm;
	}
	protected MenzaUserDAO getMenzaUserDAO()
	{
	    if (menzaUserDAO == null)
	    {
	        menzaUserDAO = new MenzaUserDAO(getPm());
	    }
	    return menzaUserDAO;
	}
%>

<%
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

    if (user != null)
    {
%>
		<p>
			You`re logged in as <%=user.getNickname()%>. <br /> 
			You`re email is	<%=user.getEmail()%>
		</p>
		<p>
<%
        List<MenzaUserType> results = getMenzaUserDAO().getMenzaUserTypes(user);
        if (results.isEmpty())
        {
%>            
			You`re not User of application "Luxury Menza"!
<%		
		}
        else
        {
%>
			You`re registred User of application "Luxury Menza"!
		</p>
		Yours roles are:
		<ul>
<%
			for (MenzaUserType t : results)
            {
%>
			<li><%=t%></li>
<%
   			}
%>
		</ul>
<%
		}
    }
    else
    {
%>
		<p>You`re not logged in!</p>
		<p>You`re not User of application "Luxury Menza"!</p>
<%
    }
%>

