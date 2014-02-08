package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ArrayAccess;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ArrayAccess:
//     Expression [ Expression ]
public class DiffArrayAccess extends DiffExpression {

	private DiffExpression array = null;
	private DiffExpression index = null;

	public DiffArrayAccess(ArrayAccess exp) {
		super(exp);

		exp.getArray().accept(ExpressionVisitor.getExpressionVisitor());
		this.array = ExpressionVisitor.getExpressionVisitor().getExpression();

		exp.getIndex().accept(ExpressionVisitor.getExpressionVisitor());
		this.index = ExpressionVisitor.getExpressionVisitor().getExpression();

	}

	public DiffExpression getArray() {
		return this.array;
	}

	public DiffExpression getIndex() {
		return this.index;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getArray());
			acceptChild(visitor, getIndex());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.array.toString());
		sb.append("[");
		sb.append(this.index.toString());
		sb.append("]");
		return sb.toString();
	}

}
