package za.ac.sun.cs.semdiff.ast;

import org.eclipse.jdt.core.dom.CatchClause;

import za.ac.sun.cs.semdiff.ast.statements.DiffBlock;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// CatchClause:
//     catch ( FormalParameter ) Block
public class DiffCatchClause extends DiffNode {

	private DiffSingleVariableDeclaration parameter = null;
	private DiffBlock block = null;

	public DiffCatchClause(CatchClause decl) {
		super(decl);

		this.parameter = new DiffSingleVariableDeclaration(decl.getException());
		this.block = new DiffBlock(decl.getBody());
	}

	public DiffSingleVariableDeclaration getParameter() {
		return this.parameter;
	}

	public DiffBlock getBlock() {
		return this.block;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getParameter());
			acceptChild(visitor, getBlock());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("catch (");
		sb.append(this.parameter.toString());
		sb.append(")\n");
		sb.append(this.block.toString());

		return sb.toString();
	}

}
