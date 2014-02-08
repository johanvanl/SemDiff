package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.InstanceofExpression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// InstanceofExpression:
//     Expression instanceof Type
public class DiffInstanceofExpression extends DiffExpression {

	private DiffExpression expression = null;
	private DiffType type = null;

	public DiffInstanceofExpression(InstanceofExpression exp) {
		super(exp);

		exp.getLeftOperand().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		type = new DiffType(exp.getRightOperand());

	}

	public DiffExpression getExpression() {
		return this.expression;
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
			acceptChild(visitor, getExpression());
			acceptChild(visitor, getType());
		}
	}

	@Override
	public String toString() {
		return expression.toString() + " instanceof " + type.toString();
	}
}
