package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Pantalla;

public class PantallaTest {
	
	private Pantalla pantalla;
	
	private void setupStage1(){
		pantalla = new Pantalla(0);
	}
	
	private void setupStage2(){
		pantalla = new Pantalla(1);
	}
	
	private void setupStage3(){
		pantalla = new Pantalla(2);
	}

	@Test
	public void testCompletado() {
		setupStage1();
		pantalla.completado();
		assertTrue(pantalla.getCompletado() && pantalla.getLevel() == 0);
	}
	
	@Test
	public void testCargar() {
		setupStage2();
		pantalla.cargar();
		assertTrue(!pantalla.getPregunta().equals("") && pantalla.getLevel() == 1);
	}

}
