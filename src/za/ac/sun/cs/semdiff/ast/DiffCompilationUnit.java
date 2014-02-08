package za.ac.sun.cs.semdiff.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.jdtvisitors.BodyDeclarationVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public class DiffCompilationUnit extends DiffNode {

	/**
	 * The source of the source file this DiffAST is associated with.
	 */
	private String source = null;

	/**
	 * The package this file belongs to.
	 */
	private DiffPackageDeclaration pakage = null;

	/**
	 * The imports contained in the source file this DiffAST is associated with.
	 */
	private List<DiffImportDeclaration> imports = null;

	/**
	 * The classes that are contained in the source file this DiffAST is
	 * associated with.
	 */
	private List<DiffBodyDeclaration> types = null;

	/**
	 * The constructor for DiffAST.
	 * 
	 * @param cu
	 *            The compilation from which the whole AST will be built.
	 * 
	 * @param source
	 *            The path to the source file. So that the original source can
	 *            be associated with the DiffAST.
	 */
	@SuppressWarnings("unchecked")
	public DiffCompilationUnit(CompilationUnit cu, String source) {
		super(cu);

		this.source = source;

		if (cu.getPackage() != null) {
			this.pakage = new DiffPackageDeclaration(cu.getPackage());
		}

		List<ImportDeclaration> imps = cu.imports();
		this.imports = new ArrayList<DiffImportDeclaration>();
		for (ImportDeclaration i : imps) {
			this.imports.add(new DiffImportDeclaration(i));
		}

		this.types = new ArrayList<DiffBodyDeclaration>();
		List<AbstractTypeDeclaration> atds = cu.types();
		for (AbstractTypeDeclaration atd : atds) {
			atd.accept(BodyDeclarationVisitor.getBodyDeclarationVisitor());
			this.types.add(BodyDeclarationVisitor.getBodyDeclarationVisitor()
					.getBodyDeclaration());
		}
	}

	/**
	 * Obtain the source from the original source file that this DiffAST is
	 * associated with.
	 * 
	 * @return The original source code.
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Obtain the DiffPackage associated with this source file.
	 * 
	 * @return The DiffPackage.
	 */
	public DiffPackageDeclaration getPackage() {
		return this.pakage;
	}

	public List<DiffImportDeclaration> getImports() {
		return this.imports;
	}

	public List<DiffBodyDeclaration> getTypes() {
		return this.types;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			acceptChild(visitor, getPackage());
			acceptChildren(visitor, getImports());
			acceptChildren(visitor, getTypes());
		}
	}

	/**
	 * The main toString method, helpful when debugging, the composition of the
	 * AST.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");

		if (this.pakage != null) {
			sb.append(pakage.toString());
		}

		sb.append("\n");
		for (DiffImportDeclaration imp : this.imports) {
			sb.append(imp.toString());
			sb.append("\n");
		}

		for (DiffBodyDeclaration decl : this.types) {
			sb.append(decl.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

}
