package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Pantalla {
	
	private int level;
	private String pregunta;
	private String respuesta;
	private boolean completado;
	
	private int x;
	private int y;
	
	public Pantalla(int level) {
		this.level = level;
		pregunta = "";
		respuesta = "";
		completado = false;
		x = ThreadLocalRandom.current().nextInt(50, 650);
		y = ThreadLocalRandom.current().nextInt(18, 500);
		cargar();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getPregunta() {
		return pregunta;
	}
	
	public String getRespuesta() {
		return respuesta;
	}
	
	public boolean getCompletado() {
		return completado;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void cargar() {
		File file = new File ("archivos/preguntas4.txt");			
		if(level <= 4) {
			file = new File ("archivos/preguntas" + level + ".txt");			
		}
		
		String[] preg = new String [6];
		String[] resp = new String [6];
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			int i = 0;
			while((line = br.readLine()) != null){
				preg[i] = line;
				line = br.readLine();
				resp[i] = line;
				i++;
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			
		}
		if(level == 0) {
			pregunta = preg[0];
			respuesta = resp[0];
		}else {
			int n = ThreadLocalRandom.current().nextInt(0, 6);
			pregunta = preg[n];
			respuesta = resp[n];			
		}
		System.out.println(pregunta);
		System.out.println(respuesta);
	}
	
	public void completado() {
		completado = true;
	}
	
	public boolean respCorrec(String resp) {
		return respuesta.equalsIgnoreCase(resp);
	}

}
