package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelGameOver extends JPanel implements ActionListener {
	
	public final static String REINICIAR = "reiniciar";
	
	private VentanaPrincipal vPrincipal;
	
	private JButton reiniciar;
	
	public PanelGameOver(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		
		setVisible(true);
		setLayout(new BorderLayout());
		
		reiniciar = new JButton("REINICIAR");
		reiniciar.setActionCommand(REINICIAR);
		reiniciar.addActionListener(this);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setOpaque(false);
		
		panel2.add(reiniciar);
		
		add(panel2, BorderLayout.SOUTH);
		
	}
	
	public void paintComponent(Graphics g){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image fondo = toolkit.getImage("imagenes/fondo1.gif");

		g.drawImage(fondo, 0, 0, 700, 910, this);
		
		try {
			g.drawImage(ImageIO.read(new File("imagenes/gameOver.png")), 175, 50, 350, 159, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//pinta la info del jugador
		int c = vPrincipal.getMapa().getJugador().getCoins();
		int p = vPrincipal.getMapa().getJugador().getPuntuacion();
    	String coins = "X " + c;
    	String vidas = "X " + 0;
    	String nickname = vPrincipal.getMapa().getJugador().getNickname();
    	String punt = "Tu puntuacion fue de: " + Math.pow(c-p, 2);
    	
    	g.setColor(Color.WHITE);
        g.drawString(nickname, 175, 240);
        
        g.drawString(vidas, 175, 270);
        Image life = toolkit.getImage("imagenes/life.png");
        g.drawImage(life, 150, 255, 16, 15, this);
        
        g.drawString(coins, 175, 300);
        Image coin = toolkit.getImage("imagenes/coin.gif");
        g.drawImage(coin, 145, 285, 23, 15, this);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		
		if(comando.equals(REINICIAR)) {
			vPrincipal.menu();
		}
		
	}

}
