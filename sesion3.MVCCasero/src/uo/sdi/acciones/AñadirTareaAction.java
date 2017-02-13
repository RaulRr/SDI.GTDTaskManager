package uo.sdi.acciones;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import javax.servlet.http.HttpSession;


public class AñadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String nombre = request.getParameter("nombreTarea");
		Task task;
		
		try {
			HttpSession  sesion = request.getSession();
			
			task = toTask(((User)sesion.getAttribute("user")), nombre);
			task.setTitle(nombre);
			
			TaskService taskService = Services.getTaskService();
			taskService.createTask(task);//Service añade la fecha
			
			Log.debug("Se ha creado una nueva tarea [%s]",
					nombre);
			
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando la nueva tarea del usuario");
			resultado="FRACASO";
		}
		return resultado;
	}
	
	private Task toTask(User user, String nombre ){
		return new Task()
		.setUserId(user.getId())
		.setTitle(nombre)
		.setCategoryId(Long.parseLong("1"));
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
