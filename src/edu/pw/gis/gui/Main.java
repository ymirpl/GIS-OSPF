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

		// SNDParser snd_parser = new SNDParser("xml/simple_test_1.xml");
		// SNDParser snd_parser = new SNDParser("xml/simple_test_0.xml");
		// SNDParser snd_parser = new SNDParser("xml/test.xml");
		SNDParser snd_parser = new SNDParser("xml/big_test.xml");
		// SNDParser snd_parser = new SNDParser("xml/giul39.xml");

		// snd_parser.graph.printAdjList();

		Genetic genetic = new Genetic(10, 64, 0.1, 0.7, 0.01, 0.7, 0.00, 64);

		genetic.createInitialPopulation(snd_parser.graph);

		snd_parser.graph.printAdjList();

		genetic.go(System.currentTimeMillis());

		// Graph top = genetic.graph_list.get(0);
		// top.printAdjList();

	}

}
