package za.ac.sun.cs.semdiff.compare;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.statements.DiffAssertStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffBlock;
import za.ac.sun.cs.semdiff.ast.statements.DiffBreakStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffConstructorInvocation;
import za.ac.sun.cs.semdiff.ast.statements.DiffContinueStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffDoStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffEnhancedForStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffExpressionStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffForStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffIfStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffLabeledStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffReturnStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffSuperConstructorInvocation;
import za.ac.sun.cs.semdiff.ast.statements.DiffSwitchCase;
import za.ac.sun.cs.semdiff.ast.statements.DiffSwitchStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffSynchronizedStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffThrowStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffTryStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffVariableDeclarationStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffWhileStatement;

public class CompareStatements {

	/*
	 * All the nodes that don't get catched, fall through to the
	 * lazyAddedDeleted.
	 */
	protected static void compareStatementNodes(DiffNode original,
			DiffNode revised) {

		if (original instanceof DiffAssertStatement
				&& revised instanceof DiffAssertStatement) {
			DiffAssertStatement original_br = (DiffAssertStatement) original;
			DiffAssertStatement revised_br = (DiffAssertStatement) revised;

			CompareNodes.compareNodes(original_br.getExpression(),
					revised_br.getExpression());
			CompareNodes.compareNodes(original_br.getMessage(),
					revised_br.getMessage());
			return;
		}

		if (original instanceof DiffBlock && revised instanceof DiffBlock) {
			DiffBlock original_block = (DiffBlock) original;
			DiffBlock revised_block = (DiffBlock) revised;

			CompareNodes.listsLcsSimilar(original_block.getStatements(),
					revised_block.getStatements());
			return;
		}

		if (original instanceof DiffBreakStatement
				&& revised instanceof DiffBreakStatement) {
			DiffBreakStatement original_br = (DiffBreakStatement) original;
			DiffBreakStatement revised_br = (DiffBreakStatement) revised;

			CompareNodes.compareNodes(original_br.getIdentifier(),
					revised_br.getIdentifier());
			return;
		}

		if (original instanceof DiffConstructorInvocation
				&& revised instanceof DiffConstructorInvocation) {
			DiffConstructorInvocation original_cons = (DiffConstructorInvocation) original;
			DiffConstructorInvocation revised_cons = (DiffConstructorInvocation) revised;

			CompareNodes.listsLcsSimilar(original_cons.getTypes(),
					revised_cons.getTypes());
			CompareNodes.listsLcsSimilar(original_cons.getArguments(),
					revised_cons.getArguments());
			return;
		}

		if (original instanceof DiffContinueStatement
				&& revised instanceof DiffDoStatement) {
			DiffContinueStatement original_co = (DiffContinueStatement) original;
			DiffContinueStatement revised_co = (DiffContinueStatement) revised;

			CompareNodes.compareNodes(original_co.getIdentifier(),
					revised_co.getIdentifier());
			return;
		}

		if (original instanceof DiffDoStatement
				&& revised instanceof DiffDoStatement) {
			DiffDoStatement original_do = (DiffDoStatement) original;
			DiffDoStatement revised_do = (DiffDoStatement) revised;

			CompareNodes.compareNodes(original_do.getStatement(),
					revised_do.getStatement());
			CompareNodes.compareNodes(original_do.getExpression(),
					revised_do.getExpression());
			return;
		}

		if (original instanceof DiffEnhancedForStatement
				&& revised instanceof DiffEnhancedForStatement) {
			DiffEnhancedForStatement original_enfor = (DiffEnhancedForStatement) original;
			DiffEnhancedForStatement revised_enfor = (DiffEnhancedForStatement) revised;

			CompareNodes.compareNodes(original_enfor.getParameter(),
					revised_enfor.getParameter());
			CompareNodes.compareNodes(original_enfor.getExpression(),
					revised_enfor.getExpression());
			CompareNodes.compareNodes(original_enfor.getStatement(),
					revised_enfor.getStatement());
			return;
		}

		if (original instanceof DiffExpressionStatement
				&& revised instanceof DiffExpressionStatement) {
			DiffExpressionStatement original_ex = (DiffExpressionStatement) original;
			DiffExpressionStatement revised_ex = (DiffExpressionStatement) revised;

			CompareNodes.compareNodes(original_ex.getExpression(),
					revised_ex.getExpression());
			return;
		}

		if (original instanceof DiffForStatement
				&& revised instanceof DiffForStatement) {
			DiffForStatement original_for = (DiffForStatement) original;
			DiffForStatement revised_for = (DiffForStatement) revised;

			CompareNodes.lazyListsEqual(
					original_for.getInitilaizers(),
					revised_for.getInitilaizers());
			CompareNodes.compareNodes(original_for.getExpression(),
					revised_for.getExpression());
			CompareNodes.lazyListsEqual(original_for.getUpdaters(),
					revised_for.getUpdaters());
			CompareNodes.compareNodes(original_for.getStatement(),
					revised_for.getStatement());
			return;
		}

		if (original instanceof DiffIfStatement
				&& revised instanceof DiffIfStatement) {
			DiffIfStatement original_if = (DiffIfStatement) original;
			DiffIfStatement revised_if = (DiffIfStatement) revised;

			CompareNodes.compareNodes(original_if.getExpression(),
					revised_if.getExpression());
			CompareNodes.compareNodes(original_if.getThenStatement(),
					revised_if.getThenStatement());
			CompareNodes.compareNodes(original_if.getElseStatement(),
					revised_if.getElseStatement());
			return;
		}

		if (original instanceof DiffLabeledStatement
				&& revised instanceof DiffLabeledStatement) {
			DiffLabeledStatement original_la = (DiffLabeledStatement) original;
			DiffLabeledStatement revised_la = (DiffLabeledStatement) revised;

			CompareNodes.compareNodes(original_la.getIdentifier(),
					revised_la.getIdentifier());
			CompareNodes.compareNodes(original_la.getStatement(),
					revised_la.getStatement());
			return;
		}

		if (original instanceof DiffReturnStatement
				&& revised instanceof DiffReturnStatement) {
			DiffReturnStatement original_re = (DiffReturnStatement) original;
			DiffReturnStatement revised_re = (DiffReturnStatement) revised;

			CompareNodes.compareNodes(original_re.getExpression(),
					revised_re.getExpression());
			return;
		}

		if (original instanceof DiffSuperConstructorInvocation
				&& revised instanceof DiffSuperConstructorInvocation) {
			DiffSuperConstructorInvocation original_su = (DiffSuperConstructorInvocation) original;
			DiffSuperConstructorInvocation revised_su = (DiffSuperConstructorInvocation) revised;

			CompareNodes.compareNodes(original_su.getExpression(),
					revised_su.getExpression());
			CompareNodes.listsLcsSimilar(original_su.getTypes(),
					revised_su.getTypes());
			CompareNodes.listsLcsSimilar(original_su.getArguments(),
					revised_su.getArguments());
			return;
		}

		if (original instanceof DiffSwitchCase
				&& revised instanceof DiffSwitchCase) {
			DiffSwitchCase original_sw = (DiffSwitchCase) original;
			DiffSwitchCase revised_sw = (DiffSwitchCase) revised;

			CompareNodes.compareNodes(original_sw.getExpression(),
					revised_sw.getExpression());
			return;
		}

		if (original instanceof DiffSwitchStatement
				&& revised instanceof DiffSwitchStatement) {
			DiffSwitchStatement original_sw = (DiffSwitchStatement) original;
			DiffSwitchStatement revised_sw = (DiffSwitchStatement) revised;

			CompareNodes.compareNodes(original_sw.getExpression(),
					revised_sw.getExpression());
			CompareNodes.listsLcsSimilar(original_sw.getStatements(),
					revised_sw.getStatements());
			return;
		}

		if (original instanceof DiffSynchronizedStatement
				&& revised instanceof DiffSynchronizedStatement) {
			DiffSynchronizedStatement original_sy = (DiffSynchronizedStatement) original;
			DiffSynchronizedStatement revised_sy = (DiffSynchronizedStatement) revised;

			CompareNodes.compareNodes(original_sy.getExpression(),
					revised_sy.getExpression());
			CompareNodes.compareNodes(original_sy.getBlock(),
					revised_sy.getBlock());
			return;
		}

		if (original instanceof DiffThrowStatement
				&& revised instanceof DiffThrowStatement) {
			DiffThrowStatement original_th = (DiffThrowStatement) original;
			DiffThrowStatement revised_th = (DiffThrowStatement) revised;

			CompareNodes.compareNodes(original_th.getExpression(),
					revised_th.getExpression());
			return;
		}

		if (original instanceof DiffTryStatement
				&& revised instanceof DiffTryStatement) {
			DiffTryStatement original_try = (DiffTryStatement) original;
			DiffTryStatement revised_try = (DiffTryStatement) revised;

			CompareNodes.lazyListsEqual(
					original_try.getResources(), revised_try.getResources());
			CompareNodes.compareNodes(original_try.getTryBlock(),
					revised_try.getTryBlock());
			CompareNodes.lazyListsEqual(
					original_try.getCatchClauses(),
					revised_try.getCatchClauses());
			CompareNodes.compareNodes(original_try.getFinallyBlock(),
					revised_try.getFinallyBlock());
			return;
		}

		if (original instanceof DiffVariableDeclarationStatement
				&& revised instanceof DiffVariableDeclarationStatement) {
			DiffVariableDeclarationStatement original_var = (DiffVariableDeclarationStatement) original;
			DiffVariableDeclarationStatement revised_var = (DiffVariableDeclarationStatement) revised;

			CompareNodes.compareNodes(original_var.getModifiers(),
					revised_var.getModifiers());
			CompareNodes.compareNodes(original_var.getType(),
					revised_var.getType());
			CompareNodes.listsLcsSimilar(original_var.getVariables(),
					revised_var.getVariables());
			return;
		}

		if (original instanceof DiffWhileStatement
				&& revised instanceof DiffWhileStatement) {
			DiffWhileStatement original_while = (DiffWhileStatement) original;
			DiffWhileStatement revised_while = (DiffWhileStatement) revised;

			CompareNodes.compareNodes(original_while.getExpression(),
					revised_while.getExpression());
			CompareNodes.compareNodes(original_while.getStatement(),
					revised_while.getStatement());
			return;
		}

		CompareNodes.lazyAddedDeleted(original, revised);
	}

