<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<title>Luxury menza | Reservation system for hungry students | MI-MDW Semestral project</title>
</head>
<body>
	<div id="page">
		<div id="header">
			<jsp:include page="login.jsp" />
		</div>
		<div id="logo">
			<h1>
				<strong>Luxury menza!</strong>
			</h1>
		</div>
		<div id="main">
			<div id="content">

				<%
    String siteNew = "home.jsp";
    String siteParam = request.getParameter("site");
    if (siteParam != null)
    {
        siteParam = siteParam.toString().toLowerCase();
    }
    else
    {
        siteParam = "";
    }
    
    if (siteParam.contains("accountedit")) 			siteNew = "accountEdit.jsp";
    if (siteParam.contains("boarderedit")) 			siteNew = "boarderEdit.jsp";
    if (siteParam.contains("boardermanageredit")) 	siteNew = "boarderManagerEdit.jsp";
    if (siteParam.contains("identity")) 			siteNew = "identity.jsp";
    if (siteParam.contains("menucardedit")) 		siteNew = "menucardEdit.jsp";
    if (siteParam.contains("menucardorder")) 		siteNew = "menucardOrder.jsp";
    if (siteParam.contains("reservationedit")) 		siteNew = "reservationEdit.jsp";
    if (siteParam.contains("reservationorder")) 	siteNew = "reservationOrder.jsp";
    if (siteParam.contains("useredit")) 			siteNew = "userEdit.jsp";
    if (siteParam.contains("userview")) 			siteNew = "userView.jsp";
    if (siteParam.contains("sklad")) 				siteNew = "sklad.jsp";
    if (siteParam.contains("restaurant")) 			siteNew = "restaurant.jsp";
%>
				<jsp:include page="<%= siteNew %>" />
			</div>
			<div id="side">
				<jsp:include page="menu.jsp" />
			</div>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>
