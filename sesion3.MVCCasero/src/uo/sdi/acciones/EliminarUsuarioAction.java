package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.AdminService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.admin.AdminServiceImpl;
import uo.sdi.dto.User;


public class EliminarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		Long userId =  Long.parseLong(request.getQueryString().split("&")[0].split("=")[1]);
		int index = Integer.parseInt(request.getQueryString().split("&")[0].split("=")[2]);
		
		try {
			AdminService adminService = new AdminServiceImpl();
			//adminService.deepDeleteUser(userId);
			adminService.findUserById(userId);
			
			@SuppressWarnings("unchecked")
			List<User> listaUsuarios = (List<User>)request.getSession().getAttribute("listaUsuarios");
			listaUsuarios.remove(index);	
			
			Log.debug("Se ha eleminado con exito al usuario de id: [%s]", 
					userId);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido eliminado al usuario de id [%s]", 
					userId);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
