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
	public float incomingTraffic;
	public int distance;
	public float draw_x; // drawing coords
	public float draw_y;
	public String name;
	
	
	public Node(int id, float draw_x, float draw_y, String name) {
		this.id = id;
		this.outDegree = -1;
		this.incomingTraffic = -1;
		this.distance = -1;
		this.draw_x = draw_x;
		this.draw_y = draw_y;
		this.name = name;
	}
	
	

}