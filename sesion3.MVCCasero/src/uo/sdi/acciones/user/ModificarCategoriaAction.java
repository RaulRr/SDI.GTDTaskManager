package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;

public class ModificarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// inicializamos el resultado
		String resultado = "EXITO";

		// Inicializamos las varibles
		Long categoryId = null;
		String nuevoNombre = null;

		try {

			// Intentamos obtener la ID de la categoría del QueryString
			categoryId = Long.parseLong(request.getQueryString().split("=")[1]);

		} catch (NumberFormatException e) {
			// Si falla no pasa nada, porque la variable la teníamos
			// inicializada a null
		}
		try {

			// Intentamos obtener el nuevo nombre de la cateogría
			nuevoNombre = request.getQueryString().split("=")[2];

			// Intentamos obtener la cateogría, cambiarle el nombre, y
			// actualizarla en la bbdd
			TaskService taskService = Services.getTaskService();
			Category actual = taskService.findCategoryById(categoryId);
			actual.setName(nuevoNombre);
			taskService.updateCategory(actual);

			// Si todo es correcto, generamos el mensaje de Log y UI
			Log.debug("Se ha modificado la categoria a [%s]", nuevoNombre);
			request.setAttribute("mensajeVerde", "Categoria modificada "
					+ "correctamente");

		} catch (BusinessException b) {

			// En caso de error, generamos el mensaje de log y cambiamos el
			// resultado a FRACASO
			resultado = "FRACASO";
			Log.debug("No se ha modificado el nombre de la categoria a [%s]",
					nuevoNombre);
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
