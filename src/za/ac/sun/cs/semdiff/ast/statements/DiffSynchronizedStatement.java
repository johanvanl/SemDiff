package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.SynchronizedStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SynchronizedStatement:
//    synchronized ( Expression ) Block
public class DiffSynchronizedStatement extends DiffStatement {

	private DiffExpression expression = null;
	private DiffBlock block = null;

	public DiffSynchronizedStatement(SynchronizedStatement stmt) {
		super(stmt);

		stmt.getExpression().accept(ExpressionVisitor.getExpressionVisitor());
		this.expression = ExpressionVisitor.getExpressionVisitor()
				.getExpression();

		this.block = new DiffBlock(stmt.getBody());

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffBlock getBlock() {
		return this.block;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getExpression());
			acceptChild(visitor, getBlock());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("synchronized (");
		sb.append(this.expression.toString());
		sb.append(")");
		sb.append(this.block.toString());

		return sb.toString();
	}

}
