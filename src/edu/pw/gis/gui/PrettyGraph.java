package edu.pw.gis.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.pw.gis.algorithm.Genetic;
import edu.pw.gis.parser.SNDParser;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.VertexLabelRenderer;

/**
 * Demonstrates jung support for drawing edge labels that
 * can be positioned at any point along the edge, and can
 * be rotated to be parallel with the edge.
 * 
 * @author Tom Nelson
 * 
 */
public class PrettyGraph extends JApplet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6077157664507049647L;

    Graph<String, Integer> jungGraph;
    VisualizationViewer<String, Integer> vv;
    VertexLabelRenderer vertexLabelRenderer;
    EdgeLabelRenderer edgeLabelRenderer;
    edu.pw.gis.graph.Graph myGraph;
    
    ScalingControl scaler = new CrossoverScalingControl();
    
    /**
     * create an instance of a simple graph with controls to
     * demo the label positioning features
     * 
     */
    
    public void getGraph(edu.pw.gis.graph.Graph g) {
		jungGraph = new DirectedSparseMultigraph<String, Integer>();
		for(edu.pw.gis.graph.Node n: g.nodeList) {
			jungGraph.addVertex(n.name);
		}
		
		for (edu.pw.gis.graph.Edge e: g.edgeList) {
			jungGraph.addEdge(e.id, e.source.name, e.target.name, EdgeType.DIRECTED);
		}

    }
    
    
    public PrettyGraph(edu.pw.gis.graph.Graph g) {
        
        // create a simple graph for the demo
        jungGraph = new DirectedSparseMultigraph<String, Integer>();
        
        getGraph(g);
        myGraph = g;
        
        Layout<String, Integer> layout = new FRLayout<String, Integer>(jungGraph);
        vv =  new VisualizationViewer<String, Integer>(layout, new Dimension(1024,780));
        vv.setBackground(Color.gray);

        vertexLabelRenderer = vv.getRenderContext().getVertexLabelRenderer();
        edgeLabelRenderer = vv.getRenderContext().getEdgeLabelRenderer();
        
        Transformer<Integer,String> stringer = new Transformer<Integer,String>(){
        	DecimalFormat twoDigit = new DecimalFormat("#,##0.00");
            public String transform(Integer e) {
                //return "Użycie: "+ twoDigit.format(myGraph.edgeList.get(e).usage) + " waga: " + myGraph.edgeList.get(e).weight;
            	return ""+ twoDigit.format(myGraph.edgeList.get(e).usage) + " / " + myGraph.edgeList.get(e).weight + " / " + twoDigit.format(myGraph.edgeList.get(e).flowSum)+ " / " + myGraph.edgeList.get(e).capacity;
            }
        };
        vv.getRenderContext().setEdgeLabelTransformer(stringer);
        vv.getRenderContext().setEdgeDrawPaintTransformer(new PickableEdgePaintTransformer<Integer>(vv.getPickedEdgeState(), Color.black, Color.cyan));
        vv.getRenderContext().setVertexFillPaintTransformer(new PickableVertexPaintTransformer<String>(vv.getPickedVertexState(), Color.red, Color.yellow));
        // add my listener for ToolTips
        //vv.setVertexToolTipTransformer(new ToStringLabeller<String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
        
        // create a frome to hold the graph
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        Container content = getContentPane();
        content.add(panel);
        
        final DefaultModalGraphMouse<String, Integer> graphMouse = new DefaultModalGraphMouse<String, Integer>();
        vv.setGraphMouse(graphMouse);
        
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        
        JCheckBox rotate = new JCheckBox("<html><center>Obróć opisy<p> krawędzi </center></html>");
        rotate.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                AbstractButton b = (AbstractButton)e.getSource();
                edgeLabelRenderer.setRotateEdgeLabels(b.isSelected());
                vv.repaint();
            }
        });
        rotate.setSelected(true);
       
        Box controls = Box.createHorizontalBox();

        JPanel zoomPanel = new JPanel(new GridLayout(0,1));
        zoomPanel.setBorder(BorderFactory.createTitledBorder("Skala"));
        zoomPanel.add(plus);
        zoomPanel.add(minus);

        JPanel rotatePanel = new JPanel();
        rotatePanel.setBorder(BorderFactory.createTitledBorder("Wyrównanie"));
        rotatePanel.add(rotate);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(rotatePanel, BorderLayout.WEST);
        
        JPanel modePanel = new JPanel(new GridLayout(2,1));
        modePanel.setBorder(BorderFactory.createTitledBorder("Mysz"));
        modePanel.add(graphMouse.getModeComboBox());
        
        JLabel label = new JLabel("Opis krawędzi: użycie / waga / przepływ / pojemność                                 ");
        label.setHorizontalTextPosition(JLabel.CENTER);

        controls.add(zoomPanel);
        controls.add(labelPanel);
        controls.add(label);
        controls.add(modePanel);
        content.add(controls, BorderLayout.SOUTH);
    }
    
    
    /**
     * a driver for this demo
     */
    public static void main(String[] args) {

		SNDParser snd_parser = new SNDParser("xml/test.xml");

		Genetic genetic = new Genetic(10, 100, 0.2, 0.7, 0.01, 0.7, 0.00, 300);

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
