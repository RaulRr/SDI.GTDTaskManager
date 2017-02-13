<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de tareas</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="table-responsive">
			<h3 align="center">Tareas:</h3>
			<table class="table table-striped table-condensed table-bordered table-hover"
				align="center">
				<tr class="row">
					<th class="col-*-*">Title</th>
					<th class="col-*-*">Comments</th>
					<th class="col-*-*">Created</th>
					<th class="col-*-*">Planned</th>
				</tr>
				<c:forEach var="entry" items="${listaTareas}" varStatus="i">
					<tr class="row" id="item_${i.index}">
						<td class="col-*-*">${entry.title}</td>
						<td class="col-*-*">${entry.comments}</td>
						<td class="col-*-*">${entry.created}</td>
						<td class="col-*-*">${entry.planned}</td>
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