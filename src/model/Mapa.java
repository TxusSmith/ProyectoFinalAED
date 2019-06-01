package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Mapa {
	
	private Jugador jugador;
	private Pantalla[] pantalla;
	
	private int[] x;
	private int[] y;
	private boolean[] visitNode;

	private int[][] adjMatrix;
	private int[][] weight;
	private boolean[][] visit;
	
	private LinkedList<Integer>[] adjList;
	
	public Mapa() {
		generateMatriz();
		x = new int[adjMatrix.length];
		y = new int[adjMatrix.length];
		visitNode = new boolean[adjMatrix.length];
		pantalla = new Pantalla[adjMatrix.length];
		possx();
		possy();
		llenarPantallas();
	}
	
	public Mapa(int modelo,int numVertices, int aristas, int costos) {
		
	}
	
	public Jugador getJugador() {
		return jugador;
	}
	
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	public int[] getXn() {
		return x;
	}
	
	public int[] getYn() {
		return y;
	}
	
	public boolean[] getVisitNode() {
		return visitNode;
	}
	
	public int[][] getAdjMatriz() {
		return adjMatrix;
	}
	
	public void setAdjMatriz(int[][] adjMatriz) {
		this.adjMatrix = adjMatriz;
	}
	
	public int[][] getWeight() {
		return weight;
	}
	
	public boolean[][] getVisit() {
		return visit;
	}
	
//	public void visited() {
//		visitNode[jugador.getNivelActual()] = true;
//	}
	
	public Pantalla[] getPantalla() {
		return pantalla;
	}
	
	public void contestar(int nivel, String resp) {
		if(pantalla[nivel].respCorrec(resp)) {
			pantalla[nivel].completado();
		}
	}
	
	public void llenarPantallas() {
		int[] levels = bfsLevels(0, adjMatrix);
		for (int i = 0; i < levels.length; i++) {
			pantalla[i] = new Pantalla(i);
		}
	}
	
	public void possx() {
		for (int i = 0; i < x.length; i++) {
	    	x[i] = ThreadLocalRandom.current().nextInt(50, 650);
		}
	}
	
	public void possy() {
		for (int i = 0; i < y.length; i++) {
	    	y[i] = ThreadLocalRandom.current().nextInt(18, 500);
		}
	}
	
	public boolean moverONoJugador(int n) {
		boolean mover = false;
		if(adjMatrix[jugador.getNivelActual()][n]>0) {
			mover = true;
		}
		return mover;
	}

	public void generateMatriz() {
		int n = ThreadLocalRandom.current().nextInt(10, 15);
		adjMatrix = new int[n][n];
		weight = new int[n][n];
		visit = new boolean[n][n];
		for (int i = 0; i < adjMatrix.length; i++) {
			int h = 0;
			for (int j = i; j < adjMatrix.length; j++) {
				//visit[i][j] = true;
				double x = ThreadLocalRandom.current().nextInt(0, 100);
				int cost = ThreadLocalRandom.current().nextInt(25, 50);
				if(i!=j && adjMatrix[i][j] == 0 && h < 2) {
					if(x%2==0) {
						adjMatrix[i][j] = 0;
						adjMatrix[j][i] = 0;
					} else {
						adjMatrix[i][j] = 1;
						adjMatrix[j][i] = 1;
						weight[i][j] = cost;
						weight[j][i] = cost;
						h++;
					}
				}
			}
		}
		print(adjMatrix);
		print(weight);
	}
	
	public void printVisist(boolean[] n) {
		String fila = "";
		for (int i = 0; i < n.length; i++) {
			fila += n[i] + " ";
		}
		System.out.println(fila);
	}
	
	public void print(int[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			String fila = "";
			for (int j = 0; j < graph.length; j++) {
				if(j == graph.length-1) {
					fila += graph[i][j];
				} else {
					fila += graph[i][j] + " ";					
				}
			}
			System.out.println(fila);
		}
	}
	
	public void print2(boolean[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			String fila = "";
			for (int j = 0; j < graph.length; j++) {
				if(j == graph.length-1) {
					fila += graph[i][j];
				} else {
					fila += graph[i][j] + " ";					
				}
			}
			System.out.println(fila);
		}
	}
	
	public ArrayList<Integer> adjacents(int node, int[][] matrix){
		
		ArrayList<Integer> adj = new ArrayList<>();
		
		for (int i = 0; i < matrix.length; i++) {
			if(matrix[node][i] > 0){
				adj.add(i);
			}
		}
		return adj;
	}
	
	public int[] bfsLevels(int n, int[][] matrix){
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int[] levels = new int[matrix.length];
		boolean visited[] = new boolean[matrix.length]; 
		
		queue.add(n);
		visited[n] = true; 
		
		//ArrayList<Integer> array = new ArrayList<Integer>();
		
		while(!queue.isEmpty()){
			n = queue.poll();
			//array.add(n);
			
			ArrayList<Integer> adj = adjacents(n, matrix);
			
			for (int i = 0; i < adj.size(); i++) {
				int s = adj.get(i);
				if(!visited[s]){
					visited[s] = true;
					queue.add(s);
					levels[s] = levels[n] + 1;
				}
			}
		}
		return levels;
	}
	
	//----------------------------------------------
	
	public int[] menorDificultad(int indice) {
	
		int[] dificultad = new int[0];
		
		return dificultad;
		
	}
	
	public int minDistance(int dist[], Boolean vis[], int size){ 
        int min = Integer.MAX_VALUE;
        int minIndex = -1; 
  
        for (int i = 0; i < size; i++){
            if (vis[i] == false && dist[i] <= min){ 
                min = dist[i]; 
                minIndex = i; 
            } 
        }
        return minIndex; 
    }
	
	public int[] dijkstra(int[][] graph, int src, int[][] adjMatriz){ 
		int num = adjMatriz.length;
		
        int[] dist = new int[num];
        Boolean[] vis = new Boolean[num]; 
  
        for (int i = 0; i < num; i++){ 
            dist[i] = Integer.MAX_VALUE; 
            vis[i] = false; 
        } 
        
        dist[src] = 0; 
  
        for (int j = 0; j < num-1; j++){ 
            int u = minDistance(dist, vis, num); 
  
            vis[u] = true; 
  
            for (int k = 0; k < num; k++){
                if (!vis[k] && graph[u][k]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][k] < dist[k]){ 
                    dist[k] = dist[u] + graph[u][k]; 
                }
            }
        } 
        return dist;
    }
	
	public int[] BFS(int node, List<Integer>[] graph){
        
        Queue<Integer> q = new LinkedList<>();	//se crea la cola y el arreglo de niveles
	    int[] levels = new int[graph.length];

	        q.add(node);	// se agrega el nodo que entro por parametro a la cola
	        levels[node] = 1;// se le pone el nivel 1 al agregado
	        int current = 0;
	       
	       while(!q.isEmpty()){
	           
	           current = q.poll(); // se saca el valor que entro a la cola, indice
	           
	           ArrayList<Integer> y = (ArrayList<Integer>) graph[current]; // se crea una variable con el array corespondiente al indice (ese indice es como tal el nodo)
	               
	           for(int i = 0; i < y.size(); i++){ // revisa las conexiones 
	               
	        	   if((y.get(i) - 1) != -1) { // es para revisar que exista una conexion
	        		   if(levels[y.get(i)] == 0){ // si en el levels hay un 0 es porque no ha sido visitado
	        			   
	        			   levels[y.get(i)] = levels[current] + 1; // se le suma uno al nivel del nodo actual
	        			   q.add(y.get(i));
	        		   } 	        		   
	        	   }
	           }
	       }
	       
	       return levels;

    }
	
	public void addEdges(int src, int dest){
		adjList[dest].add(src);
		adjList[src].add(dest);
	}

	public LinkedList<Integer>[] getAdjList() {
		return adjList;
	}

	public void setAdjList(LinkedList<Integer>[] adjList) {
		this.adjList = adjList;
	}

}
