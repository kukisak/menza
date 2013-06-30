<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.jdo.Query"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="menza.beans.*"%>
<%@ page import="menza.helpers.*"%>
<%@ page import="menza.enums.*"%>

<html>

<%
	//add user right authentication
	Long userID = Long.parseLong("0");
	MenzaUserType permitedRole = MenzaUserType.BOARDER;
  
%>
<%
    User user = UserServiceFactory.getUserService().getCurrentUser();
    PersistenceManager pm = PMF.getManager();
    
    //permition check of rights for this site
    //rights only for permitedRole
    List<MenzaUserType> types = menza.helpers.CurrentUser.getCurrentUserType(user, pm);
    //if(false){
    if(!types.contains(permitedRole)){
%>
<jsp:include page="noPermition.jsp" />
<%
    }
    else
    {
	    //get userID for current user and his role:
	    userID = menza.helpers.CurrentUser.getCurrentRoleUserId(user, permitedRole, pm);
	
	    //righted user can see (the rest of) this site!	
	    String query = "select from " + Menucard.class.getName() + " order by date";
	    List<Menucard> menucards = (List<Menucard>) pm.newQuery(query).execute();
	    if (menucards.isEmpty()) {
%>
<p>The list of menucards is empty</p>
<%
    } 
	else 
	{
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    			    			    				    	
    	String prevDate = "00/00/0000";		

%>
<table border="1">

<%
    	
        for (Menucard m : menucards) 
        {        	  					    			    				    	
    		String date = df.format(m.getDate());		    			
    		//out.print(date);    	
        	if(!date.equals(prevDate))
        	{        
        		prevDate = date;
%>
	<tr>
		<th colspan="9"></th>
	</tr>
	<tr>
		<th colspan="9"><%=date %></th>
	</tr>
	<tr>
		<th>ID</th>
		<th>Date</th>
		<th>Food Name</th>
		<th>Weight</th>
		<th>Price</th>
		<th>Type</th>
		<th>Count</th>
		<th></th>
		<th></th>
	</tr>
<%        		
        }
%>
	<tr>
		<td><%= m.getID() %></td>
		<td><%= date %></td>
		<td><%= m.getFoodname() %></td>
		<td><%= m.getWeight() %></td>
		<td><%= m.getPrice() %></td>
		<td><%= m.getType() %></td>
		<td>
			<%
	        		Query orderQuery = pm.newQuery(MenucardOrder.class);
	        		orderQuery.setFilter("userID == userIDParam");    	    
	        		orderQuery.setFilter("menucardID == menucardIDParam");
	        		orderQuery.setOrdering("date");
	        		orderQuery.declareParameters("Long userIDParam, Long menucardIDParam");    	     	       	        	   
	        	    try {
	        	        List<MenucardOrder> results = (List<MenucardOrder>) orderQuery.execute(userID, m.getID());    	        
	        	        if (results.iterator().hasNext()) {
	        	        	MenucardOrder menuOrder = results.iterator().next();
	        	            out.print(menuOrder.getCount());
	        	        } else {	
	        	        	out.print("0");
	        	        }
	        	    } finally {
	        	    	orderQuery.closeAll();
	        	    }  
        		%>
		</td>
		<td>
			<form action="/orderMenucard" method="post">
				<select name="count">
					<option value="1" selected>1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select> <input type="hidden" name="menucardID" value="<%= m.getID() %>" />
				<input type="hidden" name="userID" value="<%= userID %>" /> <input
					type="hidden" name="action" value="order" /> <input type="submit"
					value="Order" />
			</form></td>
		<td>
			<form action="/orderMenucard" method="post">
				<input type="hidden" name="menucardID" value="<%= m.getID() %>" />
				<input type="hidden" name="userID" value="<%= userID %>" /> <input
					type="hidden" name="action" value="cancel" /> <input type="submit"
					value="Cancel order" />
			</form></td>
	</tr>
	<%        
        }    
    	%>
</table>
<%
    }
        
    query = "select from " + MenucardOrder.class.getName() + " order by date";
    List<MenucardOrder> orders = (List<MenucardOrder>) pm.newQuery(query).execute();
    if (!orders.isEmpty()) {
    	%>
<!-- 
    	<table>
    	<tr>
    		<th>ID</th>
    		<th>date</th>
    		<th>userID</th>
    		<th>menuID</th>
    		<th>count</th>
		</tr>    		
    	<%
    	for (MenucardOrder o : orders) 
        {
		%>
		<tr>
			<td><%= o.getID()%></td>
			<td><%
	        		try {    			
		    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    			//DateFormat df = new SimpleDateFormat("dd.MM.yyyy");	    			
		    			String date = df.format(o.getDate());
		    			out.print(date);
		    		} catch (Exception e) {    			    		
		    		}	        		
        		%>		
			<td><%= o.getUserID()%></td>
			<td><%= o.getMenucardID()%></td>
			<td><%= o.getCount()%></td>
		</tr>	
		<%    		
        }
    	%>
    	</table>
    	 -->
<%
    }           
    pm.close();
    }
%>