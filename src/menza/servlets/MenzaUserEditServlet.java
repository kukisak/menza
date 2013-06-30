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
import menza.beans.MenzaUser;
import menza.enums.MenzaUserType;
import menza.helpers.PMF;


/**
 * serve to manage data from forms for menucards
 */
public class MenzaUserEditServlet extends HttpServlet 
{
    private static final long serialVersionUID = -4727313483939198089L;
    private static final Logger log = Logger.getLogger(MenzaUserEditServlet.class.getName());

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
        if( action.equals("save")) saveMenzaUser(req, resp);
        if( action.equals("delete")) deleteMenzaUser(req, resp);
        if( action.equals("edit")) editMenzaUser(req, resp);
        if( action.equals("new")) newMenzaUser(req, resp);    	

        /**
         * returns back to the jsp page
         */
        resp.sendRedirect("/index.jsp?site=userEdit");
    }

    /**
     * serves for saving new/edited menucard
     * @param req
     * @param resp
     * @throws IOException
     */
    private void saveMenzaUser(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        PersistenceManager pm = PMF.getManager();
        try {
            /**
             * get all params
             */

            Date date = new Date();			//default value is today    		
            String email = req.getParameter("email");                
            MenzaUserType type = getMenzaUserType(req.getParameter("type"));

            /**
             * create new menuUser in case of null menzauserId from the form
             */
            if(req.getParameter("menzauserId").equals("null"))
            {
                //if email is not equal to "@gmail.com" - not fully inserted email address, continue saving
                if(!req.getParameter("email").equals("@gmail.com"))
                {
                    MenzaUser menzaUser = new MenzaUser(email, type, date);                        
                    pm.makePersistent(menzaUser);
                    Long idUser = menzaUser.getId();

                    //if type is boarder, create also new boarder with default values
                    if(type.equals(MenzaUserType.BOARDER))
                    {
                        Boarder newBoarder = new Boarder(idUser, "name", "surname", null);
                        pm.makePersistent(newBoarder);
                    }

                    //if email is equal to "@gmail.com", redirect to editing/creating page
                }
                else 
                {
                    resp.sendRedirect("/index.jsp?site=userEdit");
                }
            }
            else 
            {
                /**
                 * edit the old one
                 */
                Long ID = Long.parseLong(req.getParameter("menzauserId"));
                MenzaUser menzaUser = pm.getObjectById(MenzaUser.class, ID);

                menzaUser.setEmail(email);
                menzaUser.setType(type);
                menzaUser.setDate(date);
                pm.makePersistent(menzaUser);
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
     * find menzaUser in DB and delete it
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteMenzaUser(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        PersistenceManager pm = PMF.getManager();
        try 
        {
            Long ID = Long.parseLong(req.getParameter("menzauserId"));
            MenzaUser menzaUser = pm.getObjectById(MenzaUser.class, ID);

            /**
             * delete all related boarders!
             */
            Query query = pm.newQuery(Boarder.class);
            query.setFilter("idUser == menzaUserIdParam");
            query.declareParameters("Long menzaUserIdParam");
            try 
            {
                List<Boarder> boarders = (List<Boarder>) query.execute(menzaUser.getId());
                Iterator iterator = boarders.iterator();
                while (iterator.hasNext()) 
                {
                    Boarder boarder = (Boarder) iterator.next();
                    deleteAllBoarderAccounts(pm, boarder.getId());
                    pm.deletePersistent(boarder);
                }
            } 
            finally 
            {
                query.closeAll();
            }
            pm.deletePersistent(menzaUser);
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
     * redirect on the menzaUser page and pass Id in GET - will be shown in the form
     * @param req
     * @param resp
     * @throws IOException
     */
    private void editMenzaUser(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try 
        {
            resp.sendRedirect("/index.jsp?site=userEdit&menzauserId="+req.getParameter("menzauserId")+"");
        } 
        catch (Exception e) 
        {                
            //resp.sendRedirect("/userEdit.jsp");
        }
    }

    /**
     * redirect on the menzaUser page without any params = new menzaUser
     */
    private void newMenzaUser(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        try 
        {
            resp.sendRedirect("/index.jsp?site=userEdit");
        }
        catch (Exception e) 
        {
            //resp.sendRedirect("/userEdit.jsp");
        }
    }

    /**
     * support function for enum parsing
     * TODO move to the enum class
     * @param type
     * @return
     */
    private MenzaUserType getMenzaUserType(String type)
    {
        type = type.toLowerCase();
        if( type.equals("manager") )
            return MenzaUserType.MANAGER;
        if( type.equals("boarder") )
            return MenzaUserType.BOARDER;
        if( type.equals("robot") )
            return MenzaUserType.ROBOT;
        if( type.equals("chef") )
            return MenzaUserType.CHEF;
        if( type.equals("issuer") )
            return MenzaUserType.ISSUER;
        return MenzaUserType.BOARDER;
    }

    /**
     * support function for deleting all boarders accounts
     * TODO 
     * @param PersistenceManager pm
     * @param Long idBoarder
     */
    private void deleteAllBoarderAccounts(PersistenceManager pm, Long idBoarder)
    {
        Query queryAccount = pm.newQuery(Account.class);
        queryAccount.setFilter("idBoarder == idBoarderParam");
        queryAccount.declareParameters("Long idBoarderParam");
        try 
        {
            List<Account> accounts = (List<Account>) queryAccount.execute(idBoarder);
            Iterator iterator = accounts.iterator();
            while (iterator.hasNext()) 
            {
                Account acc = (Account) iterator.next();
                deleteAllBoarderAccountsHistory(pm, acc.getId());
                pm.deletePersistent(acc);
            }
        } 
        finally 
        {
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
        try 
        {
            List<AccountHistory> history = (List<AccountHistory>) queryHistory.execute(idAccount);
            Iterator iterator = history.iterator();
            while (iterator.hasNext()) 
            {
                AccountHistory accHistory = (AccountHistory) iterator.next();
                pm.deletePersistent(accHistory);
            }
        } 
        finally 
        {
            queryHistory.closeAll();
        }
    }

}

