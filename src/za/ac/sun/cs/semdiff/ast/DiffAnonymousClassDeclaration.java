package za.ac.sun.cs.semdiff.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;

import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.jdtvisitors.BodyDeclarationVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// AnonymousClassDeclaration:
//     { ClassBodyDeclaration }
public class DiffAnonymousClassDeclaration extends DiffNode {

	private List<DiffBodyDeclaration> bodyDeclarations = null;

	@SuppressWarnings("unchecked")
	public DiffAnonymousClassDeclaration(AnonymousClassDeclaration decl) {
		super(decl);

		this.bodyDeclarations = new ArrayList<DiffBodyDeclaration>();
		List<BodyDeclaration> bodydecls = decl.bodyDeclarations();
		for (BodyDeclaration bd : bodydecls) {
			bd.accept(BodyDeclarationVisitor.getBodyDeclarationVisitor());
			this.bodyDeclarations.add(BodyDeclarationVisitor
					.getBodyDeclarationVisitor().getBodyDeclaration());
		}

	}

	public List<DiffBodyDeclaration> getBodyDeclarations() {
		return this.bodyDeclarations;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChildren(visitor, getBodyDeclarations());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		for (DiffBodyDeclaration bd : this.bodyDeclarations) {
			sb.append(bd.toString());
			sb.append("\n");
		}
		sb.append("}");

		return sb.toString();
	}

}
