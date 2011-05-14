/**
 * 
 */
package edu.pw.gis.graph;

/**
 * @author ymir
 * 
 */
public class Node {
	public int id;
	public int outDegree;
	public double incomingTraffic;
	public int distance;
	public double draw_x; // drawing coords
	public double draw_y;
	public String name;

	public Node() {
		this.id = -1;
		this.outDegree = 0;
		this.incomingTraffic = -1.0;
		this.distance = -1;
		this.draw_x = -1.0;
		this.draw_y = -1.0;
		this.name = "";
	}

	public Node(int id, double draw_x, double draw_y, String name) {
		this.id = id;
		this.outDegree = 0;
		this.incomingTraffic = -1;
		this.distance = -1;
		this.draw_x = draw_x;
		this.draw_y = draw_y;
		this.name = name;
	}

	/*
	public int compareTo(Node anotherNode) {
		// PriorityQueue bierze najpierw te z wyzszym, wiec trzeba to odkrecic na potrzeby Q w dijkstrze @TODO ???
		return -(this.distance - anotherNode.distance);
	}
	*/
}