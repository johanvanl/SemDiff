package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.PrefixExpression;

import za.ac.sun.cs.semdiff.ast.DiffOperator;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffPrefixExpression extends DiffExpression {

	private DiffOperator operator = null;
	private DiffExpression expression = null;

	public DiffPrefixExpression(PrefixExpression exp) {
		super(exp);

		this.operator = new DiffOperator(exp.getOperator().toString());

		exp.getOperand().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

	}

	public DiffOperator getOperator() {
		return this.operator;
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getOperator());
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		return this.operator.toString() + this.expression.toString();
	}

}
