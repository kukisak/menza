<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.Query"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="menza.beans.*"%>
<%@ page import="menza.daos.*" %>
<%@ page import="menza.helpers.*"%>
<%@ page import="menza.enums.*"%>

<%!
	private PersistenceManager pm = null;
	private AccountDAO accountDAO = null;
	private BoarderDAO boarderDAO = null;
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
	protected synchronized AccountDAO getAccountDAO()
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

	// check user autorization
    // authorization granted for users: MANAGER, ROBOT
    List<MenzaUserType> types = menza.helpers.CurrentUser.getCurrentUserType(user, getPm());
    if (!(types.contains(MenzaUserType.MANAGER) || types.contains(MenzaUserType.ROBOT))) 
    {
     	// unautorized user
%>
		<jsp:include page="noPermition.jsp" />
<%
    } 
    else 
    {
        // authorized user
        List<Boarder> boarders = getBoarderDAO().getBoarders();
    	if (boarders == null || boarders.isEmpty())
    	{
%>
			<p>The list of BOARDERS is cleared or deleted.</p>
<%
    	} 
    	else 
    	{
%>
			<p>The list of BOARDERS:</p>
			<table class="list">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Surname</th>
					<th>Email</th>
					<th>Date of add</th>
					<th>Date of abord</th>
					<th>UserID</th>
					<th>Accounts</th>
					<th>Balance</th>
					<th></th>
					<th></th>
				</tr>
<%
			//declaration of variables useful for queries
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String email = "";
            String dateAdd = "";
            String dateAboard = "";
            String postAboard = "Aboard"; //to define post function and value of button for boarder aboarding
            String accountNames = "";
            Integer accountBalance = 0;
            for (Boarder b : boarders) {
                // init variables for next turn
                email = "";
                dateAdd = "";
                dateAboard = "";
                postAboard = "Aboard";
                accountNames = "";
                accountBalance = 0;

                // Find email and date of addition from MenzaUser by idUser from Boarders
                List<MenzaUser> menzaUsers = getMenzaUserDAO().getMenzaUsers(b.getIdUser());
                if (menzaUsers == null || menzaUsers.isEmpty())
                {
                    email = "N/A";
                    dateAdd = "N/A";
                }
                else
                {
                    MenzaUser mUser = menzaUsers.get(0);
                    email = mUser.getEmail();
                    dateAdd = df.format(mUser.getDate());
                }

                // Find bank Accounts of MmenzaUser by his ID from Boarders
                List<Account> accounts = getAccountDAO().getAccounts(b.getId());
                if (accounts == null || accounts.isEmpty())
                {
                       accountNames = "NO ACCOUNTS";	                        
                }
                else
                {
                    Iterator iterator = accounts.iterator();
                    while (iterator.hasNext())
                    {
                        Account acc = (Account) iterator.next();
                        accountNames += acc.getId().toString() + ":" + acc.getAccountName().substring(0, 5) + ";\n";
                        accountBalance += acc.getAccountBalance();
                    }
                }
                // Format date of abort
				if (b.getDateOfAbord() != null)
				{
				    dateAboard = df.format(b.getDateOfAbord());
				    postAboard = "Active";
				}

%>
				<tr>
					<td><%=b.getId()%></td>
					<td><%=b.getName()%></td>
					<td><%=b.getSurname()%></td>
					<td><%=email%></td>
					<td><%=dateAdd%></td>
					<td><%=dateAboard%></td>
					<td><%=b.getIdUser()%></td>
					<td><%=accountNames%></td>
					<td><%=accountBalance%></td>
					<td>
						<form action="/BoarderManagerEdit" method="post">
							<input type="hidden" name="idBoarder" value="<%=b.getId()%>" /> <input
								type="hidden" name="action" value="<%=postAboard.toLowerCase()%>" />
							<input type="submit" value="<%=postAboard%>" />
						</form></td>
					<td>
						<form action="/BoarderManagerEdit" method="post">
							<input type="hidden" name="idBoarder" value="<%=b.getId()%>" /> <input
								type="hidden" name="idUser" value="<%=b.getIdUser()%>" /> <input
								type="hidden" name="action" value="delete" /> <input type="submit"
								value="Delete boarder" />
						</form></td>
				</tr>
<%
	    	} // End of for boarders
%>
			</table>
<%
	    } // End of else the list of boarders
    } // End of else Authorized users
%>







