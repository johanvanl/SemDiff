package za.ac.sun.cs.semdiff.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import za.ac.sun.cs.semdiff.jdtvisitors.StatementVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffBlock extends DiffStatement {

	private List<DiffStatement> statements = null;

	@SuppressWarnings("unchecked")
	public DiffBlock(Block stmt) {
		super(stmt);

		this.statements = new ArrayList<DiffStatement>();

		List<Statement> stmts = stmt.statements();
		for (Statement s : stmts) {
			s.accept(StatementVisitor.getStatementVisitor());
			this.statements.add(StatementVisitor.getStatementVisitor()
					.getStatement());
		}

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
			acceptChildren(visitor, getStatements());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (DiffStatement stmt : this.statements) {
			sb.append(stmt.toString());
			sb.append("\n");
		}

		sb.append("}");
		return sb.toString();
	}

}
