package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// MethodInvocation:
//     [ Expression . ]
//         [ < Type { , Type } > ]
//         Identifier ( [ Expression { , Expression } ] )
public class DiffMethodInvocation extends DiffExpression {

	private DiffExpression expression = null;
	private List<DiffType> typeArguments = null;
	private DiffSimpleName methodName = null;
	private List<DiffExpression> arguments = null;

	@SuppressWarnings("unchecked")
	public DiffMethodInvocation(MethodInvocation exp) {
		super(exp);

		if (exp.getExpression() != null) {
			exp.getExpression()
					.accept(ExpressionVisitor.getExpressionVisitor());
			this.expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

		this.typeArguments = new ArrayList<DiffType>();
		List<Type> types = exp.typeArguments();
		for (Type t : types) {
			this.typeArguments.add(new DiffType(t));
		}

		this.methodName = new DiffSimpleName(exp.getName());

		this.arguments = new ArrayList<DiffExpression>();
		List<Expression> expressions = exp.arguments();
		for (Expression e : expressions) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.arguments.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

	}

	public void setMethodName(DiffSimpleName methodName) {
		this.methodName = methodName;
	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public List<DiffType> getTypeArguments() {
		return this.typeArguments;
	}

	public DiffSimpleName getMethodName() {
		return this.methodName;
	}

	public List<DiffExpression> getArguments() {
		return this.arguments;
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
			acceptChildren(visitor, getTypeArguments());
			acceptChild(visitor, getMethodName());
			acceptChildren(visitor, getArguments());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.expression != null) {
			sb.append(this.expression.toString());
			sb.append(".");
		}

		if (this.typeArguments.size() > 0) {
			sb.append("<");
			String prefix = "";
			for (DiffType t : this.typeArguments) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(t.toString());
			}
			sb.append(">");
		}

		sb.append(this.methodName.toString());

		sb.append("(");
		String prefix = "";
		for (DiffExpression e : this.arguments) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(e.toString());
		}
		sb.append(")");

		return sb.toString();
	}

}
