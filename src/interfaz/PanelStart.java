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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import excepciones.CampoVacioException;
import excepciones.UsuarioYaEstaException;

public class PanelStart extends JPanel implements ActionListener {
	
	public final static String NUEVO_JUEGO = "Nuevo Juego";
	
	private VentanaPrincipal vPrincipal;
	
	private JButton butNewGame;
	
	public PanelStart(VentanaPrincipal vPrincipal){
		this.vPrincipal = vPrincipal;
		
		setVisible(true);
		setLayout(new BorderLayout());
		
		butNewGame = new JButton(NUEVO_JUEGO);
		butNewGame.setActionCommand(NUEVO_JUEGO);
		butNewGame.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.setOpaque(false);
		
		panel.add(butNewGame);
//		panel.add(new JLabel(""));
//		panel.add(new JLabel(""));
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setOpaque(false);
		
		panel2.add(panel);
		
		add(panel2, BorderLayout.SOUTH);
		
	}
	
	public void paintComponent(Graphics g){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image fondo = toolkit.getImage("imagenes/fondo1.gif");

    	g.drawImage(fondo, 0, 0, 700, 910, this);
    	try {
			g.drawImage(ImageIO.read(new File("imagenes/title.png")), 168, 70, 360, 270, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		
		if(comando.equals(NUEVO_JUEGO)) {
			try {
				vPrincipal.crearJugador();
			} catch (CampoVacioException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} 
			if(vPrincipal.getMapa().getJugador() != null) {	
				try {
					vPrincipal.character();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//vPrincipal.mapa();				
			}
		}
	}

}
