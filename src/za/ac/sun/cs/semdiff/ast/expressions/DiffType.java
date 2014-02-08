package za.ac.sun.cs.semdiff.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Type;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

//  Type:
//     PrimitiveType
//     ArrayType
//     SimpleType
//     QualifiedType
//     ParameterizedType
//     WildcardType
//
//  PrimitiveType:
//     byte
//     short
//     char
//     int
//     long
//     float
//     double
//     boolean
//     void
//  ArrayType:
//     Type [ ]
//  SimpleType:
//     TypeName
//  ParameterizedType:
//     Type < Type { , Type } >
//  QualifiedType:
//     Type . SimpleName
//  WildcardType:
//     ? [ ( extends | super) Type ]
public class DiffType extends DiffExpression {

	private DiffSimpleName type = null;
	private List<DiffType> arguments = null;

	@SuppressWarnings("unchecked")
	public DiffType(Type exp) {
		super(exp);

		this.arguments = new ArrayList<DiffType>();

		if (exp.isArrayType()) {
			ArrayType at = (ArrayType) exp;
			this.type = new DiffSimpleName(exp, at.getElementType().toString());
		}

		if (exp.isParameterizedType()) {
			ParameterizedType pt = (ParameterizedType) exp;
			this.type = new DiffSimpleName(exp, pt.getType().toString());
			List<Type> tps = pt.typeArguments();
			for (Type t : tps) {
				this.arguments.add(new DiffType(t));
			}
		}

		/*
		 * All the other Types
		 */
		if (this.type == null) {
			this.type = new DiffSimpleName(exp, exp.toString());
		}

	}

	public void setType(DiffSimpleName type) {
		this.type = type;
	}

	public DiffSimpleName getType() {
		return this.type;
	}

	public List<DiffType> getArguments() {
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
			acceptChild(visitor, getType());
			acceptChildren(visitor, getArguments());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.type.toString());

		if (this.arguments.size() > 0) {
			String prefix = "";
			sb.append("<");
			for (DiffType t : this.arguments) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(t.toString());
			}
			sb.append(">");
		}

		return sb.toString();
	}

}
