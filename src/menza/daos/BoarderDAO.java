package menza.daos;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import menza.beans.Boarder;

public class BoarderDAO extends DAO
{
    public BoarderDAO(PersistenceManager pm)
    {
        super(pm);
    }

    public Boarder getBoarder(Long id)
    {
        return getPm().getObjectById(Boarder.class, id);
    }
    
    public List<Boarder> getBoarders()
    {
        List<Boarder> results = null;
        Query query = getPm().newQuery(Boarder.class);
        query.setOrdering("id");
        try 
        {
            results = (List<Boarder>) query.execute();
        }
        catch (Exception e) 
        {
            System.err.println("Error occured in BoarderDAO.getBoarders(): " + e.getMessage());
            results = null;
        }
        finally
        {
            query.closeAll();
        }
        return results;
    }
}
