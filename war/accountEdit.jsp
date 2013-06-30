<%@ page import="javax.jdo.Query" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="menza.beans.*" %>
<%@ page import="menza.helpers.*" %>


        

<%
   	User user = UserServiceFactory.getUserService().getCurrentUser();
   	PersistenceManager pm = PMF.getManager();

   	//permition check of rights for this site
   	//rights only for BOARDER, who belongs to this account
   	Long currentBoarderId = menza.helpers.CurrentUser.getCurrentBoarderId(
   			user, pm);
   	Boolean accountOwner = false;
   	Boarder currentBoarder = null;
   	Long currentAccountId = Long.parseLong("0");
   	//getting param from request and finding out, if current boarder/user is owner of account
   	if (request.getParameter("idAccount") != null) 
   	{
   		try {
   			currentAccountId = Long.parseLong(request
   					.getParameter("idAccount"));
   			currentBoarder = pm.getObjectById(Boarder.class,
   					currentBoarderId);
   			accountOwner = CurrentUser.isAccountOwner(currentBoarder,
   					currentAccountId, pm);
   		} catch (Exception e) {
   		}
   	}

   	if (currentBoarderId == Long.parseLong("0") || !accountOwner) 
   	{
%>
       <jsp:include page="noPermition.jsp"/>
<%
	} 
   	else 
   	{
		//righted user can see (the rest of) this site!
%>

<%
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Account account = pm.getObjectById(Account.class, currentAccountId);
		String disabled = "";

		if (currentBoarder.getDateOfAbord() != null) 
		{
			disabled = "disabled=\"disabled\"";
		}
%>

        <p>Informations about your account:</p>

        <table class="list">
            <tr>
                <th>ID</th>
                <td>
                    <%=account.getId()%>
                </td>
            </tr>
	    	<tr>
                <th>Name</th>
                <td>
                    <%=account.getAccountName()%>
                </td>
            </tr>
            <tr>
                <th>Balance</th>
                <td>
                    <%=account.getAccountBalance()%>
                </td>
            </tr>
            <tr>
                <th>Bank Account</th>
                <td>
                    <%=account.getBankAccount()%>
                </td>
            </tr>
            <tr>
                <th>Date of create</th>
                <td>
                    <%=df.format(account.getDateOfCreate())%>
                </td>
            </tr>
            <tr>
                <th>Top up</th>
                <td>
                    <form action="/AccountEdit" method="post">
                        <input type="hidden" name="idAccount" value="<%=currentAccountId%>">
                        <input type="hidden" name="top" value="50">
                        <input type="hidden" name="action" value="top" />
                        <input type="submit" <%=disabled%> value="Top up 50" />
                    </form>
                </td>
                <td>
                    <form action="/AccountEdit" method="post">
                        <input type="hidden" name="idAccount" value="<%=currentAccountId%>">
                        <input type="hidden" name="top" value="100">
                        <input type="hidden" name="action" value="top" />
                        <input type="submit" <%=disabled%> value="Top up 100" />
                    </form>
                </td>
                <td>
                    <form action="/AccountEdit" method="post">
                        <input type="hidden" name="idAccount" value="<%=currentAccountId%>">
                        <input type="hidden" name="top" value="200">
                        <input type="hidden" name="action" value="top" />
                        <input type="submit" <%=disabled%> value="Top up 200" />
                    </form>
                </td>
            </tr>
            <tr>
        </table>

<%
		Query query = pm.newQuery(Account.class);
		query.setFilter("idBoarder == idBoarderParam");
		query.setOrdering("id");
		query.declareParameters("String idBoarderParam");

		try {
			List<Account> results = (List<Account>) query
					.execute(currentBoarderId);
			if (results.iterator().hasNext() && results.size() > 1) {
				//changing view of account
%>
        <fieldset>
           <legend>Change view</legend>
            <form action="/AccountEdit" method="post">
                <table>
                    <tr>
                        <th>
                            To
                        </th>
                        <td>
                            <select name="idAccount">
<%
	for (Account a : results) 
	{
		if (!a.getId().equals(account.getId())) 
		{
%>
                                <option value="<%=a.getId()%>"><%=a.getId() + ":" + a.getAccountName()%></option>
<%
		}
	}
%>
                            </select>
                        </td>
                        <td>
                           <input type="hidden" name="action" value="change" />
                           <input type="submit" value="View" />
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
                        
        <hr />

<%
	//transfering balance
%>
        <fieldset>
           <legend>Transfer balance</legend>
            <form action="/AccountEdit" method="post">
                <table>
                    <tr>
                        <th>
                            To
                        </th>
                        <td>
                            <select name="accountTo">
<%
	for (Account a : results) 
	{
		if (!a.getId().equals(account.getId())) {
%>
                                <option value="<%=a.getId()%>"><%=a.getId() + ":" + a.getAccountName()%></option>
<%
		}
	}
%>
                            </select>
                        </td>
                        <td>
                           <input type="hidden" name="idAccount" value="<%=currentAccountId%>">
                           <input type="hidden" name="action" value="transfer" />
                           <input type="submit" <%if (account.getAccountBalance() <= 0) {
							out.print(disabled);
						}%> value="Transfer" />
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
<%
	}
		} finally {
			query.closeAll();
		}
%>

        <fieldset>
		<legend>Edit account</legend>
	<form action="/AccountEdit" method="post">
            <table>
	      	<tr>
                    <th>Name</th>
                    <td>
                        <input type="text" name="name" <%if (disabled != "") {
					out.print(disabled);
				}%> value="<%=account.getAccountName()%>" />
                    </td>
                </tr>
                <tr>
                    <th>Bank account</th>
                    <td>
                        <input type="text" name="bankAccount" <%if (disabled != "") {
					out.print(disabled);
				}%> value="<%=account.getBankAccount()%>" />
                    </td>
                </tr>
	      	<tr>
                    <th colspan="2">
                        <input type="hidden" name="action" value="edit" />
	      		<input type="hidden" name="idAccount" value="<%=account.getId()%>" />
	      		<input type="submit" <%if (disabled != "") {
					out.print(disabled);
				}%> value="Edit account" />
                    </th>
                </tr>
            </table>
        </form>
        </fieldset>

        <hr />
<%
		Query queryHistory = pm.newQuery(AccountHistory.class);
		queryHistory.setFilter("idAccount == idAccountParam");
		queryHistory.setOrdering("date");
		queryHistory.declareParameters("String idAccountParam");
		String transactionType = "";

		try {
			List<AccountHistory> history = (List<AccountHistory>) queryHistory
					.execute(account.getId());
			if (history.iterator().hasNext()) {
%>
        <p>History of your account</p>
        <table class="list">
            <tr>
                <th>Date</th>
                <th>Mount</th>
                <th>Type</th>
            </tr>
<%
	for (AccountHistory h : history) {
					transactionType = "Payment";
					if (h.getType()) {
						transactionType = "Top up";
					}
%>
            <tr>
                <td><%=df.format(h.getDate())%></td>
                <td><%=h.getMount()%></td>
                <td><%=transactionType%></td>
            </tr>
<%
	}
%>             
        </table>
<%
	} else {
%>
        <p>Your account has no history.</p>
<%
	}
		} finally {
			queryHistory.closeAll();
			pm.close();
		}
%>
        <hr /> 
        
<%
         	}
         %>


   