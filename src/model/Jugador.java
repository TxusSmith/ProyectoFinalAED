package model;

public class Jugador {
	
	public final static String MARIO = "player1.gif";
	public final static String SONIC = "player2.gif";
	public final static String LINK = "player3.gif";
	
	private String nickname;
	private String character;
	
	private int puntuacion;
	private int nivelActual;
	private int coins;
	private int vidas;
	private int x;
	private int y;
	
	public Jugador(String nickname, int x, int y, String character) {
		this.nickname = nickname;
		puntuacion = 0;
		nivelActual = 0;
		coins = 30;
		vidas = 4; 
		this.x = x;
		this.y = y;
		this.character = character;
	}

	public String getNickname() {
		return nickname;
	}

	public int getVidas() {
		return vidas;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getNivelActual() {
		return nivelActual;
	}

	public void setNivelActual(int nivelActual) {
		this.nivelActual = nivelActual;
	}

	public int getCoins() {
		return coins;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public void setCharacter(String character) {
		this.character = character;
	}
	
	public void sumarCoins(int co) {
		coins += co;
	}
	
	public void restarCoins(int co) {
		coins -= co;
	}
	
	public void quitarVida() {
		vidas--;			
	}
	
	public void moverJugador(int x1, int y1) {
		
		if(x!=x1 && y!=y1) {
			float deltaX = x1 - x;
			float deltaY = y1 - y;
			float angle = (float) Math.atan2( deltaY, deltaX );
			
			double speedX = 15 * Math.cos(angle);
			double speedY = 15 * Math.sin(angle);
			
			x = (int) (x + speedX);
			y = (int) (y + speedY);			
		}
		
		double dist = Math.hypot(x-x1, y-y1);
		if(dist<8) {
			x = x1;
			y = y1;
		}
	}

}
