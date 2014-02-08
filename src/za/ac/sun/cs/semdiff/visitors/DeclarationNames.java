package za.ac.sun.cs.semdiff.visitors;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;

public class DeclarationNames extends DiffVisitor {

	private List<DiffSimpleName> names = null;

	public DeclarationNames() {
		this.names = new ArrayList<DiffSimpleName>();
	}

	public List<DiffSimpleName> getNames() {
		return this.names;
	}

	@Override
	public boolean visit(DiffSimpleName node) {
		if (node.isDeclaration()) {
			this.names.add(node);
		}
		return true;
	}

}
