package edu.pw.gis.graph;


/**
 * Struktura przechowujaca pare: krawedz i wierzcholek docelowy
 */
public class NodeEdge {
	public Node n;
	public Edge e;
	
	public NodeEdge(Node n, Edge e) {
		this.n = n; // target node
		this.e = e; // using edge
	}
	
	public NodeEdge() {
		this.n = null;
		this.e = null;
	}
}