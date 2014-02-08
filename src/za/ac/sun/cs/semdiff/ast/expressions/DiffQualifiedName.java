package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.QualifiedName;

import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffQualifiedName extends DiffName {

	private DiffName qualifier = null;
	private DiffSimpleName name = null;

	public DiffQualifiedName(QualifiedName name) {
		super(name);

		name.getQualifier().accept(NameVisitor.getNameVisitor());
		this.qualifier = NameVisitor.getNameVisitor().getName();

		this.name = new DiffSimpleName(name.getName());
	}

	public DiffName getQualifier() {
		return this.qualifier;
	}

	public DiffSimpleName getName() {
		return this.name;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getQualifier());
			acceptChild(visitor, getName());
		}
	}

	@Override
	public String toString() {
		return this.qualifier.toString() + "." + this.name.toString();
	}

}
