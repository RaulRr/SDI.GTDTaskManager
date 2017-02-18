<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE>
<html>
<head>
<title>TaskManager - Registro de Usuario</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
			<h3 align="center">Registrar nuevo Usuario</h3>
		<form action="registrarUsuario" method="POST">
			<table class="table table-striped table-condensed" style="width: 90%">
				<tr>
					<th><i class="glyphicon glyphicon-user"></i> Login:</th>
					<td><input id ="login" type="text" name="login" placeholder="Login"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-envelope"></i> Email:</th>
					<td><input id="email" type="text" name="email" size="15"
						placeholder="email"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-lock"></i> Contraseña:</th>
					<td><input id="pass" type="text" name="pass"
						placeholder="Contraseña"></td>
				</tr>
				<tr>
					<th><i class="glyphicon glyphicon-lock"></i> Repetir Contraseña:</th>
					<td><input id="rePass" type="text" name="rePass"
						placeholder="Repite la contraseña"></td>
				</tr>
				<tr>
					<td/>
					<td><input id="registrar_button_id" type="submit" value="Registrarse"></td>
				</tr>
			</table>
		</form>
			<a id="paginaAnterior_link_id" href="login.jsp"><span class="glyphicon glyphicon-circle-arrow-left"></span> Volver atrás</a>
			<%@ include file="pieDePagina.jsp"%>
	</div>
</body>
</html>