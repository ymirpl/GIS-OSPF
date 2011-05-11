package edu.pw.gis.graph;

import java.util.ArrayList;

public class AdjElement {
	public int node_id;
	public ArrayList<NodeEdge> list = new ArrayList<NodeEdge>();
	
	public AdjElement(int node_id) {
		this.node_id = node_id; // target node
	}
	
	public AdjElement() {
		this.node_id = -1;
	}
}
