/**
 * 
 */
package edu.pw.gis.graph;

/**
 * @author ymir
 * 
 */
public class Node{
	public int id; // identyfikator
	public int outDegree; // stopien wychodzacy po obliczeniu najkrotszych sciezek, konieczny do dzielenie przeplywu zgodnie z ECMP 
	public double incomingTraffic; // przeplyw wchodzacy
	public int distance; // odleglsc wyznaczone dijkstra
	public double draw_x; // koordynaty rysowania (nieuzywane)
	public double draw_y; // 
	public String name; // nazwa do wyswietlania
	public boolean done = false; // czy wierzchoelek byl juz przeliczony jako docelowy we flowCalculator

	public Node() {
		this.id = -1;
		this.outDegree = 0;
		this.incomingTraffic = 0.0;
		this.distance = 0;
		this.draw_x = -1.0;
		this.draw_y = -1.0;
		this.name = "";
	}

	public Node(int id, double draw_x, double draw_y, String name) {
		this.id = id;
		this.outDegree = 0;
		this.incomingTraffic = 0.0;
		this.distance = 0;
		this.draw_x = draw_x;
		this.draw_y = draw_y;
		this.name = name;
	}
}