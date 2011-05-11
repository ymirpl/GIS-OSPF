/**
 * 
 */
package edu.pw.gis.graph;

/**
 * @author ymir
 *
 */
public class Edge {
	public float capacity; // pojemnosc
	public int weight; // waga
	public float flow; // bierzacy przeplyw
	public float usage; // procentowe uzycie
	public Node source;
	public Node target;
	public boolean inTree; // czy nalezy do grafu skroconego wlasnie rowazanego
	public int id;
	
	public Edge(int id, float capacity, Node source, Node target) {
		this.id = id;
		this.capacity = capacity;
		this.weight = 1;
		this.flow = 0;
		this.usage = 0;
		this.source = source;
		this.target = target;
		this.inTree = false;
	}
	
	

}
