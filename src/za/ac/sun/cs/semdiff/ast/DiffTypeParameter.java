package za.ac.sun.cs.semdiff.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeParameter;

import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// TypeParameter:
//     TypeVariable [ extends Type { & Type } ]
public class DiffTypeParameter extends DiffNode {

	private DiffSimpleName variable = null;
	private List<DiffType> types = null;

	@SuppressWarnings("unchecked")
	public DiffTypeParameter(TypeParameter decl) {
		super(decl);

		this.variable = new DiffSimpleName(decl.getName());

		this.types = new ArrayList<DiffType>();
		List<Type> tps = decl.typeBounds();
		for (Type t : tps) {
			this.types.add(new DiffType(t));
		}

	}

	public DiffSimpleName getVariable() {
		return this.variable;
	}

	public List<DiffType> getTypes() {
		return this.types;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getVariable());
			acceptChildren(visitor, getTypes());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.variable.toString());

		if (this.types.size() > 0) {
			sb.append(" extends ");
			String prefix = "";
			for (DiffType t : this.types) {
				sb.append(prefix);
				prefix = " & ";
				sb.append(t.toString());
			}
		}

		return sb.toString();
	}

}
