package uo.sdi.acciones.anonimo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ValidarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		// inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos el nombre del usuario y la contraseña del request
		String nombreUsuario = request.getParameter("nombreUsuario");
		String passUsuario = request.getParameter("passUsuario");

		// Obtenemos la sesión, para realizar las comprobacioens de usuarios en
		// sesion
		HttpSession session = request.getSession();

		// Comprobamos si no hay usuarios actualmente conectados
		if (session.getAttribute("user") == null) {

			UserService userService = Services.getUserService();
			User userByLogin = null;

			// Intentamos obtener el usuario que desea iniciar sesión
			try {
				userByLogin = userService.findLoggableUser(nombreUsuario,
						passUsuario);
				// En caso erroneo, se crea el Log, el mensaje para la UI, y se
				// cambia el resutlado a FRACASO
			} catch (BusinessException b) {
				session.invalidate();
				Log.debug(
						"Algo ha ocurrido intentando iniciar sesión [%s]: %s",
						nombreUsuario, b.getMessage());
				request.setAttribute("mensajeParaElUsuario", b.getMessage());
				resultado = "FRACASO";
			}

			if (userByLogin != null) {

				// Si se obtiene correctamente y no es null, se comprueba que no
				// esté ya
				// conectado
				Object user = request.getServletContext().getAttribute(
						userByLogin.getLogin());
				if (user != null) {
					// Si el usuario ya está conectado en alguna sesión, se
					// deniega el acceso, y se generan lso mensajes de Log e
					// UI,
					// y se cambia el resutlado a FRACASO
					session.invalidate();
					Log.info("El usuario [%s] ya está conectado", nombreUsuario);
					request.setAttribute("mensajeParaElUsuario", "El usuario ["
							+ nombreUsuario + "] ya está conectado");
					resultado = "FRACASO";

				} else {
					// Si todo lo anterior está correcto, se añade el
					// usuario a
					// la sesión, se crean los mensajes de Log e UI
					// correspondioentes, y se incrementa el contador de
					// inicios
					// de sesión
					session.setAttribute("user", userByLogin);
					int contador = Integer.parseInt((String) request
							.getServletContext().getAttribute("contador"));
					request.getServletContext().setAttribute("contador",
							String.valueOf(contador + 1));
					session.setAttribute("fechaInicioSesion",
							new java.util.Date());
					request.getServletContext().setAttribute(
							userByLogin.getLogin(), userByLogin.getLogin());
					Log.info("El usuario [%s] ha iniciado sesión",
							nombreUsuario);
				}

			} else {

				// Si no se obtiene Usuario, se crea el Log y mensaje de UI
				// sobre la falta del usuario, y se cambia el resutladoa FRACASO
				session.invalidate();
				Log.info("El usuario [%s] no está registrado", nombreUsuario);
				request.setAttribute("mensajeParaElUsuario", "El usuario ["
						+ nombreUsuario + "] no está registrado");
				resultado = "FRACASO";
			}

		} else if (!nombreUsuario.equals(((User) session.getAttribute("user"))
				.getLogin())) {

			// Si hay un usuario ya conectado, se avisa, generando el mensaje
			// de Log y el de UI, y cambiando el resultado a FRACASO
			Log.info(
					"Se ha intentado iniciar sesión como [%s] teniendo la sesión iniciada como [%s]",
					nombreUsuario,
					((User) session.getAttribute("user")).getLogin());
			request.setAttribute("mensajeParaElUsuario",
					"Se ha intentado iniciar sesión como [" + nombreUsuario
							+ "] teniendo la sesión iniciada como ["
							+ ((User) session.getAttribute("user")).getLogin()
							+ "]");
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
