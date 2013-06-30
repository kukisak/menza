<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<title>Restaurant</title>
</head>
<body>
	<form action="/spravujRestaurant" method="post">
		<table>	  
			<tr>  			
		   		<td>
				   	<input type="hidden" name="action" value="get_rezervace" />
			        <input type="submit" value="Get vsech rezervaci" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <form action="/spravujRestaurant" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="description">description</label></td><td><input type="text" name="description" value="popis" /></td>									
			</tr>
			<tr>
				<td>
					<label for="participantsNumber">participantsNumber</label></td><td><input type="text" name="participantsNumber" value="0" />					
				</td>
			</tr>
			<tr>
				<td>
					<label for="reservationDate">reservationDate</label></td><td><input type="text" name="reservationDate" value="24/12/2010" />					
				</td>
			</tr>
			<tr>
				<td>
					<label for="reservationTime">reservationTime</label></td><td><input type="text" name="reservationTime" value="18:00" />					
				</td>
			</tr>
			<tr>
				<td>
					<label for="title">title</label></td><td><input type="text" name="title" value="vecirek" />					
				</td>
			</tr>
			<tr>
				<td>
					<label for="userId">userId</label></td><td><input type="text" name="userId" value="santa.claus@articpole.com" />					
				</td>
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="post_rezervace" />
			        <input type="submit" value="Post novou rezervaci" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <form action="/spravujRestaurant" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="deleteID">deleteID</label></td><td><input type="text" name="deleteID" value="0" /></td>									
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="delete_rezervace" />
			        <input type="submit" value="Delete rezervace podle id" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
</body>
</html>