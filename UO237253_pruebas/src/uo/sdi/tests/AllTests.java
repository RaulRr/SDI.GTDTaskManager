package uo.sdi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestRegistro.class,
	TestAdmin.class,
	TestTareas.class,
	TestCategorias.class
})
public class AllTests {

}