package edu.pw.gis.graph;

import java.util.ArrayList;

public class AdjElement implements Cloneable {
	public Node n;
	public ArrayList<NodeEdge> list = new ArrayList<NodeEdge>();
	
	public AdjElement(Node n) {
		this.n = n; // target node
	}
	
	public AdjElement() {
		this.n = null;
	}
	
	@Override
	protected AdjElement clone() throws CloneNotSupportedException {
		AdjElement result = new AdjElement();
		for (NodeEdge o : list) {
			result.list.add(o.clone());
		}
		result.n = this.n.clone();
		return result;
	}
}
