package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;

import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import hilos.HiloJugador;
import model.Jugador;

public class PanelMapa extends JPanel implements MouseListener, ActionListener{
	
	public final static String ENTRAR = "entrar";
	
	private VentanaPrincipal vPrincipal;
	
	private JButton entrar;
	
	public PanelMapa(VentanaPrincipal vPrincipal){
		this.vPrincipal = vPrincipal;
		
		setOpaque(false);
		setLayout(new BorderLayout());
	    setVisible(true);
	    addMouseListener(this);
	    
	    entrar = new JButton("ENTRAR");
	    entrar.setActionCommand(ENTRAR);
	    entrar.addActionListener(this);
	    entrar.setEnabled(true);
//	    entrar.setOpaque(false);
//	    entrar.setContentAreaFilled(false);
//	    entrar.setBorderPainted(false);
	    
	    JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setOpaque(false);
		
		panel2.add(entrar);
		add(panel2, BorderLayout.SOUTH);
		
	}
	
	public void entrar() {
		Jugador jug = vPrincipal.getMapa().getJugador();
		boolean visit = vPrincipal.getMapa().getVisitNode()[jug.getNivelActual()];
		if(visit) {
			entrar.setEnabled(false);
		}else {
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image fondo = toolkit.getImage("imagenes/fondo.gif");
    	g.drawImage(fondo, 0, 0, 700, 910, this);
		
    	int[][] matriz = vPrincipal.getMapa().getAdjMatriz();
    	boolean[][] visited = vPrincipal.getMapa().getVisit();
    	int[][] weight = vPrincipal.getMapa().getWeight();
    	
    	int[] levels = vPrincipal.getMapa().bfsLevels(0, matriz);
    	
    	int[] x = vPrincipal.getMapa().getXn();
    	int[] y = vPrincipal.getMapa().getYn();
    	boolean[] visitedNode = vPrincipal.getMapa().getVisitNode();
    	
    	//pinta las aristas
    	for (int i = 0; i < matriz.length; i++) {
			for (int j = i; j < matriz.length; j++) {
		    	
				if(!visited[i][j]) {
					g.setColor(Color.ORANGE);
				} else {
					g.setColor(Color.RED);					
				}
				
				if(matriz[i][j] != 0) {
					String num = Integer.toString(weight[i][j]);
					AttributedString as = new AttributedString(num);
					as.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, num.length());
					
					int x1 = x[i]+8;
					int y1 = y[i]+8;
					int x2 = x[j]+8;
					int y2 = y[j]+8;
					g.drawLine(x1,y1,x2,y2);
					g.drawString(as.getIterator(), (x1+x2)/2, (y1+y2)/2);
				}
			}
		}
    	
    	//Pinta los vertices
		for(int i = 0; i<x.length;i++) {
			
			//pinta el numero del vertice
			String num = Integer.toString(i);
	    	AttributedString as = new AttributedString(num);
	    	as.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, num.length());
	    	
	    	//pinta el numero del nivel de dificultad
	    	String level = Integer.toString(levels[i]);
	    	AttributedString asax = new AttributedString(level);
	    	asax.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, level.length());
	    	
			if(visitedNode[i]) {
				g.setColor(Color.BLUE);
				g.fillOval(x[i], y[i],16,16);	
				g.setColor(Color.GREEN);
				g.drawOval(x[i], y[i],16,16);
//				g.drawString(as.getIterator(), x[i], y[i]);
				g.drawString(asax.getIterator(), x[i], y[i]+10);
			} else {
				g.setColor(Color.BLUE);
				g.fillOval(x[i], y[i],16,16);
				g.setColor(Color.RED);
				g.drawOval(x[i], y[i],16,16);
//				g.drawString(as.getIterator(), x[i], y[i]);
				g.drawString(asax.getIterator(), x[i], y[i]+10);
			}
		}
		
		//pinta al jugador
		Jugador jugador = vPrincipal.getMapa().getJugador();
    	Image player = toolkit.getImage("imagenes/" + jugador.getCharacter());
    	g.drawImage(player, jugador.getX(), jugador.getY(), 16, 16, this);
	}

	@Override
	public void mouseClicked(MouseEvent evento) {
		int clickedX = evento.getX();
		int clickedY = evento.getY();
		
		int[] x = vPrincipal.getMapa().getXn();
    	int[] y = vPrincipal.getMapa().getYn();
    	
    	System.out.println("Orig = " + vPrincipal.getMapa().getJugador().getX() + " " + vPrincipal.getMapa().getJugador().getY());
    	for (int i = 0; i < x.length; i++) {
    		double dist = Math.hypot(x[i]+8-clickedX, y[i]+8-clickedY);
			if(dist<8) {
				System.out.println("xy a ir = " + x[i] + " " + y[i]);
				HiloJugador hilo = new HiloJugador(vPrincipal, vPrincipal.getMapa().getJugador(), x[i], y[i]);
				if(vPrincipal.getMapa().moverONoJugador(i)) {					
					vPrincipal.moverJugador(x[i], y[i], hilo);
					vPrincipal.getMapa().getJugador().setNivelActual(i);
					entrar();
				}
			}
		}
    	System.out.println("Despues = " + vPrincipal.getMapa().getJugador().getX() + " " + vPrincipal.getMapa().getJugador().getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		
		if(comando.equals(ENTRAR)) {
			vPrincipal.pantalla();
		}
		
	}

}
