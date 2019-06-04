package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Algoritmos {
	
	public ArrayList<Integer> adjacents(int node, int[][] matrix){
		
		ArrayList<Integer> adj = new ArrayList<>();
		
		for (int i = 0; i < matrix.length; i++) {
			if(matrix[node][i] > 0){
				adj.add(i);
			}
		}
		return adj;
	}
	
	public ArrayList<Integer> bfs(int n, int[][] matrix){
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		boolean visited[] = new boolean[matrix.length]; 
		
		queue.add(n);
		visited[n] = true; 
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		while(!queue.isEmpty()){
			n = queue.poll();
			array.add(n);
			
			ArrayList<Integer> adj = adjacents(n, matrix);
			
			for (int i = 0; i < adj.size(); i++) {
				int s = adj.get(i);
				if(!visited[s]){
					visited[s] = true;
					queue.add(s);
				}
			}
		}
		return array;
	}
	
	public int[] bfsLevels(int n, int[][] matrix){
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int[] levels = new int[matrix.length];
		boolean visited[] = new boolean[matrix.length]; 
		
		queue.add(n);
		visited[n] = true; 
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		while(!queue.isEmpty()){
			n = queue.poll();
			array.add(n);
			
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
	
	public void dfs2(int n, int[][] matrix, boolean[] visited, ArrayList<Integer> array){
		
		visited[n] = true;
		
		//ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(n);
		
		ArrayList<Integer> adj = adjacents(n, matrix);
		
		for (int i = 0; i < adj.size(); i++) {
			int s = adj.get(i);
			if(!visited[s]){
				//ArrayList<Integer> arrays = dfs2(s, matrix, visited, array);
				dfs2(s, matrix, visited, array);
//				for (int j = 0; j < arrays.size(); j++) {
//					array.add(arrays.get(j));
//				}
			}
		}
		
		//return array;
	}
	
	public ArrayList<Integer> dfs(int n, int[][] matrix){
		boolean[] visited = new boolean[matrix.length];
		ArrayList<Integer> array = new ArrayList<Integer>();
		dfs2(n, matrix, visited, array);
		return array;
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
	        for (int i = 1; i < graph.length; i++) 
	            System.out.println(parent[i]+" - "+ i+"\t"+ graph[i][parent[i]]); 
	}
	
	public void prim(int[][] matrix){
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
		printMST(mst, matrix.length, matrix);
	}
	
	public int minVertex(int[] weight, boolean[] inMst){
		int min = Integer.MAX_VALUE;
		int min2 = -1;
		for (int i = 0; i < inMst.length; i++) {
			if(inMst[i] == false && weight[i] < min){
				min = weight[i];
				min2 = i;
			}
		}
		return min2;
	}
		
	public int[] dijkstra(int[][] matrix, int vertex){ 
		int[] distancias = new int[matrix.length];
			
		boolean inMst[] = new boolean[matrix.length]; 
	 
		for (int i = 0; i < matrix.length; i++) { 
			distancias[i] = Integer.MAX_VALUE; 
			inMst[i] = false; 
		} 
			
		distancias[vertex] = 0; 
		for (int i = 0; i < matrix.length - 1; i++) {  
			int u = minVertex(distancias, inMst); 
			inMst[u] = true; 
			for (int j = 0; j < matrix.length; j++) {
				if (!inMst[j] && matrix[u][j] != 0 && distancias[u] != Integer.MAX_VALUE && distancias[u] + matrix[u][j] < distancias[j]) {
					distancias[j] = distancias[u] + matrix[u][j]; 
				}				
			}
		} 
		return distancias;
	} 
	
	public int[][] floydWarshall(int[][] matrix){
		int[][] fw = new int[matrix.length][matrix.length];
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				fw[i][j] = matrix[i][j];
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				for (int j2 = 0; j2 < matrix.length; j2++) {
					if(fw[j][i] + fw[i][j2] < fw[j][j2]){
						fw[j][j2] = fw[j][i] + fw[i][j2];
					}
					
				}
			}
		}
		return fw;
	}
	
	public static void main(String[] args) {
		
	}

}
