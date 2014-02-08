package za.ac.sun.cs.semdiff.jdtvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffQualifiedName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;

public class NameVisitor extends ASTVisitor {

	private static NameVisitor nv = null;
	private DiffName name = null;

	private NameVisitor() {
	}

	public static NameVisitor getNameVisitor() {
		if (nv == null) {
			nv = new NameVisitor();
		}
		return nv;
	}

	public DiffName getName() {
		return this.name;
	}

	public boolean visit(SimpleName node) {
		name = new DiffSimpleName(node);
		return false;
	}

	public boolean visit(QualifiedName node) {
		name = new DiffQualifiedName(node);
		return false;
	}

}
