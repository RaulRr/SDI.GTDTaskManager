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
import uo.sdi.dto.types.UserStatus;



public class ModificarStatusAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		Long userId =  Long.parseLong(request.getQueryString().split("&")[0].split("=")[1]);
		String login = request.getQueryString().split("&")[0].split("=")[2];
		UserStatus estado;
		List<User> listaUsuarios;
		
		try {
			AdminService adminService = new AdminServiceImpl();
			User user = adminService.findUserById(userId);
			if(user.getStatus().equals(UserStatus.ENABLED)){
				adminService.disableUser(userId);
				estado=UserStatus.DISABLED;
			}else{
				adminService.enableUser(userId);
				estado=UserStatus.ENABLED;
			}
			
			Log.debug("Modificado status del usuario [%s] - [%s] al valor [%s]", 
					userId, login, estado);
		
			listaUsuarios=adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);
						
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido actualizando el status del usuario [%s]", 
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
