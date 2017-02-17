package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class EliminarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		Long catId =  Long.parseLong(request.getQueryString().split("&")[0].split("=")[1]);
		String name = request.getQueryString().split("&")[0].split("=")[2];		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.deleteCategory(catId);;
			Log.debug("Se ha eleminado con exito la categoría: [%s] - [%s]", 
					catId, name);		
			request.setAttribute("mensajeVerde",
					"Categoría eliminada correctamente.");
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido eliminado la categoría de id [%s] - [%s]", 
					catId, name);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
