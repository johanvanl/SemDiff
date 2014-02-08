package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.ASTNode;

import za.ac.sun.cs.semdiff.ast.DiffNode;

public abstract class DiffStatement extends DiffNode {

	public DiffStatement(ASTNode node) {
		super(node);
	}

}
