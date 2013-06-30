package menza.helpers;

import javax.jdo.PersistenceManager;

public interface IVisitor
{
    public void visit(PersistenceManager pm) throws Exception;
}
