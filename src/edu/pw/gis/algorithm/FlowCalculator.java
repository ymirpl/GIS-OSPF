package edu.pw.gis.algorithm;

import edu.pw.gis.graph.*;

import java.util.*;

/**
 * Klasa wyznacza przeplywy zgodnie z regulami OSPF/ECMP i podana macierza przeplywow
 */
public class FlowCalculator implements Runnable {
	public Graph g;
	private Dijkstra d;
	public boolean debug = false;

	public FlowCalculator(Graph g) {
		this.g = g;
		d = new Dijkstra(g);
	}

	public void compute() {
		for (int source = 0; source < g.flowMatrix.size(); source++) {
			ArrayList<Double> row = g.flowMatrix.get(source);
			for (int target = 0; target < g.flowMatrix.size(); target++) {
				// obliczamy odleglosci od kazdego wierzcholka docelowego przeplywy, o ile nie byl juz badany wczesniej
				if (row.get(target) > 0 && g.nodeList.get(target).done != true) {
					// we go ;)
					d.setStartNode(target);
					d.compute();
					calculateFlows(target);
					g.nodeList.get(target).done = true;
				}
			}
			
		}
		// wyznaczamy wartosc funkcji celu
		for (Edge e : g.edgeList) {
			e.usage = e.flowSum / e.capacity;
			if (e.usage > g.highestUsage) {
				g.highestUsage = e.usage;
			}
		}
	}

	private void calculateFlows(int target_id) {
		PriorityQueue<Node> Q = new PriorityQueue<Node>(g.noNodes,
				new ExtractMax());
		Q.addAll(g.nodeList);

		while (!Q.isEmpty()) {
			Node v = Q.poll(); // zaczynjac od najbardziej oddalonego wezla
							   // docelowego przeplywu

			for (NodeEdge w : g.adjList.get(v.id).list) {
				if (w.e.inTree) {
					double flow = 0;
					flow = (g.flowMatrix.get(v.id).get(target_id) + v.incomingTraffic)
							/ v.outDegree;
					w.e.flow = flow;
					w.e.target.incomingTraffic += flow;
				}
			}
		}

		// po zbadaniu danego wierzcholka koncowegp dodajemy wyznaczone
		// przeplywy do sum przeplywowo na krawedziach
		for (Edge e : g.edgeList) {
			e.flowSum += e.flow;
		}		
	}

	/**
	 * Obliczenia zostaly zamkniete w metoddzie run, aby moc je porwadzic
	 * rownolegle na wielu watkach dla kilku grafow z populacji
	 */
	@Override
	public void run() {
		this.compute();
	}

	public static void testOne() {
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

		// add demands
		g.flowMatrix.get(0).set(3, Double.parseDouble("9.0"));

		FlowCalculator c = new FlowCalculator(g);
		c.compute();

		g.printAdjList();

	}

	public static void main(String[] args) {
		testOne();
		// testTwo();
		// testThree();
	}
}
