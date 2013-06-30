package menza.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import menza.beans.MenzaUser;
import menza.enums.MenzaUserType;

import com.google.appengine.api.users.User;

public class MenzaUserDAO extends DAO
{
    public MenzaUserDAO(PersistenceManager pm)
    {
        super(pm);
    }
    public List<MenzaUser> getMenzaUsers(Long boarderId)
    {
        List<MenzaUser> results = null;
        Query query = getPm().newQuery(MenzaUser.class);
        query.setFilter("id == idParameter");
        query.declareParameters("String idParameter");
        try 
        {
            results = (List<MenzaUser>) query.execute(boarderId);
        }
        catch (Exception e) 
        {
            System.err.println("Error occured in MenzaUserDAO.getMenzaUsers(): " + e.getMessage());
            results = null;
        }
        finally
        {
            query.closeAll();
        }
        return results;
    }
    
    public List<MenzaUserType> getMenzaUserTypes(User user)
    {
        List<MenzaUserType> results = null;
        if (user == null)
        {
            return results;
        }
        Query query = getPm().newQuery(MenzaUser.class);
        query.setFilter("email == emailParam");
        query.declareParameters("String emailParam");
        String emailParam = user.getEmail().toLowerCase();
        try 
        {
            List<MenzaUser> menzaUsers = (List<MenzaUser>) query.execute(emailParam);
            Iterator iterator = menzaUsers.iterator();
            while (iterator.hasNext()) 
            {
                if (results == null)
                {
                    results = new ArrayList<MenzaUserType>();
                }
                MenzaUser mu = (MenzaUser) iterator.next();
                results.add(mu.getType());
            }
        }
        catch (Exception e) 
        {
            System.err.println("Error occured in MenzaUserDAO.getMenzaUserTypes(): " + e.getMessage());
            results = null;
        }
        finally
        {
            query.closeAll();
        }
        return results;
        
    }
}
