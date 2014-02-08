package za.ac.sun.cs.semdiff.utils;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.visitors.NodeInOrderVisitor;

public class PrintDiff {

	private PrintDiff() {
	}

	public static String printDiff(DiffCompilationUnit cu) {
		StringBuilder sb = new StringBuilder();

		List<DiffNode> nodes = NodeInOrderVisitor.getNodes(cu);
		int index = 0;
		String source = cu.getSource();
		for (int i = 0; i < source.length(); i++) {
			// Shortcircuit prevents errors
			while (index < nodes.size() && nodes.get(index).getStart() < i) {
				index++;
			}

			while (index < nodes.size() && nodes.get(index).getStart() == i) {
				sb.append(nodes.get(index).getDifference().getPrefix());
				index++;
			}
			sb.append(source.charAt(i));
		}

		return sb.toString();
	}

}
