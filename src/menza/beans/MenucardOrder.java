package menza.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * menucard object contains all necessary params plus getters and setters
 */
@PersistenceCapable
public class MenucardOrder {
	/*
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    */
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long orderID;

    @Persistent
    private Date date;						//date when the food was ordered
    
    @Persistent
    private Long userID;					//user ID
    
    @Persistent
    private Long menucardID;				//menu ID       
    
    @Persistent
    private Integer count;					//count of pieces
    
    
    /**
     * construct new object assigning all params
     * @param date
     * @param name
     * @param weight
     * @param price
     * @param type
     */
    public MenucardOrder(Date date, Long userID, Long menucardID, Integer count) {
    	this.date = date;
    	this.userID = userID;
    	this.menucardID = menucardID;
    	this.count = count;
    }
    
    /**
     * block of getters
     * @return
     */    
    public Long getID(){
    	return orderID;
    }    
    
    public Date getDate() {
        return date;
    }

    public Long getUserID() {
    	return userID;
    }
    
    public Long getMenucardID() {
    	return menucardID;
    }
    
    public int getCount() {
    	return count;
    }

    /**
     * block of setters
     * @param date
     */    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserID(Long userID) {
    	this.userID = userID;
    }
    
    public void setMenucardID(Long menucardID) {
    	this.menucardID = menucardID;
    }    
    
    public void setCount(int count) {
    	this.count = count;
    }    
    

}
