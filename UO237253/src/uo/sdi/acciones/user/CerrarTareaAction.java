package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class CerrarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Se inicializa el resultado y el ID de la tarea
		String resultado = "EXITO";
		Long taskId = null;

		// Se intenta obtener la tarea del request
		try {
			taskId = Long.parseLong(request.getQueryString().split("=")[1]);
		} catch (Exception e) {

			// Si falla, se crea le mensaje de Log, y se cambia el resultado a
			// FRACASO
			Log.debug("Tarea no encontrada");
			resultado = "FRACASO";
			return resultado;
		}

		try {

			// Si se obtiene correctamente el taskId, se intenta cerrar la
			// tarea, y generar el mensaje de Log y UI
			Services.getTaskService().markTaskAsFinished(taskId);
			Log.debug("Se ha cerrado con exito la tarea [%s]", taskId);
			request.setAttribute("mensajeVerde",
					"Tarea finalizada correctamente");

		} catch (BusinessException b) {

			// Si falla, se cre el mensaje de Log y se cambia el resutlado a
			// FRACASO
			Log.debug("Algo ha ocurrido cerrando la tarea con id [%s]", taskId);
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
