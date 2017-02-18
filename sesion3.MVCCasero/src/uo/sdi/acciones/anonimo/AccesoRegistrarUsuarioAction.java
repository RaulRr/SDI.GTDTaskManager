package uo.sdi.acciones.anonimo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;

public class AccesoRegistrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Esta clase no hace nada, es solamente un puente para el acceso a la
		// página de registro de usuario.

		String resultado = "EXITO";

		return resultado;
	}

}
