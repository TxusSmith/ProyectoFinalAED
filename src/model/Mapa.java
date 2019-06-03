package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Mapa {
	
	private Jugador jugador;
	private Pantalla[] pantalla;

	private int[][] adjMatrix;
	private int[][] weight;
	private boolean[][] visit;
	
	private int pointsToWin;
	
	private LinkedList<Integer>[] adjList;
	
	public Mapa() {
		generateMatriz();
		pantalla = new Pantalla[adjMatrix.length];
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
		arreglarXY();
	}
	
	public void arreglarXY() {
		int[] xn = new int[pantalla.length];
		int[] yn = new int[pantalla.length];
		
		for (int i = 0; i < xn.length; i++) {
	    	xn[i] = pantalla[i].getX();
		}
		for (int i = 0; i < yn.length; i++) {
			yn[i] = pantalla[i].getY();
		}
		
		for (int i = 0; i < xn.length; i++) {
		    for (int k = i + 1; k < xn.length; k++) {
		        if (xn[k] <= xn[i]+16 && xn[k] >= xn[i]-16) {
		        	xn[k] += 32;
					pantalla[k].setX(xn[k]);
		        }
		        if (yn[k] <= yn[i]+16 && yn[k] >= yn[i]-16) {
		        	yn[k] += 32;
					pantalla[k].setY(yn[k]);
		        }
		    }
		}
	}
	
	public boolean moverONoJugador(int n) {
		boolean mover = false;
		int x = jugador.getNivelActual();
		if(adjMatrix[x][n]>0 && weight[x][n]<=jugador.getCoins()) {
			mover = true;
		}
		return mover;
	}
	
	public void edgeUpdate(int n) {
		int x = jugador.getNivelActual();
		jugador.restarCoins(weight[x][n]);
		jugador.setPuntuacion(jugador.getPuntuacion()+weight[x][n]);
		weight[x][n] = 0;
		weight[n][x] = 0;
		visit[x][n] = true;
		visit[n][x] = true;
	}
	
	public void pantallaCompletada(int n) {
		pantalla[n].completado();
		//jugador.sumarCoins(25+pantalla[n].getLevel());
		jugador.sumarCoins(25);
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
				int cost = ThreadLocalRandom.current().nextInt(25, 35);
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
		puntaje();
		print(adjMatrix);
		print(weight);
	}
	
	public void puntaje() {
		int prim = prim(weight);
		System.out.println(prim);
		int coins = 25*(adjMatrix.length+1);
		System.out.println(coins);
		pointsToWin = (int) (((double) coins/(double) prim)*10000);
		System.out.println(pointsToWin);
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
	
	public int minVertex(int[] weight, boolean[] inMst, int vertexs){
		int min = Integer.MAX_VALUE;
		int min2 = -1;
		for (int i = 0; i < vertexs; i++) {
			if(inMst[i] == false && weight[i] < min){
				min = weight[i];
				min2 = i;
			}
		}
		return min2;
	}
	
	public void printMST(int parent[], int n, int graph[][]){ 
	        System.out.println("Edge \tWeight"); 
	        for (int i = 1; i < graph.length; i++) {
	            System.out.println(parent[i]+" - "+ i+"\t"+ graph[i][parent[i]]); 
	        }
	}
	
	public int totalPrim(int parent[], int n, int graph[][]) {
		int total = 0;
		for (int i = 1; i < graph.length; i++) {
            total += graph[i][parent[i]]; 
        }
		return total;
	}
	
	public int prim(int[][] matrix){
		int[] mst = new int[matrix.length];
		int[] weight = new int[matrix.length];
		boolean[] inMst = new boolean[matrix.length];
		
		for (int i = 0; i < matrix.length; i++) { 
			weight[i] = Integer.MAX_VALUE; 
			inMst[i] = false; 
        }
		
		weight[0] = 0; 
		mst[0] = -1;
		
		for (int i = 0; i < matrix.length-1; i++) {
			int u = minVertex(weight, inMst, matrix.length);
			inMst[u] = true;
			for (int j = 0; j < matrix.length; j++) {
				if(matrix[u][j] != 0 && inMst[j] == false && matrix[u][j] < weight[j]){
					mst[j] = u;
					weight[j] = matrix[u][j];
				}
			}
		}
//		printMST(mst, matrix.length, matrix);
		return totalPrim(mst, matrix.length, matrix);
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
	
	//-------------------------------------------------------------------------------------------------------
	
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
