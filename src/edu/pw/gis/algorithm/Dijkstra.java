package edu.pw.gis.algorithm;

import java.util.*;
import edu.pw.gis.graph.*;

public class Dijkstra {
	private Graph g;
	private Node startNode;
	private PriorityQueue<Node> Q = new PriorityQueue<Node>();
	public ArrayList<Tuple> pathList;
	
	public Dijkstra(Graph g) {
		this.g = g;
		pathList = new ArrayList<Tuple>(this.g.noNodes);
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	public void compute() {
		
		
	}
	
	
	
	
	
	

}
