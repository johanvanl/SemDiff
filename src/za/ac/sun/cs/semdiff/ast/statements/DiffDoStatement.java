package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.DoStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// DoStatement:
//    do Statement while ( Expression ) ;
public class DiffDoStatement extends DiffStatement {

	private DiffStatement statement = null;
	private DiffExpression expression = null;

	public DiffDoStatement(DoStatement stmt) {
		super(stmt);

		stmt.getBody().accept(StatementVisitor.getStatementVisitor());
		this.statement = StatementVisitor.getStatementVisitor().getStatement();

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();
	}

	public DiffStatement getStatement() {
		return this.statement;
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
			acceptChild(visitor, getStatement());
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("do\n");
		sb.append(this.statement.toString());
		sb.append(" while (");
		sb.append(this.expression.toString());
		sb.append(")");

		return sb.toString();
	}

}
