package edu.pw.gis.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import edu.pw.gis.graph.Edge;
import edu.pw.gis.graph.Graph;
import edu.pw.gis.parser.SNDParser;

public class Genetic {

	public final int MAX_WEIGHT; // maksymalna waga kraw_dzi
	public final int INITIAL_POPULATION_NO; // liczba populacji (ilo__ wektor_w)
	public final double ALFA_RATE; // wsp_czynnik wyboru klasy A
	public final double BETA_RATE; // wsp_czynnik wyboru klasy B
	public final double MUTATION_RATE; // wsp_czynnik mutacji
	public final double CROSS_RATE; // wsp_czynnik krzy_owania
	public final double MAX_USAGE; // Po__dane maksymalne procentowe obci__enie
									// kraw_dzi
	public final double MAX_ITERATION_NO; // Maksymalna ilo__ iteracji (ilo__
											// pokole_)
	public ArrayList<Graph> graph_list; // badany graf
	public ArrayList<Graph> new_graph_list; // badany graf

	
	private int cardA; // liczba osobnikow w klasie A
	private int cardB; // liczba osobnikow w klasie B
	
	

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
		this.new_graph_list = new ArrayList<Graph>();
			
		this.cardA = (int) Math.floor(ALFA_RATE * INITIAL_POPULATION_NO);
		this.cardB = (int) Math.ceil(BETA_RATE * INITIAL_POPULATION_NO);
		
	}

	public void createInitialPopulation(Graph g) throws CloneNotSupportedException {
		//TODO tutaj potrzebne g__bokie kopiowanie!!!
		for (int i = 0; i < this.INITIAL_POPULATION_NO; i++) {
			Graph tmp = generateCloneWithRandomWeights(g);
			this.graph_list.add(tmp);
		}
	}
	
	public Graph generateCloneWithRandomWeights(Graph g) {
		Random rand = new Random();
		Graph tmp;
		try {
			tmp = (Graph) g.clone();
			for (int j = 0; j < tmp.edgeList.size(); j++) {
				Edge e = tmp.edgeList.get(j);
				e.weight = (int) (rand.nextInt(this.MAX_WEIGHT + 1) / e.capacity);
			}

		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
			return (Graph) null;
		}
		return tmp;
	}
	
	
	public void evaluatePopulation() {
		for(Graph g: graph_list) {
			FlowCalculator c = new FlowCalculator(g);
			c.compute();
			g.computeHighestUsageAndClearFlows();
			System.out.println(g.highestUsage);
			g.printAdjList();
		}
		
		// sortowanie celem pozniejszego podzialu na klasy
		Collections.sort(graph_list, new Comparator<Graph>() {
			public int compare(Graph g1, Graph g2) {
				if (g1.highestUsage < g2.highestUsage) {
					return -1;
				}
				else if (g1.highestUsage > g2.highestUsage) {
					return 1;
				}
				else
					return 0;
			}
		});

	}
	
	public void evolutionStep() {
		for (int i = 0; i < graph_list.size(); i++) {
			
			if (i < cardA) { // klasa A, przezywaja wszyscy
				new_graph_list.add(graph_list.get(i));
			}
			
			else if (i >= cardA && i < cardB) { // klasa B
				new_graph_list.add(getChild(graph_list.get(0)));
			}
			
			if (i >= cardB) { // klasa C, umieraja, a na ich miejsce losowane sa nowe
				new_graph_list.add(generateCloneWithRandomWeights(graph_list.get(0))); // wszystko jedno, z jakiego sklonujemy
			}
		}
		
		graph_list = new_graph_list;
		new_graph_list = new ArrayList<Graph>();
	}
	
	public Graph getChild(Graph g) {
		Graph child;
		Random rand = new Random();
		int rr = rand.nextInt(cardA+1);
		Graph dad = graph_list.get(rr);
		rr = rand.nextInt(cardB - cardA + 1) + cardA;
		Graph mom = graph_list.get(rr);
		
		
		
		try {
			child = (Graph) g.clone();
			for (int j = 0; j < child.edgeList.size(); j++) {
				Edge e = child.edgeList.get(j);
				if (rand.nextFloat() < MUTATION_RATE) // mutacja 
					e.weight = (int) (rand.nextInt(this.MAX_WEIGHT + 1));
				else if (rand.nextFloat() < CROSS_RATE)
					e.weight = dad.edgeList.get(j).weight;
				else
					e.weight = mom.edgeList.get(j).weight;
			}

		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
			return (Graph) null;
		}
		return child;
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
		
		Genetic genetic = new Genetic(10, 3, 0.2, 0.7, 0.01, 0.5, 0.5, 100);
		try {
			genetic.createInitialPopulation(snd_parser.graph);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//genetic.graph_list.get(0).addNode(new Node());
//		genetic.graph_list.get(1).nodeList.get(0).name="DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDddd";
//		
//		for(int i=0; i<genetic.INITIAL_POPULATION_NO; i++)
//		{
//			System.out.println("Graf populacji " + i);
//			System.out.println();
//			System.out.println(genetic.graph_list.get(i).nodeList.get(0).name);
//			genetic.graph_list.get(i).printAdjList();
//		}
	}

}
