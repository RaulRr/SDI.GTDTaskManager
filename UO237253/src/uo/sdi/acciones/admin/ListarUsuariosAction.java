package uo.sdi.acciones.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Inicializamos el resultado
		String resultado = "EXITO";

		List<User> listaUsuarios;

		try {
			// Intentamos obtener la lista de Usuarios
			AdminService adminService = Services.getAdminService();
			listaUsuarios = adminService.findAllUsers();

			// Si se obtiene correctamente, se incluye en el request y se crea
			// un mensaje de Log
			request.setAttribute("listaUsuarios", listaUsuarios);
			Log.debug("Obtenida lista de usuarios conteniendo [%d] usuarios",
					listaUsuarios.size());

		} catch (BusinessException b) {
			// Si falla al borrar, se indica en el log y se cambia el resultado
			// a FRACASO
			Log.debug("Algo ha ocurrido obteniendo lista de usuarios: %s",
					b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
