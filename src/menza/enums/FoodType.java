package menza.enums;

public enum FoodType {
	STARTER, SOUP, LUNCH, SALAD, DESSERT, DRINK;
	
	public static FoodType getType(String type)
	{	
		if( type.equals("starter") )return STARTER;
		if( type.equals("soup") ) 	return SOUP;
		if( type.equals("lunch") ) 	return LUNCH;
		if( type.equals("salad") ) 	return SALAD;
		if( type.equals("dessert") )return DESSERT;
		if( type.equals("drink") ) 	return DRINK;
		return LUNCH;		
	}
	
}


