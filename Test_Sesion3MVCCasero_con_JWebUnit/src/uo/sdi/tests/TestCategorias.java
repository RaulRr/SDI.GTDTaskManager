package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestCategorias {

	private WebTester prueba;

	@Before
	public void prepare() {
		prueba = new WebTester();
		prueba.setBaseUrl("http://localhost:8280/UO237253");
	}

	@Test
	public void testCategoryCreation() {
		prueba.beginAt("/"); // Navegar a la URL
		prueba.setTextField("nombreUsuario", "usuario3");
		prueba.setTextField("passUsuario", "usuario3");
		prueba.clickButton("validar_button_id"); // Seguir el hipervínculo
		prueba.clickLink("listarTareas_link_id"); //Accedemos a la lista tareas
		prueba.clickLink("paginaAnterior_link_id");//Volvemos
		prueba.clickLink("cerrarSesion_link_id"); //Probamos a cerrar sesión

	}

	
}