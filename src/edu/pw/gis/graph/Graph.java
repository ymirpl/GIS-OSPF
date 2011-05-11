/**
 * 
 */
package edu.pw.gis.graph;
import java.util.*;

public class Graph {
	public List<Edge> edgeList = new ArrayList<Edge>();
	
	public int noNodes; // number of nodes
	
	public ArrayList<ArrayList<Float>> flowMatrix; // macierz przeplywow, indeksami sa numery wezlow
	public ArrayList<AdjElement> adjList; // listy sasiedztwa dla kazdego wierzcholka
	
	public Graph(int noNodes) {
		this.noNodes = noNodes;
		
		this.flowMatrix = new ArrayList<ArrayList<Float>>(this.noNodes);
		
		for (int i = 0; i < this.noNodes; ++i) {
			this.flowMatrix.add(i, new ArrayList<Float>(this.noNodes)); 
		}
		
		this.adjList = new ArrayList<AdjElement>(this.noNodes);
		
		for (int i = 0; i < this.noNodes; ++i) {
			this.adjList.add(i, new AdjElement()); 
		}
			
	}
	
	public void addNode(Node n) {
		this.adjList.get(n.id).n = n; //  zalozenie, ze przychodza wezly w kolejnosci idekow
	}
	
	public void addEdge(Edge e) {
		this.edgeList.add(e);
		this.adjList.get(e.source.id).list.add(new NodeEdge(e.target, e));
	}
	
	public void printAdjList() {
		for (AdjElement element: this.adjList) {
			System.out.print("Node " + element.n.id + ": ");
			for (NodeEdge t: element.list) {
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
		
		
		g.printAdjList();

	}
	
}
