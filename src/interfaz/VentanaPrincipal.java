package interfaz;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import excepciones.CampoVacioException;
import hilos.HiloJugador;
import model.*;

public class VentanaPrincipal extends JFrame {
	
	private PanelStart pStart;
	private PanelMapa pMapa;
	private PanelEscojerJugador pEJugador;
	private PanelPantalla pPantalla;
	
	private Mapa mapa;
	
	private JPanel panel;
	
	public VentanaPrincipal(){
		setTitle("Super Graph Bross");
		setVisible(true);
		setLayout(new BorderLayout());
		setSize(700, 700);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		panel.setOpaque(false);
		
		pStart = new PanelStart(this);
		panel.add(pStart, BorderLayout.CENTER);		
	}
	
	public void mapa(){
		panel.removeAll();
		panel.setOpaque(false);
		pMapa = new PanelMapa(this);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.setOpaque(false);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.setOpaque(false);
		
		panel2.add(pMapa, BorderLayout.CENTER);
		
		panel.add(panel2, BorderLayout.CENTER);
		
		//pack();
		
		pMapa.revalidate();
	}
	
	public void character() throws IOException {
		panel.removeAll();
		panel.setOpaque(false);
		pEJugador = new PanelEscojerJugador(this);
		panel.add(pEJugador, BorderLayout.CENTER);
		pEJugador.revalidate();
	}
	
	public void pantalla() {
		panel.removeAll();
		panel.setOpaque(false);
		pPantalla = new PanelPantalla(this);
		panel.add(pPantalla, BorderLayout.CENTER);
		pPantalla.revalidate();
	}
	
	public void menu(){
		panel.removeAll();
		pStart = new PanelStart(this);
		panel.add(pStart, BorderLayout.CENTER);
		pStart.revalidate();
	}
	
	public void crearJugador() throws CampoVacioException{
		mapa = new Mapa();
		String nickname = JOptionPane.showInputDialog( null , "Ingrese su Nickname" );
		if(nickname == null || nickname.isEmpty()){
			throw new CampoVacioException();
		}else{
			Jugador jugador = new Jugador(nickname, mapa.getXn()[0], mapa.getYn()[0], Jugador.MARIO);
			mapa.setJugador(jugador);
			//mapa.visited();
			mapa.printVisist(mapa.getVisitNode());
		}	 
	}
	
	public Mapa getMapa() {
		return mapa;
	}
	
	public void moverJugador(int x, int y, HiloJugador hilo) {
		hilo.start();
	}

	public static void main(String[] args) {
		VentanaPrincipal vPrincipal = new VentanaPrincipal();
	}

}