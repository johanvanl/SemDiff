package za.ac.sun.cs.semdiff.ast;

import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import za.ac.sun.cs.semdiff.ast.expressions.DiffExpression;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// VariableDeclarationFragment:
//     Identifier { [] } [ = Expression ]
public class DiffVariableDeclarationFragment extends DiffNode {

	private DiffSimpleName name = null;
	private DiffExpression expression = null;

	public DiffVariableDeclarationFragment(VariableDeclarationFragment decl) {
		super(decl);

		this.name = new DiffSimpleName(decl.getName());

		if (decl.getInitializer() != null) {
			decl.getInitializer().accept(
					ExpressionVisitor.getExpressionVisitor());
			this.expression = ExpressionVisitor.getExpressionVisitor()
					.getExpression();
		}

	}

	public DiffSimpleName getName() {
		return this.name;
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
			acceptChild(visitor, getName());
			acceptChild(visitor, getExpression());
		}
	}

	@Override
	public String toString() {
		return name + "=" + expression;
	}

}
