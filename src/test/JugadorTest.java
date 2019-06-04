package test;

import static org.junit.Assert.*;

import org.junit.Test;

import hilos.HiloJugador;
import model.Jugador;

public class JugadorTest {
	
	private Jugador jugador;
	
	private void setupStage1(){
		jugador = new Jugador("XxPedrOxX", 0, 0, Jugador.MARIO);
	}
	
	private void setupStage2(){
		jugador = new Jugador("juan1999", 0, 0, Jugador.LINK);
	}
	
	private void setupStage3(){
		jugador = new Jugador("AlkaP", 0, 0, Jugador.SONIC);
	}

	@Test
	public void testSumarCoins() {
		setupStage1();
		jugador.sumarCoins(30);
		assertTrue(jugador.getCoins() == 60);
	}
	
	@Test
	public void testRestarCoins() {
		setupStage1();
		jugador.restarCoins(30);
		assertTrue(jugador.getCoins() == 0);
	}
	
	@Test
	public void testQuitarVida() {
		setupStage2();
		jugador.quitarVida();
		jugador.quitarVida();
		assertTrue(jugador.getVidas() == 2);
	}
	
	@Test
	public void moverJugador() {
		setupStage3();
		jugador.moverJugador(30, 30);
		assertTrue(jugador.getX() == 30 && jugador.getY() == 30);
	}

}
