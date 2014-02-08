package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.Assignment;

import za.ac.sun.cs.semdiff.ast.DiffOperator;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// Assignment:
//    Expression AssignmentOperator Expression
public class DiffAssignment extends DiffExpression {

	private DiffExpression left = null;
	private DiffOperator operator = null;
	private DiffExpression right = null;

	public DiffAssignment(Assignment exp) {
		super(exp);

		exp.getLeftHandSide().accept(ExpressionVisitor.getExpressionVisitor());
		this.left = ExpressionVisitor.getExpressionVisitor().getExpression();

		this.operator = new DiffOperator(exp.getOperator().toString());

		exp.getRightHandSide().accept(ExpressionVisitor.getExpressionVisitor());
		this.right = ExpressionVisitor.getExpressionVisitor().getExpression();
	}

	public DiffExpression getLeft() {
		return this.left;
	}

	public DiffOperator getOperator() {
		return this.operator;
	}

	public DiffExpression getRight() {
		return this.right;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getLeft());
			acceptChild(visitor, getOperator());
			acceptChild(visitor, getRight());
		}
	}

	@Override
	public String toString() {
		return this.left.toString() + this.operator.toString()
				+ this.right.toString();
	}

}
