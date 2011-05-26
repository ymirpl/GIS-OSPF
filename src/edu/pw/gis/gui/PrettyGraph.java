package edu.pw.gis.gui;
import edu.pw.gis.algorithm.*;
import edu.pw.gis.graph.*;
import edu.pw.gis.parser.SNDParser;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import java.io.IOException;
import javax.swing.JFrame;

public class PrettyGraph 
{

	private Graph<String, Integer> jungGraph;

	public static void main(String[] args) throws IOException {


		SNDParser snd_parser = new SNDParser("xml/simple_test_graph.xml");
		
		Genetic genetic = new Genetic(10, 100, 0.2, 0.7, 0.01, 0.7, 0.00, 700);

		genetic.createInitialPopulation(snd_parser.graph);

		genetic.go(System.currentTimeMillis());

		edu.pw.gis.graph.Graph top = genetic.graph_list.get(0);
		top.printAdjList();
		
		PrettyGraph pt = new PrettyGraph(top);

    }
 
	public void getGraph(edu.pw.gis.graph.Graph g) throws IOException {
		jungGraph = new DirectedSparseGraph<String, Integer>();
		for(Node n: g.nodeList) {
			jungGraph.addVertex(n.name);
		}
		
		int i = 0;
		for (Edge e: g.edgeList) {
			
			jungGraph.addEdge(e.id, e.source.name, e.target.name, EdgeType.DIRECTED);
		}
		
		System.out.println("Edges "+ jungGraph.getEdgeCount());
		
	}
	
	public PrettyGraph(edu.pw.gis.graph.Graph g) throws IOException {
		JFrame jf = new JFrame();
		getGraph(g);
		VisualizationViewer<String, Integer> vv = new VisualizationViewer<String, Integer>(new FRLayout<String, Integer>(jungGraph));
		jf.getContentPane().add(vv);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);		
	}
}
