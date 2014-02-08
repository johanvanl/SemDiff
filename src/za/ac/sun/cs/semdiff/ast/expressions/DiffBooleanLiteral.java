package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.BooleanLiteral;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// BooleanLiteral:
//     true
//     false
public class DiffBooleanLiteral extends DiffExpression {

	private boolean value = false;

	public DiffBooleanLiteral(BooleanLiteral exp) {
		super(exp);
		this.value = exp.booleanValue();
	}

	public boolean getbooleanValue() {
		return this.value;
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
		if (this.value) {
			return "true";
		}
		return "false";
	}

}
