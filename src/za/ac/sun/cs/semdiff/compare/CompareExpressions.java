package za.ac.sun.cs.semdiff.compare;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayInitializer;
import za.ac.sun.cs.semdiff.ast.expressions.DiffAssignment;
import za.ac.sun.cs.semdiff.ast.expressions.DiffCastExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffClassInstanceCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffConditionalExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffFieldAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffInfixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffInstanceofExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffMethodInvocation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffParenthesizedExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffPostfixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffPrefixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffQualifiedName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSuperFieldAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSuperMethodInvocation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffThisExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.ast.expressions.DiffTypeLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffVariableDeclarationExpression;

public class CompareExpressions {

	protected static void compareExpressionNodes(DiffNode original,
			DiffNode revised) {

		if (original instanceof DiffArrayAccess
				&& revised instanceof DiffArrayAccess) {
			DiffArrayAccess original_ar = (DiffArrayAccess) original;
			DiffArrayAccess revised_ar = (DiffArrayAccess) revised;

			CompareNodes.compareNodes(original_ar.getArray(),
					revised_ar.getArray());
			CompareNodes.compareNodes(original_ar.getIndex(),
					revised_ar.getIndex());
			return;
		}

		if (original instanceof DiffArrayCreation
				&& revised instanceof DiffArrayCreation) {
			DiffArrayCreation original_ar = (DiffArrayCreation) original;
			DiffArrayCreation revised_ar = (DiffArrayCreation) revised;

			CompareNodes.compareNodes(original_ar.getType(),
					revised_ar.getType());
			CompareNodes.listsLcsSimilar(original_ar.getDimensions(),
					revised_ar.getDimensions());
			CompareNodes.compareNodes(original_ar.getArrayInitializer(),
					revised_ar.getArrayInitializer());
			return;
		}

		if (original instanceof DiffArrayInitializer
				&& revised instanceof DiffArrayInitializer) {
			DiffArrayInitializer original_ar = (DiffArrayInitializer) original;
			DiffArrayInitializer revised_ar = (DiffArrayInitializer) revised;

			CompareNodes.listsLcsSimilar(original_ar.getExpressions(),
					revised_ar.getExpressions());
			return;
		}

		if (original instanceof DiffAssignment
				&& revised instanceof DiffAssignment) {
			DiffAssignment original_as = (DiffAssignment) original;
			DiffAssignment revised_as = (DiffAssignment) revised;

			if (!original_as.getOperator().equals(revised_as.getOperator())) {
				original.getDifference().setDeleted();
				revised.getDifference().setAdded();
				return;
			}

			CompareNodes.compareNodes(original_as.getLeft(),
					revised_as.getLeft());
			CompareNodes.compareNodes(original_as.getRight(),
					revised_as.getRight());
			return;
		}

		// DiffBooleanLiteral

		if (original instanceof DiffCastExpression
				&& revised instanceof DiffCastExpression) {
			DiffCastExpression original_ca = (DiffCastExpression) original;
			DiffCastExpression revised_ca = (DiffCastExpression) revised;

			CompareNodes.compareNodes(original_ca.getType(),
					revised_ca.getType());
			CompareNodes.compareNodes(original_ca.getExpression(),
					revised_ca.getExpression());
			return;
		}

		// DiffCharacterLiteral

		if (original instanceof DiffClassInstanceCreation
				&& revised instanceof DiffClassInstanceCreation) {
			DiffClassInstanceCreation original_cl = (DiffClassInstanceCreation) original;
			DiffClassInstanceCreation revised_cl = (DiffClassInstanceCreation) revised;

			CompareNodes.compareNodes(original_cl.getExpression(),
					revised_cl.getExpression());
			CompareNodes.compareNodes(original_cl.getType(),
					revised_cl.getType());
			CompareNodes.listsLcsSimilar(original_cl.getTypeArguments(),
					revised_cl.getTypeArguments());
			CompareNodes.listsLcsSimilar(original_cl.getArguments(),
					revised_cl.getArguments());
			CompareNodes.compareNodes(
					original_cl.getAnonymousClassDeclaration(),
					revised_cl.getAnonymousClassDeclaration());
			return;
		}

		if (original instanceof DiffConditionalExpression
				&& revised instanceof DiffConditionalExpression) {
			DiffConditionalExpression original_co = (DiffConditionalExpression) original;
			DiffConditionalExpression revised_co = (DiffConditionalExpression) revised;

			CompareNodes.compareNodes(original_co.getExpression(),
					revised_co.getExpression());
			CompareNodes.compareNodes(original_co.getThenExpression(),
					revised_co.getThenExpression());
			CompareNodes.compareNodes(original_co.getElseExpression(),
					revised_co.getElseExpression());
			return;
		}

		if (original instanceof DiffFieldAccess
				&& revised instanceof DiffFieldAccess) {
			DiffFieldAccess original_fi = (DiffFieldAccess) original;
			DiffFieldAccess revised_fi = (DiffFieldAccess) revised;

			CompareNodes.compareNodes(original_fi.getExpression(),
					revised_fi.getExpression());
			CompareNodes.compareNodes(original_fi.getIdentifier(),
					revised_fi.getIdentifier());
			return;
		}

		if (original instanceof DiffInfixExpression
				&& revised instanceof DiffInfixExpression) {
			DiffInfixExpression original_in = (DiffInfixExpression) original;
			DiffInfixExpression revised_in = (DiffInfixExpression) revised;

			if (!original_in.getOperator().equals(revised_in.getOperator())) {
				original.getDifference().setDeleted();
				revised.getDifference().setAdded();
				return;
			}

			CompareNodes.lazyListsSimilar(
					original_in.getExpressions(), revised_in.getExpressions());
			return;
		}

		if (original instanceof DiffInstanceofExpression
				&& revised instanceof DiffInstanceofExpression) {
			DiffInstanceofExpression original_in = (DiffInstanceofExpression) original;
			DiffInstanceofExpression revised_in = (DiffInstanceofExpression) revised;

			CompareNodes.compareNodes(original_in.getExpression(),
					revised_in.getExpression());
			CompareNodes.compareNodes(original_in.getType(),
					revised_in.getType());
			return;
		}

		if (original instanceof DiffMethodInvocation
				&& revised instanceof DiffMethodInvocation) {
			DiffMethodInvocation original_me = (DiffMethodInvocation) original;
			DiffMethodInvocation revised_me = (DiffMethodInvocation) revised;

			CompareNodes.compareNodes(original_me.getExpression(),
					revised_me.getExpression());
			CompareNodes.listsLcsSimilar(original_me.getTypeArguments(),
					revised_me.getTypeArguments());
			CompareNodes.compareNodes(original_me.getMethodName(),
					revised_me.getMethodName());
			CompareNodes.listsLcsSimilar(original_me.getArguments(),
					revised_me.getArguments());
			return;
		}

		// DiffNullLiteral

		// DiffNumberLiteral

		if (original instanceof DiffParenthesizedExpression
				&& revised instanceof DiffParenthesizedExpression) {
			DiffParenthesizedExpression original_pa = (DiffParenthesizedExpression) original;
			DiffParenthesizedExpression revised_pa = (DiffParenthesizedExpression) revised;

			CompareNodes.compareNodes(original_pa.getExpression(),
					revised_pa.getExpression());
			return;
		}

		if (original instanceof DiffPostfixExpression
				&& revised instanceof DiffPostfixExpression) {
			DiffPostfixExpression original_po = (DiffPostfixExpression) original;
			DiffPostfixExpression revised_po = (DiffPostfixExpression) revised;

			if (!original_po.getOperator().equals(revised_po.getOperator())) {
				original.getDifference().setDeleted();
				revised.getDifference().setAdded();
				return;
			}

			CompareNodes.compareNodes(original_po.getExpression(),
					revised_po.getExpression());
			return;
		}

		if (original instanceof DiffPrefixExpression
				&& revised instanceof DiffPrefixExpression) {
			DiffPrefixExpression original_pr = (DiffPrefixExpression) original;
			DiffPrefixExpression revised_pr = (DiffPrefixExpression) revised;

			if (!original_pr.getOperator().equals(revised_pr.getOperator())) {
				original.getDifference().setDeleted();
				revised.getDifference().setAdded();
				return;
			}

			CompareNodes.compareNodes(original_pr.getExpression(),
					revised_pr.getExpression());
			return;
		}

		if (original instanceof DiffQualifiedName
				&& revised instanceof DiffQualifiedName) {
			DiffQualifiedName original_qu = (DiffQualifiedName) original;
			DiffQualifiedName revised_qu = (DiffQualifiedName) revised;

			CompareNodes.compareNodes(original_qu.getQualifier(),
					revised_qu.getQualifier());
			CompareNodes.compareNodes(original_qu.getName(),
					revised_qu.getName());
			return;
		}

		// DiffSimpleName

		// DiffStringLiteral

		if (original instanceof DiffSuperFieldAccess
				&& revised instanceof DiffSuperFieldAccess) {
			DiffSuperFieldAccess original_su = (DiffSuperFieldAccess) original;
			DiffSuperFieldAccess revised_su = (DiffSuperFieldAccess) revised;

			CompareNodes.compareNodes(original_su.getClassName(),
					revised_su.getClassName());
			CompareNodes.compareNodes(original_su.getIdentifier(),
					revised_su.getIdentifier());
			return;
		}

		if (original instanceof DiffSuperMethodInvocation
				&& revised instanceof DiffSuperMethodInvocation) {
			DiffSuperMethodInvocation original_su = (DiffSuperMethodInvocation) original;
			DiffSuperMethodInvocation revised_su = (DiffSuperMethodInvocation) revised;

			CompareNodes.compareNodes(original_su.getClassName(),
					revised_su.getClassName());
			CompareNodes.listsLcsSimilar(original_su.getTypes(),
					revised_su.getTypes());
			CompareNodes.compareNodes(original_su.getIdentifier(),
					revised_su.getIdentifier());
			CompareNodes.listsLcsSimilar(original_su.getArguments(),
					revised_su.getArguments());
			return;
		}

		if (original instanceof DiffThisExpression
				&& revised instanceof DiffThisExpression) {
			DiffThisExpression original_th = (DiffThisExpression) original;
			DiffThisExpression revised_th = (DiffThisExpression) revised;

			CompareNodes.compareNodes(original_th.getClassName(),
					revised_th.getClassName());
			return;
		}

		if (original instanceof DiffType && revised instanceof DiffType) {
			DiffType original_th = (DiffType) original;
			DiffType revised_th = (DiffType) revised;

			CompareNodes.compareNodes(original_th.getType(),
					revised_th.getType());
			return;
		}

		if (original instanceof DiffTypeLiteral
				&& revised instanceof DiffTypeLiteral) {
			DiffTypeLiteral original_ty = (DiffTypeLiteral) original;
			DiffTypeLiteral revised_ty = (DiffTypeLiteral) revised;

			CompareNodes.compareNodes(original_ty.getType(),
					revised_ty.getType());
			return;
		}

		if (original instanceof DiffVariableDeclarationExpression
				&& revised instanceof DiffVariableDeclarationExpression) {
			DiffVariableDeclarationExpression original_va = (DiffVariableDeclarationExpression) original;
			DiffVariableDeclarationExpression revised_va = (DiffVariableDeclarationExpression) revised;

			CompareNodes.compareNodes(original_va.getModifiers(),
					revised_va.getModifiers());
			CompareNodes.compareNodes(original_va.getType(),
					revised_va.getType());
			CompareNodes.listsLcsSimilar(original_va.getFragments(),
					revised_va.getFragments());
			return;
		}

		CompareNodes.lazyAddedDeleted(original, revised);
	}

}
