/**
 * 
 */
package edu.pw.gis.gui;

import edu.pw.gis.algorithm.Genetic;
import edu.pw.gis.graph.Graph;
import edu.pw.gis.parser.SNDParser;

/**
 * @author ymir
 *
 */
public class Main {

	/** 
	 * @param args
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
	
		SNDParser snd_parser = new SNDParser();
		snd_parser.countNodesAndEdgesSNDNetworkXML("xml/simple_test_graph.xml");
	
		snd_parser.graph = new Graph(snd_parser.nodes_no);

		snd_parser.readSNDNetworkXML("xml/simple_test_graph.xml");
		
		Genetic genetic = new Genetic(10, 100, 0.2, 0.7, 0.01, 0.7, 0.00, 700);

		genetic.createInitialPopulation(snd_parser.graph);
		
		long start = System.currentTimeMillis();
		genetic.go();
		long end = System.currentTimeMillis();
		System.out.println("Execution time was "+(end-start)+" ms.");

		
		Graph top = genetic.graph_list.get(0);
		top.printAdjList();
		
	}

}
