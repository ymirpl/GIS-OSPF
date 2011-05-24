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
		
		Genetic genetic = new Genetic(10, 300, 0.2, 0.7, 0.01, 0.5, 0.75, 1000);

		genetic.createInitialPopulation(snd_parser.graph);
		
		genetic.go();
		
		Graph top = genetic.graph_list.get(0);
		top.printAdjList();
		
	}

}
