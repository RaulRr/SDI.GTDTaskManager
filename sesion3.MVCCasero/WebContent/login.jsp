<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Inicie sesión</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<form action="validarse" method="post" name="validarse_form_name">
			<center>
				<h1>Inicie sesión</h1>
			</center>
			<hr>
			<br>
			<table align="center" class="table table-condensed" style="width: 50%">
				<tr>
					<th align="left"><span class="glyphicon glyphicon-user"></span> Su identificador de usuario:</th>
					<td colspan="3"><input id="nombreUsuario" type="text" name="nombreUsuario" align="left"
						size="15"></td>
				</tr>
				<tr>
					<th align="left"><span class="glyphicon glyphicon-lock"></span> Su contraseña:</th>
					<td colspan="3"><input id="passUsuario" type="password" name="passUsuario" align="left"
						size="15"></td>
				</tr>
				<tr>
					<td/>
					<td><input id="validar_button_id" type="submit" value="Enviar" />
					<a id="registrar_link_id" href="accesoRegistroUsuario">Registrarse</a>
					</td>
				</tr>
			</table>
		</form>
		<center>
			<%@ include file="pieDePagina.jsp"%>
		</center>
	</div>
</body>
</html>