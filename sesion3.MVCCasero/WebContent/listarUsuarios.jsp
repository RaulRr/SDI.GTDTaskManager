<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de usuarios</title>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Email</th>
				<th>Status</th>
			</tr>
		<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a href="mostrarCategoria?id=${entry.id}">${entry.id}</a></td>
				<td>${entry.login}</td>
				<td>${entry.email}</td>
				<td>${entry.status}</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>