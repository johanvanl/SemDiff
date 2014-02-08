package za.ac.sun.cs.semdiff.compare;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffAnonymousClassDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffCatchClause;
import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.DiffSingleVariableDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffTypeParameter;
import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumConstantDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffFieldDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffTypeDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;

/*
 * These aren't all BodyDeclarations,
 * but will everything in za.ac.sun.cs.semdiff.ast
 * will fall under this.
 */
public class CompareBodyDeclarations {

	protected static void compareBodyDeclarationNodes(DiffNode original,
			DiffNode revised) {

		if (original instanceof DiffAnonymousClassDeclaration
				&& revised instanceof DiffAnonymousClassDeclaration) {
			DiffAnonymousClassDeclaration original_an = (DiffAnonymousClassDeclaration) original;
			DiffAnonymousClassDeclaration revised_an = (DiffAnonymousClassDeclaration) revised;

			compareBodyDeclarations(original_an.getBodyDeclarations(),
					revised_an.getBodyDeclarations());
			return;
		}

		if (original instanceof DiffCatchClause
				&& revised instanceof DiffCatchClause) {
			DiffCatchClause original_ca = (DiffCatchClause) original;
			DiffCatchClause revised_ca = (DiffCatchClause) revised;

			CompareNodes.compareNodes(original_ca.getParameter(),
					revised_ca.getParameter());
			CompareNodes.compareNodes(original_ca.getBlock(),
					revised_ca.getBlock());
			return;
		}

		if (original instanceof DiffCompilationUnit
				&& revised instanceof DiffCompilationUnit) {
			DiffCompilationUnit original_co = (DiffCompilationUnit) original;
			DiffCompilationUnit revised_co = (DiffCompilationUnit) revised;

			CompareNodes.compareNodes(original_co.getPackage(),
					revised_co.getPackage());
			CompareNodes.lazyListsEqual(original_co.getImports(),
					revised_co.getImports());
			compareBodyDeclarations(original_co.getTypes(),
					revised_co.getTypes());
			return;
		}

		if (original instanceof DiffEnumConstantDeclaration
				&& revised instanceof DiffEnumConstantDeclaration) {
			DiffEnumConstantDeclaration original_en = (DiffEnumConstantDeclaration) original;
			DiffEnumConstantDeclaration revised_en = (DiffEnumConstantDeclaration) revised;

			CompareNodes.compareNodes(original_en.getModifiers(),
					revised_en.getModifiers());
			CompareNodes.listsLcsSimilar(original_en.getExpressions(),
					revised_en.getExpressions());
			CompareNodes.compareNodes(
					original_en.getAnonymousClassDeclaration(),
					revised_en.getAnonymousClassDeclaration());
			return;
		}

		if (original instanceof DiffEnumDeclaration
				&& revised instanceof DiffEnumDeclaration) {
			DiffEnumDeclaration original_en = (DiffEnumDeclaration) original;
			DiffEnumDeclaration revised_en = (DiffEnumDeclaration) revised;

			if (!original_en.getIdentifier().equals(revised_en.getIdentifier())) {
				original_en.getDifference().setRenamed();
				revised_en.getDifference().setRenamed();
				revised_en.getDifference().setRelatedReference(original_en);
			}

			CompareNodes.compareNodes(original_en.getModifiers(),
					revised_en.getModifiers());
			CompareNodes.lazyListsEqual(
					original_en.getSuperInterfaceTypes(),
					revised_en.getSuperInterfaceTypes());
			CompareNodes.lazyListsSimilar(
					original_en.getEnumConstants(),
					revised_en.getEnumConstants());
			compareBodyDeclarations(original_en.getBodyDeclarations(),
					revised_en.getBodyDeclarations());
			return;
		}

		if (original instanceof DiffFieldDeclaration
				&& revised instanceof DiffFieldDeclaration) {
			DiffFieldDeclaration original_fi = (DiffFieldDeclaration) original;
			DiffFieldDeclaration revised_fi = (DiffFieldDeclaration) revised;

			CompareNodes.compareNodes(original_fi.getModifiers(),
					revised_fi.getModifiers());
			CompareNodes.compareNodes(original_fi.getType(),
					revised_fi.getType());
			CompareNodes.listsLcsSimilar(original_fi.getVariables(),
					revised_fi.getVariables());
			return;
		}

		// DiffImportDeclaration

		if (original instanceof DiffMethodDeclaration
				&& revised instanceof DiffMethodDeclaration) {
			DiffMethodDeclaration original_me = (DiffMethodDeclaration) original;
			DiffMethodDeclaration revised_me = (DiffMethodDeclaration) revised;

			if (!original_me.getIdentifier().equals(revised_me.getIdentifier())) {
				original_me.getDifference().setRenamed();
				revised_me.getDifference().setRenamed();
				revised_me.getDifference().setRelatedReference(original_me);
			}

			CompareNodes.compareNodes(original_me.getModifiers(),
					revised_me.getModifiers());
			CompareNodes.listsLcsSimilar(original_me.getTypeParameters(),
					revised_me.getTypeParameters());
			CompareNodes.compareNodes(original_me.getReturnType(),
					revised_me.getReturnType());
			CompareNodes.lazyListsEqual(
					original_me.getParamaters(), revised_me.getParamaters());
			CompareNodes.lazyListsEqual(
					original_me.getThrownExceptions(),
					revised_me.getThrownExceptions());
			CompareNodes.compareNodes(original_me.getBlock(),
					revised_me.getBlock());

			// Check for variable renames within the method.
			MethodVariableRenames.renames(original_me, revised_me);

			return;
		}

		if (original instanceof DiffModifiers
				&& revised instanceof DiffModifiers) {
			DiffModifiers original_mo = (DiffModifiers) original;
			DiffModifiers revised_mo = (DiffModifiers) revised;

			CompareNodes.lazyListsEqual(original_mo.getModifiers(),
					revised_mo.getModifiers());
			return;
		}

		// DiffPackageDeclaration

		if (original instanceof DiffSingleVariableDeclaration
				&& revised instanceof DiffSingleVariableDeclaration) {
			DiffSingleVariableDeclaration original_mo = (DiffSingleVariableDeclaration) original;
			DiffSingleVariableDeclaration revised_mo = (DiffSingleVariableDeclaration) revised;

			CompareNodes.compareNodes(original_mo.getModifiers(),
					revised_mo.getModifiers());
			CompareNodes.compareNodes(original_mo.getType(),
					revised_mo.getType());
			CompareNodes.compareNodes(original_mo.getIdentifier(),
					revised_mo.getIdentifier());
			CompareNodes.compareNodes(original_mo.getExpression(),
					revised_mo.getExpression());
			return;
		}

		if (original instanceof DiffTypeDeclaration
				&& revised instanceof DiffTypeDeclaration) {
			DiffTypeDeclaration original_ty = (DiffTypeDeclaration) original;
			DiffTypeDeclaration revised_ty = (DiffTypeDeclaration) revised;

			if (original_ty.isInterface() != revised_ty.isInterface()) {
				original_ty.getDifference().setDeleted();
				revised_ty.getDifference().setAdded();
			}

			if (!original_ty.getIdentifier().equals(revised_ty.getIdentifier())) {
				original_ty.getDifference().setRenamed();
				revised_ty.getDifference().setRenamed();
				revised_ty.getDifference().setRelatedReference(original_ty);
			}

			CompareNodes.compareNodes(original_ty.getModifiers(),
					revised_ty.getModifiers());
			CompareNodes.listsLcsSimilar(original_ty.getTypeParameters(),
					revised_ty.getTypeParameters());
			CompareNodes.compareNodes(original_ty.getSuperClass(),
					revised_ty.getSuperClass());
			CompareNodes.lazyListsEqual(
					original_ty.getSuperInterfaces(),
					revised_ty.getSuperInterfaces());
			CompareNodes.lazyListsSimilar(
					original_ty.getInstanceVariables(),
					revised_ty.getInstanceVariables());
			compareBodyDeclarations(original_ty.getTypes(),
					revised_ty.getTypes());
			compareBodyDeclarations(original_ty.getMethods(),
					revised_ty.getMethods());

			return;
		}

		if (original instanceof DiffTypeParameter
				&& revised instanceof DiffTypeParameter) {
			DiffTypeParameter original_ty = (DiffTypeParameter) original;
			DiffTypeParameter revised_ty = (DiffTypeParameter) revised;

			CompareNodes.compareNodes(original_ty.getVariable(),
					revised_ty.getVariable());
			CompareNodes.lazyListsEqual(original_ty.getTypes(),
					revised_ty.getTypes());
			return;
		}

		if (original instanceof DiffVariableDeclarationFragment
				&& revised instanceof DiffVariableDeclarationFragment) {
			DiffVariableDeclarationFragment original_va = (DiffVariableDeclarationFragment) original;
			DiffVariableDeclarationFragment revised_va = (DiffVariableDeclarationFragment) revised;

			CompareNodes.compareNodes(original_va.getName(),
					revised_va.getName());
			CompareNodes.compareNodes(original_va.getExpression(),
					revised_va.getExpression());
			return;
		}

		CompareNodes.lazyAddedDeleted(original, revised);
	}

