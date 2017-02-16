package uo.sdi.acciones;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;


public class AccesoEditarTarea implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";
		Long taskId = Long.parseLong(request.getQueryString().split("=")[1]);
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		try{
			TaskService taskService = Services.getTaskService();
			Task task = taskService.findTaskById(taskId);
			
			datosTarea(request, task);
			
			request.setAttribute("listaCategorias",
					Services.getTaskService().
					findCategoriesByUserId(usuario.getId()));
			
			Log.debug("El usuario [%s] trata de modificar la tarea [%s]",
					usuario.getLogin(), task.getTitle());
			
			
		}catch (BusinessException b) {
			Log.debug("Algo ha ocurrido con el usuario [%s] tratando de editar "
					+ "la tarea [%s]", usuario.getLogin(), taskId);
			resultado="FRACASO";
		}
		return resultado;
	}

	private void datosTarea(HttpServletRequest request, Task task) {
		request.setAttribute("titulo", task.getTitle());
		if(task.getComments() != null)
			request.setAttribute("comentarios", task.getComments());
		if(task.getPlanned() != null)
			request.setAttribute("entrega", task.getPlanned());
		if(task.getCategoryId() != null)
			request.setAttribute("categoria", task.getCategoryId());
	}
	
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
