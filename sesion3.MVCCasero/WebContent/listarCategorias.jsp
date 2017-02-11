<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de categorías</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3 align="center">Categorías</h3>
			<table class="table table-striped table-condensed table-bordered"
				align="center">
				<thead>
					<tr class="row">
						<th class="col-*-*">ID</th>
						<th class="col-*-*">Nombre</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
						<tr class="row" id="item_${i.index}">
							<td class="col-*-*"><a
								href="mostrarCategoria?id=${entry.id}">${entry.id}</a></td>
							<td class="col-*-*">${entry.name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<a id="paginaAnterior_link_id" href="login.jsp">Volver atrás</a>
		<%@ include file="pieDePagina.jsp"%>
	</div>
</body>
</html>