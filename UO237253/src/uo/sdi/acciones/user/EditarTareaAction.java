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

		// Inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos los parametros de la tarea del request
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String category = request.getParameter("category");
		String date = request.getParameter("date");// yyyy-MM-dd

		HttpSession session = request.getSession();

		// Inicializamos los atributos para la creación de la tarea
		Task task;
		User user = (User) session.getAttribute("user");
		Long categoryId = null;
		Long taskId = (Long) session.getAttribute("editTarea");
		Date realDate = null;

		try {// Si se selecciono categoria
			categoryId = Long.parseLong(category);
		} catch (NumberFormatException e) {

			// Si falla, no se pone categoría a la tarea
			categoryId = null;
		}

		try {// Si se selecciono fecha
			if (!date.equals(""))
				realDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		} catch (Exception e) {

			// Si falla, la fecha tiene formato incorrecto, se notifica y cambia
			// el resultado a FRACASO
			realDate = null;
			resultado = "FRACASO";
			session.setAttribute("mensaje", "Formato de fecha incorrecto");
			return resultado;
		}

		try {

			// Creamos la tarea con todos su datos
			TaskService taskService = Services.getTaskService();
			task = toTask(user, title, comment, categoryId, realDate);
			task.setId(taskId); // Obtenida de la sesion

			// Intentamos actualizar la tarea. Auqnue sea una nueva, al
			// coincidir el ID actualiza la que se pide
			taskService.updateTask(task);

			// Volvemos a la lista no necesitamos guardar el id de la tarea
			session.removeAttribute("editTarea");

			// Se generan los emnsajes de Log y UI
			Log.debug("El usuario [%s] ha modificado con exito la tarea [%s]",
					user.getLogin(), title);
			request.setAttribute("mensajeVerde",
					"Tarea modificada correctamente");

		} catch (BusinessException b) {

			// En caso de error, se generan los mensajes de Log y UI, y se
			// cambioa el resultado a FRACASO
			Log.debug("El usuario [%s] ha fallado al tratar de editar la tarea"
					+ " [%s]", user.getLogin(), title);
			session.setAttribute("mensaje", b.getMessage());
			resultado = "FRACASO";
		}

		return resultado;
	}

	/**
	 * Función que crea una tarea con los parametros escogidos
	 * 
	 * @param user
	 *            - Usuario dueño de la tarea
	 * @param nombre
	 *            - Nombre de la tarea
	 * @param comentario
	 *            - Comentario de la tarea
	 * @param idCategoria
	 *            - ID de la tarea (Heredado de la real)
	 * @param fecha
	 *            - Fecha planeada de la tarea
	 * @return Task - Tarea a actualizar en lña bbdd
	 */
	private Task toTask(User user, String nombre, String comentario,
			Long idCategoria, Date fecha) {
		return new Task().setUserId(user.getId()).setTitle(nombre)
				.setComments(comentario).setCategoryId(idCategoria)
				.setPlanned(fecha);
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
