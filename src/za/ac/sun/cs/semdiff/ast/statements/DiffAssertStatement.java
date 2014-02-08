package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.AssertStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// AssertStatement:
//    assert Expression [ : Expression ] ;
public class DiffAssertStatement extends DiffStatement {

	private DiffExpression expression = null;
	private DiffExpression message = null;

	public DiffAssertStatement(AssertStatement stmt) {
		super(stmt);

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		if (stmt.getMessage() != null) {
			stmt.getMessage().accept(ExpressionVisitor.getExpressionVisitor());
			this.message = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffExpression getMessage() {
		return this.message;
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
			acceptChild(visitor, getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("assert ");
		sb.append(this.expression.toString());

		if (this.message != null) {
			sb.append(" : ");
			sb.append(this.message.toString());
		}

		return sb.toString();
	}

}
