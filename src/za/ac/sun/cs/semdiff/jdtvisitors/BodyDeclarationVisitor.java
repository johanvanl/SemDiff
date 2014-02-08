package za.ac.sun.cs.semdiff.jdtvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumConstantDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffFieldDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffTypeDeclaration;

public class BodyDeclarationVisitor extends ASTVisitor {

	// BODYDECLARATION
	//
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

	private static BodyDeclarationVisitor bdv = null;
	private DiffBodyDeclaration body = null;

	private BodyDeclarationVisitor() {
	}

	public static BodyDeclarationVisitor getBodyDeclarationVisitor() {
		if (bdv == null) {
			bdv = new BodyDeclarationVisitor();
		}
		return bdv;
	}

	public DiffBodyDeclaration getBodyDeclaration() {
		return this.body;
	}

	public boolean visit(TypeDeclaration node) {
		this.body = new DiffTypeDeclaration(node);
		return false;
	}

	public boolean visit(EnumDeclaration node) {
		this.body = new DiffEnumDeclaration(node);
		return false;
	}

	public boolean visit(MethodDeclaration node) {
		this.body = new DiffMethodDeclaration(node);
		return false;
	}

	public boolean visit(FieldDeclaration node) {
		this.body = new DiffFieldDeclaration(node);
		return false;
	}
	
	public boolean visit(TypeParameter node) {
		return false; // XXX
	}

	// XXX THIS IS WHEN A BODY IS STATIC
	public boolean visit(Initializer node) {
		System.out.println("Initializer out of Visitor"); // XXX
		return false;
	}

	// XXX

	public boolean visit(EnumConstantDeclaration node) {
		this.body = new DiffEnumConstantDeclaration(node);
		return false;
	}

	public boolean visit(AnnotationTypeDeclaration node) {
		System.out.println("AnnotationTypeDeclaration out of Visitor"); // XXX
		return false;
	}

	public boolean visit(AnnotationTypeMemberDeclaration node) {
		System.out.println("AnnotationTypeMemberDeclaration out of Visitor"); // XXX
		return false;
	}

}
