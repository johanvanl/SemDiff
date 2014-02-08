package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import za.ac.sun.cs.semdiff.ast.DiffOperator;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// InfixExpression:
//     Expression InfixOperator Expression { InfixOperator Expression }
public class DiffInfixExpression extends DiffExpression {

	private List<DiffExpression> expressions = null;
	private DiffOperator operator = null;

	@SuppressWarnings("unchecked")
	public DiffInfixExpression(InfixExpression exp) {
		super(exp);

		this.expressions = new ArrayList<DiffExpression>();

		exp.getLeftOperand().accept(ExpressionVisitor.getExpressionVisitor());
		this.expressions.add(ExpressionVisitor.getExpressionVisitor()
				.getExpression());

		this.operator = new DiffOperator(exp.getOperator().toString());

		exp.getRightOperand().accept(ExpressionVisitor.getExpressionVisitor());
		this.expressions.add(ExpressionVisitor.getExpressionVisitor()
				.getExpression());

		if (exp.hasExtendedOperands()) {
			List<Expression> extended = exp.extendedOperands();
			for (Expression e : extended) {
				e.accept(ExpressionVisitor.getExpressionVisitor());
				this.expressions.add(ExpressionVisitor.getExpressionVisitor()
						.getExpression());
			}
		}

	}

	public List<DiffExpression> getExpressions() {
		return this.expressions;
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
			acceptChildren(visitor, getExpressions());
			acceptChild(visitor, getOperator());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		String prefix = "";
		for (DiffExpression e : this.expressions) {
			sb.append(prefix);
			prefix = this.operator.toString();
			sb.append(e.toString());
		}

		return sb.toString();
	}

}
