package za.ac.sun.cs.semdiff.jdtvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.WildcardType;

import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayAccess;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffArrayInitializer;
import za.ac.sun.cs.semdiff.ast.expressions.DiffAssignment;
import za.ac.sun.cs.semdiff.ast.expressions.DiffBooleanLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffCastExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffCharacterLiteral;
import za.ac.sun.cs.semdiff.ast.expressions.DiffClassInstanceCreation;
import za.ac.sun.cs.semdiff.ast.expressions.DiffConditionalExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
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

public class ExpressionVisitor extends ASTVisitor {

	// EXPRESSION
	//
	// Annotation,
	// ArrayAccess,
	// ArrayCreation,
	// ArrayInitializer,
	// Assignment,
	// BooleanLiteral,
	// CastExpression,
	// CharacterLiteral,
	// ClassInstanceCreation,
	// ConditionalExpression,
	// FieldAccess,
	// InfixExpression,
	// InstanceofExpression,
	// MethodInvocation,
	// Name,
	// NullLiteral,
	// NumberLiteral,
	// ParenthesizedExpression,
	// PostfixExpression,
	// PrefixExpression,
	// StringLiteral,
	// SuperFieldAccess,
	// SuperMethodInvocation,
	// ThisExpression,
	// TypeLiteral,
	// VariableDeclarationExpression

	private static ExpressionVisitor ev = null;
	private DiffExpression expression = null;

	private ExpressionVisitor() {
	}

	public static ExpressionVisitor getExpressionVisitor() {
		if (ev == null) {
			ev = new ExpressionVisitor();
		}
		return ev;
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public boolean visit(ArrayAccess node) {
		expression = new DiffArrayAccess(node);
		return false;
	}

	public boolean visit(ArrayCreation node) {
		expression = new DiffArrayCreation(node);
		return false;
	}

	public boolean visit(ArrayInitializer node) {
		expression = new DiffArrayInitializer(node);
		return false;
	}

	public boolean visit(Assignment node) {
		expression = new DiffAssignment(node);
		return false;
	}

	public boolean visit(BooleanLiteral node) {
		expression = new DiffBooleanLiteral(node);
		return false;
	}

	public boolean visit(CastExpression node) {
		expression = new DiffCastExpression(node);
		return false;
	}

	public boolean visit(CharacterLiteral node) {
		expression = new DiffCharacterLiteral(node);
		return false;
	}

	public boolean visit(ClassInstanceCreation node) {
		expression = new DiffClassInstanceCreation(node);
		return false;
	}

	public boolean visit(ConditionalExpression node) {
		expression = new DiffConditionalExpression(node);
		return false;
	}

	public boolean visit(FieldAccess node) {
		expression = new DiffFieldAccess(node);
		return false;
	}

	public boolean visit(InfixExpression node) {
		expression = new DiffInfixExpression(node);
		return false;
	}

	public boolean visit(InstanceofExpression node) {
		expression = new DiffInstanceofExpression(node);
		return false;
	}

	public boolean visit(MethodInvocation node) {
		expression = new DiffMethodInvocation(node);
		return false;
	}

	public boolean visit(SimpleName node) {
		expression = new DiffSimpleName(node);
		return true;
	}

	public boolean visit(QualifiedName node) {
		expression = new DiffQualifiedName(node);
		return false;
	}

	public boolean visit(NullLiteral node) {
		expression = new DiffNullLiteral(node);
		return false;
	}

	public boolean visit(NumberLiteral node) {
		expression = new DiffNumberLiteral(node);
		return false;
	}

	public boolean visit(ParenthesizedExpression node) {
		expression = new DiffParenthesizedExpression(node);
		return false;
	}

	public boolean visit(PostfixExpression node) {
		expression = new DiffPostfixExpression(node);
		return false;
	}

	public boolean visit(PrefixExpression node) {
		expression = new DiffPrefixExpression(node);
		return false;
	}

	public boolean visit(StringLiteral node) {
		expression = new DiffStringLiteral(node);
		return false;
	}

	public boolean visit(SuperFieldAccess node) {
		expression = new DiffSuperFieldAccess(node);
		return false;
	}

	public boolean visit(SuperMethodInvocation node) {
		expression = new DiffSuperMethodInvocation(node);
		return false;
	}

	public boolean visit(ThisExpression node) {
		expression = new DiffThisExpression(node);
		return false;
	}

	public boolean visit(TypeLiteral node) {
		expression = new DiffTypeLiteral(node);
		return false;
	}

	public boolean visit(VariableDeclarationExpression node) {
		expression = new DiffVariableDeclarationExpression(node);
		return false;
	}

	// ///////////////////////////
	// Types
	// ///////////////////////////

	public boolean visit(ArrayType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(ParameterizedType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(PrimitiveType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(QualifiedType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(SimpleType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(UnionType node) {
		expression = new DiffType(node);
		return false;
	}

	public boolean visit(WildcardType node) {
		expression = new DiffType(node);
		return false;
	}

}
