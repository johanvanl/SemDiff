package za.ac.sun.cs.semdiff.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffVariableDeclarationFragment;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// VariableDeclarationStatement:
//    { ExtendedModifier } Type VariableDeclarationFragment
//      { , VariableDeclarationFragment } ;
public class DiffVariableDeclarationStatement extends DiffStatement {

	private DiffModifiers modifiers;
	private DiffType type;
	private List<DiffVariableDeclarationFragment> variables;

	@SuppressWarnings("unchecked")
	public DiffVariableDeclarationStatement(VariableDeclarationStatement stmt) {
		super(stmt);
		this.modifiers = new DiffModifiers(stmt);

		this.type = new DiffType(stmt.getType());

		this.variables = new ArrayList<DiffVariableDeclarationFragment>();
		List<VariableDeclarationFragment> vars = stmt.fragments();
		for (VariableDeclarationFragment var : vars) {
			this.variables.add(new DiffVariableDeclarationFragment(var));
		}
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
		sb.append(this.modifiers.toString());
		sb.append(" ");
		sb.append(this.type.toString());
		sb.append(" ");

		String prefix = "";
		for (DiffVariableDeclarationFragment varDecl : this.variables) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(varDecl.toString());
		}

		return sb.toString();
	}

}
