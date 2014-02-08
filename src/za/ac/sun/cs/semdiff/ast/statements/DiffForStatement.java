package za.ac.sun.cs.semdiff.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ForStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ForStatement:
//    for (
//        [ ForInit ];
//        [ Expression ] ;
//        [ ForUpdate ] )
//          Statement
// ForInit:
//                Expression { , Expression }
// ForUpdate:
//                Expression { , Expression }
public class DiffForStatement extends DiffStatement {

	private List<DiffExpression> initializers = null;
	private DiffExpression expression = null;
	private List<DiffExpression> updaters = null;
	private DiffStatement statement = null;

	@SuppressWarnings("unchecked")
	public DiffForStatement(ForStatement stmt) {
		super(stmt);

		this.initializers = new ArrayList<DiffExpression>();
		List<Expression> tempInitializers = stmt.initializers();
		for (Expression e : tempInitializers) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.initializers.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

		if (stmt.getExpression() != null) {
			stmt.getExpression().accept(
					ExpressionVisitor.getExpressionVisitor());
			this.expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

		this.updaters = new ArrayList<DiffExpression>();
		List<Expression> tempUpdaters = stmt.updaters();
		for (Expression e : tempUpdaters) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.updaters.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

		stmt.getBody().accept(StatementVisitor.getStatementVisitor());
		this.statement = StatementVisitor.getStatementVisitor().getStatement();

	}

	public List<DiffExpression> getInitilaizers() {
		return this.initializers;
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public List<DiffExpression> getUpdaters() {
		return this.updaters;
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
			acceptChildren(visitor, getInitilaizers());
			acceptChild(visitor, getExpression());
			acceptChildren(visitor, getUpdaters());
			acceptChild(visitor, getStatement());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("for (");

		sb.append("[");
		String prefix = "";
		for (DiffExpression e : this.initializers) {
			sb.append(prefix);
			prefix = ",";
			sb.append(e.toString());
		}
		sb.append("]");

		sb.append(";");
		if (this.expression != null) {
			sb.append(this.expression.toString());
		}
		sb.append(";");

		sb.append("[");
		prefix = "";
		for (DiffExpression e : this.updaters) {
			sb.append(prefix);
			prefix = ",";
			sb.append(e.toString());
		}
		sb.append("]");

		sb.append(")\n");
		sb.append(this.statement.toString());

		return sb.toString();
	}

}
