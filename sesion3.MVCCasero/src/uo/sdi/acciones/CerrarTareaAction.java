package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class CerrarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		Long taskId = null;
		
		try  {
			taskId =  Long.parseLong(request.getQueryString().split("=")[1]);
		}
		catch(Exception e){
			Log.debug("Tarea no encontrada");
			resultado="FRACASO";
			return resultado;
		}
		
		try {
			Services.getTaskService().markTaskAsFinished(taskId);
			Log.debug("Se ha cerrado con exito la tarea [%s]", 
					taskId);

		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido cerrando la tarea con id [%s]", 
					taskId);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
