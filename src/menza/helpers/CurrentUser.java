/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package menza.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import menza.beans.Account;
import menza.beans.Boarder;
import menza.beans.MenzaUser;
import menza.enums.MenzaUserType;

import com.google.appengine.api.users.User;

public class CurrentUser 
{
    public static String getCurrentUserBeforeEmail(User user) 
    {
        int i = user.getEmail().indexOf("@");
        String email = user.getEmail().substring(0, i);
        return email;
    }

    public static String getCurrentUserNick(User user) 
    {
        return user.getNickname();
    }

    /**
     * Return List of MenzaUserTypes of currentUser.
     * If return "null", current user is not a user of menza.
     * Can be used for denying access to apps
     * @return List of MenzaUserTypes of currentUser
     */
    public static List<MenzaUserType> getCurrentUserType(User user, PersistenceManager pm) 
    {
        List<MenzaUserType> types = new ArrayList<MenzaUserType>();
        try 
        {
            Query query = pm.newQuery(MenzaUser.class);
            query.setFilter("email == emailParam");
            query.declareParameters("String emailParam");
            String emailParam = user.getEmail().toLowerCase();
            try 
            {                
                List<MenzaUser> results = (List<MenzaUser>) query.execute(emailParam);
                Iterator iterator = results.iterator();
                while (iterator.hasNext()) 
                {
                    MenzaUser mu = (MenzaUser) iterator.next();
                    types.add(mu.getType());
                }
            } 
            finally 
            {
                query.closeAll();
            } 		
        } 
        catch (Exception e) 
        {    		
        } 
        finally 
        {             
        }        
        return types;
    }

    /**
     * Return Id of currentBoarder.
     * If return 0, current user is not a boarder.
     * Can be used for denying access to apps
     * @return Id of currentBoarder
     */
    public static Long getCurrentBoarderId(User user, PersistenceManager pm) 
    {
        long result = Long.parseLong("0");
        List<MenzaUserType> types = getCurrentUserType(user, pm);
        if (!types.contains(MenzaUserType.BOARDER))
        {
            return result;
        }
        else
        {
            try 
            {
                Query query = pm.newQuery(MenzaUser.class);
                String email = user.getEmail();
                query.setFilter("type == " + MenzaUserType.BOARDER  + " && email == \"" + email + "\"");
                try 
                {
                    List<MenzaUser> users = (List<MenzaUser>) pm.newQuery(query).execute();
                    if (users.iterator().hasNext()) 
                    {
                        for (MenzaUser mu : users) 
                        {
                            Long muId = mu.getId();
                            Query queryBoarders = pm.newQuery(Boarder.class);
                            query.setFilter("idUser == " + muId);
                            try 
                            {
                                List<Boarder> boarders = (List<Boarder>) pm.newQuery(queryBoarders).execute();
                                if (boarders.iterator().hasNext()) 
                                {
                                    for (Boarder b : boarders) 
                                    {
                                        result = b.getId();
                                    }
                                }
                            } 
                            finally 
                            {
                                queryBoarders.closeAll();
                            }
                        }
                    }
                } 
                catch (Exception e) 
                {
                } 
                finally 
                {
                    query.closeAll();
                }
            } 
            catch (Exception e) 
            {
            } 
            finally 
            {
                //pm.close();
            }
        }
        return result;
    }

    /**
     * Return Id of currentUser (one of possibles).
     * If return 0, current user is not a user.
     * Can be used for denying access to apps
     * @return Id of currentUser
     */
    public static Long getCurrentUserId(User user, PersistenceManager pm) {
        long result = Long.parseLong("0");

        try {
            Query query = pm.newQuery(MenzaUser.class);
            String email = user.getEmail();
            query.setFilter("email == \"" + email + "\"");
            try {
                List<MenzaUser> users = (List<MenzaUser>) pm.newQuery(query).execute();
                if (users.iterator().hasNext()) {
                    for (MenzaUser mu : users) {
                        result = mu.getId();
                    }
                }
            } catch (Exception e) {
            } finally {
                query.closeAll();
            }
        } catch (Exception e) {
        } finally {
            //pm.close();
        }
        return result;
    }

    /**
     * Return Id of currentUser depending on his Role.
     * If return 0, current user is not a boarder.
     * Can be used for denying access to apps
     * @return Id of currentBoarder
     */
    public static Long getCurrentRoleUserId(User user, MenzaUserType type, PersistenceManager pm) {
        long result = Long.parseLong("0");
        try {
            Query query = pm.newQuery(MenzaUser.class);
            String email = user.getEmail();
            query.setFilter("type == " + type  + " && email == \"" + email + "\"");
            try {
                List<MenzaUser> users = (List<MenzaUser>) pm.newQuery(query).execute();
                if (users.iterator().hasNext()) {
                    for (MenzaUser mu : users) {
                        result = mu.getId();
                    }
                }
            } catch (Exception e) {
            } finally {
                query.closeAll();
            }
        } catch (Exception e) {
        } finally {
            //pm.close();
        }
        return result;
    }

    /**
     * Return if currentBoarder is owner of inserterd account.
     * If return true, currentBoarder is owner of the account.
     * Can be used for denying access to apps
     * @return is account owner?
     */
    public static Boolean isAccountOwner(Boarder boarder, Long accountId, PersistenceManager pm) {
        Boolean result = false;
        Long boarderId = boarder.getId();
        try {
            Account account = pm.getObjectById(Account.class, accountId);
            if (account.getIdBoarder().equals(boarderId)){
                result = true;
            }
        } catch (Exception e) {
        } finally {
            //pm.close();
        }
        return result;
    }

}
