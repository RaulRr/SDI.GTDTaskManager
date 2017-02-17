package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;


public class EliminarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		Long userId =  Long.parseLong(request.getQueryString().split("&")[0].split("=")[1]);
		String login = request.getQueryString().split("&")[0].split("=")[2];
		List<User> listaUsuarios;
		
		try {
			AdminService adminService = Services.getAdminService();
			adminService.deepDeleteUser(userId);
			Log.debug("Se ha eleminado con exito al usuario: [%s] - [%s]", 
					userId, login);
			
			listaUsuarios=adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);
			
			
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
