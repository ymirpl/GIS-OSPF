package edu.pw.gis.algorithm;

import java.util.ArrayList;
import java.util.Random;

import edu.pw.gis.graph.Edge;
import edu.pw.gis.graph.Graph;
import edu.pw.gis.parser.SNDParser;

public class Genetic {

	public final int MAX_WEIGHT; // maksymalna waga krawêdzi
	public final int INITIAL_POPULATION_NO; // liczba populacji (iloœæ wektorów)
	public final double ALFA_RATE; // wspó³czynnik wyboru klasy A
	public final double BETA_RATE; // wspó³czynnik wyboru klasy B
	public final double MUTATION_RATE; // wspó³czynnik mutacji
	public final double CROSS_RATE; // wspó³czynnik krzy¿owania
	public final double MAX_USAGE; // Po¿¹dane maksymalne procentowe obci¹¿enie
									// krawêdzi
	public final double MAX_ITERATION_NO; // Maksymalna iloœæ iteracji (iloœæ
											// pokoleñ)
	public ArrayList<Graph> graph_list; // badany graf

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
		this.graph_list = new ArrayList<Graph>();
	}

	public void createInitialPopulation(Graph g) throws CloneNotSupportedException {
		//TODO tutaj potrzebne g³êbokie kopiowanie!!!
		Random rand = new Random();
		for (int i = 0; i < this.INITIAL_POPULATION_NO; i++) {
			Graph tmp = (Graph) g.clone();
			for (int j = 0; j < tmp.edgeList.size(); j ++) {
				Edge e = tmp.edgeList.get(j); 
				e.weight = (int)(rand.nextInt(this.MAX_WEIGHT+1) / e.capacity); 
			}
			this.graph_list.add(tmp);
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
		
		Genetic genetic = new Genetic(10, 2, 0.2, 0.7, 0.01, 0.5, 0.5, 100);
		try {
			genetic.createInitialPopulation(snd_parser.graph);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<genetic.INITIAL_POPULATION_NO; i++)
		{
			System.out.println("Graf populacji " + i);
			System.out.println();
			genetic.graph_list.get(i).printAdjList();
		}
		
		//genetic.graph_list.get(0).addNode(new Node());
		genetic.graph_list.get(1).nodeList.get(0).name="DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDddd";
		
		for(int i=0; i<genetic.INITIAL_POPULATION_NO; i++)
		{
			System.out.println("Graf populacji " + i);
			System.out.println();
			System.out.println(genetic.graph_list.get(i).nodeList.get(0).name);
			genetic.graph_list.get(i).printAdjList();
		}
	}

}
