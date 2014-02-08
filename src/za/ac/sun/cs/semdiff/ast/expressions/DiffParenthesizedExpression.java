package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ParenthesizedExpression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ParenthesizedExpression:
//    ( Expression )
public class DiffParenthesizedExpression extends DiffExpression {

	private DiffExpression expression = null;

	public DiffParenthesizedExpression(ParenthesizedExpression exp) {
		super(exp);

		exp.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();
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
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		return "(" + this.expression.toString() + ")";
	}

}
