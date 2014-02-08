package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.CharacterLiteral;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffCharacterLiteral extends DiffExpression {

	private char charValue;

	public DiffCharacterLiteral(CharacterLiteral exp) {
		super(exp);
		charValue = exp.charValue();
	}

	public char getCharValue() {
		return this.charValue;
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
		return "\'" + Character.toString(this.charValue) + "\'";
	}

}
