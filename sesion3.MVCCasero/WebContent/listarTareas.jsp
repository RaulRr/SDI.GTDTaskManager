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
	<center>
		<%@ include file="pieDePagina.jsp"%>
	</center>
	<div class="container">
		<h3 align="center">Tareas: ${categoria}</h3>
		<div class="container">
			<form action="añadirTarea" method="POST">
				<table class="table table-bordered table-condensed"
					style="width: 54%" align="center">
					<tr>
						<th>Nueva Tarea:</th>
						<td id="nuevaTarea"><input type="text" name="nuevaTarea"
							value="nombre" style="width: 95%"></td>
						<td><input type="submit" value="NuevaTarea" style="width: 90%"></td>

					</tr>
				</table>
			</form>
		</div>
		<div class="row">
			<div class="col-*-6">
				<table
					class="table table-striped table-condensed table-bordered table-hover"
					align="center" style="width: 20%; float: left">
					<tr class="row">
						<th class="col-*-*">Categorias:</th>
					</tr>
					<tr class="row" id="item_Todas">
						<td class="col-*-*"><a href="listarTareas">Todas</a></td>
					</tr>
					<tr class="row" id="item_Inbox">
						<td class="col-*-*"><a href="listarTareas?category=inbox">Inbox</a></td>
					</tr>
					<tr class="row" id="item_Today">
						<td class="col-*-*"><a href="listarTareas?category=today">Hoy</a></td>
					</tr>
					<tr class="row" id="item_Week">
						<td class="col-*-*"><a href="listarTareas?category=week">Semana</a></td>
					</tr>
					<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
						<tr class="row" id="item_${i.index}">
							<td class="col-*-*"><a
								href="listarTareas?category=${entry.id}">${entry.name}</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="col-*-6">
				<table
					class="table table-striped table-condensed table-bordered table-hover"
					align="center" style="width: 70%">
					<tr class="row">
						<th class="col-*-*">Title</th>
						<th class="col-*-*">Comments</th>
						<th class="col-*-*">Category</th>
						<th class="col-*-*">Created</th>
						<th class="col-*-*">Planned</th>
						<th class="col-*-*">Close Task</th>
					</tr>
					<c:forEach var="entry" items="${listaTareas}" varStatus="i">
						<tr class="row" id="item_${i.index}">
							<td class="col-*-*">${entry.title}</td>
							<td class="col-*-*">${entry.comments}</td>
							<td class="col-*-*">${entry.categoryId}</td>
							<td class="col-*-*">${entry.created}</td>
							<c:if test="${entry.planned < date}">
								<td class="col-*-*"><div
										class="alert alert-danger alert-dismissable">${entry.planned}
									</div></td>
							</c:if>
							<c:if test="${entry.planned >= date || entry.planned == null}">
								<td class="col-*-*">${entry.planned}</td>
							</c:if>
							<td class="col-*-*"><a href="cerrarTarea?=${entry.id}">Finish</a></td>
						</tr>
						<c:if
							test="${categoria.equals('today') && i.index < listaTareas.size() && !entry.categoryId.equals(listaTareas[i.index+1].categoryId)}">
							<tr>
								<th colspan="7" class="alert alert-info" style="height: 50px">
								</th>
							</tr>
						</c:if>
						<c:if
							test="${categoria.equals('week') && i.index < listaTareas.size() && !entry.planned.getDate().equals(listaTareas[i.index+1].planned.getDate())}">
							<tr>
								<th colspan="7" class="alert alert-info" style="height: 50px">
								</th>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<center>
			<a id="paginaAnterior_link_id" href="principalUsuario">Volver
				atrás</a>
		</center>
	</div>
</body>
</html>