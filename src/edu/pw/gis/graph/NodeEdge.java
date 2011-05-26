package edu.pw.gis.graph;

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
	
//	protected NodeEdge clone() throws CloneNotSupportedException {
//		NodeEdge result = (NodeEdge)super.clone();
//		return result;
//	}
	
}