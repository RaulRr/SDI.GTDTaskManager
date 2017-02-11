<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<i>Iniciaste sesión el <fmt:formatDate
				pattern="dd-MM-yyyy' a las 'HH:mm"
				value="${sessionScope.fechaInicioSesion}" /> (usuario número
			${contador})
		</i> <br /> <br />
		<center>
			<h3 align="center"><jsp:getProperty property="login" name="user" /></h3>
		</center>
		<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
		<table class="table table-striped table-condensed">
			<tr>
				<td>Id:</td>
				<td id="id"><jsp:getProperty property="id" name="user" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td id="email"><form action="modificarDatos" method="POST">
						<input type="text" name="email" size="15"
							value="<jsp:getProperty property="email" name="user"/>">
						<input type="submit" value="Modificar">
					</form></td>
			</tr>
			<tr>
				<td>Es administrador:</td>
				<td id="isAdmin"><jsp:getProperty property="isAdmin"
						name="user" /></td>
			</tr>
			<tr>
				<td>Login:</td>
				<td id="login"><jsp:getProperty property="login" name="user" /></td>
			</tr>
			<tr>
				<td>Estado:</td>
				<td id="status"><jsp:getProperty property="status" name="user" /></td>
			</tr>
		</table>
		<br />
		<c:if test="${user.getIsAdmin()==true}">
			<div class="alert alert-info">
				<a id="listarUsuarios_link_id" href="listarUsuarios">Listar
					Usuarios</a>
			</div>
		</c:if>
		<br />
		<center>
			<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>
			<%@ include file="pieDePagina.jsp"%>
		</center>
	</div>
</body>
</html>