package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.FieldAccess;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// FieldAccess:
//     Expression . Identifier
public class DiffFieldAccess extends DiffExpression {

	private DiffExpression expression = null;
	private DiffSimpleName identifier = null;

	public DiffFieldAccess(FieldAccess exp) {
		super(exp);

		exp.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		this.identifier = new DiffSimpleName(exp.getName());
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getExpression());
			acceptChild(visitor, getIdentifier());
		}
	}

	@Override
	public String toString() {
		return this.expression.toString() + "." + this.identifier.toString();
	}

}
