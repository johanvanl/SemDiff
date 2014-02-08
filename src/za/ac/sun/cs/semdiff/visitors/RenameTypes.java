package za.ac.sun.cs.semdiff.visitors;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;

public class RenameTypes extends DiffVisitor {

	private List<DiffBodyDeclaration> renamedTypes = null;

	private RenameTypes(List<DiffBodyDeclaration> renamedTypes) {
		this.renamedTypes = renamedTypes;
	}

	public static void renameTypes(DiffNode node,
			List<DiffBodyDeclaration> renamedTypes) {
		RenameTypes mi = new RenameTypes(renamedTypes);
		node.accept(mi);
	}

	@Override
	public boolean visit(DiffType node) {
		for (DiffBodyDeclaration type : renamedTypes) {
			DiffBodyDeclaration related = (DiffBodyDeclaration) type
					.getDifference().getRelatedNode();
			if (node.getType().equals(type.getIdentifier())) {
				node.setType(related.getIdentifier());
			}
		}
		return true;
	}
}
