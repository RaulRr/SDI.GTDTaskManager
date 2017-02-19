package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VolverAtrasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		// Esta clase no hace nada, es solamente un puente para volver atrás
		String resultado = "EXITO";

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
