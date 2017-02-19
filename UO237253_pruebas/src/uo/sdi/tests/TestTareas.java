package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class TestTareas {

	private WebTester task;
	
	@Before
	public void prepare() {
		task = new WebTester();
		task.setBaseUrl("http://localhost:8280/UO237253");
		
		task.beginAt("/"); // Navegar a la URL
		task.setTextField("nombreUsuario", "usuario3");
		task.setTextField("passUsuario", "usuario3");
		task.clickButton("validar_button_id"); // Seguir el hipervínculo
	}
	
	@After
	public void closeSession() {
		task.clickLink("paginaAnterior_link_id");
		task.clickLink("cerrarSesion_link_id");
	}

	/**
	 *Comprueba que todos los campos para trabajar desde listar atreas se 
	 *encuentra disponibles
	 */
	@Test
	public void testNavegationUser() {
		
		//Estamos en principal_usuario
		task.assertTitleEquals("TaskManager - Página principal del usuario");
		task.assertLinkPresent("listarTareas_link_id");//Tareas
		task.assertLinkNotPresent("listarUsuarios_link_id"); //Usuarios
		task.clickLink("listarTareas_link_id");//Accedemos
		
		task.assertTitleEquals("TaskManager - Listado de tareas");
		task.assertTextPresent("Title");
		task.assertTextPresent("Comments");
		task.assertTextPresent("Category");
		task.assertTextPresent("Created");
		task.assertTextPresent("Planned");
		task.assertTextPresent("Close Task");
		task.assertButtonPresent("nuevaTarea_button_id");//Boton creacion
		task.assertElementPresent("nuevaTarea_text_id");//Formulario 
		
	}
	
	/**
	 * Probamos a crear y finalizar una tarea válida
	 */
	@Test
	public void testTaskCreation() {
		
		//Estamos en principal_usuario
		task.assertTitleEquals("TaskManager - Página principal del usuario");
		task.clickLink("listarTareas_link_id");//Accedemos
		
		task.setTextField("nuevaTarea","TareaTest");//nombre del boton
		task.clickButton("nuevaTarea_button_id");
		
		//Comprobamos que la tarea existe
		task.assertTextPresent("Tarea añadida");
		task.assertLinkPresent("editarTareaTareaTest");//Existe para editar
		task.assertLinkPresent("finish_link_idTareaTest");//finalizar
		
		task.clickLink("finish_link_idTareaTest");
		task.assertTextPresent("Tarea finalizada");
		task.assertLinkNotPresent("editarTareaTareaTest");
		task.assertLinkNotPresent("finish_link_idTareaTest");
	}
	
}