package za.ac.sun.cs.semdiff.visitors;

import za.ac.sun.cs.semdiff.ast.DiffAnonymousClassDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffCatchClause;
import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.ast.DiffImportDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.DiffOperator;
import za.ac.sun.cs.semdiff.ast.DiffPackageDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffSingleVariableDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffTypeParameter;
import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumConstantDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffFieldDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffTypeDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayInitializer;
import za.ac.sun.cs.semdiff.ast.expressions.DiffAssignment;
import za.ac.sun.cs.semdiff.ast.expressions.DiffBooleanLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffCastExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffCharacterLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffClassInstanceCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffConditionalExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffFieldAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffInfixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffInstanceofExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffMethodInvocation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffNullLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffNumberLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffParenthesizedExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffPostfixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffPrefixExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffQualifiedName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffStringLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSuperFieldAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSuperMethodInvocation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffThisExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.ast.expressions.DiffTypeLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffVariableDeclarationExpression;
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

public abstract class DiffVisitor {

	public DiffVisitor() {
	}

	public boolean visit(DiffAnonymousClassDeclaration node) {
		return true;
	}

	public boolean visit(DiffCatchClause node) {
		return true;
	}

	public boolean visit(DiffCompilationUnit node) {
		return true;
	}

	public boolean visit(DiffEnumConstantDeclaration node) {
		return true;
	}

	public boolean visit(DiffEnumDeclaration node) {
		return true;
	}

	public boolean visit(DiffFieldDeclaration node) {
		return true;
	}

	public boolean visit(DiffImportDeclaration node) {
		return true;
	}

	public boolean visit(DiffMethodDeclaration node) {
		return true;
	}

	public boolean visit(DiffModifiers node) {
		return true;
	}

	public boolean visit(DiffNode node) {
		return true;
	}

	public boolean visit(DiffOperator node) {
		return true;
	}

	public boolean visit(DiffPackageDeclaration node) {
		return true;
	}

	public boolean visit(DiffSingleVariableDeclaration node) {
		return true;
	}

	public boolean visit(DiffTypeDeclaration node) {
		return true;
	}

	public boolean visit(DiffTypeParameter node) {
		return true;
	}

	public boolean visit(DiffVariableDeclarationFragment node) {
		return true;
	}

	// ////////////////////////////
	// STATEMENTS
	// ////////////////////////////

	public boolean visit(DiffAssertStatement node) {
		return true;
	}

	public boolean visit(DiffBlock node) {
		return true;
	}

	public boolean visit(DiffBreakStatement node) {
		return true;
	}

	public boolean visit(DiffConstructorInvocation node) {
		return true;
	}

	public boolean visit(DiffContinueStatement node) {
		return true;
	}

	public boolean visit(DiffDoStatement node) {
		return true;
	}

	public boolean visit(DiffEnhancedForStatement node) {
		return true;
	}

	public boolean visit(DiffExpressionStatement node) {
		return true;
	}

	public boolean visit(DiffForStatement node) {
		return true;
	}

	public boolean visit(DiffIfStatement node) {
		return true;
	}

	public boolean visit(DiffLabeledStatement node) {
		return true;
	}

	public boolean visit(DiffReturnStatement node) {
		return true;
	}

	public boolean visit(DiffSuperConstructorInvocation node) {
		return true;
	}

	public boolean visit(DiffSwitchCase node) {
		return true;
	}

	public boolean visit(DiffSwitchStatement node) {
		return true;
	}

	public boolean visit(DiffSynchronizedStatement node) {
		return true;
	}

	public boolean visit(DiffThrowStatement node) {
		return true;
	}

	public boolean visit(DiffTryStatement node) {
		return true;
	}

	public boolean visit(DiffVariableDeclarationStatement node) {
		return true;
	}

	public boolean visit(DiffWhileStatement node) {
		return true;
	}

	// ////////////////////////////
	// EXPRESSIONS
	// ////////////////////////////

	public boolean visit(DiffArrayAccess node) {
		return true;
	}

	public boolean visit(DiffArrayCreation node) {
		return true;
	}

	public boolean visit(DiffArrayInitializer node) {
		return true;
	}

	public boolean visit(DiffAssignment node) {
		return true;
	}

	public boolean visit(DiffBooleanLiteral node) {
		return true;
	}

	public boolean visit(DiffCastExpression node) {
		return true;
	}

	public boolean visit(DiffCharacterLiteral node) {
		return true;
	}

	public boolean visit(DiffClassInstanceCreation node) {
		return true;
	}

	public boolean visit(DiffConditionalExpression node) {
		return true;
	}

	public boolean visit(DiffFieldAccess node) {
		return true;
	}

	public boolean visit(DiffInfixExpression node) {
		return true;
	}

	public boolean visit(DiffInstanceofExpression node) {
		return true;
	}

	public boolean visit(DiffMethodInvocation node) {
		return true;
	}

	public boolean visit(DiffName node) {
		return true;
	}

	public boolean visit(DiffNullLiteral node) {
		return true;
	}

	public boolean visit(DiffNumberLiteral node) {
		return true;
	}

	public boolean visit(DiffParenthesizedExpression node) {
		return true;
	}

	public boolean visit(DiffPostfixExpression node) {
		return true;
	}

	public boolean visit(DiffPrefixExpression node) {
		return true;
	}

	public boolean visit(DiffQualifiedName node) {
		return true;
	}

	public boolean visit(DiffSimpleName node) {
		return true;
	}

	public boolean visit(DiffStringLiteral node) {
		return true;
	}

	public boolean visit(DiffSuperFieldAccess node) {
		return true;
	}

	public boolean visit(DiffSuperMethodInvocation node) {
		return true;
	}

	public boolean visit(DiffThisExpression node) {
		return true;
	}

	public boolean visit(DiffType node) {
		return true;
	}

	public boolean visit(DiffTypeLiteral node) {
		return true;
	}

	public boolean visit(DiffVariableDeclarationExpression node) {
		return true;
	}

}
