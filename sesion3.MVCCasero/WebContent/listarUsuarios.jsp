<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de usuarios</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="table-responsive">
			<h3 align="center">Usuarios</h3>
			<table class="table table-striped table-condensed table-bordered table-hover"
				align="center">
				<tr class="row">
					<th class="col-*-*">ID</th>
					<th class="col-*-*"><a href="ordenarUsuarios?id=login">LOGIN</a></th>
					<th class="col-*-*"><a href="ordenarUsuarios?id=email">EMAIL</a></th>
					<th class="col-*-*"><a href="ordenarUsuarios?id=status">STATUS</a></th>
				</tr>
				<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
					<tr class="row" id="item_${i.index}">
						<td class="col-*-*">${entry.id}</td>
						<td class="col-*-*">${entry.login}</td>
						<td class="col-*-*">${entry.email}</td>
						
						<c:if test="${entry.isAdmin==true}">
							<td class="alert alert-info"><strong>${entry.status}</strong></td>
							<td class="col*-*"></td>
						</c:if>
						<c:if test="${entry.isAdmin==false}">
							<c:if test="${entry.status == 'ENABLED'}">
								<td class="alert alert-success"><a href="modificarStatus?id=${entry.id}=${entry.login}">${entry.status}</a></td>
							</c:if>
							<c:if test="${entry.status != 'ENABLED'}">
								<td class="alert alert-danger"><a href="modificarStatus?id=${entry.id}=${entry.login}">${entry.status}</a></td>
							</c:if>
							<td class="col*-*"><a href="eliminarUsuario?id=${entry.id}=${entry.login}" 
								onClick="return confirm('¿Eliminar al usuario: ${entry.id}?');">Delete</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<center>
			<a id="paginaAnterior_link_id" href="principalUsuario">Volver atrás</a>
			<%@ include file="pieDePagina.jsp"%>
		</center>
	</div>
</body>
</html>