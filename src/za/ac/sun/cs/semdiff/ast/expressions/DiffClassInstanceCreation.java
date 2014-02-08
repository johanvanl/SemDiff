package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;

import za.ac.sun.cs.semdiff.ast.DiffAnonymousClassDeclaration;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ClassInstanceCreation:
//    [ Expression . ]
//        new [ < Type { , Type } > ]
//        Type ( [ Expression { , Expression } ] )
//        [ AnonymousClassDeclaration ]
public class DiffClassInstanceCreation extends DiffExpression {

	private DiffExpression expression = null;
	private DiffType type = null;
	private List<DiffType> typeArguments = null;
	private List<DiffExpression> arguments = null;
	private DiffAnonymousClassDeclaration anonymousClassDeclaration = null;

	@SuppressWarnings("unchecked")
	public DiffClassInstanceCreation(ClassInstanceCreation exp) {
		super(exp);

		if (exp.getExpression() != null) {
			exp.getExpression()
					.accept(ExpressionVisitor.getExpressionVisitor());
			expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

		type = new DiffType(exp.getType());

		List<Type> typeParams = exp.typeArguments();
		typeArguments = new ArrayList<DiffType>();
		for (Type tp : typeParams) {
			typeArguments.add(new DiffType(tp));
		}

		arguments = new ArrayList<DiffExpression>();
		List<Expression> expressions = exp.arguments();
		for (Expression e : expressions) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			arguments.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

		if (exp.getAnonymousClassDeclaration() != null) {
			this.anonymousClassDeclaration = new DiffAnonymousClassDeclaration(
					exp.getAnonymousClassDeclaration());
		}

	}

	public DiffExpression getExpression() {
		return this.expression;
	}

	public DiffType getType() {
		return this.type;
	}

	public List<DiffType> getTypeArguments() {
		return this.typeArguments;
	}

	public List<DiffExpression> getArguments() {
		return this.arguments;
	}

	public DiffAnonymousClassDeclaration getAnonymousClassDeclaration() {
		return this.anonymousClassDeclaration;
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
			acceptChild(visitor, getType());
			acceptChildren(visitor, getTypeArguments());
			acceptChildren(visitor, getArguments());
			acceptChild(visitor, getAnonymousClassDeclaration());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.expression != null) {
			sb.append(this.expression.toString());
			sb.append(".");
		}

		sb.append("new");

		if (this.typeArguments.size() > 0) {
			sb.append("<");
			for (DiffType t : this.typeArguments) {
				sb.append(t.toString());
				sb.append(", ");
			}
			sb.append(">");
		}

		sb.append(" ");
		sb.append(this.type.toString());

		sb.append("(");
		String prefix = "";
		for (DiffExpression exp : this.arguments) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(exp.toString());
		}
		sb.append(")");

		if (this.anonymousClassDeclaration != null) {
			sb.append("\n");
			sb.append(this.anonymousClassDeclaration.toString());
		}

		return sb.toString();
	}

}
