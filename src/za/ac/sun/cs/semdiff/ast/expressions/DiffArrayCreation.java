package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Expression;

import za.ac.sun.cs.semdiff.jdtvisitors.ExpressionVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ArrayCreation:
//     new PrimitiveType [ Expression ] { [ Expression ] } { [ ] }
//     new TypeName [ < Type { , Type } > ]
//         [ Expression ] { [ Expression ] } { [ ] }
//     new PrimitiveType [ ] { [ ] } ArrayInitializer
//     new TypeName [ < Type { , Type } > ]
//         [ ] { [ ] } ArrayInitializer
public class DiffArrayCreation extends DiffExpression {

	private DiffType type = null;
	private List<DiffExpression> dimensions = null;
	private DiffArrayInitializer initializer = null;

	@SuppressWarnings("unchecked")
	public DiffArrayCreation(ArrayCreation exp) {
		super(exp);

		this.type = new DiffType(exp.getType());

		this.dimensions = new ArrayList<DiffExpression>();
		List<Expression> dims = exp.dimensions();
		for (Expression e : dims) {
			e.accept(ExpressionVisitor.getExpressionVisitor());
			this.dimensions.add(ExpressionVisitor.getExpressionVisitor()
					.getExpression());
		}

		if (exp.getInitializer() != null) {
			this.initializer = new DiffArrayInitializer(exp.getInitializer());
		}

	}

	public DiffType getType() {
		return this.type;
	}

	public List<DiffExpression> getDimensions() {
		return this.dimensions;
	}

	public DiffArrayInitializer getArrayInitializer() {
		return this.initializer;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getType());
			acceptChildren(visitor, getDimensions());
			acceptChild(visitor, getArrayInitializer());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("new ");
		sb.append(this.type.toString());
		for (DiffExpression e : this.dimensions) {
			sb.append("[");
			sb.append(e.toString());
			sb.append("]");
		}

		if (this.initializer != null) {
			sb.append(this.initializer.toString());
		}

		return sb.toString();
	}
}
