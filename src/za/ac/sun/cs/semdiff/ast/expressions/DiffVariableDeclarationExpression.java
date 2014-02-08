package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// VariableDeclarationExpression:
// { ExtendedModifier } Type VariableDeclarationFragment
//      { , VariableDeclarationFragment }
public class DiffVariableDeclarationExpression extends DiffExpression {

	private DiffModifiers modifiers = null;
	private DiffType type = null;
	private List<DiffVariableDeclarationFragment> fragments = null;

	@SuppressWarnings("unchecked")
	public DiffVariableDeclarationExpression(VariableDeclarationExpression exp) {
		super(exp);

		this.modifiers = new DiffModifiers(exp);

		this.type = new DiffType(exp.getType());

		this.fragments = new ArrayList<DiffVariableDeclarationFragment>();
		List<VariableDeclarationFragment> frgmts = exp.fragments();
		for (VariableDeclarationFragment frgmt : frgmts) {
			this.fragments.add(new DiffVariableDeclarationFragment(frgmt));
		}

	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public DiffType getType() {
		return this.type;
	}

	public List<DiffVariableDeclarationFragment> getFragments() {
		return this.fragments;
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
			acceptChildren(visitor, getFragments());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.modifiers.toString());
		sb.append(this.type.toString());
		sb.append(" ");

		sb.append("[");
		String prefix = "";
		for (DiffVariableDeclarationFragment fragment : this.fragments) {
			sb.append(prefix);
			prefix = ",";
			sb.append(fragment.toString());
		}
		sb.append("]");

		return sb.toString();
	}

}
