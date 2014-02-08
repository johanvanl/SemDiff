package za.ac.sun.cs.semdiff.utils;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffEnumDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffTypeDeclaration;
import za.ac.sun.cs.semdiff.ast.statements.DiffStatement;
import za.ac.sun.cs.semdiff.visitors.NodeInOrderVisitor;

public class GetDifferenceNodes {

	List<DiffNode> nodes = null;

	public GetDifferenceNodes(DiffNode node) {
		this.nodes = NodeInOrderVisitor.getNodes(node);
	}

	public List<DiffNode> getNodes() {
		return this.nodes;
	}

	// //////////////////////
	// ALL
	// //////////////////////

	public List<DiffNode> getAddedNodes() {
		List<DiffNode> added = new ArrayList<DiffNode>();

		for (DiffNode node : this.nodes) {
			if (node.getDifference().isAdded()) {
				added.add(node);
			}
		}
		return added;
	}

	public List<DiffNode> getDeletedNodes() {
		List<DiffNode> added = new ArrayList<DiffNode>();
		for (DiffNode node : this.nodes) {
			if (node.getDifference().isDeleted()) {
				added.add(node);
			}
		}
		return added;
	}

	public List<DiffNode> getRenamedNodes() {
		List<DiffNode> renamed = new ArrayList<DiffNode>();
		for (DiffNode node : this.nodes) {
			if (node.getDifference().isRenamed()) {
				renamed.add(node);
			}
		}
		return renamed;
	}

	public List<DiffNode> getMovedNodes() {
		List<DiffNode> moved = new ArrayList<DiffNode>();
		for (DiffNode node : this.nodes) {
			if (node.getDifference().isMoved()) {
				moved.add(node);
			}
		}
		return moved;
	}

	// //////////////////////
	// BODY DECLARATIONS
	// //////////////////////

	public List<DiffBodyDeclaration> getAddedBodyDeclarations() {
		List<DiffBodyDeclaration> add_body_decls = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getAddedNodes()) {
			if (node instanceof DiffBodyDeclaration) {
				add_body_decls.add((DiffBodyDeclaration) node);
			}
		}
		return add_body_decls;
	}

	public List<DiffBodyDeclaration> getDeletedBodyDeclarations() {
		List<DiffBodyDeclaration> del_body_decls = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getDeletedNodes()) {
			if (node instanceof DiffBodyDeclaration) {
				del_body_decls.add((DiffBodyDeclaration) node);
			}
		}
		return del_body_decls;
	}

	public List<DiffBodyDeclaration> getRenamedBodyDeclarations() {
		List<DiffBodyDeclaration> ren_body_decls = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getRenamedNodes()) {
			if (node instanceof DiffBodyDeclaration) {
				ren_body_decls.add((DiffBodyDeclaration) node);
			}
		}
		return ren_body_decls;
	}

	public List<DiffBodyDeclaration> getMovedBodyDeclarations() {
		List<DiffBodyDeclaration> mov_body_decls = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getMovedNodes()) {
			if (node instanceof DiffBodyDeclaration) {
				mov_body_decls.add((DiffBodyDeclaration) node);
			}
		}
		return mov_body_decls;
	}

	// //////////////////////
	// TYPES
	// //////////////////////

	public List<DiffBodyDeclaration> getAddedTypes() {
		List<DiffBodyDeclaration> add_types = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getAddedNodes()) {
			if (node instanceof DiffTypeDeclaration
					|| node instanceof DiffEnumDeclaration) {
				add_types.add((DiffBodyDeclaration) node);
			}
		}
		return add_types;
	}

	public List<DiffBodyDeclaration> getDeletedTypes() {
		List<DiffBodyDeclaration> del_types = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getDeletedNodes()) {
			if (node instanceof DiffTypeDeclaration
					|| node instanceof DiffEnumDeclaration) {
				del_types.add((DiffBodyDeclaration) node);
			}
		}
		return del_types;
	}

	public List<DiffBodyDeclaration> getRenamedTypes() {
		List<DiffBodyDeclaration> ren_types = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getRenamedNodes()) {
			if (node instanceof DiffTypeDeclaration
					|| node instanceof DiffEnumDeclaration) {
				ren_types.add((DiffBodyDeclaration) node);
			}
		}
		return ren_types;
	}

	public List<DiffBodyDeclaration> getMovedTypes() {
		List<DiffBodyDeclaration> mov_types = new ArrayList<DiffBodyDeclaration>();
		for (DiffNode node : getMovedNodes()) {
			if (node instanceof DiffTypeDeclaration
					|| node instanceof DiffEnumDeclaration) {
				mov_types.add((DiffBodyDeclaration) node);
			}
		}
		return mov_types;
	}

	// //////////////////////
	// METHODS
	// //////////////////////

	public List<DiffMethodDeclaration> getAddedMethods() {
		List<DiffMethodDeclaration> add_methods = new ArrayList<DiffMethodDeclaration>();
		for (DiffNode node : getAddedNodes()) {
			if (node instanceof DiffMethodDeclaration) {
				add_methods.add((DiffMethodDeclaration) node);
			}
		}
		return add_methods;
	}

	public List<DiffMethodDeclaration> getDeletedMethods() {
		List<DiffMethodDeclaration> del_methods = new ArrayList<DiffMethodDeclaration>();
		for (DiffNode node : getDeletedNodes()) {
			if (node instanceof DiffMethodDeclaration) {
				del_methods.add((DiffMethodDeclaration) node);
			}
		}
		return del_methods;
	}

	public List<DiffMethodDeclaration> getRenamedMethods() {
		List<DiffMethodDeclaration> ren_methods = new ArrayList<DiffMethodDeclaration>();
		for (DiffNode node : getRenamedNodes()) {
			if (node instanceof DiffMethodDeclaration) {
				ren_methods.add((DiffMethodDeclaration) node);
			}
		}
		return ren_methods;
	}

	public List<DiffMethodDeclaration> getMovedMethods() {
		List<DiffMethodDeclaration> mov_methods = new ArrayList<DiffMethodDeclaration>();
		for (DiffNode node : getMovedNodes()) {
			if (node instanceof DiffMethodDeclaration) {
				mov_methods.add((DiffMethodDeclaration) node);
			}
		}
		return mov_methods;
	}

	// //////////////////////
	// STATEMENTS
	// //////////////////////

	public List<DiffStatement> getAddedStatements() {
		List<DiffStatement> add_statements = new ArrayList<DiffStatement>();
		for (DiffNode node : getAddedNodes()) {
			if (node instanceof DiffStatement) {
				add_statements.add((DiffStatement) node);
			}
		}
		return add_statements;
	}

	public List<DiffStatement> getDeletedStatements() {
		List<DiffStatement> del_statements = new ArrayList<DiffStatement>();
		for (DiffNode node : getDeletedNodes()) {
			if (node instanceof DiffStatement) {
				del_statements.add((DiffStatement) node);
			}
		}
		return del_statements;
	}

	public List<DiffStatement> getRenamedStatements() {
		List<DiffStatement> ren_statements = new ArrayList<DiffStatement>();
		for (DiffNode node : getRenamedNodes()) {
			if (node instanceof DiffStatement) {
				ren_statements.add((DiffStatement) node);
			}
		}
		return ren_statements;
	}

}
