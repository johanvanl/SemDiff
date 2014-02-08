package za.ac.sun.cs.semdiff.similarity;

import za.ac.sun.cs.semdiff.ast.DiffNode;

public interface Similarity {

	public abstract double similarity(DiffNode originalNode,
			DiffNode revisedNode);

}
