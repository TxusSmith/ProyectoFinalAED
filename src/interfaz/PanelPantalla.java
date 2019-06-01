package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Jugador;

public class PanelPantalla extends JPanel implements ActionListener {
	
	public final static String SALIR = "salir";
	
	public final static String RESP1 = "uno";
	public final static String RESP2 = "dos";
	public final static String RESP3 = "tres";
	public final static String RESP4 = "cuatro";
	
	private VentanaPrincipal vPrincipal;
	
	private JPanel panel;
	
	private JButton salir;
	private JButton resp1;
	private JButton resp2;
	private JButton resp3;
	private JButton resp4;
	
	public PanelPantalla(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		
		setVisible(true);
		setLayout(new BorderLayout());
		
		salir = new JButton("SALIR");
		salir.setActionCommand(SALIR);
		salir.addActionListener(this);

//	    resp1 = new JButton("X");
//	    resp1.setActionCommand(RESP1);
//	    resp1.addActionListener(this);
//	    
//	    resp2 = new JButton("X");
//	    resp2.setActionCommand(RESP2);
//	    resp2.addActionListener(this);
//	    
//	    resp3 = new JButton("X");
//	    resp3.setActionCommand(RESP3);
//	    resp3.addActionListener(this);
//	    
//	    resp4 = new JButton("X");
//	    resp4.setActionCommand(RESP4);
//	    resp4.addActionListener(this);
//
//		panel = new JPanel();
//		panel.setLayout(new GridLayout(2, 2));
//		panel.setOpaque(false);
//		
//		panel.add(resp1);
//		panel.add(resp2);
//		panel.add(resp3);
//		panel.add(resp4);
		
		ponerRespuestas();
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setOpaque(false);
		
		panel2.add(salir);
		
		//add(panel, BorderLayout.SOUTH);
		add(panel2, BorderLayout.NORTH);
		
	}
	
	public void ponerRespuestas() {
		
		ArrayList<String> respuestas = new ArrayList<>();
		
		File file = new File ("archivos/respuestas.txt");
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line = "";

			while((line = br.readLine()) != null){
				respuestas.add(line);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			
		}
		
		int n = ThreadLocalRandom.current().nextInt(0, respuestas.size()+1);
		int m = ThreadLocalRandom.current().nextInt(0, respuestas.size()+1);
		int o = ThreadLocalRandom.current().nextInt(0, respuestas.size()+1);
		
		String resp = vPrincipal.getMapa().getPantalla()[vPrincipal.getMapa().getJugador().getNivelActual()].getRespuesta();

	    resp1 = new JButton(resp);
	    resp1.setActionCommand(RESP1);
	    resp1.addActionListener(this);
	    
	    resp2 = new JButton(respuestas.get(n));
	    resp2.setActionCommand(RESP2);
	    resp2.addActionListener(this);
	    
	    resp3 = new JButton(respuestas.get(m));
	    resp3.setActionCommand(RESP3);
	    resp3.addActionListener(this);
	    
	    resp4 = new JButton(respuestas.get(o));
	    resp4.setActionCommand(RESP4);
	    resp4.addActionListener(this);

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		panel.setOpaque(false);
		
		panel.add(resp1);
		panel.add(resp2);
		panel.add(resp3);
		panel.add(resp4);
		
		add(panel, BorderLayout.SOUTH);
	}
	
	public void paintComponent(Graphics g){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image fondo = toolkit.getImage("imagenes/fondo1.gif");

    	g.drawImage(fondo, 0, 0, 700, 910, this);
    	
    	String preg = vPrincipal.getMapa().getPantalla()[vPrincipal.getMapa().getJugador().getNivelActual()].getPregunta();
    	
    	g.setColor(Color.WHITE);
        g.drawString(preg, 100, 200);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		
		if(comando.equals(SALIR)) {
			vPrincipal.mapa();
		}else if(comando.equals(RESP1)) {
			
		}else if(comando.equals(RESP2)) {
		
		}else if(comando.equals(RESP3)) {
			
		}else {
			
		}
		
	}

}
