package za.ac.sun.cs.semdiff.ast.statements;

import org.eclipse.jdt.core.dom.SwitchCase;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SwitchCase:
//     case Expression  :
//     default :
public class DiffSwitchCase extends DiffStatement {

	private DiffExpression expression = null;
	private boolean isDefault = false;

	public DiffSwitchCase(SwitchCase stmt) {
		super(stmt);

		if (stmt.getExpression() != null) {
			stmt.getExpression().accept(
					ExpressionVisitor.getExpressionVisitor());
			this.expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

		this.isDefault = stmt.isDefault();

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public boolean isDefault() {
		return this.isDefault;
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
		}
	}

	@Override
	public String toString() {
		if (this.isDefault) {
			return "default :";
		}

		if (this.expression != null) {
			return "case " + this.expression.toString() + " :";
		}

		return "case :";
	}
}
