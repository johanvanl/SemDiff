package za.ac.sun.cs.semdiff.visitors;

import java.util.ArrayList;
import java.util.List;

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

public class NodeInOrderVisitor extends DiffVisitor {

	private List<DiffNode> nodes = null;

	private NodeInOrderVisitor() {
		this.nodes = new ArrayList<DiffNode>();
	}

	private List<DiffNode> getNodes() {
		return this.nodes;
	}

	public static List<DiffNode> getNodes(DiffNode node) {
		NodeInOrderVisitor visitor = new NodeInOrderVisitor();
		node.accept(visitor);
		return visitor.getNodes();
	}

	public static int numberOfNodes(DiffNode node) {
		return getNodes(node).size();
	}

	public boolean visit(DiffAnonymousClassDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffCatchClause node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffCompilationUnit node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffEnumConstantDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffEnumDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffFieldDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffImportDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffMethodDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffModifiers node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffNode node) {
		this.nodes.add(node);
		return true;
	}

	public boolean visit(DiffOperator node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffPackageDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSingleVariableDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffTypeDeclaration node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffTypeParameter node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationFragment node) {
		nodes.add(node);
		return true;
	}

	// ////////////////////////////
	// STATEMENTS
	// ////////////////////////////

	public boolean visit(DiffAssertStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffBlock node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffBreakStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffConstructorInvocation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffContinueStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffDoStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffEnhancedForStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffExpressionStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffForStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffIfStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffLabeledStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffReturnStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSuperConstructorInvocation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSwitchCase node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSwitchStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSynchronizedStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffThrowStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffTryStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationStatement node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffWhileStatement node) {
		nodes.add(node);
		return true;
	}

	// ////////////////////////////
	// EXPRESSIONS
	// ////////////////////////////

	public boolean visit(DiffArrayAccess node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffArrayCreation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffArrayInitializer node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffAssignment node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffBooleanLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffCastExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffCharacterLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffClassInstanceCreation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffConditionalExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffFieldAccess node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffInfixExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffInstanceofExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffMethodInvocation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffName node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffNullLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffNumberLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffParenthesizedExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffPostfixExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffPrefixExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffQualifiedName node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSimpleName node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffStringLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSuperFieldAccess node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffSuperMethodInvocation node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffThisExpression node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffType node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffTypeLiteral node) {
		nodes.add(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationExpression node) {
		nodes.add(node);
		return true;
	}

}
