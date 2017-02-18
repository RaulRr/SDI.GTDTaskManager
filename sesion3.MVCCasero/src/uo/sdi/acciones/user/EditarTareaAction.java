package uo.sdi.acciones.user;


import java.text.SimpleDateFormat;
import java.util.Date;

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


public class EditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String category = request.getParameter("category");
		String date = request.getParameter("date");//yyyy-MM-dd
		HttpSession  session = request.getSession();
		
		Task task;
		User user = (User)session.getAttribute("user");
		
		Long categoryId = null;
		Long taskId = (Long)session.getAttribute("editTarea");
		Date realDate = null;
		
		try{//Si se selecciono categoria y cual
			categoryId = Long.parseLong(category);			
		}catch(NumberFormatException e){
			categoryId = null;
		}
		
		try{//si se selecciono fecha y cual
			if(date != null)
				realDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			
		}catch(Exception e){
			realDate = null;
			resultado="FRACASO";
			request.setAttribute("mensajeParaElUsuario",
					"Formato de fecha incorrecto");
			return resultado;
		}

		try {
			TaskService taskService = Services.getTaskService();
			task = toTask(user, title, comment, categoryId, realDate);
			task.setId(taskId); //recogemos el id de tarea de la sesion
			
			taskService.updateTask(task);
			
			//Volvemos a la lista no necesitamos guardar el id de la tarea
			session.removeAttribute("editTarea"); 
			Log.debug("El usuario [%s] ha modificado con exito la tarea [%s]", 
					user.getLogin(), title);
			request.setAttribute("mensajeVerde",
					"Tarea modificada correctamente");
		}
		catch (BusinessException b) {
			Log.debug("El usuario [%s] ha fallado al tratar de editar la tarea"
					+ " [%s]", user.getLogin(), title);
			session.setAttribute("mensaje", b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	private Task toTask(User user, String nombre, String comentario,
			Long idCategoria, Date fecha){
		return new Task()
		.setUserId(user.getId())
		.setTitle(nombre)
		.setComments(comentario)
		.setCategoryId(idCategoria)
		.setPlanned(fecha);
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
