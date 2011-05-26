/**
 * 
 */
package edu.pw.gis.gui;

import java.awt.Container;

import javax.swing.JFrame;

import edu.pw.gis.algorithm.Genetic;
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

		 SNDParser snd_parser = new SNDParser("xml/simple_test_1.xml");
//		 SNDParser snd_parser = new SNDParser("xml/simple_test_0.xml");
		// SNDParser snd_parser = new SNDParser("xml/test.xml");
//		SNDParser snd_parser = new SNDParser("xml/big_test.xml");
		// SNDParser snd_parser = new SNDParser("xml/giul39.xml");

		// snd_parser.graph.printAdjList();

		// Genetic genetic = new Genetic(10, 64, 0.1, 0.7, 0.01, 0.7, 0.00, 64);
		//
		// genetic.createInitialPopulation(snd_parser.graph);
		//
		// snd_parser.graph.printAdjList();
		//
		// genetic.go(System.currentTimeMillis());

		// Graph top = genetic.graph_list.get(0);
		// top.printAdjList();

		Genetic genetic = new Genetic(10, 10, 0.2, 0.7, 0.01, 0.7, 0.00, 10);

		genetic.createInitialPopulation(snd_parser.graph);

		genetic.go(System.currentTimeMillis());

		edu.pw.gis.graph.Graph top = genetic.graph_list.get(0);
		top.printAdjList();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frame.getContentPane();
		content.add(new PrettyGraph(top));
		frame.pack();
		frame.setVisible(true);

	}

}
