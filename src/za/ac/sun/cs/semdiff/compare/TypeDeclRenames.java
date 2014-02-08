package za.ac.sun.cs.semdiff.compare;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffTypeDeclaration;
import za.ac.sun.cs.semdiff.lcs.LcsDiffNodeEqual;
import za.ac.sun.cs.semdiff.lcs.LongestCommonSubsequence.DiffEntry;
import za.ac.sun.cs.semdiff.utils.GetDifferenceNodes;
import za.ac.sun.cs.semdiff.visitors.RenameTypes;

public class TypeDeclRenames {

	public static void renames(DiffCompilationUnit original,
			DiffCompilationUnit revised) {
		GetDifferenceNodes diff_or = new GetDifferenceNodes(original);
		List<DiffNode> deleted = diff_or.getDeletedNodes();

		GetDifferenceNodes diff_re = new GetDifferenceNodes(revised);
		List<DiffNode> added = diff_re.getAddedNodes();

		List<DiffBodyDeclaration> revised_renamedTypes = new ArrayList<DiffBodyDeclaration>();
		List<DiffBodyDeclaration> ren_body = diff_re
				.getRenamedBodyDeclarations();
		for (DiffBodyDeclaration body : ren_body) {
			if (body instanceof DiffTypeDeclaration
					|| body instanceof DiffEnumDeclaration) {
				revised_renamedTypes.add(body);
			}
		}

		RenameTypes.renameTypes(revised, revised_renamedTypes);

		LcsDiffNodeEqual lcs = new LcsDiffNodeEqual(deleted, added);
		List<DiffEntry<DiffNode>> lcs_list = lcs.diff();

		for (DiffEntry<DiffNode> entry : lcs_list) {
			if (entry.isSame()) {
				entry.getValue().getDifference().setUnchanged();
				entry.getYValue().getDifference().setUnchanged();
			}
		}
	}

}
