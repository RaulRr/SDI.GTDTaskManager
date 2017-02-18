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
			<table class="table table-striped table-condensed" style="width: 90%"
				align="center">
				<tr>
					<th><i class="glyphicon glyphicon-user"></i> Login:</th>
					<td id="login"><input type="text" name="login" placeholder="Login"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-envelope"></i> Email:</th>
					<td id="email"><input type="text" name="email" size="15"
						placeholder="email"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-lock"></i> Contraseña:</th>
					<td id="pass"><input type="text" name="pass"
						placeholder="Contraseña"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-lock"></i> Repetir Contraseña:</th>
					<td id="rePass"><input type="text" name="rePass"
						placeholder="Repite la contraseña"></td>
				</tr>
				<tr>
					<td/>
					<td><input type="submit" value="Registrarse"></td>
				</tr>
			</table>
		</form>
		<center>
			<a id="paginaAnterior_link_id" href="login.jsp"><span class="glyphicon glyphicon-circle-arrow-left"></span> Volver atrás</a>
			<%@ include file="pieDePagina.jsp"%>
		</center>
	</div>
</body>
</html>