<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="menza.beans.*" %>
<%@ page import="menza.helpers.*" %>
<%@ page import="menza.enums.*"%>

<html>

<%
	//add user right authentication
	//Long userID = Long.parseLong("0");
	MenzaUserType permitedRole = MenzaUserType.MANAGER;

%>
<%
    User user = UserServiceFactory.getUserService().getCurrentUser();
    PersistenceManager pm = PMF.getManager();

    //permition check of rights for this site
    //rights only for permitedRole
    List<MenzaUserType> types = menza.helpers.CurrentUser.getCurrentUserType(user, pm);
    if(false){
    //if(!types.contains(permitedRole)){
%>
       <jsp:include page="noPermition.jsp"/>
<%
    }else{
    //get userID for current user and his role:
    //userID = common.CurrentUser.getCurrentRoleUserId(user, permitedRole, pm);

    //righted user can see (the rest of) this site!
		MenzaUser editMenzaUser = new MenzaUser("@gmail.com", MenzaUserType.BOARDER, new Date());
		if(request.getParameter("menzauserId") != null){
			try {
				Long Id = Long.parseLong(request.getParameter("menzauserId"));
                                editMenzaUser = pm.getObjectById(MenzaUser.class, Id);
                        } catch (Exception e) {
                        }
		}
	%>
	<fieldset>
		<legend>Add a new menza user</legend>
	<form action="/MenzaUserEdit" method="post">
            <table>
                <tr>
                     <th colspan = "2">
		    	<input type="hidden" name="action" value="new" />
		        <input type="submit" value="Create new" />
                     </th>
                </tr>
            </table>
        </form>
	<form action="/MenzaUserEdit" method="post">
            <table>
	      	<tr>
                    <th>Email</th>
                    <td>
                        <input type="text" name="email" value="<%= editMenzaUser.getEmail()%>" />
                    </td>
                </tr>
	      	<tr>                    
                    <th>Type</th>
                    <td>
                        <select name="type">
                            <option value="boarder"     <% if(editMenzaUser.getType() == MenzaUserType.BOARDER) out.print("selected"); %> >Boarder</option>
                            <option value="issuer"	<% if(editMenzaUser.getType() == MenzaUserType.ISSUER) out.print("selected"); %>>Issuer</option>
                            <option value="chef"	<% if(editMenzaUser.getType() == MenzaUserType.CHEF) out.print("selected"); %>>Chef</option>
                            <option value="manager"	<% if(editMenzaUser.getType() == MenzaUserType.MANAGER) out.print("selected"); %>>Manager</option>
                            <option value="robot"	<% if(editMenzaUser.getType() == MenzaUserType.ROBOT) out.print("selected"); %>>Robot</option>
			</select>
                    </td>
                </tr>

	      	<tr>
                    <th colspan="2">
                        <input type="hidden" name="date" value="<%
                            try {
	    			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	    			    			
	    			String date = df.format(editMenzaUser.getDate());
	    			out.print(date);
                            } catch (Exception e) {
                            }
	    		%>" />
                        <input type="hidden" name="action" value="save" />
	      		<input type="hidden" name="menzauserId" value="<%= editMenzaUser.getId() %>" />
	      		<input type="submit" value="Save menza user" />
                    </th>
                </tr>
            </table>
        </form>
        </fieldset>


<%    
    String query = "select from " + MenzaUser.class.getName() + " order by id";
    List<MenzaUser> users = (List<MenzaUser>) pm.newQuery(query).execute();
    if (users.isEmpty()) {
%>
        <p>The list of users of application "Luxusní menza" is clear or deleted.</p>
<%
    } else {        
%>
        <p>The list of users of application "Luxusní menza":</p>
        <table class="list">
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Type</th>
                <th>Date of add</th>
            </tr>
<%
        for (MenzaUser u: users) {
            try {
	    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    	String userDate = df.format(u.getDate());

%>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getType() %></td>
                <td>
<%              
                out.print(userDate);
            } catch (Exception e) {
            }
%>
                </td>
                <td>
                    <form action="/MenzaUserEdit" method="post">
                        <input type="hidden" name="menzauserId" value="<%= u.getId() %>">
                        <input type="hidden" name="action" value="delete" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
                <td>
                    <form action="/MenzaUserEdit" method="post">
                        <input type="hidden" name="menzauserId" value="<%= u.getId() %>">
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


   
