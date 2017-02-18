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
		
		String resultado="EXITO";
		Long categoryId = null;
		String nuevoNombre = null;
		try{
			categoryId =  Long.parseLong(request.getQueryString().split("=")[1]);
		}catch(NumberFormatException e){
			
		}
		try{
			nuevoNombre = request.getQueryString().split("=")[2];
			TaskService taskService = Services.getTaskService();
			Category actual = taskService.findCategoryById(categoryId);
			actual.setName(nuevoNombre);
			taskService.updateCategory(actual);
			
			Log.debug("Se ha modificado la categoria a [%s]",
					nuevoNombre);
			request.setAttribute("mensajeVerde", "Categoria modificada "
					+ "correctamente");
			
		}catch (BusinessException b){
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
