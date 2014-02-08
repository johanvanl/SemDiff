package za.ac.sun.cs.semdiff.matcher;

import java.util.Iterator;
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

public class DiffASTMatcher {

	public DiffASTMatcher() {

	}

	@SuppressWarnings("rawtypes")
	public boolean safeSubtreeListMatch(List l1, List l2) {
		int s1 = l1.size();
		int s2 = l2.size();
		if (s1 != s2) {
			return false;
		}
		for (Iterator it1 = l1.iterator(), it2 = l2.iterator(); it1.hasNext();) {
			DiffNode n1 = (DiffNode) it1.next();
			DiffNode n2 = (DiffNode) it2.next();
			if (!n1.subtreeMatch(this, n2)) {
				return false;
			}
		}
		return true;
	}

	public final boolean safeSubtreeMatch(Object node1, Object node2) {
		if (node1 == null && node2 == null) {
			return true;
		}
		if (node1 == null || node2 == null) {
			return false;
		}
		// N.B. call subtreeMatch even node1==node2!=null
		return ((DiffNode) node1).subtreeMatch(this, node2);
	}

	public static boolean safeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.equals(o2);
	}

	/*
	 * ****************************************************
	 * --------------------------- BODY
	 * ****************************************************
	 */

	public boolean match(DiffAnonymousClassDeclaration node, Object other) {

		if (!(other instanceof DiffAnonymousClassDeclaration)) {
			return false;
		}

		DiffAnonymousClassDeclaration o = (DiffAnonymousClassDeclaration) other;
		return safeSubtreeListMatch(node.getBodyDeclarations(),
				o.getBodyDeclarations());
	}

	public boolean match(DiffCompilationUnit node, Object other) {

		if (!(other instanceof DiffCompilationUnit)) {
			return false;
		}

		DiffCompilationUnit o = (DiffCompilationUnit) other;
		return safeSubtreeMatch(node.getPackage(), o.getPackage())
				&& safeSubtreeListMatch(node.getImports(), o.getImports())
				&& safeSubtreeListMatch(node.getTypes(), o.getTypes());
	}

	public boolean match(DiffCatchClause node, Object other) {

		if (!(other instanceof DiffCatchClause)) {
			return false;
		}

		DiffCatchClause o = (DiffCatchClause) other;
		return safeSubtreeMatch(node.getParameter(), o.getParameter())
				&& safeSubtreeMatch(node.getBlock(), o.getBlock());
	}

	public boolean match(DiffEnumConstantDeclaration node, Object other) {

		if (!(other instanceof DiffEnumConstantDeclaration)) {
			return false;
		}

		DiffEnumConstantDeclaration o = (DiffEnumConstantDeclaration) other;
		return safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeListMatch(node.getExpressions(),
						o.getExpressions())
				&& safeSubtreeMatch(node.getAnonymousClassDeclaration(),
						o.getAnonymousClassDeclaration());
	}

	public boolean match(DiffEnumDeclaration node, Object other) {

		if (!(other instanceof DiffEnumDeclaration)) {
			return false;
		}

		DiffEnumDeclaration o = (DiffEnumDeclaration) other;
		return safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeListMatch(node.getSuperInterfaceTypes(),
						o.getSuperInterfaceTypes())
				&& safeSubtreeListMatch(node.getEnumConstants(),
						o.getEnumConstants())
				&& safeSubtreeListMatch(node.getBodyDeclarations(),
						o.getBodyDeclarations());
	}

	public boolean match(DiffFieldDeclaration node, Object other) {

		if (!(other instanceof DiffFieldDeclaration)) {
			return false;
		}

		DiffFieldDeclaration o = (DiffFieldDeclaration) other;
		return safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getVariables(), o.getVariables());
	}

	public boolean match(DiffImportDeclaration node, Object other) {

		if (!(other instanceof DiffImportDeclaration)) {
			return false;
		}

		DiffImportDeclaration o = (DiffImportDeclaration) other;
		return node.isStatic() == o.isStatic()
				&& node.onDemand() == o.onDemand()
				&& safeSubtreeMatch(node.getName(), o.getName());
	}

	public boolean match(DiffMethodDeclaration node, Object other) {

		if (!(other instanceof DiffMethodDeclaration)) {
			return false;
		}

		DiffMethodDeclaration o = (DiffMethodDeclaration) other;
		return node.isConstructor() == o.isConstructor()
				&& safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeListMatch(node.getTypeParameters(),
						o.getTypeParameters())
				&& safeSubtreeMatch(node.getReturnType(), o.getReturnType())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeListMatch(node.getParamaters(), o.getParamaters())
				&& safeSubtreeListMatch(node.getThrownExceptions(),
						o.getThrownExceptions())
				&& safeSubtreeMatch(node.getBlock(), o.getBlock());
	}

	public boolean match(DiffModifiers node, Object other) {

		if (!(other instanceof DiffModifiers)) {
			return false;
		}

		DiffModifiers o = (DiffModifiers) other;
		return node.getModifierFlags() == o.getModifierFlags();
	}

	public boolean match(DiffOperator node, Object other) {

		if (!(other instanceof DiffOperator)) {
			return false;
		}

		DiffOperator o = (DiffOperator) other;
		return safeEquals(node.getOperator(), o.getOperator());
	}

	public boolean match(DiffPackageDeclaration node, Object other) {

		if (!(other instanceof DiffPackageDeclaration)) {
			return false;
		}

		DiffPackageDeclaration o = (DiffPackageDeclaration) other;
		return safeSubtreeMatch(node.getName(), o.getName());
	}

	public boolean match(DiffSingleVariableDeclaration node, Object other) {

		if (!(other instanceof DiffSingleVariableDeclaration)) {
			return false;
		}

		DiffSingleVariableDeclaration o = (DiffSingleVariableDeclaration) other;
		return safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffTypeDeclaration node, Object other) {

		if (!(other instanceof DiffTypeDeclaration)) {
			return false;
		}

		DiffTypeDeclaration o = (DiffTypeDeclaration) other;
		return node.isInterface() == o.isInterface()
				&& safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeListMatch(node.getTypeParameters(),
						node.getTypeParameters())
				&& safeSubtreeMatch(node.getSuperClass(), o.getSuperClass())
				&& safeSubtreeListMatch(node.getSuperInterfaces(),
						o.getSuperInterfaces())
				&& safeSubtreeListMatch(node.getInstanceVariables(),
						o.getInstanceVariables())
				&& safeSubtreeListMatch(node.getTypes(), o.getTypes())
				&& safeSubtreeListMatch(node.getMethods(), o.getMethods());
	}

	public boolean match(DiffTypeParameter node, Object other) {

		if (!(other instanceof DiffTypeParameter)) {
			return false;
		}

		DiffTypeParameter o = (DiffTypeParameter) other;
		return safeSubtreeMatch(node.getVariable(), o.getVariable())
				&& safeSubtreeListMatch(node.getTypes(), o.getTypes());
	}

	public boolean match(DiffVariableDeclarationFragment node, Object other) {

		if (!(other instanceof DiffVariableDeclarationFragment)) {
			return false;
		}

		DiffVariableDeclarationFragment o = (DiffVariableDeclarationFragment) other;
		return safeSubtreeMatch(node.getName(), o.getName())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	// public boolean match(DiffInitializer node, Object other) {
	// return false;
	// }

	// public boolean match(DiffAnnotationTypeDeclaration node, Object other) {
	// return false;
	// }

	// public boolean match(DiffAnnotationTypeMemberDeclaration node, Object
	// other) {
	// return false;
	// }

	/*
	 * ****************************************************
	 * --------------------------- STATEMENTS
	 * ****************************************************
	 */

	public boolean match(DiffAssertStatement node, Object other) {

		if (!(other instanceof DiffAssertStatement)) {
			return false;
		}

		DiffAssertStatement o = (DiffAssertStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getMessage(), o.getMessage());
	}

	public boolean match(DiffBlock node, Object other) {

		if (!(other instanceof DiffBlock)) {
			return false;
		}

		DiffBlock o = (DiffBlock) other;
		return safeSubtreeListMatch(node.getStatements(), o.getStatements());
	}

	public boolean match(DiffBreakStatement node, Object other) {

		if (!(other instanceof DiffBreakStatement)) {
			return false;
		}

		DiffBreakStatement o = (DiffBreakStatement) other;
		return safeSubtreeMatch(node.getIdentifier(), o.getIdentifier());
	}

	public boolean match(DiffConstructorInvocation node, Object other) {

		if (!(other instanceof DiffConstructorInvocation)) {
			return false;
		}

		DiffConstructorInvocation o = (DiffConstructorInvocation) other;
		return safeSubtreeListMatch(node.getTypes(), o.getTypes())
				&& safeSubtreeListMatch(node.getArguments(), o.getArguments());
	}

	public boolean match(DiffContinueStatement node, Object other) {

		if (!(other instanceof DiffContinueStatement)) {
			return false;
		}

		DiffContinueStatement o = (DiffContinueStatement) other;
		return safeSubtreeMatch(node.getIdentifier(), o.getIdentifier());
	}

	public boolean match(DiffDoStatement node, Object other) {

		if (!(other instanceof DiffDoStatement)) {
			return false;
		}

		DiffDoStatement o = (DiffDoStatement) other;
		return safeSubtreeMatch(node.getStatement(), o.getStatement())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffExpressionStatement node, Object other) {

		if (!(other instanceof DiffExpressionStatement)) {
			return false;
		}

		DiffExpressionStatement o = (DiffExpressionStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffForStatement node, Object other) {

		if (!(other instanceof DiffForStatement)) {
			return false;
		}

		DiffForStatement o = (DiffForStatement) other;
		return safeSubtreeListMatch(node.getInitilaizers(), o.getInitilaizers())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeListMatch(node.getUpdaters(), o.getUpdaters())
				&& safeSubtreeMatch(node.getStatement(), o.getStatement());
	}

	public boolean match(DiffIfStatement node, Object other) {

		if (!(other instanceof DiffIfStatement)) {
			return false;
		}

		DiffIfStatement o = (DiffIfStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getThenStatement(),
						o.getThenStatement())
				&& safeSubtreeMatch(node.getElseStatement(),
						o.getElseStatement());
	}

	public boolean match(DiffLabeledStatement node, Object other) {

		if (!(other instanceof DiffLabeledStatement)) {
			return false;
		}

		DiffLabeledStatement o = (DiffLabeledStatement) other;
		return safeSubtreeMatch(node.getIdentifier(), o.getStatement())
				&& safeSubtreeMatch(node.getStatement(), o.getStatement());
	}

	public boolean match(DiffReturnStatement node, Object other) {

		if (!(other instanceof DiffReturnStatement)) {
			return false;
		}

		DiffReturnStatement o = (DiffReturnStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffSuperConstructorInvocation node, Object other) {

		if (!(other instanceof DiffSuperConstructorInvocation)) {
			return false;
		}

		DiffSuperConstructorInvocation o = (DiffSuperConstructorInvocation) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeListMatch(node.getTypes(), o.getTypes())
				&& safeSubtreeListMatch(node.getArguments(), o.getArguments());
	}

	public boolean match(DiffSwitchCase node, Object other) {

		if (!(other instanceof DiffSwitchCase)) {
			return false;
		}

		DiffSwitchCase o = (DiffSwitchCase) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& node.isDefault() == o.isDefault();
	}

	public boolean match(DiffSwitchStatement node, Object other) {

		if (!(other instanceof DiffSwitchStatement)) {
			return false;
		}

		DiffSwitchStatement o = (DiffSwitchStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeListMatch(node.getStatements(), o.getStatements());
	}

	public boolean match(DiffSynchronizedStatement node, Object other) {

		if (!(other instanceof DiffSynchronizedStatement)) {
			return false;
		}

		DiffSynchronizedStatement o = (DiffSynchronizedStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getBlock(), o.getBlock());
	}

	public boolean match(DiffThrowStatement node, Object other) {

		if (!(other instanceof DiffThrowStatement)) {
			return false;
		}

		DiffThrowStatement o = (DiffThrowStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffTryStatement node, Object other) {

		if (!(other instanceof DiffTryStatement)) {
			return false;
		}

		DiffTryStatement o = (DiffTryStatement) other;
		return safeSubtreeListMatch(node.getResources(), o.getResources())
				&& safeSubtreeMatch(node.getTryBlock(), o.getTryBlock())
				&& safeSubtreeListMatch(node.getCatchClauses(),
						o.getCatchClauses())
				&& safeSubtreeMatch(node.getFinallyBlock(), o.getFinallyBlock());
	}

	// public boolean match(TypeDeclarationStatement node) {
	// return false; // XXX
	// }

	public boolean match(DiffVariableDeclarationStatement node, Object other) {

		if (!(other instanceof DiffVariableDeclarationStatement)) {
			return false;
		}

		DiffVariableDeclarationStatement o = (DiffVariableDeclarationStatement) other;
		return safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getVariables(), o.getVariables());
	}

	public boolean match(DiffWhileStatement node, Object other) {

		if (!(other instanceof DiffWhileStatement)) {
			return false;
		}

		DiffWhileStatement o = (DiffWhileStatement) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getStatement(), o.getStatement());
	}

	public boolean match(DiffEnhancedForStatement node, Object other) {

		if (!(other instanceof DiffEnhancedForStatement)) {
			return false;
		}

		DiffEnhancedForStatement o = (DiffEnhancedForStatement) other;
		return safeSubtreeMatch(node.getParameter(), o.getParameter())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getStatement(), o.getStatement());
	}

	/*
	 * ****************************************************
	 * --------------------------- EXPRESSIONS
	 * ****************************************************
	 */

	public boolean match(DiffArrayAccess node, Object other) {

		if (!(other instanceof DiffArrayAccess)) {
			return false;
		}

		DiffArrayAccess o = (DiffArrayAccess) other;
		return safeSubtreeMatch(node.getArray(), o.getArray())
				&& safeSubtreeMatch(node.getIndex(), o.getIndex());
	}

	public boolean match(DiffArrayCreation node, Object other) {

		if (!(other instanceof DiffArrayCreation)) {
			return false;
		}

		DiffArrayCreation o = (DiffArrayCreation) other;
		return safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getDimensions(), o.getDimensions())
				&& safeSubtreeMatch(node.getArrayInitializer(),
						o.getArrayInitializer());
	}

	public boolean match(DiffArrayInitializer node, Object other) {

		if (!(other instanceof DiffArrayInitializer)) {
			return false;
		}

		DiffArrayInitializer o = (DiffArrayInitializer) other;
		return safeSubtreeListMatch(node.getExpressions(), o.getExpressions());
	}

	public boolean match(DiffAssignment node, Object other) {

		if (!(other instanceof DiffAssignment)) {
			return false;
		}

		DiffAssignment o = (DiffAssignment) other;
		return safeSubtreeMatch(node.getLeft(), o.getLeft())
				&& safeEquals(node.getOperator(), o.getOperator())
				&& safeSubtreeMatch(node.getRight(), o.getRight());
	}

	public boolean match(DiffBooleanLiteral node, Object other) {

		if (!(other instanceof DiffBooleanLiteral)) {
			return false;
		}

		DiffBooleanLiteral o = (DiffBooleanLiteral) other;
		return node.getbooleanValue() == o.getbooleanValue();
	}

	public boolean match(DiffCastExpression node, Object other) {

		if (!(other instanceof DiffCastExpression)) {
			return false;
		}

		DiffCastExpression o = (DiffCastExpression) other;
		return safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffCharacterLiteral node, Object other) {

		if (!(other instanceof DiffCharacterLiteral)) {
			return false;
		}

		DiffCharacterLiteral o = (DiffCharacterLiteral) other;
		return node.getCharValue() == o.getCharValue();
	}

	public boolean match(DiffClassInstanceCreation node, Object other) {

		if (!(other instanceof DiffClassInstanceCreation)) {
			return false;
		}

		DiffClassInstanceCreation o = (DiffClassInstanceCreation) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getTypeArguments(),
						o.getTypeArguments())
				&& safeSubtreeListMatch(node.getArguments(),
						node.getArguments())
				&& safeSubtreeMatch(node.getAnonymousClassDeclaration(),
						o.getAnonymousClassDeclaration());
	}

	public boolean match(DiffConditionalExpression node, Object other) {

		if (!(other instanceof DiffConditionalExpression)) {
			return false;
		}

		DiffConditionalExpression o = (DiffConditionalExpression) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getThenExpression(),
						o.getThenExpression())
				&& safeSubtreeMatch(node.getElseExpression(),
						o.getElseExpression());
	}

	public boolean match(DiffFieldAccess node, Object other) {

		if (!(other instanceof DiffFieldAccess)) {
			return false;
		}

		DiffFieldAccess o = (DiffFieldAccess) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier());
	}

	public boolean match(DiffInfixExpression node, Object other) {

		if (!(other instanceof DiffInfixExpression)) {
			return false;
		}

		DiffInfixExpression o = (DiffInfixExpression) other;
		return safeSubtreeListMatch(node.getExpressions(), o.getExpressions())
				&& safeEquals(node.getOperator(), o.getOperator());
	}

	public boolean match(DiffInstanceofExpression node, Object other) {

		if (!(other instanceof DiffInstanceofExpression)) {
			return false;
		}

		DiffInstanceofExpression o = (DiffInstanceofExpression) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeMatch(node.getType(), o.getType());
	}

	public boolean match(DiffMethodInvocation node, Object other) {

		if (!(other instanceof DiffMethodInvocation)) {
			return false;
		}

		DiffMethodInvocation o = (DiffMethodInvocation) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeSubtreeListMatch(node.getTypeArguments(),
						o.getTypeArguments())
				&& safeSubtreeMatch(node.getMethodName(), o.getMethodName())
				&& safeSubtreeListMatch(node.getArguments(), o.getArguments());
	}

	public boolean match(DiffNullLiteral node, Object other) {

		if (!(other instanceof DiffNullLiteral)) {
			return false;
		}

		return true;
	}

	public boolean match(DiffNumberLiteral node, Object other) {

		if (!(other instanceof DiffNumberLiteral)) {
			return false;
		}

		DiffNumberLiteral o = (DiffNumberLiteral) other;
		return safeEquals(node.getLiteral(), o.getLiteral());
	}

	public boolean match(DiffParenthesizedExpression node, Object other) {

		if (!(other instanceof DiffParenthesizedExpression)) {
			return false;
		}

		DiffParenthesizedExpression o = (DiffParenthesizedExpression) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffPostfixExpression node, Object other) {

		if (!(other instanceof DiffPostfixExpression)) {
			return false;
		}

		DiffPostfixExpression o = (DiffPostfixExpression) other;
		return safeSubtreeMatch(node.getExpression(), o.getExpression())
				&& safeEquals(node.getOperator(), o.getOperator());
	}

	public boolean match(DiffPrefixExpression node, Object other) {

		if (!(other instanceof DiffPrefixExpression)) {
			return false;
		}

		DiffPrefixExpression o = (DiffPrefixExpression) other;
		return safeEquals(node.getOperator(), o.getOperator())
				&& safeSubtreeMatch(node.getExpression(), o.getExpression());
	}

	public boolean match(DiffQualifiedName node, Object other) {

		if (!(other instanceof DiffQualifiedName)) {
			return false;
		}

		DiffQualifiedName o = (DiffQualifiedName) other;
		return safeEquals(node.getQualifier(), o.getQualifier())
				&& safeEquals(node.getName(), o.getName());
	}

	public boolean match(DiffSimpleName node, Object other) {

		if (!(other instanceof DiffSimpleName)) {
			return false;
		}

		DiffSimpleName o = (DiffSimpleName) other;
		return safeEquals(node.getName(), o.getName())
				&& node.isDeclaration() == o.isDeclaration();
	}

	public boolean match(DiffStringLiteral node, Object other) {

		if (!(other instanceof DiffStringLiteral)) {
			return false;
		}

		DiffStringLiteral o = (DiffStringLiteral) other;
		return safeEquals(node.getLiteral(), o.getLiteral());
	}

	public boolean match(DiffSuperFieldAccess node, Object other) {

		if (!(other instanceof DiffSuperFieldAccess)) {
			return false;
		}

		DiffSuperFieldAccess o = (DiffSuperFieldAccess) other;
		return safeSubtreeMatch(node.getClassName(), o.getClassName())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier());
	}

	public boolean match(DiffSuperMethodInvocation node, Object other) {

		if (!(other instanceof DiffSuperMethodInvocation)) {
			return false;
		}

		DiffSuperMethodInvocation o = (DiffSuperMethodInvocation) other;
		return safeSubtreeMatch(node.getClassName(), o.getClassName())
				&& safeSubtreeListMatch(node.getTypes(), o.getTypes())
				&& safeSubtreeMatch(node.getIdentifier(), o.getIdentifier())
				&& safeSubtreeListMatch(node.getArguments(), o.getArguments());
	}

	public boolean match(DiffThisExpression node, Object other) {

		if (!(other instanceof DiffThisExpression)) {
			return false;
		}

		DiffThisExpression o = (DiffThisExpression) other;
		return safeSubtreeMatch(node.getClassName(), o.getClassName());
	}

	public boolean match(DiffType node, Object other) {

		if (!(other instanceof DiffType)) {
			return false;
		}

		DiffType o = (DiffType) other;
		return safeEquals(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getArguments(), o.getArguments());
	}

	public boolean match(DiffTypeLiteral node, Object other) {

		if (!(other instanceof DiffTypeLiteral)) {
			return false;
		}

		DiffTypeLiteral o = (DiffTypeLiteral) other;
		return safeSubtreeMatch(node.getType(), o.getType());
	}

	public boolean match(DiffVariableDeclarationExpression node, Object other) {

		if (!(other instanceof DiffVariableDeclarationExpression)) {
			return false;
		}

		DiffVariableDeclarationExpression o = (DiffVariableDeclarationExpression) other;
		return safeSubtreeMatch(node.getModifiers(), o.getModifiers())
				&& safeSubtreeMatch(node.getType(), o.getType())
				&& safeSubtreeListMatch(node.getFragments(), o.getFragments());
	}

}
