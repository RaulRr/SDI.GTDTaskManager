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

		// Obtenemos el ID, el Login y el Status de la request
		Long userId = 
				Long.parseLong(request.getQueryString().split("=")[1]);//id
		
		String login = request.getQueryString().split("=")[2];
		
		UserStatus estadoActual = UserStatus.valueOf(
				request.getQueryString().split("=")[3]);
		
		UserStatus estadoFinal;
		List<User> listaUsuarios;

		try {
			AdminService adminService = Services.getAdminService();
			
			// Si el Status es ENABLED, se cambia a DISABLED, y viceversa
			if (estadoActual.equals(UserStatus.ENABLED)) {
				adminService.disableUser(userId);
				estadoFinal = UserStatus.DISABLED;
			} else {
				adminService.enableUser(userId);
				estadoFinal = UserStatus.ENABLED;
			}

			// Creamos el mensaje de Log, actualizamos la lista de Usuarios, y
			// la actualizamos en el Request
			Log.debug(
					"Modificado status del usuario [%s] - [%s] al valor [%s]",
					userId, login, estadoFinal);

			listaUsuarios = adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);

		} catch (BusinessException b) {
			// Si falla, se crea el mensaje de log ys e cambia el resultado a
			// FRACASO
			Log.debug("Algo ha ocurrido actualizando el status del usuario "
					+ "[%s] - [%s]", userId, login);
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
