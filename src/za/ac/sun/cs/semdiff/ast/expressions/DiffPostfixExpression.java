package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.PostfixExpression;

import za.ac.sun.cs.semdiff.ast.DiffOperator;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// PostfixExpression:
//     Expression PostfixOperator
public class DiffPostfixExpression extends DiffExpression {

	private DiffExpression expression;
	private DiffOperator operator;

	public DiffPostfixExpression(PostfixExpression exp) {
		super(exp);

		exp.getOperand().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		this.operator = new DiffOperator(exp.getOperator().toString());
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffOperator getOperator() {
		return this.operator;
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
			acceptChild(visitor, getOperator());
		}
	}

	@Override
	public String toString() {
		return this.expression.toString() + this.operator.toString();
	}
}
