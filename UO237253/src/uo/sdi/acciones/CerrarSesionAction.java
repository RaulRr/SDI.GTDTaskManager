package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Iniciamos el resultado
		String resultado = "EXITO";

		try {
			// Obtenemos el Login del usuario de la QueryString y contexto
			HttpSession session = request.getSession();
			String user = request.getQueryString().split("=")[1];
			String user1 = (String) request.getServletContext().getAttribute(
					user);

			if (user.equals(user1)) {
				// Eliminamos al usuario tanto de la sesión como del contexto,
				// si intenta cerrarse a si mismo
				request.getServletContext().removeAttribute(user);
				session.invalidate();

				// Si todo correcto, se generan el mensaje de Log y UI
				Log.debug("Se ha cerrado sesión con éxito: [%s]", user);
				request.setAttribute("mensajeVerde",
						"Sesión cerrada correctamente. Vuelva pronto " + user);

			} else {

				// Si intenta cerrar la sesión de otro usuario, se notifica
				// generando lso mensajes de Log y UI, y se cambia el resultado
				// a FRACASO
				resultado = "FRACASO";
				request.setAttribute("mensajeParaElUsuario",
						"No puedes cerrar la sesión de otro usuario");
				Log.debug("Intentando cerrar sesión de [%s] siendo [%s]",
						user1, user);
			}

		} catch (Exception e) {

			// Si algo falla, se genera el mensaje de Log y se cambia el
			// resultado a FRACASO
			Log.debug("Fallo al cerrar sesión");
			resultado = "FRACASO";
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
