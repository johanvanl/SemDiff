package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.BreakStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// BreakStatement:
//    break [ Identifier ] ;
public class DiffBreakStatement extends DiffStatement {

	private DiffSimpleName identifier = null;

	public DiffBreakStatement(BreakStatement stmt) {
		super(stmt);
		if (stmt.getLabel() != null) {
			this.identifier = new DiffSimpleName(stmt.getLabel());
		}
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
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
		}
	}

	@Override
	public String toString() {
		if (identifier != null) {
			return "break:" + this.identifier.toString();
		}
		return "break";
	}

}
