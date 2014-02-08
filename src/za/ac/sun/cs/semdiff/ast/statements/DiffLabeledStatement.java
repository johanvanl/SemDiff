package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.LabeledStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// LabeledStatement:
//    Identifier : Statement
public class DiffLabeledStatement extends DiffStatement {

	private DiffSimpleName identifier = null;
	private DiffStatement statement = null;

	public DiffLabeledStatement(LabeledStatement stmt) {
		super(stmt);

		this.identifier = new DiffSimpleName(stmt.getLabel());

		stmt.getBody().accept(StatementVisitor.getStatementVisitor());
		this.statement = StatementVisitor.getStatementVisitor().getStatement();
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
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
			acceptChild(visitor, getIdentifier());
			acceptChild(visitor, getStatement());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.identifier.toString());
		sb.append(" : ");
		sb.append(this.statement.toString());

		return sb.toString();
	}

}
