package edu.pw.gis.graph;

import java.util.ArrayList;

public class AdjElement {
	public Node n;
	public ArrayList<NodeEdge> list = new ArrayList<NodeEdge>();
	
	public AdjElement(Node n) {
		this.n = n; // target node
	}
	
	public AdjElement() {
		this.n = new Node();
	}
}
