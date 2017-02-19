package uo.sdi.acciones.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.date.DateUtil;
import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

import javax.servlet.http.HttpSession;

public class AñadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Se inicializa el resultado
		String resultado = "EXITO";

		// Se obtienen el nombre de la tarea y el usuario activo
		String nombre = request.getParameter("nuevaTarea");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// Se inicializan disntas variables para su uso posterior
		String category = "";
		Long categoryId = null;
		Task task;

		try {
			// Se intenta obtener la categoría de la sesion, y removerla
			category = (String) session.getAttribute("categoria");
			session.removeAttribute("categoria");

			// Si se hace correctamente, se crea la nueva tarea con lso datos
			// obtenidos
			TaskService taskService = Services.getTaskService();
			categoryId = findCategoryId(
					taskService.findCategoriesByUserId(user.getId()), category);
			task = toTask(user, nombre, categoryId);

			// Si al categoria es 'today' se incluye la fecha planeda
			if (category != null && category.equals("today"))
				task.setPlanned(DateUtil.today());

			// Service añadede por defecto la fecha de creacion
			taskService.createTask(task);

			// Se crea el mensae de Log y UI
			Log.debug("El usuario [%s] ha creado una nueva tarea [%s]",
					user.getLogin(), nombre);
			request.setAttribute("mensajeVerde", 
					"Tarea añadida correctamente.");

		} catch (BusinessException b) {

			// Si ocurre un error, se genera el mensaje de Log y se cambia el
			// resutlado a FRACASO
			Log.debug("Algo ha ocurrido creando la nueva tarea del usuario "
					+ "[%s]", user.getLogin());
			resultado = "FRACASO";
		}
		return resultado;
	}

	/**
	 * Función que devuelve el id de una categoría según su nombre
	 * 
	 * @param findCategoriesByUserId
	 *            - Lista de categorías de un usuario dado
	 * @param category
	 *            - Nombre de la categoría cuyo ID se quiere obtener
	 * @return
	 */
	private Long findCategoryId(List<Category> findCategoriesByUserId,
			String category) {
		for (Category cat : findCategoriesByUserId) {
			if (cat.getName().equals(category)) {
				return cat.getId();
			}
		}
		return null;
	}

	/**
	 * 
	 * Función que devuelve una nueva tarea según los aprametros de entrada
	 * 
	 * @param user
	 *            - Uduario dueño de la tarea
	 * @param nombre
	 *            - Nombre de la tarea
	 * @param idCategoria
	 *            - ID de la categoría a la que pertenece la tarea
	 * @return
	 */
	private Task toTask(User user, String nombre, Long idCategoria) {
		return new Task().setUserId(user.getId()).setTitle(nombre)
				.setCategoryId(idCategoria);
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
