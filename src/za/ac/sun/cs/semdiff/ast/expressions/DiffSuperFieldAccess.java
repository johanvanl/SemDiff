package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.SuperFieldAccess;

import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SuperFieldAccess:
//     [ ClassName . ] super . Identifier
public class DiffSuperFieldAccess extends DiffExpression {

	private DiffName className = null;
	private DiffSimpleName identifier = null;

	public DiffSuperFieldAccess(SuperFieldAccess exp) {
		super(exp);

		if (exp.getQualifier() != null) {
			exp.getQualifier().accept(NameVisitor.getNameVisitor());
			this.className = NameVisitor.getNameVisitor().getName();
		}

		this.identifier = new DiffSimpleName(exp.getName());
	}

	public DiffName getClassName() {
		return this.className;
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
			acceptChild(visitor, getClassName());
			acceptChild(visitor, getIdentifier());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.className != null) {
			sb.append(this.className.toString());
			sb.append(".");
		}

		sb.append("super.");
		sb.append(this.identifier.toString());

		return sb.toString();
	}

}
