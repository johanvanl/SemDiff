package za.ac.sun.cs.semdiff.jdtvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

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
import za.ac.sun.cs.semdiff.ast.statements.DiffStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffSuperConstructorInvocation;
import za.ac.sun.cs.semdiff.ast.statements.DiffSwitchCase;
import za.ac.sun.cs.semdiff.ast.statements.DiffSwitchStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffSynchronizedStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffThrowStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffTryStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffVariableDeclarationStatement;
import za.ac.sun.cs.semdiff.ast.statements.DiffWhileStatement;

public class StatementVisitor extends ASTVisitor {

	// STATEMENT
	//
	// AssertStatement,
	// Block,
	// BreakStatement,
	// ConstructorInvocation,
	// ContinueStatement,
	// DoStatement,
	// EmptyStatement,
	// ExpressionStatement,
	// ForStatement,
	// IfStatement,
	// LabeledStatement,
	// ReturnStatement,
	// SuperConstructorInvocation,
	// SwitchCase,
	// SwitchStatement,
	// SynchronizedStatement,
	// ThrowStatement,
	// TryStatement,
	// TypeDeclarationStatement,
	// VariableDeclarationStatement,
	// WhileStatement,
	// EnhancedForStatement

	private static StatementVisitor sv = null;
	private DiffStatement statement = null;

	private StatementVisitor() {
	}

	public static StatementVisitor getStatementVisitor() {
		if (sv == null) {
			sv = new StatementVisitor();
		}
		return sv;
	}

	public DiffStatement getStatement() {
		return this.statement;
	}

	public boolean visit(AssertStatement node) {
		statement = new DiffAssertStatement(node);
		return false;
	}

	public boolean visit(Block node) {
		statement = new DiffBlock(node);
		return false;
	}

	public boolean visit(BreakStatement node) {
		statement = new DiffBreakStatement(node);
		return false;
	}

	public boolean visit(ConstructorInvocation node) {
		statement = new DiffConstructorInvocation(node);
		return false;
	}

	public boolean visit(ContinueStatement node) {
		statement = new DiffContinueStatement(node);
		return false;
	}

	public boolean visit(DoStatement node) {
		statement = new DiffDoStatement(node);
		return false;
	}

	public boolean visit(ExpressionStatement node) {
		statement = new DiffExpressionStatement(node);
		return false;
	}

	public boolean visit(ForStatement node) {
		statement = new DiffForStatement(node);
		return false;
	}

	public boolean visit(IfStatement node) {
		statement = new DiffIfStatement(node);
		return false;
	}

	public boolean visit(LabeledStatement node) {
		statement = new DiffLabeledStatement(node);
		return false;
	}

	public boolean visit(ReturnStatement node) {
		statement = new DiffReturnStatement(node);
		return false;
	}

	public boolean visit(SuperConstructorInvocation node) {
		statement = new DiffSuperConstructorInvocation(node);
		return false;
	}

	public boolean visit(SwitchCase node) {
		statement = new DiffSwitchCase(node);
		return false;
	}

	public boolean visit(SwitchStatement node) {
		statement = new DiffSwitchStatement(node);
		return false;
	}

	public boolean visit(SynchronizedStatement node) {
		statement = new DiffSynchronizedStatement(node);
		return false;
	}

	public boolean visit(ThrowStatement node) {
		statement = new DiffThrowStatement(node);
		return false;
	}

	public boolean visit(TryStatement node) {
		statement = new DiffTryStatement(node);
		return false;
	}

	public boolean visit(TypeDeclarationStatement node) {
		return false; // XXX
	}

	public boolean visit(VariableDeclarationStatement node) {
		statement = new DiffVariableDeclarationStatement(node);
		return false;
	}

	public boolean visit(WhileStatement node) {
		statement = new DiffWhileStatement(node);
		return false;
	}

	public boolean visit(EnhancedForStatement node) {
		statement = new DiffEnhancedForStatement(node);
		return false;
	}

}
