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
	public void tareaNavigationTest() {
		
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
	public void tareaTaskCreation() {
		
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
	
	/**
	 * Probamos a crear una tarea sin nombre
	 */
	@Test
	public void tareaTaskNullTitleCreation() {
		
		//Estamos en principal_usuario
		task.assertTitleEquals("TaskManager - Página principal del usuario");
		task.clickLink("listarTareas_link_id");//Accedemos
		
		task.setTextField("nuevaTarea","");
		task.clickButton("nuevaTarea_button_id");
		
		//Comprobamos que la tarea no existe y seguimos en el listado
		task.assertTitleEquals("TaskManager - Listado de tareas");
		task.assertTextNotPresent("Tarea añadida");
	}
	
	/**
	 * Probamos a editar el titulo de una tarea
	 */
	@Test
	public void tareaTaskEditor() {
		
		//Estamos en principal_usuario
		task.assertTitleEquals("TaskManager - Página principal del usuario");
		task.clickLink("listarTareas_link_id");//Accedemos
		
		task.setTextField("nuevaTarea","TareaTest");//creamos
		task.clickButton("nuevaTarea_button_id");
		task.clickLink("editarTareaTareaTest");//Accedemos al editor
		
		task.assertTitleEquals("TaskManager - Editor Tarea");
		task.assertTextPresent("Title");
		task.assertTextPresent("Comments");
		task.assertTextPresent("New Planned Date:");
		task.assertTextPresent("Categories");
		task.assertLinkPresent("paginaAnterior_link_id");
		task.assertTextPresent("TareaTest");//Presente el nombre de la tarea
		task.assertButtonPresent("editar_button_id");
		task.setTextField("title","TareaModificada");
		task.clickButton("editar_button_id");//Editamos y volvemos a la lista
		
		task.assertTitleEquals("TaskManager - Listado de tareas");
		task.assertLinkPresent("editarTareaTareaModificada");//Con nuevo titulo
		task.assertLinkPresent("finish_link_idTareaModificada");
		task.clickLink("finish_link_idTareaModificada");
		task.assertTextPresent("Tarea finalizada ");
	}
	
}