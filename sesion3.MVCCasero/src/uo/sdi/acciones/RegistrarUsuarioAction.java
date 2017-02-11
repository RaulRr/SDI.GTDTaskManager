package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class RegistrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String rePassword = request.getParameter("rePass");
		UserService userService = Services.getUserService();

		// Comprobamos la contraseña
		if (!email.matches("[-\\w\\.]+@\\w+\\.\\w+")) {
			request.setAttribute("mensajeParaElUsuario",
					"Error. Formato del correo inválido.");
			resultado = "FRACASO";
			return resultado;
		}

		// Comprobamos la igualdad de las contraseñas
		if (!password.equals(rePassword)) {
			request.setAttribute("mensajeParaElUsuario",
					"Error. Las contraseñas no coinciden.");
			resultado = "FRACASO";
			return resultado;

		} else {
			// Si son iguales, se comprueba que el formato sea valido
			if (password.length() < 8) {
				request.setAttribute("mensajeParaElUsuario",
						"Error. Formato de la contraseña inválido. Debe tener al menos 8 caracteres.");
				resultado = "FRACASO";
				return resultado;
			} else if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*[0-9].*")) {
				request.setAttribute("mensajeParaElUsuario",
						"Error. Formato de la contraseña inválido. Debe tener letras y números.");
				resultado = "FRACASO";
				return resultado;
			}
		}

		try {
			// Con las comprobaciones realizadas, creamos el usuario
			User user = new User();
			user.setEmail(email);
			user.setIsAdmin(false);
			user.setLogin(login);
			user.setPassword(password);

			// Intentamos registrar al usuario
			userService.registerUser(user);
			request.setAttribute("mensajeParaElUsuario", "registro");
		} catch (BusinessException p) {
			request.setAttribute("mensajeParaElUsuario", "Error. El usuario ya existe");
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
