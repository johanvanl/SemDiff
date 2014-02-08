package za.ac.sun.cs.semdiff.ast;

import org.eclipse.jdt.core.dom.PackageDeclaration;

import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// PackageDeclaration:
//   [ Javadoc ] { Annotation } package Name ;
public class DiffPackageDeclaration extends DiffNode {

	private DiffName name = null;

	public DiffPackageDeclaration(PackageDeclaration decl) {
		super(decl);
		decl.getName().accept(NameVisitor.getNameVisitor());
		this.name = NameVisitor.getNameVisitor().getName();
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
		return "package " + name;
	}

}
