package za.ac.sun.cs.semdiff.ast.expressions;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffSimpleName extends DiffName {

	boolean isDeclaration = false;
	String name = null;

	public DiffSimpleName(SimpleName name) {
		super(name);
		this.name = name.getIdentifier();
		this.isDeclaration = name.isDeclaration();
	}

	public DiffSimpleName(Modifier exp) {
		super(exp);
		this.name = exp.toString();
	}

	public DiffSimpleName(ASTNode node, String name) {
		super(null);
		this.name = name;
		setStart(node.getStartPosition());
		setLength(node.getLength());
	}

	public DiffSimpleName(DiffSimpleName name) {
		super(null);
		this.name = name.getName();
		this.isDeclaration = name.isDeclaration;
		setStart(name.getStart());
		setLength(name.getLength());
	}

	public void setName(DiffSimpleName name) {
		this.name = name.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeclaration() {
		return this.isDeclaration;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return this.name;
	}

}