	/**
	 * 
	 * @param original
	 * @param revised
	 */
	@SuppressWarnings("unchecked")
	protected static void compareBodyDeclarations(
			List<? extends DiffNode> original, List<? extends DiffNode> revised) {

		// Make copies, so that there isn't any work done on the original copies
		List<DiffBodyDeclaration> original_body = new ArrayList<DiffBodyDeclaration>(
				(List<DiffBodyDeclaration>) original);
		List<DiffBodyDeclaration> revised_body = new ArrayList<DiffBodyDeclaration>(
				(List<DiffBodyDeclaration>) revised);

		// //////////////////////////////////////////////////
		// Remove all unchanged bodyDeclarations
		int index = 0;
		while (index < original_body.size()) {
			if (revised_body.contains(original_body.get(index))) {
				revised_body.remove(original_body.get(index));
				original_body.remove(index);
				index--;
			}
			index++;
		}

		// //////////////////////////////////////////////////
		// If two contain the same names and are of the same types
		int original_index = 0;
		while (original_index < original_body.size()) {
			int revised_index = 0;
			while (revised_index < revised_body.size()) {
				DiffName original_identifier = original_body
						.get(original_index).getIdentifier();
				DiffName revised_identifier = revised_body.get(revised_index)
						.getIdentifier();
				if (original_identifier != null
						&& original_identifier.equals(revised_identifier)
						&& CompareNodes.isSameType(
								original_body.get(original_index),
								revised_body.get(revised_index))) {

					CompareNodes.compareNodes(
							original_body.get(original_index),
							revised_body.get(revised_index));

					original_body.remove(original_index);
					revised_body.remove(revised_index);
					original_index--;
					break;
				}
				revised_index++;
			}
			original_index++;
		}

		// //////////////////////////////////////////////////
		// Enum constants should stop here
		if ((original_body.size() > 0 && original_body.get(0) instanceof DiffEnumConstantDeclaration)
				|| (revised_body.size() > 0 && revised_body.get(0) instanceof DiffEnumConstantDeclaration)) {
			CompareNodes
					.lazyListsEqual(original_body, revised_body);
			return;
		}

		// //////////////////////////////////////////////////
		// Check for renames, where only the identifier changed
		original_index = 0;
		while (original_index < original_body.size()) {
			int revised_index = 0;
			while (revised_index < revised_body.size()) {

				DiffBodyDeclaration originalBodyDecl = original_body
						.get(original_index);
				DiffBodyDeclaration revisedBodyDecl = revised_body
						.get(revised_index);

				if (originalBodyDecl instanceof DiffFieldDeclaration
						|| revisedBodyDecl instanceof DiffFieldDeclaration) {
					revised_index++;
					continue;
				}

				DiffSimpleName actualName = revisedBodyDecl.getIdentifier();

				// Temporarily change the revised methods name to
				// the name of the original one.
				revisedBodyDecl.setIdentifier(originalBodyDecl.getIdentifier());

				boolean equalDifferentIdentifiers = originalBodyDecl
						.equals(revisedBodyDecl);

				// Set it back
				revisedBodyDecl.setIdentifier(actualName);

				if (equalDifferentIdentifiers) {
					CompareNodes
							.compareNodes(originalBodyDecl, revisedBodyDecl);

					original_body.remove(original_index);
					revised_body.remove(revised_index);
					original_index--;
					break;
				}

				revised_index++;
			}
			original_index++;
		}

		// //////////////////////////////////////////////////
		// Check for renames, where the nodes are similar and above
		// the threshold for the length of the tokens
		CompareNodes.lazyListsSimilar(original_body, revised_body);
	}

	protected static boolean isSimilar(DiffNode original, DiffNode revised) {

		// DiffAnonymousClassDeclaration

		// DiffCatchClause

		// DiffCompilationUnit

		// DiffEnumConstantDeclaration
		if (original instanceof DiffEnumConstantDeclaration
				&& revised instanceof DiffEnumConstantDeclaration) {
			DiffEnumConstantDeclaration original_en = (DiffEnumConstantDeclaration) original;
			DiffEnumConstantDeclaration revised_en = (DiffEnumConstantDeclaration) revised;
			return original_en.getIdentifier().equals(
					revised_en.getIdentifier());
		}

		// DiffEnumDeclaration

		// DiffFieldDeclaration

		// DiffImportDeclaration

		// DiffMethodDeclaration

		// DiffModifiers

		// DiffPackageDeclaration

		// DiffSingleVariableDeclaration

		// DiffTypeDeclaration

		// DiffTypeParameter

		// DiffVariableDeclarationFragment

		return false;
	}
}
