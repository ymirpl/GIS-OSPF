/**
 * 
 */
package edu.pw.gis.parser;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

/**
 * @author ymir
 * 
 */
public class SNDParser {

	private int nodes_no = 0;
	private int edges_no = 0;

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

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

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

				boolean bfname = false;
				boolean blname = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("node")) {
						System.out.println("Node name : "
								+ attributes.getValue(0));
					}

					if (qName.equalsIgnoreCase("x")) {
						bfname = true;
					}

					if (qName.equalsIgnoreCase("y")) {
						blname = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// System.out.println("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (bfname) {
						System.out.println("x : "
								+ new String(ch, start, length));
						bfname = false;
					}

					if (blname) {
						System.out.println("y : "
								+ new String(ch, start, length));
						blname = false;
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
//		snd_parser.readSNDNetworkXML("xml/giul39.xml");
	}

}
