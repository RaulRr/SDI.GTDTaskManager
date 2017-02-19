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

	/**
	 * Registrar un usuario satisfactoriamente
	 */
	@Test
	public void registroUsuarioTest() {
		john.beginAt("/"); // Navegar a la URL
		john.assertLinkPresent("registrar_link_id"); // Comprobar que existe
		john.clickLink("registrar_link_id"); // Seguir el hipervínculo
		
		// Comprobar título de la página
		john.assertTitleEquals("TaskManager - Registro de Usuario");
		john.assertTextPresent("Login:"); // Comprobar Login en la pagina
		john.assertTextPresent("Email:"); // Comprobar Email en la pagina
		john.assertTextPresent("Contraseña:"); // Comprobar Contraseña en la
												
		john.assertTextPresent("Repetir Contraseña:");

		john.setTextField("login", "john"); //Rellenamos los campos
		john.setTextField("email", "john@gmail.com");//del formulario
		john.setTextField("pass", "john123456");
		john.setTextField("rePass", "john123456");
		john.clickButton("registrar_button_id");//Pulsamos

		// Volvimos a la ventana de login
		john.assertTitleEquals("TaskManager - Inicie sesión");
		john.assertTextPresent("Usuario registrado correctamente");
	}
	
	/**
	 * No hay login
	 */
	@Test
	public void registroNullLogin() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "");
		mary.setTextField("email", "john@gmail.com");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123456");
		mary.clickButton("registrar_button_id");

		//Seguimos en el registro
		mary.assertTitleEquals("TaskManager - Registro de Usuario");
		mary.assertTextPresent("Error");
	}
	
	/**
	 * Ya existe un usario con el login "usuario3"
	 */
	@Test
	public void registroLoginExistenteTest() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "usuario3");
		mary.setTextField("email", "john@gmail.com");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123456");
		mary.clickButton("registrar_button_id");

		//Seguimos en el registro
		mary.assertTitleEquals("TaskManager - Registro de Usuario");
		mary.assertTextPresent("Error");
	}

	/**
	 * Comprobamos error con email no válido
	 */
	@Test
	public void registroFailEmailTest() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "mary");
		mary.setTextField("email", "mary");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123456");
		mary.clickButton("registrar_button_id");
		
		//Seguimos en el registro
		mary.assertTitleEquals("TaskManager - Registro de Usuario"); 
		mary.assertTextPresent("Error"); //Aparece un mensaje de error
	}
	
	/**
	 * Comprobamos error con passwords distintas
	 */
	@Test
	public void registroDifferentPasswordsTest() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "mary");
		mary.setTextField("email", "mary@gmail.com");
		mary.setTextField("pass", "john123456");
		mary.setTextField("rePass", "john123");
		mary.clickButton("registrar_button_id");

		//Seguimos en el registro
		mary.assertTitleEquals("TaskManager - Registro de Usuario"); // registro
		mary.assertTextPresent("Error");
	}
	
	/**
	 * Comprobamos error con password no valida
	 */
	@Test
	public void registroInvalidPasswordTest() {
		mary.beginAt("/"); // Navegar a la URL
		mary.clickLink("registrar_link_id"); // Seguir el hipervínculo

		mary.setTextField("login", "mary");
		mary.setTextField("email", "mary@gmail.com");
		mary.setTextField("pass", "john");
		mary.setTextField("rePass", "john");
		mary.clickButton("registrar_button_id");

		//Seguimos en el registro
		mary.assertTitleEquals("TaskManager - Registro de Usuario"); // registro
		mary.assertTextPresent("Error");
	}
}