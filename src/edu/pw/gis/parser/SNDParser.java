package edu.pw.gis.parser;

import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import edu.pw.gis.graph.Edge;
import edu.pw.gis.graph.Graph;
import edu.pw.gis.graph.Node;

/**
 * @author ymir
 * 
 */
public class SNDParser {

	public int nodes_no = 0;
	public int edges_no = 0;
	public int links_no = 0;
	public double max_capacity = 0;
	public Graph graph;
	public String xml_network_path;

	public SNDParser(String xml_network_path) {
		countNodesAndEdgesSNDNetworkXML(xml_network_path);
		
		graph = new Graph(nodes_no);

		readSNDNetworkXML(xml_network_path);
	}

	private ArrayList<String> nodes_list = new ArrayList<String>();

	public void countNodesAndEdgesSNDNetworkXML(String xml_path) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean b_edge = false;
				boolean b_capacity = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("node")) {
						nodes_no++;
					}

					if (qName.equalsIgnoreCase("link")) {
						b_edge = true;
						links_no++;
					}

					if (b_edge && qName.equalsIgnoreCase("capacity")) {
						edges_no++;
					}

					if (b_edge && qName.equals("capacity")) {
						b_capacity = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("link")) {
						b_edge = false;
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (b_capacity) {
						if (max_capacity < Double.parseDouble(new String(ch,
								start, length))) {
							max_capacity = Double.parseDouble(new String(ch,
									start, length));
							;
						}
						b_capacity = false;
					}
				}

			};

			saxParser.parse(xml_path, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readSNDNetworkXML(String xml_path) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean b_coord_x = false;
				boolean b_coord_y = false;
				boolean b_edge_capacity = false;
				boolean b_edge_source = false;
				boolean b_edge_target = false;
				boolean b_edge = false;
				boolean b_demand = false;
				boolean b_demand_source = false;
				boolean b_demand_target = false;
				boolean b_demand_value = false;

				// Parametry wêz³a
				int node_id = -1;
				double node_coord_x = -1.0;
				double node_coord_y = -1.0;
				String node_name = "";
				String demand_source_node_name = "";
				String demand_target_node_name = "";
				double demand_value = -1.0;

				// Parametry krawêdzi
				int edge_id = -1;
				double edge_capacity; // pojemnosc
				Node edge_source;
				Node edge_target;
				String edge_name;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("node")) {
						node_name = attributes.getValue(0);
						node_id++;
						nodes_list.add(node_id, node_name);
					}

					if (qName.equalsIgnoreCase("x")) {
						b_coord_x = true;
					}

					if (qName.equalsIgnoreCase("y")) {
						b_coord_y = true;
					}

					if (qName.equalsIgnoreCase("link")) {
						b_edge = true;
					}

					if (b_edge && qName.equalsIgnoreCase("capacity")) {
						edge_id++;
						b_edge_capacity = true;
					}

					if (b_edge && qName.equalsIgnoreCase("source")) {
						b_edge_source = true;
					}

					if (b_edge && qName.equalsIgnoreCase("target")) {
						b_edge_target = true;
					}

					if (qName.equalsIgnoreCase("demand")) {
						b_demand = true;
					}

					if (b_demand && qName.equalsIgnoreCase("source")) {
						b_demand_source = true;

					}

					if (b_demand && qName.equalsIgnoreCase("target")) {
						b_demand_target = true;

					}

					if (b_demand && qName.equalsIgnoreCase("demandValue")) {
						b_demand_value = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("node")) {
						Node node = new Node(node_id, node_coord_x,
								node_coord_y, node_name);
						graph.addNode(node);
					}

					if (b_edge && qName.equalsIgnoreCase("capacity")) {
						Edge edge = new Edge(edge_id, edge_capacity,
								edge_source, edge_target, edge_name);
						graph.addEdge(edge);

					}
					if (b_edge && qName.equalsIgnoreCase("link")) {
						b_edge = false;
					}

					if (b_demand && qName.equalsIgnoreCase("demand")) {

						int i = nodes_list.indexOf(demand_source_node_name);
						int j = nodes_list.indexOf(demand_target_node_name);

						graph.flowMatrix.get(i).set(j, demand_value);

						b_demand = false;
						demand_source_node_name = "XXX";
						demand_target_node_name = "YYY";
						demand_value = -1.0;
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (b_coord_x) {
						node_coord_x = Double.parseDouble(new String(ch, start,
								length));
						b_coord_x = false;
					}

					if (b_coord_y) {
						node_coord_y = Double.parseDouble(new String(ch, start,
								length));
						b_coord_y = false;
					}

					if (b_edge_capacity) {
						edge_capacity = Double.parseDouble(new String(ch,
								start, length));
						b_edge_capacity = false;
					}

					if (b_edge_source) {
						edge_source = graph.nodeList.get(nodes_list
								.indexOf(new String(ch, start, length)));
						b_edge_source = false;
					}

					if (b_edge_target) {
						edge_target = graph.nodeList.get(nodes_list
								.indexOf(new String(ch, start, length)));
						b_edge_target = false;
					}

					if (b_demand_source) {
						demand_source_node_name = new String(ch, start, length);
						b_demand_source = false;
					}

					if (b_demand_target) {
						demand_target_node_name = new String(ch, start, length);
						b_demand_target = false;
					}

					if (b_demand_value) {
						demand_value = Double.parseDouble(new String(ch, start,
								length));
						b_demand_value = false;
					}

					if (b_edge_capacity) {
						edge_capacity = Double.parseDouble(new String(ch,
								start, length));
						b_edge_capacity = false;
					}
				}

			};

			saxParser.parse(xml_path, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) {
		SNDParser snd_parser = new SNDParser("xml/simple_test_graph.xml");
		System.out.println("liczba wezlow: " + snd_parser.nodes_no);
		System.out.println("liczba polaczen: " + snd_parser.links_no);
		System.out.println("liczba krawedzi: " + snd_parser.edges_no);
		snd_parser.graph.printAdjList();
		
	}

}
