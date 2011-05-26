/**
 * 
 */
package edu.pw.gis.graph;

public class Edge {
	public double capacity; // pojemnosc
	public int weight; // waga
	public double flow; // bierzacy przeplyw
	public double usage; // procentowe uzycie
	public Node source; // wierzcholek poczatkowy
	public Node target; // wierzcholek docelowy
	public boolean inTree; // czy nalezy do grafu skroconego wlasnie rowazanego
	public int id; // indetyfikator (musi byc zgodny z kolejny miejscem we
					// wszelkich tablicach)
	public String name; // nazwa (nieuzywane)
	public double flowSum; // calkowity przeplyw

	public Edge(int id, double capacity, Node source, Node target, String name) {
		this.id = id;
		this.capacity = capacity;
		this.weight = 1;
		this.flow = 0;
		this.usage = 0;
		this.flowSum = 0;
		this.source = source;
		this.target = target;
		this.inTree = false;
		this.name = name;
	}

	public Edge(int id, double capacity, Node source, Node target, String name,
			double usage) {
		this.id = id;
		this.capacity = capacity;
		this.weight = 1;
		this.flow = 0;
		this.usage = 0;
		this.flowSum = 0;
		this.source = source;
		this.target = target;
		this.inTree = false;
		this.name = name;
		this.usage = usage;
	}
}
