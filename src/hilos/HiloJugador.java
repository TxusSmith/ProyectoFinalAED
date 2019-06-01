package hilos;

import interfaz.VentanaPrincipal;
import model.Jugador;

public class HiloJugador extends Thread{
	
	private VentanaPrincipal vPrincipal;
	private Jugador jugador;
	private int x;
	private int y;
	private boolean continuar;
	
	public HiloJugador(VentanaPrincipal vPrincipal, Jugador jugador, int x, int y) {
		this.vPrincipal = vPrincipal;
		this.jugador = jugador;
		this.x = x;
		this.y = y;
		continuar = true;
	}
	
	public void detenerHilo() {
		continuar = false;
	}
	
	public void run() {
		while(continuar) {
			
			jugador.moverJugador(x, y);	
			
			if(jugador.getX() == x && jugador.getY() == y) {
				//vPrincipal.getMapa().visited();
				continuar = false;
			}

			try{
				Thread.sleep(50);
			}catch (Exception e) {
				e.printStackTrace();
			}
			vPrincipal.repaint();
		}
	}
}
