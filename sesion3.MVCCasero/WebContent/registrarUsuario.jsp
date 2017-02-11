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
			<h3 align="center">Registrar nuevo Usuario</h3>
		</center>
		<form action="registrarUsuario" method="POST">
			<table class="table table-striped table-condensed" style="width: 50%"
				align="center">
				<tr>
					<th>Login:</th>
					<td id="login"><input type="text" name="login" value="Login"></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td id="email"><input type="text" name="email" size="15"
						value="email"></td>
				</tr>
				<tr>
					<th>Contraseña:</th>
					<td id="pass"><input type="text" name="pass"
						value="Contraseña"></td>
				</tr>
				<tr>
					<th>Repetir Contraseña:</th>
					<td id="rePass"><input type="text" name="rePass"
						value="Repite la contraseña"></td>
				</tr>
				<tr>
					<td/>
					<td><input type="submit" value="Registrarse"></td>
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