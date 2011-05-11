/**
 * 
 */
package edu.pw.gis.parser;

import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import edu.pw.gis.graph.Graph;
import edu.pw.gis.graph.Node;

/**
 * @author ymir
 * 
 */
public class SNDParser {

	public int nodes_no = 0;
	public int edges_no = 0;
	public Graph graph;
	
	public ArrayList<String> node_list = new ArrayList<String>();

	/*
	 * Funkcja zliczaj¹ca liczbê wierszy i krawêdzi w grafie zapisanym w pliku
	 * XML. Zliczamy wêz³y node i links
	 * 
	 * @param xml_path: œcie¿ka do pliku XML z zapisanym grafem
	 */
	public void countNodesAndEdgesSNDNetworkXML(String xml_path) {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("node")) {
						nodes_no++;
					}

					if (qName.equalsIgnoreCase("link")) {
						edges_no++;
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
				Node node = new Node();

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("node")) {
						node.name = attributes.getValue(0);
						node_list.add(node.name);
						node.id = node_list.size()-1;
					}

					if (qName.equalsIgnoreCase("x")) {
						b_coord_x = true;
					}

					if (qName.equalsIgnoreCase("y")) {
						b_coord_y = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("node")) {
						System.out.println("Node name: " + node.id + " " + node.name);
						System.out.println("x: " + node.draw_x + " y: " + node.draw_y);
					}
					graph.addNode(node);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (b_coord_x) {
						node.draw_x = Double.parseDouble(new String(ch, start, length));
						b_coord_x = false;
					}

					if (b_coord_y) {
						node.draw_y = Double.parseDouble(new String(ch, start, length));
						b_coord_y = false;
					}
					//
					// if (bnname) {
					// System.out.println("Nick Name : "
					// + new String(ch, start, length));
					// bnname = false;
					// }
					//
					// if (bsalary) {
					// System.out.println("Salary : "
					// + new String(ch, start, length));
					// bsalary = false;
					// }

				}

			};

			saxParser.parse(xml_path, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNodes_no() {
		return nodes_no;
	}

	public void setNodes_no(int nodes_no) {
		this.nodes_no = nodes_no;
	}

	public int getEdges_no() {
		return edges_no;
	}

	public void setEdges_no(int edges_no) {
		this.edges_no = edges_no;
	}

	public static void main(String argv[]) {
		System.out.println("test");
		SNDParser snd_parser = new SNDParser();
		snd_parser.countNodesAndEdgesSNDNetworkXML("xml/giul39.xml");
		System.out.println("liczba krawêdzi: " + snd_parser.getEdges_no());
		System.out.println("liczba wêz³ów: " + snd_parser.getNodes_no());
		
		snd_parser.graph = new Graph(snd_parser.nodes_no);
		
		snd_parser.readSNDNetworkXML("xml/giul39.xml");
		
//		for(int i = 0; i < snd_parser.node_list.size(); i++)
//			System.out.println("(" + i +") " + snd_parser.node_list.get(i));
		
		snd_parser.graph.addNode(new Node(1,1.0,1.0,"test"));
		
		snd_parser.graph.printAdjList();
	}

}
