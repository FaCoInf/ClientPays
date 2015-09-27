<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	
	<select name="country">
   <c:forEach items="${listPays}" var="paysItem">
       <option value="${paysItem.nom}">${paysItem.nom}</option>
   </c:forEach>
</select>

	<h1>${pays.nom}</h1>
	<h2>${pays.nomCapital}</h2>
	<h3>${pays.nbHabitants}</h3>
</body>
</html>
