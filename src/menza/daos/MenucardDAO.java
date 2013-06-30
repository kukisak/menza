package menza.daos;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import menza.beans.Menucard;
import menza.helpers.PMF;


public class MenucardDAO extends DAO {

    public MenucardDAO()
    {
        super(PMF.getManager());
    }
    
    public MenucardDAO(PersistenceManager pm)
    {
        super(pm);
    }

    public Menucard getMenucard(Long id)
    {
        return getPm().getObjectById(Menucard.class, id);
    }
    
    public List<Menucard> getMenucards()
    {
        List<Menucard> results = null;
        String query = "select from " + Menucard.class.getName() + " order by date";
        try 
        {
            results = (List<Menucard>)getPm().newQuery(query).execute();
        } 
        catch (Exception e)
        {
            System.err.println("Error occured in MenucardDAO.getMenucards(): " + e.getMessage());
            results = null;
        }
        return results;
    }
    
	public String save(Menucard menu){
		PersistenceManager pm = PMF.getManager();
		String status;
		try {
			pm.makePersistent(menu);
			status = "Menu was succesfully created.";
		} catch (Exception e){
			status = "Exception occured durring creating new menu." + e.getMessage();
		} finally {
			pm.close();
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public List<Menucard> getAll(){
		PersistenceManager pm = PMF.getManager();
		List<Menucard> menucards = new ArrayList<Menucard>();
		String query = "select from " + Menucard.class.getName() + " order by date";
		try {
		    menucards = (List<Menucard>)pm.newQuery(query).execute();
		} catch (Exception e){
			System.out.println(e.getMessage());			
		}
		return menucards;
	}
	
	@SuppressWarnings("unchecked")
	public String changeMenucard(Menucard menu){
		PersistenceManager pm = PMF.getManager();
		
		Query query = pm.newQuery(Menucard.class);
	    query.setFilter("menucardID == menucardID_param");
	    query.declareParameters("String menucardID_param");
	    List<Menucard> list = null;
	    try {
	    	list = (List<Menucard>)query.execute(menu.getMenucardID());
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    }
	    if (list == null || list.isEmpty()){
	    	return "No such ID="+menu.getID().toString()+" found in data store.";
	    }
	    for (Menucard val : list)
	    {
//			System.out.println(val.toString());
	    	Object serverMenuID = pm.getObjectId(val);
			Menucard serverMenu = (Menucard) pm.getObjectById(serverMenuID, true);

	    	serverMenu.setDate(menu.getDate());
	    	serverMenu.setFoodname(menu.getFoodname());
	    	serverMenu.setPrice(menu.getPrice());
	    	serverMenu.setType(menu.getType());
	    	serverMenu.setWeight(menu.getWeight());

	    	pm.makePersistent(serverMenu);
	    	pm.evict(serverMenu);
	    }
	    pm.close();
	    return "Menu with ID="+menu.getMenucardID().toString()+" was succesfully changed.";
	    
    }
	    
	    
	@SuppressWarnings("unchecked")
	public String deleteMenucard(Menucard menu){
		PersistenceManager pm = PMF.getManager();
		
		Query query = pm.newQuery(Menucard.class);
	    query.setFilter("menucardID == menucardID_param");
	    query.declareParameters("String menucardID_param");
	    List<Menucard> list = null;
	    try {
	    	list = (List<Menucard>)query.execute(menu.getMenucardID());
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    }
	    if (list == null || list.isEmpty()){
	    	return "No such ID="+menu.getID().toString()+" found in data store.";
	    }
	    for (Menucard val : list)
	    {
			System.out.println(val.toString());
	    	Object serverMenuID = pm.getObjectId(val);
			Menucard serverMenu = (Menucard) pm.getObjectById(serverMenuID, true);
	    	pm.deletePersistent(serverMenu);
	    	pm.evict(serverMenu);
	    }
	    pm.close();
	    return "Menu with ID="+menu.getMenucardID().toString()+" was succesfully deleted.";
	}
}
