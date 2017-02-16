<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Registro de Usuario</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<center>
			<h3 align="center">Editar tarea</h3>
		</center>
		<form action="modificarTarea" method="POST">
			<table class="table table-striped table-condensed" style="width: 70%"
				align="center">
				<tr>
					<th>Title:</th>
					<td id="title"><input type="text" style="width:100%; height:30px;"  name="title" value="${titulo}"></td>
				</tr>
				<tr>
					<th>Comments:</th>
					<td id="comment"><textarea rows="6" cols="100">${comentarios}</textarea></td>
				</tr>
				<tr>
					<th>Planned Date:</th>
					<td id="planned"><input type="text" name="planned" value="${entrega}"></td>
				</tr>
				<tr>
					<th>Categories:</th>
					<td align="center">
						<select name="categoria" size="1">
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
						</select>
					</td>
				</tr>
				<tr>
					<td/>
					<td><input type="submit" value="Editar Tarea"></td>
				</tr>
			</table>
		</form>
		<center>
			<a id="paginaAnterior_link_id" href="login.jsp">Volver atrás</a>
			<%@ include file="pieDePagina.jsp"%>
		</center>
	</div>
</body>
</html>