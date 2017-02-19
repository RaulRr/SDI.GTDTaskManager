package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class EliminarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Inizializamos el resultado
		String resultado = "EXITO";

		// Obtenemos lo parametros de la queryString
		Long catId = Long.parseLong(request.getQueryString().split("&")[0]
				.split("=")[1]);
		String name = request.getQueryString().split("&")[0].split("=")[2];

		try {

			// Intentamos borrar la categoría seleccionada
			TaskService taskService = Services.getTaskService();
			taskService.deleteCategory(catId);

			// Si nada falla, creamos los mensajes de Log y UI
			Log.debug("Se ha eleminado con exito la categoría: [%s] - [%s]",
					catId, name);
			request.setAttribute("mensajeVerde",
					"Categoría eliminada correctamente.");

		} catch (BusinessException b) {

			// Si algo falla, se crean los mensajes de Log y UI, y se cambia el
			// resultado a FRACASO
			Log.debug(
					"Algo ha ocurrido eliminado la categoría de id [%s] - [%s]",
					catId, name);
			resultado = "FRACASO";
		}
		
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
