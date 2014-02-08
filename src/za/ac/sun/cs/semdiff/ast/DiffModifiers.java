package za.ac.sun.cs.semdiff.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// Modifier:
//     public
//     protected
//     private
//     static
//     abstract
//     final
//     native
//     synchronized
//     transient
//     volatile
//     strictfp
public class DiffModifiers extends DiffNode {

	private List<DiffSimpleName> modifiers = null;

	/**
	 * Will be used for comparison.
	 */
	private int modifierFlags;

	@SuppressWarnings("unchecked")
	public DiffModifiers(BodyDeclaration decl) {
		super(decl);
		this.modifiers = new ArrayList<DiffSimpleName>();
		this.modifierFlags = decl.getModifiers();
		setModifiers(decl.modifiers());
	}

	@SuppressWarnings("unchecked")
	public DiffModifiers(VariableDeclarationStatement decl) {
		super(decl);
		this.modifiers = new ArrayList<DiffSimpleName>();
		this.modifierFlags = decl.getModifiers();
		setModifiers(decl.modifiers());
	}

	@SuppressWarnings("unchecked")
	public DiffModifiers(SingleVariableDeclaration decl) {
		super(decl);
		this.modifiers = new ArrayList<DiffSimpleName>();
		this.modifierFlags = decl.getModifiers();
		setModifiers(decl.modifiers());
	}

	@SuppressWarnings("unchecked")
	public DiffModifiers(VariableDeclarationExpression decl) {
		super(decl);
		this.modifiers = new ArrayList<DiffSimpleName>();
		this.modifierFlags = decl.getModifiers();
		setModifiers(decl.modifiers());
	}

	private void setModifiers(List<IExtendedModifier> modifiers) {
		for (IExtendedModifier m : modifiers) {
			if (m instanceof Modifier) {
				Modifier mod = (Modifier) m;
				this.modifiers.add(new DiffSimpleName(mod));
			}
		}
	}

	public List<DiffSimpleName> getModifiers() {
		return this.modifiers;
	}

	public int getModifierFlags() {
		return this.modifierFlags;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChildren(visitor, getModifiers());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("[");
		String prefix = "";
		for (DiffName modifier : this.modifiers) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(modifier.toString());

		}
		sb.append("]");
		return sb.toString();
	}

}
