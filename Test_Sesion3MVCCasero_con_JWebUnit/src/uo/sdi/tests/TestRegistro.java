package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestRegistro {

	private WebTester john;
	private WebTester mary;

	@Before
	public void prepare() {
		john = new WebTester();
		mary = new WebTester();
		john.setBaseUrl("http://localhost:8280/UO237253");
		mary.setBaseUrl("http://localhost:8280/UO237253");
	}

	@Test
	public void testRegistrarUsuario() {
		john.beginAt("/"); // Navegar a la URL
		john.assertLinkPresent("registrar_link_id"); // Comprobar que existe el
														// hipervínculo
		john.clickLink("registrar_link_id"); // Seguir el hipervínculo

		john.assertTitleEquals("TaskManager - Registro de Usuario");// Comprobar
																	// título
																	// de la
																	// página
		john.assertTextPresent("Login:"); // Comprobar Login en la pagina
		john.assertTextPresent("Email:"); // Comprobar Email en la pagina
		john.assertTextPresent("Contraseña:"); // Comprobar Contraseña en la
												// pagina
		john.assertTextPresent("Repetir Contraseña:");

		john.setTextField("login", "john");
		john.setTextField("email", "john@gmail.com");
		john.setTextField("pass", "john123456");
		john.setTextField("rePass", "john123456");
		john.clickButton("registrar_button_id");

		john.assertTitleEquals("TaskManager - Inicie sesión"); // Volvimos a la
																// ventana de
																// login
	}

	@Test
	public void testRegistrarUsuarioFailLogin() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "");
		mary.setTextField("email", "john@gmail.com");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123456");
		mary.clickButton("registrar_button_id");

		mary.assertTitleEquals("TaskManager - Inicie sesión"); // Seguimos en el
																// registro
		mary.assertTextPresent("Error");
	}

	@Test
	public void testRegistrarUsuarioFailEmail() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "mary");
		mary.setTextField("email", "mary");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123456");
		mary.clickButton("registrar_button_id");

		mary.assertTitleEquals("TaskManager - Registro de Usuario"); // Seguimos
																		// en el
																	// registro
		mary.assertTextPresent("Error");
	}

	@Test
	public void testRegistrarUsuarioFailPassword() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "mary");
		mary.setTextField("email", "mary@gmail.com");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123");
		mary.clickButton("registrar_button_id");

		mary.assertTitleEquals("TaskManager - Registro de Usuario"); // Seguimos
																		// en el
																	// registro
		mary.assertTextPresent("Error");
	}
}