package za.ac.sun.cs.semdiff.ast;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// import [ static ] Name [ . * ] ;
public class DiffImportDeclaration extends DiffNode {

	private boolean isStatic = false;
	private boolean onDemand = false;
	private DiffName name = null;

	public DiffImportDeclaration(ImportDeclaration decl) {
		super(decl);
		this.isStatic = decl.isStatic();
		this.onDemand = decl.isOnDemand();

		decl.getName().accept(NameVisitor.getNameVisitor());
		this.name = NameVisitor.getNameVisitor().getName();
	}

	public boolean isStatic() {
		return this.isStatic;
	}

	public boolean onDemand() {
		return this.onDemand;
	}

	public DiffName getName() {
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
			acceptChild(visitor, getName());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("import ");

		if (isStatic) {
			sb.append("static ");
		}

		sb.append(name.toString());

		if (onDemand) {
			sb.append(".*");
		}

		return sb.toString();
	}
}
