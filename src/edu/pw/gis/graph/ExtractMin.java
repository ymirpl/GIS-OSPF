package edu.pw.gis.graph;

import java.util.Comparator;

/**
 * Komparator do kolejki priorytetowej ustawia elementy w kolejnosci od najblizszego (z najmniejszy distance)
 */
public class ExtractMin implements Comparator<Node> {
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
