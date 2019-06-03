package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JButton;
import javax.swing.JPanel;

import hilos.HiloJugador;
import model.Jugador;
import model.Pantalla;

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
	    
	    JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setOpaque(false);
		
		panel2.add(entrar);
		add(panel2, BorderLayout.SOUTH);
		
		entrar();
		
	}
	
	public void entrar() {
		Jugador jug = vPrincipal.getMapa().getJugador();
		boolean visit = vPrincipal.getMapa().getPantalla()[jug.getNivelActual()].getCompletado();
		if(visit) {
			entrar.setEnabled(false);
		}else {
			entrar.setEnabled(true);
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
    	
    	Pantalla[] visitedNode = vPrincipal.getMapa().getPantalla();
    	
    	
    	//pinta la info del jugador
    	String coins = "X " + vPrincipal.getMapa().getJugador().getCoins();
    	String vidas = "X " + vPrincipal.getMapa().getJugador().getVidas();
    	String nickname = vPrincipal.getMapa().getJugador().getNickname();
    	String points = "Puntos a superar: " + vPrincipal.getMapa().getPointsToWin();
    	String puntosJ = "Puntos del jugador: " + vPrincipal.getMapa().puntajeJugador();
    	
    	g.setColor(Color.WHITE);
    	g.drawString(points, 350, 540);
    	g.drawString(puntosJ, 350, 555);
    	
        g.drawString(nickname, 100, 540);
        
        g.drawString(vidas, 100, 570);
        Image life = toolkit.getImage("imagenes/life.png");
        g.drawImage(life, 80, 555, 16, 15, this);
        
        g.drawString(coins, 100, 600);
        Image coin = toolkit.getImage("imagenes/coin.gif");
        g.drawImage(coin, 75, 585, 23, 15, this);
    	
    	//pinta las aristas
    	for (int i = 0; i < matriz.length; i++) {
			for (int j = i; j < matriz.length; j++) {
		    	
				if(!visited[i][j]) {
					g.setColor(Color.ORANGE);
				} else {
					g.setColor(Color.GREEN);					
				}
				
				if(matriz[i][j] != 0) {
					String num = Integer.toString(weight[i][j]);
					AttributedString as = new AttributedString(num);
					as.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, num.length());
					
					int x1 = vPrincipal.getMapa().getPantalla()[i].getX()+8;
					int y1 = vPrincipal.getMapa().getPantalla()[i].getY()+8;
					int x2 = vPrincipal.getMapa().getPantalla()[j].getX()+8;
					int y2 = vPrincipal.getMapa().getPantalla()[j].getY()+8;
					g.drawLine(x1,y1,x2,y2);
					g.drawString(as.getIterator(), (x1+x2)/2, (y1+y2)/2);
				}
			}
		}
    	
    	//Pinta los vertices
		for(int i = 0; i<vPrincipal.getMapa().getPantalla().length;i++) {
			
			//pinta el numero del vertice
			String num = Integer.toString(i);
	    	AttributedString as = new AttributedString(num);
	    	as.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, num.length());
	    	
	    	//pinta el numero del nivel de dificultad
	    	String level = Integer.toString(levels[i]);
	    	AttributedString asax = new AttributedString(level);
	    	asax.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, level.length());
	    	
			if(visitedNode[i].getCompletado()) {
				g.setColor(Color.BLUE);
				g.fillOval(vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY(),16,16);	
				g.setColor(Color.GREEN);
				g.drawOval(vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY(),16,16);
				if(nickname.equalsIgnoreCase("prim") || nickname.equalsIgnoreCase("level") || nickname.equalsIgnoreCase("nivel")) {
					g.drawString(as.getIterator(), vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY());					
				}else if(nickname.equalsIgnoreCase("nodo") || nickname.equalsIgnoreCase("node")){
					g.drawString(as.getIterator(), vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY());
				}
			} else {
				g.setColor(Color.BLUE);
				g.fillOval(vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY(),16,16);
				g.setColor(Color.RED);
				g.drawOval(vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY(),16,16);
				if(nickname.equalsIgnoreCase("prim") || nickname.equalsIgnoreCase("level") || nickname.equalsIgnoreCase("nivel")) {
					g.drawString(as.getIterator(), vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY());					
				}else if(nickname.equalsIgnoreCase("nodo") || nickname.equalsIgnoreCase("node")){
					g.drawString(as.getIterator(), vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY());
				}
			}
		}
		
		//pinta al jugador
		Jugador jugador = vPrincipal.getMapa().getJugador();
    	Image player = toolkit.getImage("imagenes/" + jugador.getCharacter());
    	g.drawImage(player, jugador.getX(), jugador.getY(), 16, 16, this);
    	g.drawImage(player, 80, 525, 16, 15, this);
	}

	@Override
	public void mouseClicked(MouseEvent evento) {
		int clickedX = evento.getX();
		int clickedY = evento.getY();
    	
    	System.out.println("Orig = " + vPrincipal.getMapa().getJugador().getX() + " " + vPrincipal.getMapa().getJugador().getY());
    	for (int i = 0; i < vPrincipal.getMapa().getPantalla().length; i++) {
    		double dist = Math.hypot(vPrincipal.getMapa().getPantalla()[i].getX()+8-clickedX, vPrincipal.getMapa().getPantalla()[i].getY()+8-clickedY);
			if(dist<8) {
				System.out.println("xy a ir = " + vPrincipal.getMapa().getPantalla()[i].getX() + " " + vPrincipal.getMapa().getPantalla()[i].getY());
				HiloJugador hilo = new HiloJugador(vPrincipal, vPrincipal.getMapa().getJugador(), vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY());
				if(vPrincipal.getMapa().moverONoJugador(i)) {	
					vPrincipal.getMapa().edgeUpdate(i);
					vPrincipal.moverJugador(vPrincipal.getMapa().getPantalla()[i].getX(), vPrincipal.getMapa().getPantalla()[i].getY(), hilo);
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
