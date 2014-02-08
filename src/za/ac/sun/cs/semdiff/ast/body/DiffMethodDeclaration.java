package za.ac.sun.cs.semdiff.ast.body;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

import za.ac.sun.cs.semdiff.ast.DiffModifiers;
import za.ac.sun.cs.semdiff.ast.DiffSingleVariableDeclaration;
import za.ac.sun.cs.semdiff.ast.DiffTypeParameter;
import za.ac.sun.cs.semdiff.ast.expressions.DiffName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;
import za.ac.sun.cs.semdiff.ast.statements.DiffBlock;
import za.ac.sun.cs.semdiff.jdtvisitors.NameVisitor;
import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

//MethodDeclaration:
//    [ Javadoc ] { ExtendedModifier }
//                  [ < TypeParameter { , TypeParameter } > ]
//        ( Type | void ) Identifier (
//        [ FormalParameter
//                     { , FormalParameter } ] ) {[ ] }
//        [ throws TypeName { , TypeName } ] ( Block | ; )
// ConstructorDeclaration:
//    [ Javadoc ] { ExtendedModifier }
//                  [ < TypeParameter { , TypeParameter } > ]
//        Identifier (
//                  [ FormalParameter
//                         { , FormalParameter } ] )
//        [throws TypeName { , TypeName } ] Block
public class DiffMethodDeclaration extends DiffBodyDeclaration {

	private boolean isConstructor = false;
	private DiffModifiers modifiers = null;
	private List<DiffTypeParameter> typeParameters = null;
	private DiffType returnType = null;
	private List<DiffSingleVariableDeclaration> paramaters = null;
	private List<DiffName> thrownExceptions = null;
	private DiffBlock block = null;

	/**
	 * The constructor for DiffMethod, which also sets the MethodDeclaration
	 * which is associated with this class.
	 * 
	 * @param decl
	 *            The MethodDeclaration to associate this DiffMethod with.
	 */
	@SuppressWarnings("unchecked")
	public DiffMethodDeclaration(MethodDeclaration decl) {
		super(decl);

		this.isConstructor = decl.isConstructor();
		this.modifiers = new DiffModifiers(decl);

		this.typeParameters = new ArrayList<DiffTypeParameter>();
		List<TypeParameter> tps = decl.typeParameters();
		for (TypeParameter tp : tps) {
			this.typeParameters.add(new DiffTypeParameter(tp));
		}

		if (decl.getReturnType2() != null) {
			this.returnType = new DiffType(decl.getReturnType2());
		}

		setIdentifier(new DiffSimpleName(decl.getName()));

		this.paramaters = new ArrayList<DiffSingleVariableDeclaration>();
		List<SingleVariableDeclaration> params = decl.parameters();
		for (SingleVariableDeclaration var : params) {
			this.paramaters.add(new DiffSingleVariableDeclaration(var));
		}

		this.thrownExceptions = new ArrayList<DiffName>();
		List<Name> tes = decl.thrownExceptions();
		for (Name te : tes) {
			te.accept(NameVisitor.getNameVisitor());
			this.thrownExceptions.add(NameVisitor.getNameVisitor().getName());
		}

		if (decl.getBody() != null) {
			this.block = new DiffBlock(decl.getBody());
		}
	}

	public boolean isConstructor() {
		return this.isConstructor;
	}

	public DiffModifiers getModifiers() {
		return this.modifiers;
	}

	public List<DiffTypeParameter> getTypeParameters() {
		return this.typeParameters;
	}

	public DiffType getReturnType() {
		return this.returnType;
	}

	public List<DiffSingleVariableDeclaration> getParamaters() {
		return this.paramaters;
	}

	public List<DiffName> getThrownExceptions() {
		return this.thrownExceptions;
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
			acceptChild(visitor, getModifiers());
			acceptChildren(visitor, getTypeParameters());
			acceptChild(visitor, getReturnType());
			acceptChild(visitor, getIdentifier());
			acceptChildren(visitor, getParamaters());
			acceptChildren(visitor, getThrownExceptions());
			acceptChild(visitor, getBlock());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.isConstructor) {
			sb.append("method(constructor): ");
		} else {
			sb.append("method: ");
		}
		sb.append(this.modifiers.toString());
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

		if (this.returnType != null) {
			sb.append(this.returnType.toString());
			sb.append(" ");
		}

		sb.append(getIdentifier().toString());

		sb.append("(");
		String prefix = "";
		for (DiffSingleVariableDeclaration var : this.paramaters) {
			sb.append(prefix);
			prefix = ", ";
			sb.append(var.toString());
		}
		sb.append(")");

		if (this.thrownExceptions.size() > 0) {
			sb.append(" throws ");
			prefix = "";
			for (DiffName n : this.thrownExceptions) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(n.toString());
			}
		}

		if (this.block != null) {
			sb.append("\n");
			sb.append(this.block.toString());
		}

		return sb.toString();
	}
}
