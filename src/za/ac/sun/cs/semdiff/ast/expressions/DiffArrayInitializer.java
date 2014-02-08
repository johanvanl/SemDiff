package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ArrayInitializer:
//     { [ Expression { , Expression} [ , ]] }
public class DiffArrayInitializer extends DiffExpression {

	List<DiffExpression> expressions = null;

	@SuppressWarnings("unchecked")
	public DiffArrayInitializer(ArrayInitializer exp) {
		super(exp);

		this.expressions = new ArrayList<DiffExpression>();
		List<Expression> exps = exp.expressions();
		for (Expression e : exps) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.expressions.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

	}

	public List<DiffExpression> getExpressions() {
		return this.expressions;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChildren(visitor, getExpressions());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		String prefix = "";
		for (DiffExpression e : this.expressions) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(e.toString());
		}
		sb.append("}");

		return sb.toString();
	}

}
