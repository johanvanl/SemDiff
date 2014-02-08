package za.ac.sun.cs.semdiff.visitors;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.ast.body.DiffFieldDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;

public class FieldDeclarationNames extends DiffVisitor {

	private List<DiffSimpleName> names = null;

	private boolean follow = false;

	public FieldDeclarationNames() {
		this.names = new ArrayList<DiffSimpleName>();
	}

	public List<DiffSimpleName> getNames() {
		return this.names;
	}

	@Override
	public boolean visit(DiffFieldDeclaration node) {
		for (DiffVariableDeclarationFragment var : node.getVariables()) {
			follow = true;
			var.accept(this);
		}
		return false;
	}

	@Override
	public boolean visit(DiffVariableDeclarationFragment node) {
		if (!follow) {
			return true;
		}

		follow = false;

		this.names.add(node.getName());
		return false;
	}

}
