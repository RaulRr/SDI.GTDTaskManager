package uo.sdi.acciones;

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

		String resultado = "EXITO";
		String nombre = request.getParameter("nuevaTarea");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String category = "";
		Long categoryId = null;

		Task task;

		try {
			category = (String) session.getAttribute("categoria");
			session.removeAttribute("categoria");
			
			TaskService taskService = Services.getTaskService();
			categoryId = findCategoryId(
					taskService.findCategoriesByUserId(user.getId()), category);
			task = toTask(user, nombre, categoryId);

			if (category != null && category.equals("today"))
				task.setPlanned(DateUtil.today());

			taskService.createTask(task);// Service añadede la fecha de creacion

			Log.debug("El usuario %s ha creado una nueva tarea %s", 
					user.getLogin(), nombre);
		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando la nueva tarea del usuario %s",
					user.getLogin());
			resultado = "FRACASO";
		}
		return resultado;
	}

	private Long findCategoryId(List<Category> findCategoriesByUserId,
			String category) {
		for (Category cat : findCategoriesByUserId) {
			if (cat.getName().equals(category)) {
				return cat.getId();
			}
		}
		return null;
	}

	private Task toTask(User user, String nombre, Long idCategoria) {
		return new Task().setUserId(user.getId()).setTitle(nombre)
				.setCategoryId(idCategoria);
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
