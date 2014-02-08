package za.ac.sun.cs.semdiff.ast.body;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffTypeParameter;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.jdtvisitors.BodyDeclarationVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// TypeDeclaration:
//     ClassDeclaration
//     InterfaceDeclaration
// ClassDeclaration:
// [ Javadoc ] { ExtendedModifier } class Identifier
//             [ < TypeParameter { , TypeParameter } > ]
//             [ extends Type ]
//             [ implements Type { , Type } ]
//             { { ClassBodyDeclaration | ; } }
//
// InterfaceDeclaration:
// [ Javadoc ] { ExtendedModifier } interface Identifier
//             [ < TypeParameter { , TypeParameter } > ]
//             [ extends Type { , Type } ]
//             { { InterfaceBodyDeclaration | ; } }
public class DiffTypeDeclaration extends DiffBodyDeclaration {

	private boolean isInterface = false;
	private DiffModifiers modifiers = null;
	private List<DiffTypeParameter> typeParameters = null;
	private DiffType superClass = null;
	private List<DiffType> superInterfaces = null;

	/**
	 * The instance variables of this class.
	 */
	private List<DiffFieldDeclaration> instanceVariables = null;

	/**
	 * The inner classes of this class.
	 */
	private List<DiffBodyDeclaration> types = null;

	/**
	 * The methods contained within this class.
	 */
	private List<DiffMethodDeclaration> methods = null;

	/**
	 * The constructor for DiffClass, which also sets the class associated with
	 * DiffClass.
	 * 
	 * @param clazz
	 *            The class to be associated with DiffClass.
	 */
	@SuppressWarnings("unchecked")
	public DiffTypeDeclaration(TypeDeclaration decl) {
		super(decl);

		this.isInterface = decl.isInterface();
		this.modifiers = new DiffModifiers(decl);
		setIdentifier(new DiffSimpleName(decl.getName()));

		List<TypeParameter> typeParams = decl.typeParameters();
		this.typeParameters = new ArrayList<DiffTypeParameter>();
		for (TypeParameter tp : typeParams) {
			this.typeParameters.add(new DiffTypeParameter(tp));
		}

		if (decl.getSuperclassType() != null) {
			this.superClass = new DiffType(decl.getSuperclassType());
		}

		List<Type> superI = decl.superInterfaceTypes();
		this.superInterfaces = new ArrayList<DiffType>();
		for (Type t : superI) {
			this.superInterfaces.add(new DiffType(t));
		}

		this.instanceVariables = new ArrayList<DiffFieldDeclaration>();
		FieldDeclaration[] iv = decl.getFields();
		for (int i = 0; i < iv.length; i++) {
			this.instanceVariables.add(new DiffFieldDeclaration(iv[i]));
		}

		this.types = new ArrayList<DiffBodyDeclaration>();
		TypeDeclaration[] tps = decl.getTypes();
		for (int i = 0; i < tps.length; i++) {
			tps[i].accept(BodyDeclarationVisitor.getBodyDeclarationVisitor());
			this.types.add(BodyDeclarationVisitor.getBodyDeclarationVisitor()
					.getBodyDeclaration());
		}

		this.methods = new ArrayList<DiffMethodDeclaration>();
		MethodDeclaration[] mths = decl.getMethods();
		for (int i = 0; i < mths.length; i++) {
			this.methods.add(new DiffMethodDeclaration(mths[i]));
		}

	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public List<DiffTypeParameter> getTypeParameters() {
		return this.typeParameters;
	}

	public DiffType getSuperClass() {
		return this.superClass;
	}

	public List<DiffType> getSuperInterfaces() {
		return this.superInterfaces;
	}

	public List<DiffFieldDeclaration> getInstanceVariables() {
		return this.instanceVariables;
	}

	public List<DiffBodyDeclaration> getTypes() {
		return this.types;
	}

	public List<DiffMethodDeclaration> getMethods() {
		return this.methods;
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
			acceptChildren(visitor, getTypeParameters());
			acceptChild(visitor, getSuperClass());
			acceptChildren(visitor, getSuperInterfaces());
			acceptChildren(visitor, getInstanceVariables());
			acceptChildren(visitor, getTypes());
			acceptChildren(visitor, getMethods());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");

		sb.append(this.modifiers.toString());
		sb.append(" ");

		if (this.isInterface) {
			sb.append("interface ");
		} else {
			sb.append("class ");
		}

		sb.append(getIdentifier().toString());
		sb.append(" ");

		if (this.typeParameters.size() > 0) {
			sb.append("<");
			String prefix = "";
			for (DiffTypeParameter tp : this.typeParameters) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(tp.toString());
			}
			sb.append(">");
		}

		if (this.superClass != null) {
			sb.append(" extends ");
			sb.append(superClass.toString());
		}

		if (this.superInterfaces.size() > 0) {
			sb.append(" implements ");
			String prefix = "";
			for (DiffType s : this.superInterfaces) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(s.toString());
			}
		}

		sb.append("\n");

		for (DiffFieldDeclaration var : this.instanceVariables) {
			sb.append(var.toString());
			sb.append("\n");
		}

		for (DiffBodyDeclaration type : this.types) {
			sb.append(type.toString());
			sb.append("\n");
		}

		for (DiffMethodDeclaration methodDecl : this.methods) {
			sb.append(methodDecl.toString());
			sb.append("\n");
		}

		sb.append("END-OF-CLASS\n");

		return sb.toString();
	}

}
