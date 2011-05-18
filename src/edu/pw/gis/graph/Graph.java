/**
 * 
 */
package edu.pw.gis.graph;
import java.util.*;

public class Graph {
	public List<Edge> edgeList = new ArrayList<Edge>();
	public List<Node> nodeList = new ArrayList<Node>();

	public int noNodes; // number of nodes

	public ArrayList<ArrayList<Float>> flowMatrix; // macierz przeplywow,
													// indeksami sa numery
													// wezlow
	public ArrayList<AdjElement> adjList; // listy sasiedztwa dla kazdego
											// wierzcholka
	public ArrayList<AdjElement> revertedAdjList; // lista sasiedztwa, w ktorej kierunki krawedzi wszelkich sa owdrocone 

	public Graph(int noNodes) {
		this.noNodes = noNodes;

		this.flowMatrix = new ArrayList<ArrayList<Float>>(this.noNodes);

		for (int i = 0; i < this.noNodes; ++i) {
			this.flowMatrix.add(i, new ArrayList<Float>(this.noNodes));
			for (int j = 0; j < this.noNodes; ++j) {
				this.flowMatrix.get(i).add(j, Float.parseFloat("0.0"));
			}
		}

		this.adjList = new ArrayList<AdjElement>(this.noNodes);
		this.revertedAdjList = new ArrayList<AdjElement>(this.noNodes);

		for (int i = 0; i < this.noNodes; ++i) {
			this.adjList.add(i, new AdjElement());
			this.revertedAdjList.add(i, new AdjElement());
		}
		
	}
	
	public double getHighestUsage() {
		double usage = 0;
		for (Edge e: edgeList) {
			if (e.usage > usage) {
				usage = e.usage;
			}
		}
		return usage;
	}

	public void addNode(Node n) {
		this.nodeList.add(n);
		this.adjList.get(n.id).n = n; //  zalozenie, ze przychodza wezly w kolejnosci idekow
		this.revertedAdjList.get(n.id).n = n;
	}

	public void addEdge(Edge e) {
		this.edgeList.add(e);
		this.adjList.get(e.source.id).list.add(new NodeEdge(e.target, e));
		this.revertedAdjList.get(e.target.id).list.add(new NodeEdge(e.source, e));
	}

	public void printAdjList() {
		for (AdjElement element: this.adjList) {
			System.out.print("Node " + element.n.name + " outD: " + element.n.outDegree +" :: " );
			for (NodeEdge t: element.list) {
				System.out.println(t.n.name  + "(" + t.e.id + "), inTree:" + t.e.inTree + " flow:" + t.e.flow);
			}
		}
		System.out.println("");
	}
	
	public void printRevertedAdjList() {
		for (AdjElement element: this.revertedAdjList) {
			System.out.print("Node " + element.n.name + " outD: " + element.n.outDegree +" :: " );
			for (NodeEdge t: element.list) {
				System.out.println(t.n.name  + "(" + t.e.id + "), inTree:" + t.e.inTree + " ");
			}
		}
		System.out.println("");
	}
	
	public void cleanDistances() {
		// reverts all node distances to MAX_INT, so that we can compute Dijkstra for other starting nodes
		for (int i = 0; i < this.noNodes; ++i) {
			nodeList.get(i).distance = Integer.MAX_VALUE;
		}
	}
	
	

	public static void main(String[] args) {
		Graph g = new Graph(2);
		Node n = new Node(0, 0, 0, "janek");
		g.addNode(n);
		Node n1 = new Node(1, 0, 0, "wiesiek");
		g.addNode(n1);
		Edge e = new Edge(0, 100, n, n1, "test");
		g.addEdge(e);
		e = new Edge(1, 100, n1, n, "test");
		g.addEdge(e);
		
		g.printRevertedAdjList();
		g.printAdjList();
		System.out.println(g.nodeList.get(1).name);

		// zmieniamy via edge
		g.adjList.get(0).list.get(0).n.name = "zmieniony";
		
		
		g.printAdjList();
		System.out.println(g.nodeList.get(1).name);


	}

}
