package za.ac.sun.cs.semdiff.ast.body;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.Expression;

import za.ac.sun.cs.semdiff.ast.DiffAnonymousClassDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// EnumConstantDeclaration:
//     [ Javadoc ] { ExtendedModifier } Identifier
//         [ ( [ Expression { , Expression } ] ) ]
//         [ AnonymousClassDeclaration ]
public class DiffEnumConstantDeclaration extends DiffBodyDeclaration {

	private DiffModifiers modifiers = null;
	private List<DiffExpression> expressions = null;
	private DiffAnonymousClassDeclaration anonymousClassDeclaration = null;

	@SuppressWarnings("unchecked")
	public DiffEnumConstantDeclaration(EnumConstantDeclaration decl) {
		super(decl);

		this.modifiers = new DiffModifiers(decl);
		setIdentifier(new DiffSimpleName(decl.getName()));

		this.expressions = new ArrayList<DiffExpression>();
		List<Expression> exps = decl.arguments();
		for (Expression exp : exps) {
			exp.accept(ExpressionVisitor.getExpressionVisitor());
			this.expressions.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

		if (decl.getAnonymousClassDeclaration() != null) {
			this.anonymousClassDeclaration = new DiffAnonymousClassDeclaration(
					decl.getAnonymousClassDeclaration());
		}

	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public List<DiffExpression> getExpressions() {
		return this.expressions;
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
			acceptChild(visitor, getModifiers());
			acceptChild(visitor, getIdentifier());
			acceptChildren(visitor, getExpressions());
			acceptChild(visitor, getAnonymousClassDeclaration());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.modifiers.toString());
		sb.append(" ");
		sb.append(getIdentifier().toString());

		sb.append("(");
		String prefix = "";
		for (DiffExpression e : this.expressions) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(e.toString());
		}
		sb.append(")");

		if (this.anonymousClassDeclaration != null) {
			sb.append("\n");
			sb.append(this.anonymousClassDeclaration.toString());
		}

		return sb.toString();
	}

}
