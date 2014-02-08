package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.NullLiteral;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffNullLiteral extends DiffExpression {

	public DiffNullLiteral(NullLiteral exp) {
		super(exp);
	}
	
	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}
	
	@Override
	public void accept0(DiffVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "null";
	}
	
}
