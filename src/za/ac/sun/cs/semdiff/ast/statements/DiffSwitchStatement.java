package za.ac.sun.cs.semdiff.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SwitchStatement:
//    switch ( Expression )
//            { { SwitchCase | Statement } } }
public class DiffSwitchStatement extends DiffStatement {

	private DiffExpression expression = null;
	private List<DiffStatement> statements = null;

	@SuppressWarnings("unchecked")
	public DiffSwitchStatement(SwitchStatement stmt) {
		super(stmt);

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		this.statements = new ArrayList<DiffStatement>();
		List<Statement> stmts = stmt.statements();
		for (Statement s : stmts) {
			s.accept(StatementVisitor.getStatementVisitor());
			this.statements.add(StatementVisitor.getStatementVisitor()
					.getStatement());
		}

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public List<DiffStatement> getStatements() {
		return this.statements;
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
			acceptChildren(visitor, getStatements());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("switch (");
		sb.append(this.expression.toString());
		sb.append(") {\n");

		for (DiffStatement s : this.statements) {
			sb.append(s.toString());
			sb.append("\n");
		}

		sb.append("}");

		return sb.toString();
	}

}
