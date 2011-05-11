/**
 * 
 */
package edu.pw.gis.graph;
import java.util.*;

public class Graph {
	public List<Edge> edgeList = new ArrayList<Edge>();
	public ArrayList<Node> nodeArray = new ArrayList<Node>();
	
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
		this.nodeArray.add(n);
		this.adjList.get(n.id).node_id = n.id; //  zalozenie, ze przychodza wezly w kolejnosci idekow
	}
	
	public void addEdge(Edge e) {
		this.edgeList.add(e);
		this.adjList.get(e.source.id).list.add(new NodeEdge(e.target.id, e));
	}
	
	public void printAdjList() {
		for (AdjElement element: this.adjList) {
			System.out.print("Node " + this.nodeArray.get(element.node_id).name + ": ");
			for (NodeEdge t: element.list) {
				System.out.println(t.node_id  + "(" + t.e.id + "), ");
			}
		}
	}
	
	/*
	 * 	public int outDegree;
	public double incomingTraffic;
	public int distance;
	public double draw_x; // drawing coords
	public double draw_y;
	public String name;
	 */
	
	public void setOutDegree(int id, int outDegree) {
		this.nodeArray.get(id).outDegree = outDegree;
	}
	
	public void setDistance(int id, int d) {
		this.nodeArray.get(id).distance = d;
	}
	
	public void setDrawX(int id, int d) {
		this.nodeArray.get(id).distance = d;
	}

	
	
	public static void main(String[] args) {
/*
 
		Node n = new Node(0, 0, 0, "janek");
		g.addNode(n);
		Node n1 = new Node(1, 0, 0, "wiesiek");
		g.addNode(n1);
		Node n3 = new Node(2, 0, 0, "ziutek");
		g.addNode(n3);
		Edge e = new Edge(0, 100, n, n1);
		g.addEdge(e);
		e = new Edge(1, 100, n1, n);
		g.addEdge(e);
		
		
		g.printAdjList();
*/
		
		Node[] a1 = new Node[1];
		Node[] a2 = new Node[1];
		
		Node n = new Node(0, 0, 0, "janek");
		a1[0] = n;
		n.name = "wiesiek";
		a2[0] = n;
		
		System.out.println(a1[0].name);
		
		
	}
	
}
