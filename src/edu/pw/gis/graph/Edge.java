/**
 * 
 */
package edu.pw.gis.graph;

/**
 * @author ymir
 *
 */
public class Edge implements Cloneable{
	public double capacity; // pojemnosc
	public int weight; // waga
	public double flow; // bierzacy przeplyw
	public double usage; // procentowe uzycie
	public Node source;
	public Node target;
	public boolean inTree; // czy nalezy do grafu skroconego wlasnie rowazanego
	public int id;
	public String name;
	
	public Edge(int id, double capacity, Node source, Node target, String name) {
		this.id = id;
		this.capacity = capacity;
		this.weight = 1;
		this.flow = 0;
		this.usage = 0;
		this.source = source;
		this.target = target;
		this.inTree = false;
		this.name = name;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Edge result = (Edge)super.clone();
		return result;
	}

}
