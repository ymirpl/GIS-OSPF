package edu.pw.gis.algorithm;

import java.util.*;
import edu.pw.gis.graph.*;
import edu.pw.gis.parser.SNDParser;

/**
 * Klasa zawiera algorytm Dijkstry
 */
public class Dijkstra {
	public Graph g;
	private int startNodeId = -1;
	private PriorityQueue<Node> Q;
	public boolean debug;

	public Dijkstra(Graph g) {
		this.g = g;
		Q = new PriorityQueue<Node>(g.noNodes, new ExtractMin());
		Q.addAll(g.nodeList);
	}

	public int getStartNode() {
		return startNodeId;
	}

	public void setStartNode(int startNodeId) {
		this.startNodeId = startNodeId;
	}

	public void compute() throws IllegalArgumentException {
		HashMap<Node, Edge> parallel = new HashMap<Node, Edge>();
		// trzymamy rozwiazania dla krawedzi rownoleglych. Pod indeksem wezla
		// dcelowego znajduje sie prowadzaca do niego krawedz
		if (startNodeId < 0) {
			throw new IllegalArgumentException("Set start node first.");
		}

		resetMeasures(); // wyczyszczenie wszelkich wlasnosci krawedzi

		Node startNode = g.nodeList.get(startNodeId);
		Q.remove(startNode);
		startNode.distance = 0;
		Q.add(startNode);

		while (!Q.isEmpty()) {
			Node u = Q.poll();

			if (u.distance == Integer.MAX_VALUE) {
				if (this.debug)
					System.err
							.println("Wierzcholek -- slepa uliczka. Graf niespojny?");
				break;
			}

			// dla kazdego sasiada u
			for (NodeEdge v : g.revertedAdjList.get(u.id).list) {
				// uzyj krawdzi o kierunkach odwroconych
				int new_distance = u.distance + v.e.weight;
				if (new_distance <= v.n.distance) {

					if (parallel.containsKey(v.n)
							&& new_distance < v.n.distance) {
						// juz jest krawedz to tego wierzcholka, trzeba ja
						// usunac z Tree i nie dodawac outDegree, ale tylko
						// jezeli nie bedzie to krawedz tak samo dobra jak, ta
						// co jest. Wtedy zachowac nalezy obie, podzieli sie
						// przez nie przeplyw ECMP.
						parallel.get(v.n).inTree = false;
					} else {
						v.n.outDegree += 1;
					}

					v.e.inTree = true;
					parallel.put(v.n, v.e); // do tego wezla prowadzi taka
											// krawedz najlepiej

					if (new_distance < v.n.distance) {
						Q.remove(v.n); // konieczne, aby zaktualizowac kolejke
						v.n.distance = new_distance;
						Q.add(v.n);
					}
				}
			}
		}

	}

	/**
	 * Wyzeruj wlasciwosci krawedzi
	 */
	private void resetMeasures() {
		for (Node n : this.g.nodeList) {
			n.distance = Integer.MAX_VALUE;
			n.outDegree = 0;
			n.incomingTraffic = 0;
		}
		for (Edge e : this.g.edgeList) {
			e.inTree = false;
			e.flow = 0;
			e.usage = 0;
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
		Edge e = new Edge(0, 1, g.nodeList.get(0), g.nodeList.get(1),
				"left-upper");
		g.addEdge(e);
		e = new Edge(1, 1, g.nodeList.get(0), g.nodeList.get(2), "right-upper");
		g.addEdge(e);
		e = new Edge(2, 1, g.nodeList.get(1), g.nodeList.get(3), "left-lower");
		g.addEdge(e);
		e = new Edge(3, 1, g.nodeList.get(2), g.nodeList.get(3), "right-lower");
		g.addEdge(e);

		g.printAdjList();

		Dijkstra d = new Dijkstra(g);
		d.setStartNode(3);
		d.compute();

		g.printAdjList();
	}

	public static void testTwo() {
		SNDParser snd_parser = new SNDParser("xml/giul39.xml");

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
		Edge e = new Edge(0, 1, g.nodeList.get(0), g.nodeList.get(1),
				"left-upper");
		g.addEdge(e);
		e = new Edge(1, 1, g.nodeList.get(0), g.nodeList.get(2), "right-upper");
		g.addEdge(e);
		e = new Edge(2, 1, g.nodeList.get(1), g.nodeList.get(3), "left-lower");
		g.addEdge(e);
		e = new Edge(3, 1, g.nodeList.get(2), g.nodeList.get(3), "right-lower");
		g.addEdge(e);
		e = new Edge(4, 1, g.nodeList.get(0), g.nodeList.get(1),
				"leftmost-upper");
		g.addEdge(e);

		g.printRevertedAdjList();

		Dijkstra d = new Dijkstra(g);
		d.setStartNode(1);
		d.compute();

		g.printAdjList();
	}

	public static void testSNDGraph() {
		SNDParser snd_parser = new SNDParser("xml/simple_test_graph.xml");

		Dijkstra d = new Dijkstra(snd_parser.graph);
		d.setStartNode(7);
		d.compute();

		snd_parser.graph.printAdjList();
	}

	/**
	 * Testy
	 */
	public static void main(String[] args) {
		// testOne();
		// testTwo();
		testThree();
		// testSNDGraph();
	}

}
