/**
 * 
 */
package edu.pw.gis.graph;
import java.util.*;

/**
 * @author ymir
 *
 */
public class Graph {
	public List<Node> nodeList = new ArrayList<Node>(); 
	public List<Edge> edgeList = new ArrayList<Edge>();
	
	public ArrayList<ArrayList<Float>> flowMatrix = new ArrayList<ArrayList<Float>>(); // macierz przeplywow, indeksami sa numery wezlow
	public ArrayList<ArrayList<Node>> adjList = new ArrayList<ArrayList<Node>>(); // listy sasiedztwa dla kazdego wierzcholka
	/**
	 * 
	 */
	
	public Graph() {
	}
	
	public void addNode(Node n) {
		this.nodeList.add(n.id, n);
		this.adjList.add(n.id, new ArrayList<Node>());
	}
	
	public void addEdge(Edge e) {
		this.edgeList.add(e.id, e);
		this.adjList.get(e.source.id).add(e.target);
	}
	
	public void printAdjList() {
		for (ArrayList<Node> list: this.adjList) {
			System.out.println("Node " + this.adjList.indexOf(list) + ": ");
			for (Node n: list) {
				System.out.println(n.id + ", ");
			}
			System.out.println("\n");
		}
	}
	
	
}
