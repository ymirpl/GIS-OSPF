package edu.pw.gis.algorithm;

import java.util.ArrayList;
import java.util.Random;

import edu.pw.gis.graph.Edge;
import edu.pw.gis.graph.Graph;
import edu.pw.gis.parser.SNDParser;

public class Genetic {

	public final int MAX_WEIGHT; // maksymalna waga kraw�dzi
	public final int INITIAL_POPULATION_NO; // liczba populacji (ilo�� wektor�w)
	public final double ALFA_RATE; // wsp�czynnik wyboru klasy A
	public final double BETA_RATE; // wsp�czynnik wyboru klasy B
	public final double MUTATION_RATE; // wsp�czynnik mutacji
	public final double CROSS_RATE; // wsp�czynnik krzy�owania
	public final double MAX_USAGE; // Po��dane maksymalne procentowe obci��enie
									// kraw�dzi
	public final double MAX_ITERATION_NO; // Maksymalna ilo�� iteracji (ilo��
											// pokole�)
	public ArrayList<Graph> GRAPH_LIST; // badany graf

	public Genetic(int max_weight, int initial_population,
			double alfa_rate, double beta_rate, double mutation_rate,
			double cross_rate, double max_usage, double max_iteration_no) {
		this.MAX_WEIGHT = max_weight;
		this.INITIAL_POPULATION_NO = initial_population;
		this.ALFA_RATE = alfa_rate;
		this.BETA_RATE = beta_rate;
		this.MUTATION_RATE = mutation_rate;
		this.CROSS_RATE = cross_rate;
		this.MAX_USAGE = max_usage;
		this.MAX_ITERATION_NO = max_iteration_no;
		this.GRAPH_LIST = new ArrayList<Graph>();
	}

	public void createInitialPopulation(Graph g) {
		//TODO tutaj potrzebne g��bokie kopiowanie!!!
		Graph tmp = g;
		Random rand = new Random();
		for (int i = 0; i < this.INITIAL_POPULATION_NO; i++) {
			for (int j = 0; j < tmp.edgeList.size(); j ++) {
				Edge e = tmp.edgeList.get(j); 
				e.weight = (int)(rand.nextInt(this.MAX_WEIGHT+1) / e.capacity); 
			}
			this.GRAPH_LIST.add(tmp);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SNDParser snd_parser = new SNDParser();
		snd_parser.countNodesAndEdgesSNDNetworkXML("xml/test.xml");
		
		snd_parser.graph = new Graph(snd_parser.nodes_no);
		snd_parser.readSNDNetworkXML("xml/test.xml");
		
		Genetic genetic = new Genetic(10, 10, 0.2, 0.7, 0.01, 0.5, 0.5, 100);
		genetic.createInitialPopulation(snd_parser.graph);
	}

}
