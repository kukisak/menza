package menza.daos;

import javax.jdo.PersistenceManager;

public abstract class DAO
{
    private PersistenceManager pm = null;
    
    protected PersistenceManager getPm()
    {
        return pm;
    }
    public DAO(PersistenceManager pm)
    {
        this.pm = pm;
    }

}
