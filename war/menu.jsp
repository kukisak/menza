<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="menza.helpers.*" %>
<%@ page import="menza.enums.*"%>
<%@ page import="menza.daos.*"%>


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

    //permition check for right menu
    List<MenzaUserType> types = getMenzaUserDAO().getMenzaUserTypes(user);
%>
	Go to <a href="index.jsp?site=home">Main page</a>
	<hr/>
    <strong>
    Available servlets for all:
	</strong>
	<ul class="menu">
	    <li>
	        <a href="index.jsp?site=identity">Identity checking</a>
	    </li>
	    <li>
	        <a href="index.jsp?site=sklad">Consumed WS from Skladiste</a>
	    </li>
	    <li>
	        <a href="index.jsp?site=restaurant">Consumed WS from Restaurant</a>
	    </li>
	</ul>
	<hr/>
    
<%
	if (types != null && !types.isEmpty())
	{
	    
	    //role = BOARDER
	    if(types != null && types.contains(MenzaUserType.BOARDER))
	    {
%>
         <strong>
             Available servlets for BOARDERs:
         </strong>
         <ul class="menu">
             <li>
                 <a href="index.jsp?site=boarderEdit">Edit Boarder Account</a>
             </li>
             <li>
                 <a href="index.jsp?site=menucardOrder">Order menucard</a>
             </li>
             <li>
                 <a href="index.jsp?site=reservationOrder">Order Reservation</a>
             </li>
         </ul>
        <hr/>
<%      
	    }
	    //role = MANAGER
	    if(types.contains(MenzaUserType.MANAGER))
	    {
%>

        <strong>
            Available servlets for MANAGERs:
        </strong>
        <ul class="menu">
            <li>
                <a href="index.jsp?site=userView">View Menza User Account</a>
            </li>
            <li>
                <a href="index.jsp?site=userEdit">Edit Menza User Account</a>
            </li>
            <li>
                <a href="index.jsp?site=boarderManagerEdit">Edit Boarder Account</a>
            </li>
            <li>
                <a href="index.jsp?site=reservationEdit">Edit Reservation</a>
            </li>
        </ul>
       <hr/>
<%
	    }
	    //role = ISSUER
	    if(types.contains(MenzaUserType.ISSUER))
	    {
 %>

            <strong>
                Available servlets for ISSUERs:
            </strong>
            <ul class="menu">
                <li>
                    No servlets
                </li>
            </ul>
            <hr/>
<%
	    }
	    //role = CHEF
	    if(types.contains(MenzaUserType.CHEF))
	    {
 %>

            <strong>
                Available servlets for CHEFs:
            </strong>
            <ul class="menu">
                <li>
                    <a href="index.jsp?site=menucardEdit">Edit menucard</a>
                </li>
            </ul>
           <hr/>
<%
	    }
	    //role = ROBOT
	    if(types.contains(MenzaUserType.ROBOT))
	    {
 %>

            <strong>
                Available servlets for ROBOTSs:
            </strong>
            <ul class="menu">
                <li>
                   No servlets
                </li>
            </ul>
           <hr/>
<%
	    }
	}
%>
