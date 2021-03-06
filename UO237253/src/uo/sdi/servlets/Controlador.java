package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.acciones.admin.EliminarUsuarioAction;
import uo.sdi.acciones.admin.ListarUsuariosAction;
import uo.sdi.acciones.admin.ModificarStatusAction;
import uo.sdi.acciones.admin.OrdenarUsuariosAction;
import uo.sdi.acciones.anonimo.AccesoRegistrarUsuarioAction;
import uo.sdi.acciones.anonimo.RegistrarUsuarioAction;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.acciones.user.AccesoEditarTareaAction;
import uo.sdi.acciones.user.AñadirTareaAction;
import uo.sdi.acciones.user.CerrarTareaAction;
import uo.sdi.acciones.user.CrearCategoriaAction;
import uo.sdi.acciones.user.EditarTareaAction;
import uo.sdi.acciones.user.EliminarCategoriaAction;
import uo.sdi.acciones.user.ListarTareasAction;
import uo.sdi.acciones.user.ModificarCategoriaAction;
import uo.sdi.acciones.user.ModificarDatosAction;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;

	// <rol, <opcion, objetoAccion>>
	private Map<String, Map<String, Accion>> mapaDeAcciones;

	// <rol, <opcion, <resultado, JSP>>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion;

	public void init() throws ServletException {
		crearMapaAcciones();
		crearMapaDeNavegacion();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;

		try {

			// Obtener el string que hay a la derecha de la última /
			accionNavegadorUsuario = request.getServletPath().replace("/", "");

			rolAntes = obtenerRolDeSesion(request);

			objetoAccion = buscarObjetoAccionParaAccionNavegador(rolAntes,
					accionNavegadorUsuario);

			request.removeAttribute("mensajeParaElUsuario");

			resultado = objetoAccion.execute(request, response);

			rolDespues = obtenerRolDeSesion(request);

			jspSiguiente = buscarJSPEnMapaNavegacionSegun(rolDespues,
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch (PersistenceException e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción relacionada con la "
					+ "persistencia [%s]", e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: "
							+ "contacte con el responsable de la aplicación");
			jspSiguiente = "/login.jsp";

		} catch (Exception e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de la "
							+ "aplicación");
			jspSiguiente = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspSiguiente);

		dispatcher.forward(request, response);
	}

	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null)
			return "ANONIMO";
		else if (((User) sesion.getAttribute("user")).getIsAdmin())
			return "ADMIN";
		else
			return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol,
			String opcion) {

		Accion accion = mapaDeAcciones.get(rol).get(opcion);
		// Arreglo para enviar a la página por defecto si se salta el mapa
		if (accion == null)
			accion = mapaDeAcciones.get(rol).get("error");

		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
				opcion, rol);

		return accion;
	}

	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion,
			String resultado) {
		Map<String, String> jspSiguienteM = mapaDeNavegacion.get(rol).get(
				opcion);
		if (jspSiguienteM == null) { // Arreglo para enviar a la página por
										// defecto si se salta el mapa
			jspSiguienteM = mapaDeNavegacion.get(rol).get("error");
		}

		String jspSiguiente = jspSiguienteM.get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras "
				+ "realizar [%s] con rol [%s]", jspSiguiente, resultado,
				opcion, rol);

		return jspSiguiente;
	}

	private void crearMapaAcciones() {

		mapaDeAcciones = new HashMap<String, Map<String, Accion>>();

		Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("accesoRegistroUsuario",
				new AccesoRegistrarUsuarioAction());
		mapaPublico.put("registrarUsuario", new RegistrarUsuarioAction());
		mapaPublico.put("cerrarSesion", new CerrarSesionAction());
		mapaPublico.put("error", new VolverAtrasAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);

		Map<String, Accion> mapaRegistrado = new HashMap<String, Accion>();
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistrado.put("principalUsuario", new VolverAtrasAction());
		mapaRegistrado.put("listarTareas", new ListarTareasAction());
		mapaRegistrado.put("añadirTarea", new AñadirTareaAction());
		mapaRegistrado.put("editarTarea", new AccesoEditarTareaAction());
		mapaRegistrado.put("modificarTarea", new EditarTareaAction());
		mapaRegistrado.put("cerrarTarea", new CerrarTareaAction());
		mapaRegistrado.put("nuevaCategoria", new CrearCategoriaAction());
		mapaRegistrado.put("eliminarCategoria", new EliminarCategoriaAction());
		mapaRegistrado
				.put("modificarCategoria", new ModificarCategoriaAction());
		mapaRegistrado.put("validarse", new ValidarseAction());
		mapaRegistrado.put("error", new VolverAtrasAction());
		mapaDeAcciones.put("USUARIO", mapaRegistrado);

		Map<String, Accion> mapaAdmin = new HashMap<String, Accion>();
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaAdmin.put("listarUsuarios", new ListarUsuariosAction());
		mapaAdmin.put("modificarStatus", new ModificarStatusAction());
		mapaAdmin.put("ordenarUsuarios", new OrdenarUsuariosAction());
		mapaAdmin.put("eliminarUsuario", new EliminarUsuarioAction());
		mapaAdmin.put("principalUsuario", new VolverAtrasAction());
		mapaAdmin.put("validarse", new ValidarseAction());
		mapaAdmin.put("error", new VolverAtrasAction());
		mapaDeAcciones.put("ADMIN", mapaAdmin);
	}

	private void crearMapaDeNavegacion() {

		mapaDeNavegacion = 
				new HashMap<String, Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP = 
				new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de ANONIMO
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/registrarUsuario.jsp");
		opcionResultadoYJSP.put("registrarUsuario", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/registrarUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("accesoRegistroUsuario", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("error", resultadoYJSP);

		mapaDeNavegacion.put("ANONIMO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de USUARIOS normales
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("principalUsuario", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("listarTareas", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas?category=añadida");
		resultadoYJSP.put("FRACASO", "/listarTareas");
		opcionResultadoYJSP.put("añadirTarea", resultadoYJSP);

		// Entrar al editor de tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/editarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/listarTareas.jsp");
		opcionResultadoYJSP.put("editarTarea", resultadoYJSP);
		
		//Aqui ya editamos la tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas?category=editada");
		resultadoYJSP.put("FRACASO", "/editarTarea");
		opcionResultadoYJSP.put("modificarTarea", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarTarea", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas");
		resultadoYJSP.put("FRACASO", "/listarTareas");
		opcionResultadoYJSP.put("nuevaCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarTareas");
		resultadoYJSP.put("FRACASO", "/listarTareas");
		opcionResultadoYJSP.put("modificarCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("error", resultadoYJSP);

		mapaDeNavegacion.put("USUARIO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación del ADMINISTRADOR
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarStatus", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();// Ordenar usuarios
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/listarUsuarios.jsp");
		opcionResultadoYJSP.put("ordenarUsuarios", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();// Eliminar un usuario
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("eliminarUsuario", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("principalUsuario", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("error", resultadoYJSP);

		mapaDeNavegacion.put("ADMIN", opcionResultadoYJSP);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}