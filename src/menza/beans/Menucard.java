package menza.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;

import menza.enums.FoodType;

/**
 * menucard object contains all necessary params plus getters and setters
 */
@PersistenceCapable
@XmlRootElement
public class Menucard {
	/*
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    /**/
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long menucardID;

    @Persistent
    private Date date;						//date when the food is cooked
    
	@Persistent
    private String foodname;				//food name
    
    @Persistent
    private Integer weight;					//food weight       
    
    @Persistent
    private Integer price;					//food price
    
    @Persistent
    private FoodType type;					//food type - from enum - SOUP, LUNCH, STARTER, DRINK, SALAD, DESSERT
    
    /**
     * construct new object assigning all params
     * @param date
     * @param name
     * @param weight
     * @param price
     * @param type
     */
    public Menucard(Date date, String foodname, Integer weight, Integer price, FoodType type) {
    	this.date = date;
    	this.foodname = foodname;
    	this.weight = weight;
    	this.price = price;
    	this.type = type;    	
    }
    
    /**
     * construct new object assigning all params
     * @param date
     * @param name
     * @param weight
     * @param price
     * @param type
     */
    public Menucard(String date, String foodname, Integer weight, Integer price, String type) {
    	this.setDate(date);
    	this.setFoodname(foodname);
    	this.setWeight(weight);
    	this.setPrice(price);
    	this.setType(type);    	
    }
    /**
     * default constructor
     */
    public Menucard(){
    	
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
    	return getMenucardID();
//    	return menucardID;
    }    

    public Long getMenucardID() {
		return menucardID;
	}

    public Date getDate() {
        return date;
    }

    public String getFoodname() {
        return foodname;
    }

    public Integer getWeight() {
        return weight;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public FoodType getType() {
    	return type;
    }

	public void setMenucardID(Long menucardID) {
		this.menucardID = menucardID;
	}

	public void setMenucardID(String menucardID) {
		this.menucardID = Long.parseLong(menucardID);
	}
	
    /**
     * block of setters
     * @param date
     */    
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * 
     * @param date as String in format dd-MM-yyyy
     */
    public void setDate(String date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	try {
    		this.date = df.parse(date);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		// set to alternative date when exception occured
    		this.date = new Date();
    	}
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public void setType(FoodType type) {
    	this.type = type;
    }
    
    public void setType(String type) {
    	this.type = FoodType.getType(type);
    }
    
	@Override
    public String toString() {
        return "Menucard{" + 
        	"menucardID=" + String.valueOf(menucardID) + 
        	", date="+ String.valueOf(date) +
        	", foodname="+ String.valueOf(foodname) +
        	", price=" + String.valueOf(price) + 
        	", type=" + String.valueOf(type) + 
        	", weight=" + String.valueOf(weight) + 
        	"}";
    }

}
