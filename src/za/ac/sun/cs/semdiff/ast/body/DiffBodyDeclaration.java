package za.ac.sun.cs.semdiff.ast.body;

import org.eclipse.jdt.core.dom.ASTNode;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;

// ClassDeclaration
// InterfaceDeclaration
// EnumDeclaration
// MethodDeclaration
// ConstructorDeclaration
// FieldDeclaration
// Initializer
// EnumConstantDeclaration
// AnnotationTypeDeclaration
// AnnotationTypeMemberDeclaration
public abstract class DiffBodyDeclaration extends DiffNode {

	private DiffSimpleName identifier = null;

	public DiffBodyDeclaration(ASTNode node) {
		super(node);
	}

	public void setIdentifier(DiffSimpleName identifier) {
		this.identifier = identifier;
	}

	public void setIdentifier(ASTNode node, String identifier) {
		this.identifier = new DiffSimpleName(node, identifier);
	}

	public DiffSimpleName getIdentifier() {
		return this.identifier;
	}

}
