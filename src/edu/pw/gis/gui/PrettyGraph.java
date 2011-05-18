package edu.pw.gis.gui;
import edu.pw.gis.graph.*;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import org.apache.commons.collections15.FactoryUtils;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * A class that shows the minimal work necessary to load and visualize a graph.
 */
public class PrettyGraph 
{

    public static void main(String[] args) throws IOException 
    {
        JFrame jf = new JFrame();
        Graph g = getGraph();
        VisualizationViewer vv = new VisualizationViewer(new FRLayout(g));
        jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
 
    
    public static Graph getGraph() throws IOException 
    {
        Graph g = new UndirectedSparseGraph();
        return g;
    }
}

