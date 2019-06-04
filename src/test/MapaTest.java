package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import model.Mapa;

public class MapaTest {
	
	private Mapa mapa;
	
	private void setupStage1(){
		mapa = new Mapa();
		mapa.generateMatriz();
		int[][] graph = new int[][]{
			{0, 4, 0, 0, 0, 0, 0, 8, 0}, 
            {4, 0, 8, 0, 0, 0, 0, 11, 0}, 
            {0, 8, 0, 7, 0, 4, 0, 0, 2}, 
            {0, 0, 7, 0, 9, 14, 0, 0, 0}, 
            {0, 0, 0, 9, 0, 10, 0, 0, 0}, 
            {0, 0, 4, 14, 10, 0, 2, 0, 0}, 
            {0, 0, 0, 0, 0, 2, 0, 1, 6}, 
            {8, 11, 0, 0, 0, 0, 1, 0, 7}, 
            {0, 0, 2, 0, 0, 0, 6, 7, 0} 
           }; 
		mapa.setAdjMatriz(graph);
	}
	
	private void setupStage2(){
		setupStage1();
		
		// se va a crear la lista de adj desde la matriz creada
		
		int [][] graph = mapa.getAdjMatriz();		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[graph.length]; // la lista tiene el mismo tamano de la matriz ya que es cuadrada
		
		for(int i = 0; i < adjList.length; i++)		// inicializo las array de cada espacio del areglo
            adjList[i] = new LinkedList<Integer>();
		
		for(int i = 0; i < adjList.length; i++)		// tomo el valor en 
			for(int j = 0; j < adjList.length; j++)	// y lo agrego en el arreglo en la posicion [i]
				//mapa.addEdges(i, graph[i][j]);
				if(graph[i][j] != 0){
					//adjList[i].set(j,graph[i][j]);	// en la posicion j del array
					adjList[i].add(graph[i][j]);
				}
		
		mapa.setAdjList(adjList);
		
	}
	
	@Test
	public void testDijkstra1() {
		setupStage1();
		int[][] graph = mapa.getAdjMatriz();
		int[] dist = mapa.dijkstra(graph, 0, graph);
		assertTrue(dist[2] == 12);
	}

	@Test
	public void testDijkstra2() {
		setupStage1();
		int[][] graph = mapa.getAdjMatriz();
		int[] dist = mapa.dijkstra(graph, 0, graph);
		assertTrue(dist[4] == 21);
	}
	
	@Test
	public void testBFS1(){
		setupStage2();
		
		int[] levels = mapa.BFS(8, mapa.getAdjList());
		assertTrue(levels[6] == 1);
		
	}
	
	@Test
	public void testBFS2(){
		setupStage2();
		
		int[] levels = mapa.BFS(0, mapa.getAdjList());
		assertTrue(levels[4] == 4);
		
	}

}
