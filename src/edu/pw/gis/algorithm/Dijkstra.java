package edu.pw.gis.algorithm;

import java.util.*;
import edu.pw.gis.graph.*;
import edu.pw.gis.parser.SNDParser;

public class Dijkstra {
	public Graph g;
	private int startNodeId = -1;
	private PriorityQueue<Node> Q;
	private ArrayList<NodeEdge> pathList;
	
	public Dijkstra(Graph g) {
		this.g = g;
		// na razie nie jest uzywane
		pathList = new ArrayList<NodeEdge>(this.g.noNodes); // indeksom w tablicy beda odpowiadac ideki nodek docelowych
		
		Q = new PriorityQueue<Node>(g.noNodes, new ExtractMin());
		
		for (int i = 0; i < g.noNodes; ++i) {
			this.pathList.add(i, new NodeEdge()); 
		}
		Q.addAll(g.nodeList);
	}

	public int getStartNode() {
		return startNodeId;
	}

	public void setStartNode(int startNodeId) {
		this.startNodeId = startNodeId;
	}
	
	public void compute() throws IllegalArgumentException {
		
		if (startNodeId < 0) {
			throw new IllegalArgumentException("Set start node first.");
		}
		
		setInfinityDistance();
		g.nodeList.get(startNodeId).distance = 0;
		
		while(!Q.isEmpty()) {
			Node u = Q.poll();
			
			if (u.distance == Integer.MAX_VALUE) {
				System.out.println("Niespojny");
				// graf niespojny
				break;
			}
			
			// dla kazdego sasiada u
			for (NodeEdge v: g.adjList.get(u.id).list) {
				int new_distance = u.distance + v.e.weight;
				if (new_distance <= v.n.distance) {
					Q.remove(v.n);
					v.n.distance = new_distance;
					u.outDegree += 1;
					v.e.inTree = true;
					Q.add(v.n);
				}
			}
		}
		
	}
	
	private void setInfinityDistance() {
		for(Node n: this.g.nodeList) {
			n.distance = Integer.MAX_VALUE;
		}
	}
	
	public static void testOne() {
		// graf typu romb 
		Graph g = new Graph(4);
		Node n = new Node(0, 0, 0, "top");
		g.addNode(n);
		n = new Node(1, 0, 0, "left");
		g.addNode(n);
		n = new Node(2, 0, 0, "right");
		g.addNode(n);
		n = new Node(3, 0, 0, "bottom");
		g.addNode(n);
		Edge e = new Edge(0, 1, g.nodeList.get(0), g.nodeList.get(1), "left-upper");
		g.addEdge(e);
		e = new Edge(1, 1, g.nodeList.get(0), g.nodeList.get(2), "right-upper");
		g.addEdge(e);
		e = new Edge(2, 1, g.nodeList.get(1), g.nodeList.get(3), "left-lower");
		g.addEdge(e);
		e = new Edge(3, 1, g.nodeList.get(2), g.nodeList.get(3), "right-lower");
		g.addEdge(e);
		
		g.printAdjList();
		
		Dijkstra d = new Dijkstra(g);
		d.setStartNode(0);
		d.compute();
		
		g.printAdjList();
	}
	
	public static void testTwo() {
		SNDParser snd_parser = new SNDParser();
		snd_parser.countNodesAndEdgesSNDNetworkXML("xml/giul39.xml");
		snd_parser.graph = new Graph(snd_parser.nodes_no);
		snd_parser.readSNDNetworkXML("xml/giul39.xml");
		
		Dijkstra d = new Dijkstra(snd_parser.graph);
		d.setStartNode(0);
		d.compute();
		snd_parser.graph.printAdjList();
		
	}
	
	public static void testThree() {
		// graf typu romb 
		Graph g = new Graph(4);
		Node n = new Node(0, 0, 0, "top");
		g.addNode(n);
		n = new Node(1, 0, 0, "left");
		g.addNode(n);
		n = new Node(2, 0, 0, "right");
		g.addNode(n);
		n = new Node(3, 0, 0, "bottom");
		g.addNode(n);
		Edge e = new Edge(0, 1, g.nodeList.get(0), g.nodeList.get(1), "left-upper");
		g.addEdge(e);
		e = new Edge(1, 1, g.nodeList.get(0), g.nodeList.get(2), "right-upper");
		g.addEdge(e);
		e = new Edge(2, 1, g.nodeList.get(1), g.nodeList.get(3), "left-lower");
		g.addEdge(e);
		e = new Edge(3, 1, g.nodeList.get(2), g.nodeList.get(3), "right-lower");
		g.addEdge(e);
		e = new Edge(4, 1, g.nodeList.get(0), g.nodeList.get(1), "leftmost-upper");
		g.addEdge(e);
		
		Dijkstra d = new Dijkstra(g);
		d.setStartNode(0);
		d.compute();
		
		g.printAdjList();
	}
	public static void main(String[] args) {
		//testOne();
		//testTwo();
		testThree();
		
		/*
		Graph g = new Graph(2);
		Node n = new Node(0, 0, 0, "janek");
		g.addNode(n);
		Node n1 = new Node(1, 0, 0, "wiesiek");
		g.addNode(n1);
		Edge e = new Edge(0, 100, n, n1, "test");
		g.addEdge(e);
		e = new Edge(1, 100, n1, n, "test");
		g.addEdge(e);
		
		Dijkstra d = new Dijkstra(g);
		d.setStartNode(0);
		d.compute();
		
		// jave refs test
		System.out.println(d.Q.peek().name);
		d.g.nodeList.get(0).name = "nowy";
		System.out.println(d.Q.peek().name);
		// end java refs test
		 */
	}

	
	
	
	
	
	

}
