package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.TypeLiteral;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// TypeLiteral:
//     ( Type | void ) . class
public class DiffTypeLiteral extends DiffExpression {

	private DiffType type = null;

	public DiffTypeLiteral(TypeLiteral exp) {
		super(exp);
		this.type = new DiffType(exp.getType());
	}

	public DiffType getType() {
		return this.type;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}
	
	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getType());
		}
	}

	@Override
	public String toString() {
		return this.type.toString() + ".class";
	}

}
