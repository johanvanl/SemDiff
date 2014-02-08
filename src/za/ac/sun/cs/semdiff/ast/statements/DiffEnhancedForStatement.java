package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.EnhancedForStatement;

import za.ac.sun.cs.semdiff.ast.DiffSingleVariableDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// EnhancedForStatement:
//    for ( FormalParameter : Expression )
//                        Statement
public class DiffEnhancedForStatement extends DiffStatement {

	private DiffSingleVariableDeclaration parameter = null;
	private DiffExpression expression = null;
	private DiffStatement statement = null;

	public DiffEnhancedForStatement(EnhancedForStatement stmt) {
		super(stmt);
		this.parameter = new DiffSingleVariableDeclaration(stmt.getParameter());

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		stmt.getBody().accept(StatementVisitor.getStatementVisitor());
		this.statement = StatementVisitor.getStatementVisitor().getStatement();

	}

	public DiffSingleVariableDeclaration getParameter() {
		return this.parameter;
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffStatement getStatement() {
		return this.statement;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getParameter());
			acceptChild(visitor, getExpression());
			acceptChild(visitor, getStatement());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("for (");
		sb.append(this.parameter.toString());
		sb.append(" : ");
		sb.append(this.expression.toString());
		sb.append(")\n");
		sb.append(this.statement.toString());

		return sb.toString();
	}

}
