package edu.pw.gis.algorithm;

import java.util.*;
import edu.pw.gis.graph.*;

public class Dijkstra {
	private Graph g;
	private Node startNode;
	private PriorityQueue<Node> Q = new PriorityQueue<Node>();
	public ArrayList<NodeEdge> pathList;
	
	public Dijkstra(Graph g) {
		this.g = g;
		pathList = new ArrayList<NodeEdge>(this.g.noNodes); // indeksom w tablicy beda odpowiadac ideki nodek docelowych
		
		for (int i = 0; i < g.noNodes; ++i) {
			this.pathList.add(i, new NodeEdge()); 
		}
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
