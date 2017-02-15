package uo.sdi.acciones;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.date.DateUtil;
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
		String nombre = request.getParameter("nuevaTarea");
		HttpSession  sesion = request.getSession();
		String category = "";
		Long categoryId = null;

		Task task;

		try{
			category = (String)sesion.getAttribute("categoria");
			categoryId = Long.parseLong(category);
		}catch (NumberFormatException e){

		}		
		try {

			task = toTask(((User)sesion.getAttribute("user")), nombre, 
					categoryId);

			if(category != null && category.equals("today"))
				task.setPlanned(DateUtil.today());

			TaskService taskService = Services.getTaskService();
			taskService.createTask(task);//Service añadede la fecha de creacion
			
			
			Log.debug("Se ha creado una nueva tarea [%s]",
					nombre);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando la nueva tarea del usuario");
			resultado="FRACASO";
		}
		return resultado;
	}
	
	private Task toTask(User user, String nombre, Long idCategoria){
		return new Task()
		.setUserId(user.getId())
		.setTitle(nombre)
		.setCategoryId(idCategoria);
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
