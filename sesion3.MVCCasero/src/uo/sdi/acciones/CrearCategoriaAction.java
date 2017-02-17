package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String nombre = request.getParameter("nuevaCategoria");
		HttpSession  sesion = request.getSession();

		Category cat;
	
		try {

			//Se crea la categoría
			cat = new Category();
			cat.setUserId(((User)sesion.getAttribute("user")).getId());
			cat.setName(nombre);

			//Se añade la categoría a la bbdd
			TaskService taskService = Services.getTaskService();
			taskService.createCategory(cat);			
			
			Log.debug("Se ha creado una nueva categoría [%s]",
					nombre);
			request.setAttribute("mensajeVerde",
					"Categoría añadida correctamente.");
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando la nueva categoría del usuario");
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
