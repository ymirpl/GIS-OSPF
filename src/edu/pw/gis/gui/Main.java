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
		snd_parser.countNodesAndEdgesSNDNetworkXML("xml/big_test.xml");
	
		snd_parser.graph = new Graph(snd_parser.nodes_no);

		snd_parser.readSNDNetworkXML("xml/big_test.xml");
		
		Genetic genetic = new Genetic(10, 100, 0.2, 0.7, 0.01, 0.5, 0.75, 1);

		genetic.createInitialPopulation(snd_parser.graph);
		
		for(int i=0; i<genetic.MAX_ITERATION_NO; i++) {
			genetic.evaluatePopulation();
			System.out.println("new population ===================================================");
			
			genetic.printUsages();
		}
		
	}

}
