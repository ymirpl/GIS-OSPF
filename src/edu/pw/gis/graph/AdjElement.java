package edu.pw.gis.graph;

import java.util.ArrayList;

/**
 * Element list sasiedztwa. Zawiera 
 */
public class AdjElement {
	public Node n;
	public ArrayList<NodeEdge> list = new ArrayList<NodeEdge>();
	
	public AdjElement(Node n) {
		this.n = n; // target node
	}
	
	public AdjElement() {
		this.n = null;
	}

}
