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


public class AccesoEditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";
		Long taskId = null;
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		
		
		try{
			//viene desde listar tareas?
			taskId = Long.parseLong(request.getQueryString().split("=")[1]);
			
		}catch(Exception e){}
		
		try{
			if(taskId == null){//se fallo al modificar. recargar pagina
				taskId = (Long)session.getAttribute("editTarea"); 
				request.setAttribute("mensajeParaElUsuario", 
						session.getAttribute("mensaje"));
				session.removeAttribute("mensaje");
			}
			
			TaskService taskService = Services.getTaskService();
			Task task = taskService.findTaskById(taskId);
			
			datosTarea(request, task);
			
			request.setAttribute("listaCategorias",
					Services.getTaskService().
					findCategoriesByUserId(usuario.getId()));
			
			session.setAttribute("editTarea", taskId);//en sesion la id
			
			Log.debug("El usuario [%s] accede al editor de tareas",
					usuario.getLogin());
			
			
		}catch (BusinessException b) {
			Log.debug("Algo ha ocurrido con el usuario [%s] tratando de acceder"
					+ " al editor de tarea", usuario.getLogin());
			resultado="FRACASO";
		}
		return resultado;
	}

	private void datosTarea(HttpServletRequest request, Task task) {
		request.setAttribute("titulo", task.getTitle());
		if(task.getComments() != null)
			request.setAttribute("comentarios", task.getComments());
		if(task.getPlanned() != null){
			//dd/MM/yyyy
			String date = alb.util.date.DateUtil.toString(task.getPlanned());
			
			date = date.replace('/', '-'); //para que lo reconozca el html
			date = date.split("-")[2]+"-"+date.split("-")[1]+
					"-"+date.split("-")[0];
			request.setAttribute("fecha", date);
			}
		if(task.getCategoryId() != null)
			request.setAttribute("categoria", task.getCategoryId());
	}
	
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
