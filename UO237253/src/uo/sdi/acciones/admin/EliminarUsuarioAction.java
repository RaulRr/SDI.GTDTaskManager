package uo.sdi.acciones.admin;

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

		// Inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos el ID del usuario, y el Login de la request
		Long userId = Long.parseLong(request.getQueryString().split("&")[0]
				.split("=")[1]);
		String login = request.getQueryString().split("&")[0].split("=")[2];

		List<User> listaUsuarios;

		try {
			// Intentamos borrar el usuario
			AdminService adminService = Services.getAdminService();
			adminService.deepDeleteUser(userId);

			// Si se borra correctamente, se crean los mensajes de log, se
			// actualiza la lista de Usuarios, y se genera el mensaje a mostrar
			// por pantalla
			Log.debug("Se ha eleminado con exito al usuario: [%s] - [%s]",
					userId, login);
			listaUsuarios = adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);
			request.setAttribute("mensajeVerde", "Usuario eliminado "
					+ "correctamente");

		} catch (BusinessException b) {
			// Si falla al borrar, se indica en el log y se cambia el resultado
			// a FRACASO
			Log.debug("Algo ha ocurrido eliminado al usuario: [%s] - [%s]",
					userId, login);

			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
