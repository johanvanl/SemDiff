package za.ac.sun.cs.semdiff.compare;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.ast.statements.DiffStatement;
import za.ac.sun.cs.semdiff.lcs.LcsDiffNodeSimilar;
import za.ac.sun.cs.semdiff.lcs.LongestCommonSubsequence.DiffEntry;
import za.ac.sun.cs.semdiff.similarity.LcsDistance;
import za.ac.sun.cs.semdiff.similarity.Similarity;
import za.ac.sun.cs.semdiff.utils.GetDifferenceNodes;
import za.ac.sun.cs.semdiff.visitors.TokenVisitor;

public class CompareNodes {

	protected static int MINIMUM_NUMBER_OF_TOKENS = 25;
	protected static double MINIMUM_SIMILARITY_RATIO = 0.85;

	public static void startComparison(DiffCompilationUnit original,
			DiffCompilationUnit revised) {
		compareNodes(original, revised);
		movedBodyDeclarations(original, revised);
		TypeDeclRenames.renames(original, revised);
		MethodDeclRenames.renames(original, revised);
		FieldDeclRenames.renames(original, revised);
	}

	protected static void compareNodes(DiffNode original, DiffNode revised) {
		if (original == null && revised == null) {
			return;
		}

		if (original != null && revised == null) {
			original.getDifference().setDeleted();
			return;
		}

		if (revised != null && original == null) {
			revised.getDifference().setAdded();
			return;
		}

		if (original instanceof DiffStatement) {
			CompareStatements.compareStatementNodes(original, revised);
			return;
		}

		if (original instanceof DiffExpression) {
			CompareExpressions.compareExpressionNodes(original, revised);
			return;
		}

		CompareBodyDeclarations.compareBodyDeclarationNodes(original, revised);
	}

	protected static void lazyAddedDeleted(DiffNode original, DiffNode revised) {
		if (original == null && revised == null) {
			return;
		}

		if (original != null && revised == null) {
			original.getDifference().setDeleted();
			return;
		}

		if (revised != null && original == null) {
			revised.getDifference().setAdded();
			return;
		}

		if (!original.equals(revised)) {
			original.getDifference().setDeleted();
			revised.getDifference().setAdded();
		}
	}

	protected static void lazyListsEqual(List<? extends DiffNode> original,
			List<? extends DiffNode> revised) {
		for (int i = 0; i < original.size(); i++) {
			if (!revised.contains(original.get(i))) {
				original.get(i).getDifference().setDeleted();
			}
		}
		for (int i = 0; i < revised.size(); i++) {
			if (!original.contains(revised.get(i))) {
				revised.get(i).getDifference().setAdded();
			}
		}
	}

	protected static void lazyListsSimilar(List<? extends DiffNode> original,
			List<? extends DiffNode> revised) {

		// Make copies of the lists
		List<DiffNode> original_copy = new ArrayList<DiffNode>(original);
		List<DiffNode> revised_copy = new ArrayList<DiffNode>(revised);

		int original_index = 0;
		while (original_index < original_copy.size()) {
			int revised_index = 0;
			while (revised_index < revised_copy.size()) {

				if (isSimilar(original_copy.get(original_index),
						revised_copy.get(revised_index))) {
					compareNodes(original_copy.get(original_index),
							revised_copy.get(revised_index));

					original_copy.remove(original_index);
					revised_copy.remove(revised_index);
					original_index--;

					break;
				}

				revised_index++;
			}
			original_index++;
		}

		// //////////////////////////////////////////////////
		// Set the rest to deleted or added
		for (DiffNode or : original_copy) {
			or.getDifference().setDeleted();
		}

		for (DiffNode re : revised_copy) {
			re.getDifference().setAdded();
		}
	}

	@SuppressWarnings("unchecked")
	protected static void listsLcsSimilar(List<? extends DiffNode> original,
			List<? extends DiffNode> revised) {

		if (original.size() == 0 && revised.size() == 0) {
			return;
		}

		LcsDiffNodeSimilar lcs = new LcsDiffNodeSimilar(
				(List<DiffNode>) original, (List<DiffNode>) revised);
		List<DiffEntry<DiffNode>> lcs_list = lcs.getOptimalLcs();

		int original_index = 0;
		int revised_index = 0;
		for (DiffEntry<DiffNode> entry : lcs_list) {
			if (entry.isAdded()) {
				revised.get(revised_index).getDifference().setAdded();
				revised_index++;
				continue;
			}

			if (entry.isRemoved()) {
				original.get(original_index).getDifference().setDeleted();
				original_index++;
				continue;
			}

			// it's a match
			if (!original.get(original_index)
					.equals(revised.get(revised_index))) {
				CompareNodes.compareNodes(original.get(original_index),
						revised.get(revised_index));
			}

			original_index++;
			revised_index++;
		}

	}

	protected static void movedBodyDeclarations(DiffCompilationUnit original,
			DiffCompilationUnit revised) {

		GetDifferenceNodes diff_original = new GetDifferenceNodes(original);
		GetDifferenceNodes diff_revised = new GetDifferenceNodes(revised);

		List<DiffBodyDeclaration> deleted = diff_original
				.getDeletedBodyDeclarations();
		List<DiffBodyDeclaration> added = diff_revised
				.getAddedBodyDeclarations();

		for (DiffBodyDeclaration del : deleted) {
			for (DiffBodyDeclaration add : added) {
				if (add.getIdentifier().equals(del.getIdentifier())
						&& isSimilar(add, del)) {
					add.getDifference().setUnchanged();
					del.getDifference().setUnchanged();

					compareNodes(add, del);

					add.getDifference().setMoved();
					del.getDifference().setMoved();
					break;
				}
			}
		}

	}

	public static boolean isSimilar(DiffNode original, DiffNode revised) {
		if (original == null && revised == null) {
			return true;
		}

		if (original == null) {
			return false;
		}

		if (!isSameType(original, revised)) {
			return false;
		}

		if (original.equals(revised)) {
			return true;
		}

		if (original instanceof DiffBodyDeclaration) {
			if (CompareBodyDeclarations.isSimilar(original, revised)) {
				return true;
			}
		}

		if (original instanceof DiffStatement) {
			if (CompareStatements.isSimilar(original, revised)) {
				return true;
			}
		}

		Similarity sim = new LcsDistance();
		double similarity = sim.similarity(original, revised);
		int tokens = Math.min(TokenVisitor.numberOfTokens(original),
				TokenVisitor.numberOfTokens(revised));
		if (similarity > MINIMUM_SIMILARITY_RATIO
				&& tokens > MINIMUM_NUMBER_OF_TOKENS) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isSameType(DiffNode original, DiffNode revised) {
		if (original == null || revised == null) {
			return false;
		}
		Class or = original.getClass();
		Class re = revised.getClass();
		return or.equals(re);
	}

}
