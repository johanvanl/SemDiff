package za.ac.sun.cs.semdiff.visitors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
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

public class TokenVisitor extends DiffVisitor {

	private List<String> tokens = null;

	private TokenVisitor() {
		this.tokens = new ArrayList<String>();
	}

	private List<String> getTokens() {
		return this.tokens;
	}

	public static List<String> getTokenList(DiffNode node) {
		TokenVisitor tv = new TokenVisitor();
		node.accept(tv);
		return tv.getTokens();
	}

	public static int numberOfTokens(DiffNode node) {
		return getTokenList(node).size();
	}

	private static Comparator<DiffBodyDeclaration> comp = new Comparator<DiffBodyDeclaration>() {
		@Override
		public int compare(DiffBodyDeclaration o1, DiffBodyDeclaration o2) {
			int c = Integer.compare(numberOfTokens(o1), numberOfTokens(o2));
			if (c == 0) {
				c = o1.getIdentifier().toString()
						.compareToIgnoreCase(o2.getIdentifier().toString());
			}
			return c;
		}
	};

	private void addNode(DiffNode node) {
		this.tokens.add(node.getClass().getSimpleName().substring(4));
	}

	public boolean visit(DiffAnonymousClassDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffCatchClause node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffCompilationUnit node) {
		addNode(node);

		if (node.getPackage() != null) {
			node.getPackage().accept(this);
		}

		List<DiffImportDeclaration> imports = node.getImports();
		for (DiffImportDeclaration imp : imports) {
			imp.accept(this);
		}

		// Make a copy of the list
		List<DiffBodyDeclaration> types_copy = new ArrayList<DiffBodyDeclaration>(
				node.getTypes());
		Collections.sort(types_copy, comp);
		for (DiffBodyDeclaration body : types_copy) {
			body.accept(this);
		}

		return false;
	}

	public boolean visit(DiffEnumConstantDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffEnumDeclaration node) {
		addNode(node);

		node.getModifiers().accept(this);
		node.getIdentifier().accept(this);

		List<DiffType> superInterfaceTypes = node.getSuperInterfaceTypes();
		for (DiffType sit : superInterfaceTypes) {
			sit.accept(this);
		}

		// Make a copy
		List<DiffEnumConstantDeclaration> enumConstants = new ArrayList<DiffEnumConstantDeclaration>(
				node.getEnumConstants());
		Collections.sort(enumConstants, comp);
		for (DiffEnumConstantDeclaration enumCon : enumConstants) {
			enumCon.accept(this);
		}

		// Make a copy
		List<DiffBodyDeclaration> bodyDeclarations = new ArrayList<DiffBodyDeclaration>(
				node.getBodyDeclarations());
		Collections.sort(bodyDeclarations, comp);
		for (DiffBodyDeclaration body : bodyDeclarations) {
			body.accept(this);
		}

		return false;
	}

	public boolean visit(DiffFieldDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffImportDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffMethodDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffModifiers node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffNode node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffOperator node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffPackageDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSingleVariableDeclaration node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffTypeDeclaration node) {
		addNode(node);

		node.getModifiers().accept(this);
		node.getIdentifier().accept(this);

		List<DiffTypeParameter> typeParameters = node.getTypeParameters();
		for (DiffTypeParameter tp : typeParameters) {
			tp.accept(this);
		}

		if (node.getSuperClass() != null) {
			node.getSuperClass().accept(this);
		}

		List<DiffType> superInterfaces = node.getSuperInterfaces();
		for (DiffType si : superInterfaces) {
			si.accept(this);
		}

		// Make a copy
		List<DiffFieldDeclaration> variables = new ArrayList<DiffFieldDeclaration>(
				node.getInstanceVariables());
		Collections.sort(variables, comp);
		for (DiffFieldDeclaration variable : variables) {
			variable.accept(this);
		}

		// Make a copy
		List<DiffBodyDeclaration> types = new ArrayList<DiffBodyDeclaration>(
				node.getTypes());
		Collections.sort(types, comp);
		for (DiffBodyDeclaration type : types) {
			type.accept(this);
		}

		// Make a copy
		List<DiffMethodDeclaration> methods = new ArrayList<DiffMethodDeclaration>(
				node.getMethods());
		Collections.sort(methods, comp);
		for (DiffMethodDeclaration method : methods) {
			method.accept(this);
		}

		return false;
	}

	public boolean visit(DiffTypeParameter node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationFragment node) {
		addNode(node);
		return true;
	}

	// ////////////////////////////
	// STATEMENTS
	// ////////////////////////////

	public boolean visit(DiffAssertStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffBlock node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffBreakStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffConstructorInvocation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffContinueStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffDoStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffEnhancedForStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffExpressionStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffForStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffIfStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffLabeledStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffReturnStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSuperConstructorInvocation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSwitchCase node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSwitchStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSynchronizedStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffThrowStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffTryStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationStatement node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffWhileStatement node) {
		addNode(node);
		return true;
	}

	// ////////////////////////////
	// EXPRESSIONS
	// ////////////////////////////

	public boolean visit(DiffArrayAccess node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffArrayCreation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffArrayInitializer node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffAssignment node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffBooleanLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffCastExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffCharacterLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffClassInstanceCreation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffConditionalExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffFieldAccess node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffInfixExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffInstanceofExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffMethodInvocation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffNullLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffNumberLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffParenthesizedExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffPostfixExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffPrefixExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffQualifiedName node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSimpleName node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffStringLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSuperFieldAccess node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffSuperMethodInvocation node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffThisExpression node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffType node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffTypeLiteral node) {
		addNode(node);
		return true;
	}

	public boolean visit(DiffVariableDeclarationExpression node) {
		addNode(node);
		return true;
	}

}
