<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
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
	//Long userID = Long.parseLong("0");
	MenzaUserType permitedRole = MenzaUserType.MANAGER;

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
    //userID = common.CurrentUser.getCurrentRoleUserId(user, permitedRole, pm);

    //righted user can see (the rest of) this site!
    String query = "select from " + MenzaUser.class.getName() + " order by id";
    List<MenzaUser> users = (List<MenzaUser>) pm.newQuery(query).execute();
    if (users.isEmpty()) {
%>
<p>The list of users of application "Luxusní menza" is cleared or deleted.</p>
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
   
