package uo.sdi.acciones.user;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// inizializamos el resultado
		String resultado = "EXITO";

		// Obtenemos el Usuario de la sesión
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");

		// Inicializamos variables para uso posterior
		String category = null;
		String filtro = null;

		try {

			// Se intenta obtener la categoría a listar de la QueryString
			category = request.getQueryString().split("&")[0].split("=")[1];

			if (category.contains("%20")) {

				// Si la categoría incluye espacios, hay que reformatearla al
				// recibirla
				String[] cat = category.split("%20");
				category = "";
				for (String s : cat)
					category += " " + s;
				category = category.substring(1);

			}

			// Se intenta obtener el filtro de la QueryString
			filtro = request.getQueryString().split("&")[1].split("=")[1];
		} catch (Exception n) {
			// No se controlan excepciones, dado que un fallo aquí implica
			// únicamente que no se está pasando una categoría o un filtro
		}

		// Se encarga de mostrar los mensajes de añadir o modificar
		if (category != null
				&& (category.equals("añadida") || category.equals("editada"))) {

			request.setAttribute("mensajeVerde", "Tarea " + category
					+ " correctamente");
		}

		// Si viene tras editar una categoría, se elimina ela tributo
		// editarTarea
		if (session.getAttribute("editTarea") != null) {

			session.removeAttribute("editTarea");
		}

		// Se cambia el filtro al opuesto para poder hacer el switch en la vista
		if (filtro == null || filtro.equals("no"))
			request.getSession().setAttribute("filtro", "no");
		else
			request.getSession().setAttribute("filtro", "si");

		// Declaramos las variables de las listas que vamos a usar
		// posteriormente
		List<Task> listaTareas;
		List<Category> listaCategorias;

		try {
			// Si es la primera vez que entra, no hay categoría, así que muestra
			// todas las tareas
			if (category == null)
				listaTareas = Services.getTaskService().findAllTasksByUserId(
						usuario.getId());
			else {

				// Si tiene categorias, probamos con las 3 predefinidas
				if (category.equals("inbox")) {
					listaTareas = Services.getTaskService()
							.findInboxTasksByUserId(usuario.getId());

					// Ordenamos la lista
					ordenarTareas(listaTareas, "planned");

				} else if (category.equals("today")) {
					listaTareas = Services.getTaskService()
							.findTodayTasksByUserId(usuario.getId());

					// Ordenamos la lista
					ordenarTareas(listaTareas, "today");

				} else if (category.equals("week")) {
					listaTareas = Services.getTaskService()
							.findWeekTasksByUserId(usuario.getId());

					// Ordenamos la lista
					ordenarTareas(listaTareas, "planned");
					ordenarTareas(listaTareas, "week");
				}

				// Si no es ninguna predefinida, intentamos convertir a Long
				// para sacar la categoría personalizada
				else
					try {
						Long cat = null;
						if (filtro != null)
							cat = Services
									.getTaskService()
									.findCategoryByName(category,
											usuario.getId()).getId();
						else
							cat = Long.parseLong(category);

						category = Services.getTaskService()
								.findCategoryById(cat).getName();
						listaTareas = Services.getTaskService()
								.findTasksByCategoryId(cat);

						// Como salen todas las listas de la categoría,
						// eliminamos las que no son del usuario activo
						for (Task t : listaTareas) {
							if (t.getUserId() != usuario.getId())
								listaTareas.remove(t);
						}

						// Si el filtro está activo, obtenemos las terminadas de
						// la categoría, y eliminamos als que no son del usuario
						// activo, y las añadimos a la lista general
						if (filtro != null && filtro.equals("si")) {
							List<Task> listaAcabadas = Services
									.getTaskService()
									.findFinishedTasksByCategoryId(cat);
							for (Task t : listaAcabadas) {
								if (t.getUserId() != usuario.getId())
									listaAcabadas.remove(t);
							}
							listaTareas.addAll(listaAcabadas);
						}

					} catch (Exception e) {
						// Si da excepción, es porque se ha metido una categoría
						// no numérica en la queryString, por lo que devolvemos
						// todas
						listaTareas = Services.getTaskService()
								.findAllTasksByUserId(usuario.getId());
					}
			}

			// Añadimos todos los atributos usados por la vista a la request
			request.getSession().setAttribute("listaTareas", listaTareas);
			request.getSession().setAttribute("date",
					new Date(System.currentTimeMillis()));
			request.getSession().setAttribute("categoria", category);

			listaCategorias = Services.getTaskService().findCategoriesByUserId(
					usuario.getId());
			request.setAttribute("listaCategorias", listaCategorias);

			// Creamos los mensajes de Log
			Log.debug(
					"Obtenida lista de categorías conteniendo [%d] categorías",
					listaCategorias.size());
			Log.debug("Obtenida lista de tareas conteniendo [%d] tareas",
					listaTareas.size());

		} catch (BusinessException b) {

			// Si algo falla, se crea el mensaje de Log y se cambia el resutlado
			// a FRACASO
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
					b.getMessage());
			resultado = "FRACASO";
		}

		return resultado;
	}

	/**
	 * Método para ordenar la lista obtenida según la categoría de la vista
	 * 
	 * @param lista
	 *            - Lista a ordenar
	 * @param comparador
	 *            - Comparador (Equivalente a la categoría) para ordenar
	 * @throws BusinessException
	 */
	private void ordenarTareas(List<Task> lista, String comparador)
			throws BusinessException {

		switch (comparador) {

		// Ordenamos por categoría, y si son iguales, por fecha planeada
		case "today":
			Collections.sort(lista, new Comparator<Task>() {
				@Override
				public int compare(Task o1, Task o2) {
					if (o1.getCategoryId() == null)
						return -1;
					if (o2.getCategoryId() == null)
						return 1;

					if (o1.getCategoryId().compareTo(o2.getCategoryId()) == 0) {
						if (o1.getPlanned() == null)
							return -1;
						if (o2.getPlanned() == null)
							return 1;

						return o1.getPlanned().compareTo(o2.getPlanned());
					} else
						return o1.getCategoryId().compareTo(o2.getCategoryId());
				}
			});
			break;

		// Ordenamos por fecha planeada, y si son iguales, por título
		case "week":
			Collections.sort(lista, new Comparator<Task>() {
				@Override
				public int compare(Task o1, Task o2) {
					if (o1.getPlanned() == null)
						return -1;
					if (o2.getPlanned() == null)
						return 1;

					if (o1.getPlanned().compareTo(o2.getPlanned()) == 0)
						return o1.getTitle().compareTo(o2.getTitle());
					else
						return o1.getPlanned().compareTo(o2.getPlanned());
				}
			});
			break;

		// Ordenamos por la fecha planeada
		case "planned":
			Collections.sort(lista, new Comparator<Task>() {
				@Override
				public int compare(Task o1, Task o2) {
					if (o1.getPlanned() == null)
						return -1;
					if (o2.getPlanned() == null)
						return 1;
					return o1.getPlanned().compareTo(o2.getPlanned());
				}
			});
			break;

		default:
			throw new BusinessException();
		}
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
