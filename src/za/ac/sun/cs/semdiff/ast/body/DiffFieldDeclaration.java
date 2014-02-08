package za.ac.sun.cs.semdiff.ast.body;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// FieldDeclaration:
//     [Javadoc] { ExtendedModifier } Type VariableDeclarationFragment
//          { , VariableDeclarationFragment } ;
public class DiffFieldDeclaration extends DiffBodyDeclaration {

	private DiffModifiers modifiers = null;
	private DiffType type = null;
	private List<DiffVariableDeclarationFragment> variables = null;

	@SuppressWarnings("unchecked")
	public DiffFieldDeclaration(FieldDeclaration decl) {
		super(decl);

		this.modifiers = new DiffModifiers(decl);
		this.type = new DiffType(decl.getType());

		this.variables = new ArrayList<DiffVariableDeclarationFragment>();
		List<VariableDeclarationFragment> vars = decl.fragments();
		for (VariableDeclarationFragment var : vars) {
			this.variables.add(new DiffVariableDeclarationFragment(var));
		}

		// Never going to be used except when sorting in TokenVisitor
		StringBuilder sb = new StringBuilder();
		String prefix = "";
		for (DiffVariableDeclarationFragment var : this.variables) {
			sb.append(prefix);
			prefix = ",";
			sb.append(var.getName().toString());
		}
		setIdentifier(decl, sb.toString());
	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public DiffType getType() {
		return this.type;
	}

	public List<DiffVariableDeclarationFragment> getVariables() {
		return this.variables;
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
			acceptChildren(visitor, getVariables());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSTANCE VARIABLE: ");
		sb.append(modifiers.toString()).append(" ");
		sb.append(type.toString()).append(" ");
		String prefix = "";
		for (DiffVariableDeclarationFragment varDecl : variables) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(varDecl.toString());
		}

		return sb.toString();
	}

}
