package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SuperMethodInvocation:
//     [ ClassName . ] super .
//         [ < Type { , Type } > ]
//         Identifier ( [ Expression { , Expression } ] )
public class DiffSuperMethodInvocation extends DiffExpression {

	private DiffName className = null;
	private List<DiffType> types = null;
	private DiffSimpleName identifier = null;
	private List<DiffExpression> arguments = null;

	@SuppressWarnings("unchecked")
	public DiffSuperMethodInvocation(SuperMethodInvocation exp) {
		super(exp);

		if (exp.getQualifier() != null) {
			exp.getQualifier().accept(NameVisitor.getNameVisitor());
			this.className = NameVisitor.getNameVisitor().getName();
		}

		List<Type> typeArguments = exp.typeArguments();
		this.types = new ArrayList<DiffType>();
		for (Type t : typeArguments) {
			this.types.add(new DiffType(t));
		}

		this.identifier = new DiffSimpleName(exp.getName());

		this.arguments = new ArrayList<DiffExpression>();
		List<Expression> expressions = exp.arguments();
		for (Expression e : expressions) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.arguments.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

	}

	public DiffName getClassName() {
		return this.className;
	}

	public List<DiffType> getTypes() {
		return this.types;
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
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
			acceptChild(visitor, getClassName());
			acceptChildren(visitor, getTypes());
			acceptChild(visitor, getIdentifier());
			acceptChildren(visitor, getArguments());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.className != null) {
			sb.append(this.className.toString()).append(".");
		}

		sb.append("super.");

		if (this.types.size() > 0) {
			sb.append("<");
			String prefix = "";
			for (DiffType tp : this.types) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(tp.toString());
			}
			sb.append(">");
		}

		sb.append(" ").append(this.identifier.toString()).append(" ");

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
