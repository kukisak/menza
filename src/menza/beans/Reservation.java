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
public class Reservation {
	/*
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    */
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long reservationID;

    @Persistent
    private Long userID;					//userID			
    
    @Persistent
    private Date dateStart;					//date when the table is reserved
    
    @Persistent
    private Date dateEnd;					//date when the table is not reserved any more             
    
    @Persistent
    private Integer price;					//price per hour
    
    @Persistent
    private Integer count;					//people count
        
    private final static int MAX_COUNT = 50;
    
    /**
     * construct new object assigning all params
     * @param date
     * @param name
     * @param weight
     * @param price
     * @param type
     */
    public Reservation(Long userID, Date dateStart, Date dateEnd, Integer price, Integer count) {
    	this.userID = userID;
    	this.dateStart = dateStart;
    	this.dateEnd = dateEnd;    	
    	this.price = price;  
    	this.count = count;
    }
    
    /**
     * block of getters
     * @return
     */
    /*
    public Key getKey() {
        return key;
    }
    */
    public Long getID(){
    	return reservationID;
    }
    
    public Long getUserID(){
    	return userID;
    }   
    
    public Date getDateStart() {
        return dateStart;
    }
    
    public Date getDateEnd() {
        return dateEnd;
    }
    
    public Integer getPrice() {
        return price;
    }  
    
    public Integer getCount() {
        return count;
    }  
    
    public static int getMaxCount() {
    	return MAX_COUNT;
    }

    /**
     * block of setters
     * @param date
     */    
    
    public void setUserID(Long id) {
        this.userID = id;
    }
    
    public void setDateStart(Date date) {
        this.dateStart = date;
    }
    
    public void setDateEnd(Date date) {
        this.dateEnd = date;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
        

}
