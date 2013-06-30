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
import menza.helpers.PMF;

/**
 * serve to manage data from forms for menucards
 */
public class AccountEditServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AccountEditServlet.class.getName());

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
    	if( action.equals("viewAccount")) viewAccount(req, resp);
        if( action.equals("change")) viewAccount(req, resp);
        if( action.equals("top")) topAccount(req, resp);
        if( action.equals("transfer")) transferAccount(req, resp);
        if( action.equals("edit")) editAccount(req, resp);
    	/**
    	 * returns back to the jsp page
    	 */
        resp.sendRedirect("/index.jsp?site=accountEdit&idAccount="+req.getParameter("idAccount")+"");
    }


    /**
     * redirect on the account page and pass ID in GET - for checking the owner of account
     * @param req
     * @param resp
     * @throws IOException
     */
    private void viewAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	try {
    		resp.sendRedirect("/index.jsp?site=accountEdit&idAccount="+req.getParameter("idAccount")+"");
    	} catch (Exception e) {
    	}
    }

    /**
     * find account in DB and delete it
     * also delete its accounthistory, if exists
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	PersistenceManager pm = PMF.getManager();
    	try {
            Long idAccount = Long.parseLong(req.getParameter("idAccount"));
            Account account = pm.getObjectById(Account.class, idAccount);

            /**
            * delete history of Account
             */
            deleteAccountHistory(pm, idAccount);
            
    	    pm.deletePersistent(account);
    	} catch (Exception e){
                System.out.println("chyba v deleteAccount :" + e);
    	} finally {
            pm.close();
        }
    }

    /**
     * find account in DB and change it by parameters
     * @param req
     * @param resp
     * @throws IOException
     */
    private void editAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PersistenceManager pm = PMF.getManager();
    	try {
            Long idAccount = Long.parseLong(req.getParameter("idAccount"));
            Account account = pm.getObjectById(Account.class, idAccount);
            account.setAccountName(req.getParameter("name"));
            account.setBankAccount(req.getParameter("bankAccount"));
            pm.makePersistent(account);
    	} catch (Exception e) {
    		
    	} finally {
            pm.close();
        }
    }

    /**
     * find account in DB and top up it by increasing its balance
     * @param req
     * @param resp
     * @throws IOException
     */
    private void topAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PersistenceManager pm = PMF.getManager();
        Date date = new Date();			//default value is today
        Boolean type = true;
    	try {
            Long idAccount = Long.parseLong(req.getParameter("idAccount"));
            Account account = pm.getObjectById(Account.class, idAccount);
            Integer mount = Integer.parseInt(req.getParameter("top"));
            AccountHistory history = new AccountHistory(idAccount, mount, date, type);
            account.setAccountBalance(account.getAccountBalance() + mount);
            pm.makePersistent(account);
            pm.makePersistent(history);
    	} catch (Exception e) {

    	} finally {
            pm.close();
        }
    }

    /**
     * find account in DB and transfer its balance to another boarders account
     * @param req
     * @param resp
     * @throws IOException
     */
    private void transferAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PersistenceManager pm = PMF.getManager();
        Date date = new Date();			//default value is today
        Boolean type = true;
    	try {
            Long idAccount = Long.parseLong(req.getParameter("idAccount"));
            Account account = pm.getObjectById(Account.class, idAccount);
            Long idAccountTo = Long.parseLong(req.getParameter("accountTo"));
            Account accountTo = pm.getObjectById(Account.class, idAccountTo);
            Integer mount = account.getAccountBalance();
            AccountHistory history = new AccountHistory(idAccount, mount, date, !type);
            AccountHistory historyTo = new AccountHistory(idAccountTo, mount, date, type);
            account.setAccountBalance(0);
            accountTo.setAccountBalance(accountTo.getAccountBalance() + mount);
            pm.makePersistent(account);
            pm.makePersistent(accountTo);
            pm.makePersistent(history);
            pm.makePersistent(historyTo);
    	} catch (Exception e) {

    	} finally {
            pm.close();
        }
    }

    
    /**
     * support function for deleting History belonged to Account
     * TODO
     * @param PersistenceManager pm
     * @param Long idAccount
     */
    private void deleteAccountHistory(PersistenceManager pm, Long idAccount)
    {
            Query queryHistory = pm.newQuery(AccountHistory.class);
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
            } catch (Exception e){
                //System.out.println("chyba v deleteHistory :" + e);
    	    } finally {
    	        queryHistory.closeAll();
    	    }
    }
    
}