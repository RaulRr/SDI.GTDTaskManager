package uo.sdi.acciones.anonimo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public class RegistrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// inicializamos el resultado
		String resultado = "EXITO";

		// Obtenemos los parametros del nuevo usuario a registrar
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String rePassword = request.getParameter("rePass");

		// Obtenemos el userService que usaremos agragar el usuario a la BBDD
		UserService userService = Services.getUserService();

		// Realizamos todas las comprobaciones de los parametros de entrada. Si
		// son erroneos, se cambia el resultado a FRACASO, y se genera el
		// mensaje correspondiente para la UI

		// Comprobamos el formato del Email
		// Debe incluir al menos texto, una @, texto, '.', y texto
		if (!email.matches("[-\\w\\.]+@\\w+\\.\\w+")) {
			request.setAttribute("mensajeParaElUsuario",
					"Error. Formato del correo inválido.");
			resultado = "FRACASO";
			return resultado;
		}

		// Comprobamos que el tamaño del Login nos ea menor de 3
		if (login.length()<3) {
			request.setAttribute("mensajeParaElUsuario",
					"Error. Login demasiado corto");
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
			// Al menos 8 caracteres
			if (password.length() < 8) {
				request.setAttribute("mensajeParaElUsuario",
						"Error. Formato de la contraseña inválido. Debe tener al menos 8 caracteres.");
				resultado = "FRACASO";
				return resultado;
				// Si cumple el tamaño, se comprueba que tengan al menos una
				// letra y un número
			} else if (!password.matches(".*[a-zA-Z].*")
					|| !password.matches(".*[0-9].*")) {
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

			// Si se realiza correctamente, añadimos el mensaje para la UI y el
			// Log
			request.setAttribute("mensajeVerde",
					"Usuario registrado correctamente.");
			Log.debug("Se ha registrado correctamente el usuario  " + "[%s]",
					login);

		} catch (BusinessException p) {
			// En caso de error, se genera el mensaje para la UI, y el Log, y se
			// cambia el resultado a FRACASO
			request.setAttribute("mensajeParaElUsuario",
					"Error. El usuario ya existe.");
			Log.debug("Algo ha ocurrido registrando al usuario " + "[%s]",
					login);
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
