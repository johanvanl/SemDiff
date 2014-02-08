package za.ac.sun.cs.semdiff.visitors;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffMethodInvocation;

public class RenameMethodInvocations extends DiffVisitor {

	private List<DiffMethodDeclaration> renamedMethods = null;

	private RenameMethodInvocations(List<DiffMethodDeclaration> renamedMethods) {
		this.renamedMethods = renamedMethods;
	}

	public static void renameMethodInvocations(DiffNode node,
			List<DiffMethodDeclaration> renamedMethods) {
		RenameMethodInvocations mi = new RenameMethodInvocations(renamedMethods);
		node.accept(mi);
	}

	@Override
	public boolean visit(DiffMethodInvocation node) {
		for (DiffMethodDeclaration method : renamedMethods){
			DiffMethodDeclaration related =(DiffMethodDeclaration)method.getDifference().getRelatedNode(); 
			if (node.getMethodName().equals(method.getIdentifier())){
				node.setMethodName(related.getIdentifier());
			}
		}
		return true;
	}
}
