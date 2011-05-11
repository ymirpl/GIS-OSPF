package edu.pw.gis.graph;

public class NodeEdge {
	public int node_id;
	public Edge e;
	
	public NodeEdge(int node_id, Edge e) {
		this.node_id = node_id; // target node
		this.e = e; // using edge
	}
	
}