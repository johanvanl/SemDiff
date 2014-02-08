package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ConditionalExpression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ConditionalExpression:
//     Expression ? Expression : Expression
public class DiffConditionalExpression extends DiffExpression {

	private DiffExpression expression = null;
	private DiffExpression thenExpression = null;
	private DiffExpression elseExpression = null;

	public DiffConditionalExpression(ConditionalExpression exp) {
		super(exp);

		exp.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		exp.getThenExpression()
				.accept(ExpressionVisitor.getExpressionVisitor());
		this.thenExpression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		exp.getElseExpression()
				.accept(ExpressionVisitor.getExpressionVisitor());
		this.elseExpression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffExpression getThenExpression() {
		return this.thenExpression;
	}

	public DiffExpression getElseExpression() {
		return this.elseExpression;
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
			acceptChild(visitor, getThenExpression());
			acceptChild(visitor, getElseExpression());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.expression.toString());
		sb.append(" ? ");
		sb.append(this.thenExpression.toString());
		sb.append(" : ");
		sb.append(this.elseExpression.toString());

		return sb.toString();
	}

}
