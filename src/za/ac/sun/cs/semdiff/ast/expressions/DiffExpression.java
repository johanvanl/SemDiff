package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ASTNode;

import za.ac.sun.cs.semdiff.ast.DiffNode;

public abstract class DiffExpression extends DiffNode {

	public DiffExpression(ASTNode node) {
		super(node);
	}

}
