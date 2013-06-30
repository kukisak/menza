package menza.helpers;

import javax.jdo.PersistenceManager;

public class DBService implements IService
{

    @Override
    public void accept(IVisitor visitor)
    {
        PersistenceManager pm = null;
        try
        {
            pm = PMF.getManager();
            visitor.visit(pm);
        }
        catch (Exception e) 
        {
            System.err.println("Error occured in visitor " + visitor.getClass() + " with exception: " + e);
        }
        finally
        {
            pm.close();
        }
    }

}
