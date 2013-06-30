package menza.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
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
import menza.helpers.PMF;


/**
 * serve to manage data from forms for menucards
 */
public class BoarderEditServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1353164890530881380L;
    private static final Logger log = Logger.getLogger(BoarderEditServlet.class.getName());

    /**
     * handle with POST data
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        /**
         * detects current action based on action param
         */
        String action = req.getParameter("action");    	
        if( action.equals("edit")) editBoarder(req, resp);
        if( action.equals("newAccount")) saveAccount(req, resp);
        if( action.equals("deleteAccount")) deleteAccount(req, resp);
        /**
         * returns back to the jsp page
         */
        resp.sendRedirect("/index.jsp?site=boarderEdit");
    }


    /**
     * find boarder in DB and delete it
     * also delete his accounts and accounthistory, if exist
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
     * find boarder in DB and active him by deleting his date of aboarding aplication
     * @param req
     * @param resp
     * @throws IOException
     */
    private void editBoarder(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        PersistenceManager pm = PMF.getManager();
        try 
        {
            Long idBoarder = Long.parseLong(req.getParameter("myId"));
            Boarder boarder = pm.getObjectById(Boarder.class, idBoarder);
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            boarder.setName(name);
            boarder.setSurname(surname);
            pm.makePersistent(boarder);
        } 
        catch (Exception e) 
        {

        } 
        finally 
        {
            pm.close();
        }
    }

    private void saveAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        PersistenceManager pm = PMF.getManager();
        try 
        {
            /**
             * get all params
             */
            Date date = new Date();			//default value is today
            String name = req.getParameter("name");
            String bankAccount = req.getParameter("account");
            Integer balance = 0;
            Long id = Long.parseLong(req.getParameter("myId"));

            /**
             * create new menuUser in case of null menzauserId from the form
             */
            //if bankAccount is not equal to "/" (not fully inserted bankAccount) continue saving
            if(!bankAccount.equals("/"))
            {
                Account account = new Account(id, name, balance, bankAccount, date);
                pm.makePersistent(account);                        

            }
            else 
            {
                //if bankAccount is equal to "/", redirect to editing/creating page
                resp.sendRedirect("/index.jsp?site=boarderEdit");
            }

        } 
        catch (Exception e) 
        {
        } 
        finally 
        {
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
            Iterator iterator = history.iterator();
            while (iterator.hasNext()) 
            {
                AccountHistory accHistory = (AccountHistory) iterator.next();
                pm.deletePersistent(accHistory);
            }
        } catch (Exception e){
            System.err.println("Error in delete account history :" + e);
        } finally {
            queryHistory.closeAll();
        }
    }

}