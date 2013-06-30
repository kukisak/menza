<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="menza.beans.*" %>
<%@ page import="menza.helpers.*" %>
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
       <jsp:include page="noPermition.jsp"/>
<%
    }else{
    //get userID for current user and his role:
    userID = menza.helpers.CurrentUser.getCurrentRoleUserId(user, permitedRole, pm);

    //righted user can see (the rest of) this site!
		Reservation editReservation = new Reservation( userID ,new Date(), new Date(), 100, 1);	
		if(request.getParameter("reservationID") != null){			
			try {    		    		
				Integer ID = Integer.parseInt(request.getParameter("reservationID"));
	    		editReservation = pm.getObjectById(Reservation.class, ID);    		            	    		    		    	
	    	} catch (Exception e) {	    	
	    	}			
		}
%>	
	<fieldset>
		<legend>Add a new reservation</legend>	
	<form action="/orderReservation" method="post">
		<table>	    
			<tr><th colspan = "2">    			
		    	<input type="hidden" name="action" value="new" />
		        <input type="submit" value="Create new" />
	        </th></tr>	        
        
    </form>
	<form action="/orderReservation" method="post">
				
			<tr><th>ID</th><td><%= editReservation.getID()%></td></tr>
    	    <tr><th>User ID</th>	<td>	<input type="hidden" name="userID" value="<%=editReservation.getUserID() %>"/><%= editReservation.getUserID()%>			</td></tr>        
	    	<tr><th>Day and time From</th>		<td>	<input type="text" name="dateStart" value="<% 
	    		try {    			
	    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy-H:mm");
	    			//DateFormat df = new SimpleDateFormat("dd.MM.yyyy");	    			
	    			String date = df.format(editReservation.getDateStart());
	    			out.print(date);
	    		} catch (Exception e) {    			    		
	    		}	 	    		
	    		%>" />			</td></tr>
	    	<tr><th>Day and time To</th>		<td>	<input type="text" name="dateEnd" value="<% 
	    		try {    			
	    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy-H:mm");
	    			//DateFormat df = new SimpleDateFormat("dd.MM.yyyy");	    			
	    			String date = df.format(editReservation.getDateEnd());
	    			out.print(date);
	    		} catch (Exception e) {    			    		
	    		}	 	    		
	    		%>" />			</td></tr>
	    			      	
	      	<tr><th>Price (one seat)[Kc]</th>	<td>	<input type="hidden" name="price" value="<%=editReservation.getPrice() %>" /><%= editReservation.getPrice()%>			</td></tr>	      	
	      	<tr><th>People count</th>	<td>	<input type="text" name="count" value="<%= editReservation.getCount()%>" />			</td></tr>
	    		      		 
								     		      	    
	      	<tr><th colspan="2">
	      						<input type="hidden" name="action" value="save" />	      						
	      						<input type="hidden" name="reservationID" value="<%= editReservation.getID() %>" />
	      						<input type="submit" value="Save reservation" />
	      		</th></tr>
		</table>		
    </form>
    <%	if(request.getParameter("problem") != null) { 
    	if(request.getParameter("problem").equals("noSpace")){ %>
    	<p>Sorry, there are not enough seats.</p>
    <% } else if(request.getParameter("problem").equals("revDate")){ %>
    	<p>Sorry, start date can not be after end date.</p>
    <% } 
    	} %>
    </fieldset>

<%	   
    String query = "select from " + Reservation.class.getName() + " where userID == " + userID + " order by dateStart";
    List<Reservation> reservations = (List<Reservation>) pm.newQuery(query).execute();
    if (reservations.isEmpty()) {
%>
<p>The list of reservations is empty</p>
<%
    } else {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy-H:mm");    			    			    				    	
    	String prevDate = "00/00/0000-0:00";		
    	%>
    	<table border="1">
    	<%
        for (Reservation m : reservations) 
        {
        	String date = df.format(m.getDateStart());		    			
    		//out.print(date);    	
        	//if(!date.equals(prevDate)){   
        	if(!(date.substring(0,10)).equals(prevDate.substring(0,10))){
        		prevDate = date;
        		%>        		
        			<tr><th colspan="9"></th></tr>
					<tr><th colspan="9"><%=date.substring(0,10) %></th></tr>
					<tr>
						<th>ID</th>
			    		<th>userID</th>
			        	<th>Date From</th>
			        	<th>Date To</th>			        	
			        	<th>Price</th>			        			        
			        	<th>People count</th>
			        	<th></th>
			        	<th></th>        	                	
			        </tr> 		               	
        		<%        		
        	}
        %>
        	<tr>
        		<td><%= m.getID() %>		</td>
        		<td><%= m.getUserID() %>	</td>        		
        		<td><%= df.format(m.getDateStart()) %>	</td>
        		<td><%= df.format(m.getDateEnd()) %>	</td>
        		<td><%= m.getPrice() %>		</td>
        		<td><%= m.getCount() %>		</td>        		
        		<td>         			
        			<form action="/orderReservation" method="post">
        				<input type="hidden" name="reservationID" value="<%= m.getID() %>">
        				<input type="hidden" name="action" value="delete" />
        				<input type="submit" value="Delete" />
        			</form>        			        	
        		</td>
        		<td>
        			<form action="/orderReservation" method="post">
        				<input type="hidden" name="reservationID" value="<%= m.getID() %>">
        				<input type="hidden" name="action" value="edit" />
        				<input type="submit" value="Edit" />
        			</form>
        		</td>
        	</tr>
        <%
        }
    	%>
    	</table>
    	<%
    }
    }
    pm.close();
%>
	    
  