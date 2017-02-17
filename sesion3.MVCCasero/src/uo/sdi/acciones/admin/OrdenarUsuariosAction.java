package uo.sdi.acciones.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import alb.util.log.Log;

public class OrdenarUsuariosAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<User> listaUsuarios;
		String comparador =  request.getQueryString().split("&")[0].
				split("=")[1];
		
		try {
			AdminService adminService = Services.getAdminService();
			//Para ordenar buscamos de nuevo todos los usuarios
			listaUsuarios=adminService.findAllUsers();
			ordenarUsuarios(listaUsuarios, comparador);
			request.setAttribute("listaUsuarios", listaUsuarios);
			
			Log.debug("Obtenida y ordenada lista de usuarios siguiendo [%s]", 
					comparador);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido ordenando usuarios por: [%s]",
					comparador);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	private void ordenarUsuarios(List<User> lista, String comparador) 
			throws BusinessException{
		
		switch(comparador){
			case "login":
				Collections.sort(lista, new Comparator<User>() {
					@Override
					public int compare(User o1, User o2) {
						return o1.getLogin().compareTo(o2.getLogin());			            
			        }
			    });
				break;
				
			case "email":
				Collections.sort(lista, new Comparator<User>() {
					@Override
					public int compare(User o1, User o2) {
						return o1.getEmail().compareTo(o2.getEmail());			            
			        }
			    });
				break;
				
			case "status":
				Collections.sort(lista, new Comparator<User>() {
					@Override
					public int compare(User o1, User o2) {
						if(o1.getStatus().equals(o2.getStatus())){
							return 0;
						}
						else if(o1.getStatus().equals(UserStatus.ENABLED)){
							return -1;
						}
						else{
							return 1;
						}
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
