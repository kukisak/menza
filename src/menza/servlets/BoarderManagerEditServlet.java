package menza.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menza.beans.Account;
import menza.beans.AccountHistory;
import menza.beans.Boarder;
import menza.beans.MenzaUser;
import menza.helpers.PMF;


/**
 * serve to manage data from forms for menucards
 */
public class BoarderManagerEditServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(BoarderManagerEditServlet.class.getName());

    /**
     * handle with POST data
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

    	/**
    	 * detects current action based on action param
    	 */
    	String action = req.getParameter("action");    	
    	if( action.equals("delete")) deleteBoarder(req, resp);
    	if( action.equals("active")) activeBoarder(req, resp);
    	if( action.equals("aboard")) aboardBoarder(req, resp);

    	/**
    	 * returns back to the jsp page
    	 */
        resp.sendRedirect("/index.jsp?site=boarderManagerEdit");
    }

    
    /**
     * find boarder in DB and delete it
     * also delete his accounts and accounthistory, if exist
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteBoarder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	PersistenceManager pm = PMF.getManager();
        PersistenceManager pmUser = PMF.getManager();
    	try {
            Long idBoarder = Long.parseLong(req.getParameter("idBoarder"));
            Boarder boarder = pm.getObjectById(Boarder.class, idBoarder);
            Long idUser = Long.parseLong(req.getParameter("idUser"));
            MenzaUser menzaUser = pmUser.getObjectById(MenzaUser.class, idUser);

            /**
            * delete all related Accounts
             */
            deleteAllBoarderAccounts(pm, idBoarder);
            
    	    pm.deletePersistent(boarder);
            pmUser.deletePersistent(menzaUser);
    	} catch (Exception e) {
    		
    	} finally {
            pm.close();
        }
    }

    /**
     * find boarder in DB and active him by deleting his date of aboarding aplication
     * @param req
     * @param resp
     * @throws IOException
     */
    private void activeBoarder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PersistenceManager pm = PMF.getManager();
    	try {
            Long idBoarder = Long.parseLong(req.getParameter("idBoarder"));
            Boarder boarder = pm.getObjectById(Boarder.class, idBoarder);
            boarder.setDateOfAbord(null);
            pm.makePersistent(boarder);
    	} catch (Exception e) {
    		
    	} finally {
            pm.close();
        }
    }

    /**
     * find boarder in DB and active him by deleting his date of aboarding aplication
     * @param req
     * @param resp
     * @throws IOException
     */
    private void aboardBoarder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PersistenceManager pm = PMF.getManager();
    	try {
            Long idBoarder = Long.parseLong(req.getParameter("idBoarder"));
            Boarder boarder = pm.getObjectById(Boarder.class, idBoarder);
            Date date = new Date();
            boarder.setDateOfAbord(date);
            pm.makePersistent(boarder);
    	} catch (Exception e) {
    	} finally {
            pm.close();
        }
    }
    
    /**
     * support function for deleting all boarders accounts
     * TODO
     * @param PersistenceManager pm
     * @param Long idBoarder
     */
    public void deleteAllBoarderAccounts(PersistenceManager pm, Long idBoarder)
    {
            Query queryAccount = pm.newQuery(Account.class);
    	    queryAccount.setFilter("idBoarder == idBoarderParam");
    	    queryAccount.declareParameters("Long idBoarderParam");
    	    try {
    	        List<Account> accounts = (List<Account>) queryAccount.execute(idBoarder);
    	        if (accounts.iterator().hasNext()) {
    	            for (Account a : accounts) {
                        deleteAllBoarderAccountsHistory(pm, a.getId());
    	            	pm.deletePersistent(a);
    	            }
    	        } else {
    	            // ... no results ... nothing to delete
    	        }
    	    } finally {
    	        queryAccount.closeAll();
    	    }
    }

    /**
     * support function for deleting History belonged to Account
     * TODO
     * @param PersistenceManager pm
     * @param Long idAccount
     */
    private void deleteAllBoarderAccountsHistory(PersistenceManager pm, Long idAccount)
    {
            Query queryHistory = pm.newQuery(Account.class);
    	    queryHistory.setFilter("idAccount == idAccountParam");
    	    queryHistory.declareParameters("Long idAccountParam");
    	    try {
    	        List<AccountHistory> history = (List<AccountHistory>) queryHistory.execute(idAccount);
    	        if (history.iterator().hasNext()) {
    	            for (AccountHistory h : history) {
                        //need to add deleting of AccountHistory in tables!
    	            	pm.deletePersistent(h);
    	            }
    	        } else {
    	            // ... no results ... nothing to delete
    	        }
    	    } finally {
    	        queryHistory.closeAll();
    	    }
    }
    
}