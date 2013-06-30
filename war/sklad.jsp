<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<title>Skladiste</title>
</head>
<body>
	<form action="/spravujSklad" method="post">
		<table>	  
			<tr>  			
		   		<td>
				   	<input type="hidden" name="action" value="export" />
			        <input type="submit" value="Export stavu skladu" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <form action="/spravujSklad" method="post">
		<table>	   
			<tr> 			
		   		<td>
				   	<input type="hidden" name="action" value="ciselnik_get" />
			        <input type="submit" value="Get vsechny ciselniky na sklade" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <form action="/spravujSklad" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="PLU">PLU</label></td><td><input type="text" name="PLU" value="0" /></td>									
			</tr>
			<tr>
				<td>
					<label for="nazev">nazev</label></td><td><input type="text" name="nazev" value="jmeno" />					
				</td>
			</tr>
			<tr>
				<td>
					<label for="cena">cena</label></td><td><input type="text" name="cena" value="100" />					
				</td>
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="ciselnik_put" />
			        <input type="submit" value="Put novy ciselnik na sklad" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <form action="/spravujSklad" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="PLU">PLU</label></td><td><input type="text" name="PLU" value="0" /></td>									
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="ciselnik_delete" />
			        <input type="submit" value="Delete ciselnik podle PLU" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
    <!-- 
     <form action="/spravujSklad" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="PLU">PLU</label></td><td><input type="text" name="PLU" value="0" /></td>									
			</tr>
			<tr>
				<td>
					<label for="pocet">pocet</label></td><td><input type="text" name=""pocet"" value="10" />					
				</td>
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="put_nasklad" />
			        <input type="submit" value="Naskladnit polozky" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    
     <form action="/spravujSklad" method="post">
		<table border="1">	    		
			<tr>
				<td><label for="PLU">PLU</label></td><td><input type="text" name="PLU" value="0" /></td>									
			</tr>
			<tr>
				<td>
					<label for="pocet">pocet</label></td><td><input type="text" name=""pocet"" value="10" />					
				</td>
			</tr>
			<tr>	
		   		<td colspan="2">
				   	<input type="hidden" name="action" value="put_vysklad" />
			        <input type="submit" value="Vyskladnit polozky" />
	        	</td>
		   	</tr>
	      </table>	                
    </form>
    -->

</body>
</html>