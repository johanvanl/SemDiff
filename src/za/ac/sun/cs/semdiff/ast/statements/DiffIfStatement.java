package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.IfStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// IfStatement:
//    if ( Expression ) Statement [ else Statement]
public class DiffIfStatement extends DiffStatement {

	private DiffExpression expression = null;
	private DiffStatement thenStatement = null;
	private DiffStatement elseStatement = null;

	public DiffIfStatement(IfStatement stmt) {
		super(stmt);

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		stmt.getThenStatement().accept(StatementVisitor.getStatementVisitor());
		this.thenStatement = StatementVisitor.getStatementVisitor()
				.getStatement();

		if (stmt.getElseStatement() != null) {
			stmt.getElseStatement().accept(
					StatementVisitor.getStatementVisitor());
			this.elseStatement = StatementVisitor.getStatementVisitor()
					.getStatement();
		}

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffStatement getThenStatement() {
		return this.thenStatement;
	}

	public DiffStatement getElseStatement() {
		return this.elseStatement;
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
			acceptChild(visitor, getThenStatement());
			acceptChild(visitor, getElseStatement());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("if (");
		sb.append(this.expression.toString());
		sb.append(")\n");
		sb.append(this.thenStatement.toString());

		if (this.elseStatement != null) {
			sb.append("\nelse\n");
			sb.append(this.elseStatement.toString());
		}

		return sb.toString();
	}
}
