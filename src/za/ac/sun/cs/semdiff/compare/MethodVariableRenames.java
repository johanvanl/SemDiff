package za.ac.sun.cs.semdiff.compare;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.lcs.LcsDiffNodeEqual;
import za.ac.sun.cs.semdiff.lcs.LongestCommonSubsequence.DiffEntry;
import za.ac.sun.cs.semdiff.utils.GetDifferenceNodes;
import za.ac.sun.cs.semdiff.utils.Permutations;
import za.ac.sun.cs.semdiff.visitors.DeclarationNames;
import za.ac.sun.cs.semdiff.visitors.SimpleNameRename;

public class MethodVariableRenames {

	protected static void renames(DiffMethodDeclaration original,
			DiffMethodDeclaration revised) {
		GetDifferenceNodes diff_or = new GetDifferenceNodes(original);
		List<DiffNode> deleted = diff_or.getDeletedNodes();

		GetDifferenceNodes diff_re = new GetDifferenceNodes(revised);
		List<DiffNode> added = diff_re.getAddedNodes();

		DeclarationNames dn = new DeclarationNames();
		for (DiffNode del : deleted) {
			del.accept(dn);
		}
		List<DiffSimpleName> del_declarations = dn.getNames();

		dn = new DeclarationNames();
		for (DiffNode add : added) {
			add.accept(dn);
		}
		List<DiffSimpleName> add_declarations = dn.getNames();

		int best_lcs = -1;
		List<DiffSimpleName> best_del = null;
		List<DiffSimpleName> best_add = null;

		int min = Math.min(del_declarations.size(), add_declarations.size());
		int max = Math.max(del_declarations.size(), add_declarations.size());

		if (min > 5 || (min > 3 && max > 20)) {
			return;
		}

		for (int i = 0; i < min; i++) {
			List<List<DiffSimpleName>> del_perms = (new Permutations<DiffSimpleName>(
					del_declarations, i + 1)).getPermutations();
			List<List<DiffSimpleName>> add_perms = (new Permutations<DiffSimpleName>(
					add_declarations, i + 1)).getPermutations();

			for (List<DiffSimpleName> del : del_perms) {
				for (List<DiffSimpleName> add : add_perms) {
					SimpleNameRename renameVisitor = new SimpleNameRename(del,
							add);
					for (DiffNode node : deleted) {
						node.accept(renameVisitor);
					}

					LcsDiffNodeEqual lcs = new LcsDiffNodeEqual(deleted, added);
					List<DiffEntry<DiffNode>> lcs_list = lcs.diff();

					int same = 0;
					for (DiffEntry<DiffNode> entry : lcs_list) {
						if (entry.isSame()) {
							same++;
						}
					}

					if (same > best_lcs) {
						best_lcs = same;
						best_del = del;
						best_add = add;
					}

					renameVisitor.revertRenames();
				}
			}

		}

		if (best_lcs < 3) {
			return;
		}

		for (DiffSimpleName name : del_declarations) {
			if (best_del.contains(name)) {
				name.getDifference().setRenamed();
			}
		}

		for (DiffSimpleName name : add_declarations) {
			if (best_add.contains(name)) {
				name.getDifference().setRenamed();
			}
		}

		SimpleNameRename renameVisitor = new SimpleNameRename(best_del,
				best_add);
		for (DiffNode node : deleted) {
			node.accept(renameVisitor);
		}

		LcsDiffNodeEqual lcs = new LcsDiffNodeEqual(deleted, added);
		List<DiffEntry<DiffNode>> lcs_list = lcs.diff();

		for (DiffEntry<DiffNode> entry : lcs_list) {
			if (entry.isSame()) {
				entry.getValue().getDifference().setUnchanged();
				entry.getYValue().getDifference().setUnchanged();
			}
		}

		// Don't revert the renames, so that method decl renames as well as
		// types can use these
		// renameVisitor.revertRenames();

	}

}
