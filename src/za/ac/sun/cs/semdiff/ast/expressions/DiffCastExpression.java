package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.CastExpression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// CastExpression:
//     ( Type ) Expression
public class DiffCastExpression extends DiffExpression {

	private DiffType type = null;
	private DiffExpression expression = null;

	public DiffCastExpression(CastExpression exp) {
		super(exp);

		exp.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		this.type = new DiffType(exp.getType());
	}

	public DiffType getType() {
		return type;
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
			acceptChild(visitor, getType());
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(this.type.toString());
		sb.append(") ");
		sb.append(this.expression.toString());
		return sb.toString();
	}

}
