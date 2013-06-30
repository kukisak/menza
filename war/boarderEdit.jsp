<%@page import="menza.daos.BoarderDAO"%>
<%@page import="menza.daos.AccountDAO"%>
<%@ page import="menza.helpers.*" %>
<%@ page import="menza.beans.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Date"%>



<%!
	private PersistenceManager pm = null;
	private AccountDAO accountDAO = null;
	private BoarderDAO boarderDAO = null;

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
	protected AccountDAO getAccountDAO()
	{
	    if (accountDAO == null)
	    {
	        accountDAO = new AccountDAO(getPm());
	    }
	    return accountDAO;
	}
	protected BoarderDAO getBoarderDAO()
	{
	    if (boarderDAO == null)
	    {
	        boarderDAO = new BoarderDAO(getPm());
	    }
	    return boarderDAO;
	}
	
%>
<%
	UserService userService = UserServiceFactory.getUserService();
 	User user = userService.getCurrentUser();

    // check user autorization
    // authorization granted for users: BOARDER
    Long currentBoarderId = CurrentUser.getCurrentBoarderId(user, getPm());
    if(currentBoarderId == Long.parseLong("0"))
    {
        // unautorized user
%>
       <jsp:include page="noPermition.jsp"/>
<%
    }
    else
    {
    	// autorized user
		Boarder boarder =  getBoarderDAO().getBoarder(currentBoarderId);
		String checkbox = "checked=\"checked\"";
        String dateOfAboard = "";
        String disabled = "disabled=\"disabled\"";
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            dateOfAboard = df.format(boarder.getDateOfAbord());
	    	checkbox = "";
        } catch (Exception e) {
        }
%>

        <p>Informations about you in application "Luxusní menza":</p>

        <table class="list">
            <tr>
                <th>Email</th>
                <td>
                    <%= user.getEmail()%>
                </td>
            </tr>
	    <tr>
                <th>Name</th>
                <td>
                    <%= boarder.getName()%>
                </td>
            </tr>
            <tr>
                <th>Surname</th>
                <td>
                    <%= boarder.getSurname()%>
                </td>
            </tr>
            <tr>
                <th>Active</th>
                <td>
                    <input type="checkbox" <%= disabled%>  name="check" <%= checkbox%> />
                </td>
            </tr>
<%
        if(checkbox=="") 
        {
%>
            <tr>
                <th>Date of aboard</th>
                <td>
                    <%= dateOfAboard%>
                </td>
            </tr>
<%
        }
%>
        </table>

<%
		List<Account> results = getAccountDAO().getAccounts(boarder.getId());
		if (results == null || results.isEmpty())
		{
%>
	        <p>You have no accounts connected to your border account.</p>
<%           
		}
		else
		{
%>
        <p>The list of account(s) connected to boarder account:</p>
        <table class="list">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Balance</th>
                <th>Bank account</th>
            </tr>
<%
		    Iterator iterator = results.iterator();
			Integer totalBalance = 0;
		    while (iterator.hasNext())
		    {
		        Account account = (Account) iterator.next();
                totalBalance = totalBalance + account.getAccountBalance();
%>
            <tr>
                <td><%= account.getId() %></td>
                <td><%= account.getAccountName() %></td>
                <td><%= account.getAccountBalance() %></td>
                <td><%= account.getBankAccount() %></td>

                <td>
                    <form action="/AccountEdit" method="post">
                        <input type="hidden" name="idAccount" value="<%= account.getId() %>">
                        <input type="hidden" name="active" value="<%= checkbox %>">
                        <input type="hidden" name="action" value="viewAccount" />
                        <input type="submit" value="View & Top up" />
                    </form>
                </td>
                <td>
                    <form action="/BoarderEdit" method="post">
                        <input type="hidden" name="idAccount" value="<%= account.getId() %>">
                        <input type="hidden" name="action" value="deleteAccount" />
                        <input type="submit" <% if(checkbox=="" && account.getAccountBalance() != 0){out.print(disabled);}%> value="Delete" />
                    </form>
                </td>
            </tr>
<%
		    }
%>
             <tr class="result">
                <th>&nbsp;</th>
                <th>Total balance</th>
                <td><%= totalBalance %></td>
            </tr>
        </table>
<%
		}
%>
        <hr /> 
        <fieldset>
		<legend>Create account</legend>
		<form action="/BoarderEdit" method="post">
            <table>
	      	<tr>
                <th>Name</th>
                <td>
                    <input type="text" <% if(checkbox==""){out.print(disabled);}%> name="name" value="My Account" />
                </td>
            </tr>
            <tr>
                <th>Bank account</th>
                <td>
                    <input type="text" <% if(checkbox==""){out.print(disabled);}%> name="account" value="/" />
                </td>
            </tr>
			<tr>
                <th colspan="2">
	                <input type="hidden" name="action" value="newAccount" />
		      		<input type="hidden" name="myId" value="<%= boarder.getId() %>" />
		      		<input type="submit" <% if(checkbox==""){out.print(disabled);}%> value="New Account" />
                </th>
            </tr>
	        </table>
        </form>
        </fieldset>

	<fieldset>
		<legend>Edit boarder</legend>
		<form action="/BoarderEdit" method="post">
            <table>	      	
	      	<tr>                    
                    <th>Name</th>
                    <td>
                        <input type="text" name="name" <% if(checkbox==""){out.print(disabled);}%> value="<%= boarder.getName()%>" />
                    </td>
                </tr>
                <tr>
                    <th>Surname</th>
                    <td>
                        <input type="text" name="surname" <% if(checkbox==""){out.print(disabled);}%> value="<%= boarder.getSurname()%>" />
                    </td>
                </tr>                
	      	<tr>
                    <th colspan="2">                        
                        <input type="hidden" name="action" value="edit" />
	      		<input type="hidden" name="myId" value="<%= boarder.getId() %>" />
	      		<input type="submit" <% if(checkbox==""){out.print(disabled);}%> value="Edit boarder" />
                    </th>
                </tr>
            </table>
        </form>
        </fieldset>
<%
    }
%>        
        
        
        