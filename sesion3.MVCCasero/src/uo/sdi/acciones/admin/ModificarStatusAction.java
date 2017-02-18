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
import uo.sdi.dto.types.UserStatus;

public class ModificarStatusAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos el ID del usuario, y el Login de la request
		Long userId = Long.parseLong(request.getQueryString().split("&")[0]
				.split("=")[1]);
		String login = request.getQueryString().split("&")[0].split("=")[2];

		UserStatus estado;
		List<User> listaUsuarios;

		try {
			// Intenamos obtener el Usuario de la BBDD
			AdminService adminService = Services.getAdminService();
			User user = adminService.findUserById(userId);

			// Si el Status es ENABLED, se cambia a DISABLED, y viceversa
			if (user.getStatus().equals(UserStatus.ENABLED)) {
				adminService.disableUser(userId);
				estado = UserStatus.DISABLED;
			} else {
				adminService.enableUser(userId);
				estado = UserStatus.ENABLED;
			}

			// Creamos el mensaje de Log, actualizamos la lista de Usuarios, y
			// la actualizamos en el Request
			Log.debug(
					"Modificado status del usuario [%s] - [%s] al valor [%s]",
					userId, login, estado);

			listaUsuarios = adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);

		} catch (BusinessException b) {
			// Si falla, se crea el mensaje de log ys e cambia el resultado a
			// FRACASO
			Log.debug("Algo ha ocurrido actualizando el status del usuario "
					+ "[%s] - [%s]", userId);
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