	public static boolean isSimilar(DiffNode original, DiffNode revised) {

		// DiffAssertStatement

		// DiffBlock

		// DiffBreakStatement

		// DiffConstructorInvocation

		// DiffContinueStatement

		// DiffDoStatement

		// DiffEnhancedForStatement

		// DiffExpressionStatement

		// DiffForStatement

		if (original instanceof DiffIfStatement
				&& revised instanceof DiffIfStatement) {
			DiffIfStatement original_if = (DiffIfStatement) original;
			DiffIfStatement revised_if = (DiffIfStatement) revised;
			return original_if.getExpression().equals(
					revised_if.getExpression());
		}

		// DiffLabeledStatement

		// DiffReturnStatement

		// DiffSuperConstructorInvocation

		// DiffSwitchCase

		// DiffSwitchStatement

		// DiffSynchronizedStatement

		// DiffThrowStatement

		// DiffTryStatement

		// DiffVariableDeclarationStatement

		if (original instanceof DiffWhileStatement
				&& revised instanceof DiffWhileStatement) {
			DiffWhileStatement original_while = (DiffWhileStatement) original;
			DiffWhileStatement revised_while = (DiffWhileStatement) revised;
			return original_while.getExpression().equals(
					revised_while.getExpression());
		}

		return false;
	}

}
