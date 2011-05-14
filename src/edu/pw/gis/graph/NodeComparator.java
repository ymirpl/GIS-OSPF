package edu.pw.gis.graph;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node x, Node y) {
		if (x.distance < y.distance)
			return -1;
		else if (x.distance > y.distance)
			return 1;
		else
			return 0;
	}
}
