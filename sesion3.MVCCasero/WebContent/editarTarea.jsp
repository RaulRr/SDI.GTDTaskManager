<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Registro de Usuario</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3 align="center">Editar tarea</h3>
		<form action="modificarTarea" method="POST">
			<table class="table table-striped table-condensed" style="width: 70%">
				<tr>
					<th>Title:</th>
					<td id="title"><input type="text"
						style="width: 100%; height: 30px;" name="title" value="${titulo}"></td>
				</tr>
				<tr>
					<th>Comments:</th>
					<td id="comment"><textarea name="comment" rows="6" cols="100">${comentarios}</textarea></td>
				</tr>
				<tr>
					<th>New Planned Date:</th>
					<td><input type="date" name="date" value="${fecha}">Old
						Plannded Date: ${fecha}</td>
				</tr>
				<tr>
					<th>Categories:</th>
					<td align="center"><select name="category" size="1">
							<option value="none">None</option>
							<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
								<c:choose>
									<c:when test="${entry.id == categoria}">
										<option selected value="${entry.id}">${entry.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${entry.id}">${entry.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td />
					<td align="center"><input type="submit" value="Editar Tarea"></td>
				</tr>
			</table>
		</form>
		<%@ include file="pieDePagina.jsp"%>
		<a id="paginaAnterior_link_id" href="listarTareas"><span
			class="glyphicon glyphicon-circle-arrow-left"></span> Volver atrás</a>

	</div>
</body>
</html>