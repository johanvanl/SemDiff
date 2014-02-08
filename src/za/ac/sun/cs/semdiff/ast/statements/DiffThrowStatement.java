package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.ThrowStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ThrowStatement:
//     throw Expression ;
public class DiffThrowStatement extends DiffStatement {

	private DiffExpression expression = null;

	public DiffThrowStatement(ThrowStatement stmt) {
		super(stmt);

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
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
		return "throw " + this.expression.toString();
	}

}
