package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestCategorias {

	private WebTester categoria;

	@Before
	public void prepare() {
		categoria = new WebTester();
		categoria.setBaseUrl("http://localhost:8280/UO237253");
	}

	/**
	 * Probamos la creacion de categorias desde el listarTareas
	 */
	@Test
	public void categoriaCreacionTest() {
		categoria.beginAt("/"); // Navegar a la URL
		categoria.setTextField("nombreUsuario", "usuario3");
		categoria.setTextField("passUsuario", "usuario3");
		categoria.clickButton("validar_button_id"); // Seguir el hipervínculo
		categoria.clickLink("listarTareas_link_id"); // Accedemos a la lista
														// tareas

		// Miramos los campos de categoria--No exsite nada
		// NewCategory sera el nombre de la categoria a crear
		categoria.assertLinkNotPresent("nombreCategoria_link_idNewCategory");
		categoria.assertLinkNotPresent("modificarCategoria_link_idNewCategory");
		categoria.assertLinkNotPresent("deleteCategoria_link_idNewCategory");

		categoria.assertButtonPresent("nuevaCategoria_button_id");
		categoria.setTextField("nuevaCategoria_text_id", "NewCategory");
		categoria.clickButton("nuevaCategoria_button_id");

		// Podemos elminar o moficar el nombre de categoria
		categoria.assertTextPresent("NewCategory");
		categoria.assertLinkPresent("modificarCategoria_link_idNewCategory");
		categoria.assertLinkPresent("deleteCategoria_link_idNewCategory");
	}

	@After
	public void closeSession() {
		categoria.clickLink("paginaAnterior_link_id");
		categoria.clickLink("cerrarSesion_link_id");
	}

}