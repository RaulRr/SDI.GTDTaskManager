package uo.sdi.acciones;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		String category = null;
		String filtro = null;
		try {
			category = request.getQueryString().split("&")[0].split("=")[1];
			if(category.contains("%20")){
				String[] cat = category.split("%20");
				category = "";
				for(String s : cat)
					category += " " + s;
				category = category.substring(1);
			}
			filtro = request.getQueryString().split("&")[1].split("=")[1];
		} catch (Exception n) {

		}

		//Este if se encarga de comprobar la categoria del crear o eliminar tarea del que provenimos
		//nos devuelve la categoria en la que estabamos
		if (category!= null && (category.equals("recargar") || 
				category.equals("editar"))){
			request.setAttribute("mensajeVerde", "Tarea añadida correctamente");
			category = (String)session.getAttribute("categoria");		
		}

		if (filtro == null || filtro.equals("no"))
			request.getSession().setAttribute("filtro", "no");
		else
			request.getSession().setAttribute("filtro", "si");

		List<Task> listaTareas;
		List<Category> listaCategorias;

		try {
			// Si es la priemra vez que entra, no hay categoría, así que muestra
			// todas las tareas
			if (category == null)
				listaTareas = Services.getTaskService().findAllTasksByUserId(
						usuario.getId());
			else {

				// Si tiene categorias, probamos con las 3 predefinidas
				if (category.equals("inbox")) {
					listaTareas = Services.getTaskService()
							.findInboxTasksByUserId(usuario.getId());
					ordenarTareas(listaTareas, "planned");
				} else if (category.equals("today")) {
					listaTareas = Services.getTaskService()
							.findTodayTasksByUserId(usuario.getId());
					ordenarTareas(listaTareas, "today");
				} else if (category.equals("week")) {
					listaTareas = Services.getTaskService()
							.findWeekTasksByUserId(usuario.getId());
					ordenarTareas(listaTareas, "planned");
					ordenarTareas(listaTareas, "week");
				}

				// Si no es ninguna predefinida, intentamos convertir a Long
				// para sacar la categoría personalizada
				else
					try {
						Long cat = null;
						if (filtro != null)
							cat = Services.getTaskService()
									.findCategoryByName(category, usuario.getId()).getId();
						else
							cat = Long.parseLong(category);
						
						category = Services.getTaskService()
								.findCategoryById(cat).getName();
						listaTareas = Services.getTaskService()
								.findTasksByCategoryId(cat);

						for (Task t : listaTareas) {
							if (t.getUserId() != usuario.getId())
								listaTareas.remove(t);
						}
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

			request.getSession().setAttribute("listaTareas", listaTareas);
			request.getSession().setAttribute("date",
					new Date(System.currentTimeMillis()));
			request.getSession().setAttribute("categoria", category);

			listaCategorias = Services.getTaskService().findCategoriesByUserId(
					usuario.getId());
			request.setAttribute("listaCategorias", listaCategorias);
			request.setAttribute("separador", "");

			Log.debug(
					"Obtenida lista de categorías conteniendo [%d] categorías",
					listaCategorias.size());

			Log.debug("Obtenida lista de tareas conteniendo [%d] tareas",
					listaTareas.size());
		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
					b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	private void ordenarTareas(List<Task> lista, String comparador)
			throws BusinessException {
		switch (comparador) {
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
