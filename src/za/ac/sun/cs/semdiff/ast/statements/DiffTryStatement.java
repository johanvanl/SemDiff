package za.ac.sun.cs.semdiff.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import za.ac.sun.cs.semdiff.ast.DiffCatchClause;
import za.ac.sun.cs.semdiff.ast.expressions.DiffVariableDeclarationExpression;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// TryStatement:
//    try [ ( Resources ) ]
//        Block
//        [ { CatchClause } ]
//        [ finally Block 
public class DiffTryStatement extends DiffStatement {

	private List<DiffVariableDeclarationExpression> resources = null;
	private DiffBlock tryBlock = null;
	private List<DiffCatchClause> catchClauses = null;
	private DiffBlock finallyBlock = null;

	@SuppressWarnings("unchecked")
	public DiffTryStatement(TryStatement stmt) {
		super(stmt);

		this.resources = new ArrayList<DiffVariableDeclarationExpression>();
		List<VariableDeclarationExpression> res = stmt.resources();
		for (VariableDeclarationExpression vde : res) {
			this.resources.add(new DiffVariableDeclarationExpression(vde));
		}

		this.tryBlock = new DiffBlock(stmt.getBody());

		this.catchClauses = new ArrayList<DiffCatchClause>();
		List<CatchClause> catches = stmt.catchClauses();
		for (CatchClause c : catches) {
			this.catchClauses.add(new DiffCatchClause(c));
		}

		if (stmt.getFinally() != null) {
			this.finallyBlock = new DiffBlock(stmt.getFinally());
		}

	}

	public List<DiffVariableDeclarationExpression> getResources() {
		return this.resources;
	}

	public DiffBlock getTryBlock() {
		return this.tryBlock;
	}

	public List<DiffCatchClause> getCatchClauses() {
		return this.catchClauses;
	}

	public DiffBlock getFinallyBlock() {
		return this.finallyBlock;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChildren(visitor, getResources());
			acceptChild(visitor, getTryBlock());
			acceptChildren(visitor, getCatchClauses());
			acceptChild(visitor, getFinallyBlock());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("try");

		if (this.resources.size() > 0) {
			sb.append(" (");
			String prefix = "";
			for (DiffVariableDeclarationExpression res : this.resources) {
				sb.append(prefix);
				prefix = ",";
				sb.append(res.toString());
			}
			sb.append(")");
		}

		sb.append("\n");
		sb.append(this.tryBlock.toString());

		if (this.catchClauses.size() > 0) {
			for (DiffCatchClause cc : this.catchClauses) {
				sb.append("\n");
				sb.append(cc.toString());
			}
		}

		if (this.finallyBlock != null) {
			sb.append("\nfinally\n");
			sb.append(this.finallyBlock.toString());
		}

		return sb.toString();
	}

}
