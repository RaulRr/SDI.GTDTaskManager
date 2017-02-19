package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestAdmin {

	private WebTester admin;
	
	@Before
	public void prepare() {
		admin = new WebTester();
		admin.setBaseUrl("http://localhost:8280/UO237253");
	}

	/**
	 *Comprueba que como administrador podernos validarnos a la pagina principal
	 *y comprobar los campos que podemos emplear asi como acceder a la lista 
	 *usuarios volver y cerrar sesion 
	 */
	@Test
	public void adminvalidacionTest() {
		admin.beginAt("/"); // Navegar a la URL
		admin.assertButtonPresent("validar_button_id"); //El boton de validarse
		admin.setTextField("nombreUsuario", "administrador1");
		admin.setTextField("passUsuario", "administrador1");
		admin.clickButton("validar_button_id"); // Seguir el hipervínculo
		
		//Estamos en principal_usuario
		admin.assertTitleEquals("TaskManager - Página principal del usuario");
		admin.assertLinkPresent("listarUsuarios_link_id"); //Usuarios
		admin.assertLinkNotPresent("listarTareas_link_id"); //No tareas
		admin.assertLinkPresent("cerrarSesion_link_id");
		
		admin.clickLink("listarUsuarios_link_id"); //Accedemos a la lista
		admin.assertTitleEquals("TaskManager - Listado de usuarios");
		admin.assertLinkPresent("paginaAnterior_link_id");
		
		admin.clickLink("paginaAnterior_link_id");//Volvemos
		admin.clickLink("cerrarSesion_link_id"); //Probamos a cerrar sesión
		admin.assertTitleEquals("TaskManager - Inicie sesión");
	}
	
	/**
	 * Comprobamos que existen y funcionan los enlaces de ordenacion
	 */
	@Test
	public void adminListaUsuariosTest(){
		admin.beginAt("/"); // Navegar a la URL
		admin.setTextField("nombreUsuario", "administrador1");
		admin.setTextField("passUsuario", "administrador1");
		admin.clickButton("validar_button_id"); // Seguir el hipervínculo
		admin.clickLink("listarUsuarios_link_id"); //Accedemos a la lista
		
		admin.assertLinkPresent("login_link_id");//Ordenar por login
		admin.assertLinkPresent("email_link_id");//Ordenar por email
		admin.assertLinkPresent("status_link_id");//Ordenar por estado
		
		admin.clickLink("login_link_id");
		admin.clickLink("email_link_id");//Ordenar por email
		admin.clickLink("status_link_id");//Ordenar por estado
		
		admin.clickLink("paginaAnterior_link_id");//Volvemos
		admin.clickLink("cerrarSesion_link_id"); //Probamos a cerrar sesión
		admin.assertTitleEquals("TaskManager - Inicie sesión");		
	}
	

	/**
	 * Comprobamos que podemos modificar el estado de un usuario
	 */
	@Test
	public void adminCambiarEstadoTest(){
		admin.beginAt("/"); // Navegar a la URL
		admin.setTextField("nombreUsuario", "administrador1");
		admin.setTextField("passUsuario", "administrador1");
		admin.clickButton("validar_button_id"); // Seguir el hipervínculo
		admin.clickLink("listarUsuarios_link_id"); //Accedemos a la lista
		
		admin.assertLinkPresent("status_link_id1");//Usuario1 o primero
		admin.assertLinkNotPresentWithExactText("DISABLED");
		admin.clickLink("status_link_id1"); //Cambiamos el estado
		admin.assertLinkPresentWithExactText("DISABLED");
		admin.clickLink("status_link_id1"); 
		admin.assertLinkNotPresentWithExactText("DISABLED");
		admin.assertLinkPresentWithExactText("ENABLED");
		
		admin.clickLink("paginaAnterior_link_id");//Volvemos
		admin.clickLink("cerrarSesion_link_id"); //Probamos a cerrar sesión
		admin.assertTitleEquals("TaskManager - Inicie sesión");		
	}
	
	/**
	 * Comprobamos que podemos eliminar a cualquier usuario
	 */
	@Test
	public void adminEliminarUsuarioTest(){
		admin.beginAt("/"); // Navegar a la URL
		admin.setTextField("nombreUsuario", "administrador1");
		admin.setTextField("passUsuario", "administrador1");
		admin.clickButton("validar_button_id"); // Seguir el hipervínculo
		admin.clickLink("listarUsuarios_link_id"); //Accedemos a la lista
		
		admin.assertLinkPresent("delete_link_id1");//Link borrar al usuario1
		admin.assertLinkPresentWithExactText("Delete");
		
		admin.clickLink("paginaAnterior_link_id");//Volvemos
		admin.clickLink("cerrarSesion_link_id"); //Probamos a cerrar sesión
		admin.assertTitleEquals("TaskManager - Inicie sesión");		
	}

}