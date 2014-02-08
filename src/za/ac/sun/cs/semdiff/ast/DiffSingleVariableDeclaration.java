package za.ac.sun.cs.semdiff.ast;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// SingleVariableDeclaration:
//   { ExtendedModifier } Type [ ... ] Identifier { [] } [ = Expression ]
public class DiffSingleVariableDeclaration extends DiffNode {

	private DiffModifiers modifiers = null;
	private DiffType type = null;
	private DiffSimpleName identifier = null;
	private DiffExpression expression = null;

	public DiffSingleVariableDeclaration(SingleVariableDeclaration decl) {
		super(decl);

		this.modifiers = new DiffModifiers(decl);
		this.type = new DiffType(decl.getType());
		this.identifier = new DiffSimpleName(decl.getName());

		if (decl.getInitializer() != null) {
			decl.getInitializer().accept(
					ExpressionVisitor.getExpressionVisitor());
			this.expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public DiffType getType() {
		return this.type;
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
	}

	public DiffExpression getExpression() {
		return this.expression;
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
			acceptChild(visitor, getType());
			acceptChild(visitor, getIdentifier());
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(modifiers.toString());
		sb.append(" ");
		sb.append(type.toString());
		sb.append(" ");
		sb.append(identifier);
		sb.append("=");
		sb.append(expression);

		return sb.toString();
	}

}
