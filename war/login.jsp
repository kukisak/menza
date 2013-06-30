<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="menza.helpers.*"%>
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
    String siteParam = request.getParameter("site");

    if (user == null)
    {
%>
<div class="left">
    Hello!&nbsp;
    <a href="<%= userService.createLoginURL(request.getRequestURI()+"?site=" + siteParam) %>">Sign in</a>
    to enjoy.
</div>
<%
    }
    else
    {
%>
<div class="left">
    <a href="<%= userService.createLogoutURL(request.getRequestURI()+"?site=" + siteParam) %>">Sign out</a>.

</div>
<div class="right">
	Logged in as <strong><%=user.getNickname()%></strong>.&nbsp;
	<%
            List<MenzaUserType> results = getMenzaUserDAO().getMenzaUserTypes(user);
            if (results == null || results.isEmpty())
            {
	%>
	Not User of application "Luxury Menza"!
	<%
		    }
            else
            {
	%>
	Roles:&nbsp;

	<%
			    for (MenzaUserType t : results)
		        {
	%>

	<%=t%>;&nbsp;
<%
			    }
			}
%>
</div>
<%
    }
%>
