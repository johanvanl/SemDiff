package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.ContinueStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ContinueStatement:
//    continue [ Identifier ] 
public class DiffContinueStatement extends DiffStatement {

	private DiffSimpleName identifier = null;

	public DiffContinueStatement(ContinueStatement stmt) {
		super(stmt);
		if (stmt.getLabel() != null) {
			this.identifier = new DiffSimpleName(stmt.getLabel());
		}
	}

	public DiffName getIdentifier() {
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
			return "continue: " + identifier.toString();
		}
		return "continue";
	}

}
