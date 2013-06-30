package menza.daos;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import menza.beans.Account;

public class AccountDAO extends DAO
{
    public AccountDAO(PersistenceManager pm)
    {
        super(pm);
    }

    public Account getAccount(Long id)
    {
        return null;
    }
    
    public List<Account> getAccounts(Long boarderId)
    {
        List<Account> results = null;
        Query query = getPm().newQuery(Account.class);
        query.setFilter("idBoarder == idBoarderParam");
        query.setOrdering("id");
        query.declareParameters("String idBoarderParam");
        try
        {
            results = (List<Account>) query.execute(boarderId);
        }
        catch (Exception e)
        {
            System.err.println("Error occured in AccountDAO.getAccounts(): " + e.getMessage());
            results = null;
        }
        finally
        {
            query.closeAll();
        }
        return results;

    }
}
