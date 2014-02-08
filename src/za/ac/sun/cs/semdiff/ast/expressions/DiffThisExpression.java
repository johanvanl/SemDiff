package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ThisExpression;

import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// ThisExpression:
//     [ ClassName . ] this
public class DiffThisExpression extends DiffExpression {

	private DiffName className = null;

	public DiffThisExpression(ThisExpression exp) {
		super(exp);
		if (exp.getQualifier() != null) {
			exp.getQualifier().accept(NameVisitor.getNameVisitor());
			this.className = NameVisitor.getNameVisitor().getName();
		}
	}

	public DiffName getClassName() {
		return this.className;
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
		}
	}

	@Override
	public String toString() {
		if (this.className != null) {
			return this.className.toString() + ".this";
		}
		return "this";
	}

}
