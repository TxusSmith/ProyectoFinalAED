package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jugador;

public class PanelEscojerJugador extends JPanel implements ActionListener {
	
	public final static String MARIO = "mario";
	public final static String SONIC = "sonic";
	public final static String LINK = "link";
	
	private VentanaPrincipal vPrincipal;
	
	private JButton mario;
	private JButton sonic;
	private JButton link;
	
	public PanelEscojerJugador(VentanaPrincipal vPrincipal) throws IOException {
		this.vPrincipal = vPrincipal;
		
		setVisible(true);
		setLayout(new BorderLayout());
		
		File file1 = new File("imagenes/player1.gif");
		URL url1 = file1.toURI().toURL();
		Icon icon1 = new ImageIcon(url1);
	    
	    mario = new JButton(icon1);
	    mario.setActionCommand(MARIO);
	    mario.addActionListener(this);
	    mario.setOpaque(false);
	    mario.setContentAreaFilled(false);
	    mario.setBorderPainted(false);
	    
	    File file2 = new File("imagenes/player2.gif");
		URL url2 = file2.toURI().toURL();
		Icon icon2 = new ImageIcon(url2);
		
	    sonic = new JButton(icon2);
	    sonic.setActionCommand(SONIC);
	    sonic.addActionListener(this);
	    sonic.setOpaque(false);
	    sonic.setContentAreaFilled(false);
	    sonic.setBorderPainted(false);
		
	    File file3 = new File("imagenes/player3.gif");
		URL url3 = file3.toURI().toURL();
		Icon icon3 = new ImageIcon(url3);
	    
	    link = new JButton(icon3);
	    link.setActionCommand(LINK);
	    link.addActionListener(this);
	    link.setOpaque(false);
	    link.setContentAreaFilled(false);
	    link.setBorderPainted(false);
	    
	    JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		panel.setOpaque(false);
		
		panel.add(mario);
		panel.add(sonic);
		panel.add(link);
		
		add(panel, BorderLayout.CENTER);
	    
	}
	
	public void paintComponent(Graphics g){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image fondo = toolkit.getImage("imagenes/fondo1.gif");

    	g.drawImage(fondo, 0, 0, 700, 910, this);
    	try {
			g.drawImage(ImageIO.read(new File("imagenes/choose.png")), 175, 30, 350, 159, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		
		if(comando.equals(MARIO)) {
			vPrincipal.getMapa().getJugador().setCharacter(Jugador.MARIO);
			vPrincipal.mapa();
		} else if(comando.equals(SONIC)) {
			vPrincipal.getMapa().getJugador().setCharacter(Jugador.SONIC);
			vPrincipal.mapa();
		} else {
			vPrincipal.getMapa().getJugador().setCharacter(Jugador.LINK);
			vPrincipal.mapa();
		}
	}

}
