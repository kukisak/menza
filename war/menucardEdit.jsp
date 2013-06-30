<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="menza.beans.*"%>
<%@ page import="menza.helpers.*"%>
<%@ page import="menza.enums.*"%>
<%@ page import="menza.daos.*"%>


<html>

<%!
	private PersistenceManager pm = null;
	private MenzaUserDAO menzaUserDAO = null;
	private MenucardDAO menucardDAO = null;

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
	protected MenucardDAO getMenucardDAO()
	{
	    if (menucardDAO == null)
	    {
	        menucardDAO = new MenucardDAO(getPm());
	    }
	    return menucardDAO;
	}
	
%>

<%
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	
	//add user right authentication
	MenzaUserType permitedRole = MenzaUserType.CHEF;

    //permition check of rights for this site
    //rights only for permitedRole
    List<MenzaUserType> types = getMenzaUserDAO().getMenzaUserTypes(user);
    if(!types.contains(permitedRole))
    {
%>
<jsp:include page="noPermition.jsp" />
<%
    }
    else
    {
		Menucard editMenucard = null;	
		if(request.getParameter("menucardID") != null)
		{
			Long ID = Long.parseLong(request.getParameter("menucardID"));
		    editMenucard = getMenucardDAO().getMenucard(ID);
		}
		else
		{
		    editMenucard = new Menucard(new Date(), "Basta", 100, 100, FoodType.LUNCH);
		}
%>
<fieldset>
	<legend>Add a new menucard</legend>
	<form action="/editMenucard" method="post">
		<table>
			<tr>
				<th colspan="2"><input type="hidden" name="action" value="new" />
					<input type="submit" value="Create new" /></th>
			</tr>

			</form>
			<form action="/editMenucard" method="post">


				<tr>
					<th>Day</th>
					<td><input type="text" name="date"
						value="<%
				if (editMenucard.getDate() != null)
				{
	    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    			String date = df.format(editMenucard.getDate());
	    			out.print(date);
				}
	    		%>" />
					</td>
				</tr>
				<tr>
					<th>Food name</th>
					<td><input type="text" name="foodname"
						value="<%= editMenucard.getFoodname()%>" /></td>
				</tr>
				<tr>
					<th>Weight(g)</th>
					<td><input type="text" name="weight"
						value="<%= editMenucard.getWeight()%>" /></td>
				</tr>
				<tr>
					<th>Price(Kc)</th>
					<td><input type="text" name="price"
						value="<%= editMenucard.getPrice()%>" /></td>
				</tr>
				<tr>
					<th>Type</th>
					<td><select name="type">
							<option value="starter"
								<% if(editMenucard.getType() == FoodType.STARTER) out.print("selected"); %>>Starter</option>
							<option value="soup"
								<% if(editMenucard.getType() == FoodType.SOUP) out.print("selected"); %>>Soup</option>
							<option value="lunch"
								<% if(editMenucard.getType() == FoodType.LUNCH) out.print("selected"); %>>Lunch</option>
							<option value="salad"
								<% if(editMenucard.getType() == FoodType.SALAD) out.print("selected"); %>>Salad</option>
							<option value="dessert"
								<% if(editMenucard.getType() == FoodType.DESSERT) out.print("selected"); %>>Dessert</option>
							<option value="drink"
								<% if(editMenucard.getType() == FoodType.DRINK) out.print("selected"); %>>Drink</option>
					</select></td>
				</tr>

				<tr>
					<th colspan="2"><input type="hidden" name="action"
						value="save" /> <input type="hidden" name="menucardID"
						value="<%= editMenucard.getID() %>" /> <input type="submit"
						value="Save menucard" /></th>
				</tr>
		</table>
	</form>
</fieldset>

<%	   
			    List<Menucard> menucards = getMenucardDAO().getMenucards();
			    if (menucards == null || menucards.isEmpty()) 
			    {
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
			<form action="/editMenucard" method="post">
				<input type="hidden" name="menucardID" value="<%= m.getID() %>">
				<input type="hidden" name="action" value="delete" /> <input
					type="submit" value="Delete" />
			</form></td>
		<td>
			<form action="/editMenucard" method="post">
				<input type="hidden" name="menucardID" value="<%= m.getID() %>">
				<input type="hidden" name="action" value="edit" /> <input
					type="submit" value="Edit" />
			</form></td>
	</tr>
	<%
			     	} // for menucards
    	%>
</table>
<%
	 			} // else menucards contain some of them
    } // else autorized user
%>