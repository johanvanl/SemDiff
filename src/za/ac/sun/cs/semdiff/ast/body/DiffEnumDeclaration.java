package za.ac.sun.cs.semdiff.ast.body;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Type;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.jdtvisitors.BodyDeclarationVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// EnumDeclaration:
//     [ Javadoc ] { ExtendedModifier } enum Identifier
//         [ implements Type { , Type } ]
//         {
//        [ EnumConstantDeclaration { , EnumConstantDeclaration } ] [ , ]
//         [ ; { ClassBodyDeclaration | ; } ]
//         }
public class DiffEnumDeclaration extends DiffBodyDeclaration {

	private DiffModifiers modifiers = null;
	private List<DiffType> superInterfaceTypes = null;
	private List<DiffEnumConstantDeclaration> enumConstants = null;
	private List<DiffBodyDeclaration> bodyDeclarations = null;

	@SuppressWarnings("unchecked")
	public DiffEnumDeclaration(EnumDeclaration decl) {
		super(decl);

		this.modifiers = new DiffModifiers(decl);
		setIdentifier(new DiffSimpleName(decl.getName()));

		this.superInterfaceTypes = new ArrayList<DiffType>();
		List<Type> sits = decl.superInterfaceTypes();
		for (Type sit : sits) {
			this.superInterfaceTypes.add(new DiffType(sit));
		}

		this.enumConstants = new ArrayList<DiffEnumConstantDeclaration>();
		List<EnumConstantDeclaration> ecds = decl.enumConstants();
		for (EnumConstantDeclaration ecd : ecds) {
			this.enumConstants.add(new DiffEnumConstantDeclaration(ecd));
		}

		this.bodyDeclarations = new ArrayList<DiffBodyDeclaration>();
		List<BodyDeclaration> tempBodyDeclarations = decl.bodyDeclarations();
		for (BodyDeclaration bd : tempBodyDeclarations) {
			bd.accept(BodyDeclarationVisitor.getBodyDeclarationVisitor());
			this.bodyDeclarations.add(BodyDeclarationVisitor
					.getBodyDeclarationVisitor().getBodyDeclaration());
		}

	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public List<DiffType> getSuperInterfaceTypes() {
		return this.superInterfaceTypes;
	}

	public List<DiffEnumConstantDeclaration> getEnumConstants() {
		return this.enumConstants;
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
			acceptChild(visitor, getModifiers());
			acceptChild(visitor, getIdentifier());
			acceptChildren(visitor, getSuperInterfaceTypes());
			acceptChildren(visitor, getEnumConstants());
			acceptChildren(visitor, getBodyDeclarations());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.modifiers.toString());
		sb.append(" enum ");
		sb.append(getIdentifier().toString());

		if (this.superInterfaceTypes.size() > 0) {
			sb.append(" implements ");
			String prefix = "";
			for (DiffType s : this.superInterfaceTypes) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(s.toString());
			}
		}

		sb.append("\n");
		String prefix = "";
		for (DiffEnumConstantDeclaration e : this.enumConstants) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(e.toString());
		}

		for (DiffBodyDeclaration decl : this.bodyDeclarations) {
			sb.append("\n");
			sb.append(decl.toString());
		}

		return sb.toString();
	}
}
