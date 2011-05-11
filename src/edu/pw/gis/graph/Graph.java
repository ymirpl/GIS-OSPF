/**
 * 
 */
package edu.pw.gis.graph;
import java.util.*;

public class Graph {
	public List<Node> nodeList = new ArrayList<Node>();
	public List<Edge> edgeList = new ArrayList<Edge>();
	
	public int noNodes; // number of nodes
	
	public ArrayList<ArrayList<Float>> flowMatrix; // macierz przeplywow, indeksami sa numery wezlow
	public ArrayList<ArrayList<Tuple>> adjList; // listy sasiedztwa dla kazdego wierzcholka
	
	public Graph(int noNodes) {
		this.noNodes = noNodes;
		
		this.flowMatrix = new ArrayList<ArrayList<Float>>(this.noNodes);
		
		for (int i = 0; i < this.noNodes; ++i) {
			this.flowMatrix.add(i, new ArrayList<Float>(this.noNodes)); 
		}
		
		this.adjList = new ArrayList<ArrayList<Tuple>>(this.noNodes);
		
		for (int i = 0; i < this.noNodes; ++i) {
			this.adjList.add(i, new ArrayList<Tuple>()); 
		}
			
	}
	
	public void addNode(Node n) {
		this.nodeList.add(n); // @TODO zalozenie, ze przychodza wezly w kolejnosci ideksow
	}
	
	public void addEdge(Edge e) {
		this.edgeList.add(e);
		this.adjList.get(e.source.id).add(new Tuple(e.target, e));
	}
	
	public void printAdjList() {
		for (ArrayList<Tuple> list: this.adjList) {
			System.out.print("Node " + this.adjList.indexOf(list) + ": ");
			for (Tuple t: list) {
				System.out.println(t.n.id  + "(" + t.e.id + "), ");
			}
		}
	}
	
	public static void main(String[] args) {
		Graph g = new Graph(2);
		Node n = new Node(0, 0, 0, "janek");
		g.addNode(n);
		Node n1 = new Node(1, 0, 0, "wiesiek");
		g.addNode(n);
		Edge e = new Edge(0, 100, n, n1);
		g.addEdge(e);
		e = new Edge(1, 100, n1, n);
		g.addEdge(e);
		
		g.nodeList.get(1).name = "aaaa";
		System.out.println(g.adjList.get(0).get(0).n.name);
		
		g.printAdjList();

	}
	
}
