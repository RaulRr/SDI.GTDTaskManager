package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos los parametros de la request
		String nuevoEmail = request.getParameter("email");
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));

		// Clonamos el usuario para su tratamiento
		User userClone = Cloner.clone(user);
		userClone.setEmail(nuevoEmail);

		try {

			// Intentamos actualizar el usuario en BBDD
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);

			// Si todo correcto, generamos los mensajes de Log y UI, y cambiamos
			// el usuario de la sesión
			Log.debug("Modificado email de [%s] con el valor [%s]",
					userClone.getLogin(), nuevoEmail);
			request.setAttribute("mensajeVerde", "Se ha modificado "
					+ "correctamente el nuevo correo");
			session.setAttribute("user", userClone);

		} catch (BusinessException b) {

			// En caso de error, generamos el mensaje de Log y cambiamos el
			// resultado a FRACASO
			Log.debug("Algo ha ocurrido actualizando el email de [%s] a [%s]: "
					+ "%s", user.getLogin(), nuevoEmail, b.getMessage());
			resultado = "FRACASO";
		}
		
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
