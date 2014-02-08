package za.ac.sun.cs.semdiff.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Point;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.ast.body.DiffBodyDeclaration;
import za.ac.sun.cs.semdiff.ast.body.DiffMethodDeclaration;
import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.statements.DiffStatement;
import za.ac.sun.cs.semdiff.visitors.FieldDeclarationNames;

public class Summary {

	private DiffNode node = null;
	private GetDifferenceNodes diffNodes = null;
	private double similarity;
	private String errorMessages = null;

	private Map<Point, Point> map = null;

	public Summary(DiffNode node, double similarity, String errorMessages) {
		this.node = node;
		this.diffNodes = new GetDifferenceNodes(node);
		this.similarity = similarity;
		this.errorMessages = errorMessages;

		this.map = new HashMap<Point, Point>();
	}

	public Map<Point, Point> getMap() {
		return this.map;
	}

	public String getSimilarityString() {
		if (similarity < 0.00001) {
			return "Token Similarity : 0.0%";
		}
		double percentage = similarity * 100;
		DecimalFormat df = new DecimalFormat("#.0");
		return "Token Similarity : " + df.format(percentage) + "%";
	}

	private void addToMap(int offset, DiffBodyDeclaration node) {
		Point key = new Point(offset, node.getIdentifier().getLength());
		Point value = new Point(node.getIdentifier().getStart(), node
				.getIdentifier().getLength());
		this.map.put(key, value);
	}

	private void addToMap(int offset, DiffSimpleName node) {
		Point key = new Point(offset, node.getLength());
		Point value = new Point(node.getStart(), node.getLength());
		this.map.put(key, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!this.errorMessages.equals("")) {
			sb.append("############# ERRORS  #############\n");
			sb.append(this.errorMessages);
		}

		sb.append("############# SUMMARY #############\n");
		sb.append(getSimilarityString());

		// /////////////////
		// STATEMENTS
		// /////////////////

		List<DiffStatement> add_statements = this.diffNodes
				.getAddedStatements();
		if (add_statements.size() > 0) {
			sb.append("\nNumber of added statements : ");
			sb.append(add_statements.size());
		}

		List<DiffStatement> del_statements = this.diffNodes
				.getDeletedStatements();
		if (del_statements.size() > 0) {
			sb.append("\nNumber of deleted statements : ");
			sb.append(del_statements.size());
		}

		// /////////////////
		// TYPES
		// /////////////////

		List<DiffBodyDeclaration> add_types = this.diffNodes.getAddedTypes();
		if (add_types.size() > 0) {
			sb.append("\nAdded Types :\n");
			String prefix = "";
			for (DiffBodyDeclaration body_decl : add_types) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), body_decl);
				sb.append(body_decl.getIdentifier());
			}
		}

		List<DiffBodyDeclaration> del_types = this.diffNodes.getDeletedTypes();
		if (del_types.size() > 0) {
			sb.append("\nDeleted Types :\n");
			String prefix = "";
			for (DiffBodyDeclaration body_decl : del_types) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), body_decl);
				sb.append(body_decl.getIdentifier());
			}
		}

		List<DiffBodyDeclaration> ren_types = this.diffNodes.getRenamedTypes();
		if (ren_types.size() > 0) {
			sb.append("\nRenamed Types :\n");
			String prefix = "";
			for (DiffBodyDeclaration body_decl : ren_types) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), body_decl);
				sb.append(body_decl.getIdentifier());
				if (body_decl.getDifference().getRelatedNode() != null) {
					sb.append(" (from ");
					DiffBodyDeclaration related = (DiffBodyDeclaration) body_decl
							.getDifference().getRelatedNode();
					sb.append(related.getIdentifier()).append(")");
				}
			}
		}

		List<DiffBodyDeclaration> mov_types = this.diffNodes.getMovedTypes();
		if (mov_types.size() > 0) {
			sb.append("\nMoved Types :\n");
			String prefix = "";
			for (DiffBodyDeclaration body_decl : mov_types) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), body_decl);
				sb.append(body_decl.getIdentifier());
			}
		}

		// /////////////////
		// METHODS
		// /////////////////

		List<DiffMethodDeclaration> add_methods = this.diffNodes
				.getAddedMethods();
		if (add_methods.size() > 0) {
			sb.append("\nAdded Methods :\n");
			String prefix = "";
			for (DiffMethodDeclaration met_decl : add_methods) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), met_decl);
				sb.append(met_decl.getIdentifier());
			}
		}

		List<DiffMethodDeclaration> del_methods = this.diffNodes
				.getDeletedMethods();
		if (del_methods.size() > 0) {
			sb.append("\nDeleted Methods :\n");
			String prefix = "";
			for (DiffMethodDeclaration met_Decl : del_methods) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), met_Decl);
				sb.append(met_Decl.getIdentifier());
			}
		}

		List<DiffMethodDeclaration> ren_methods = this.diffNodes
				.getRenamedMethods();
		if (ren_methods.size() > 0) {
			sb.append("\nRenamed Methods :\n");
			String prefix = "";
			for (DiffMethodDeclaration met_decl : ren_methods) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), met_decl);
				sb.append(met_decl.getIdentifier());
				if (met_decl.getDifference().getRelatedNode() != null) {
					sb.append(" (from ");
					DiffMethodDeclaration related = (DiffMethodDeclaration) met_decl
							.getDifference().getRelatedNode();
					sb.append(related.getIdentifier()).append(")");
				}
			}
		}

		List<DiffMethodDeclaration> mov_methods = this.diffNodes
				.getMovedMethods();
		if (mov_methods.size() > 0) {
			sb.append("\nMoved Methods :\n");
			String prefix = "";
			for (DiffMethodDeclaration met_decl : mov_methods) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), met_decl);
				sb.append(met_decl.getIdentifier());
			}
		}

		// /////////////////
		// FIELD DECLARATIONS
		// /////////////////

		FieldDeclarationNames fdn = new FieldDeclarationNames();
		this.node.accept(fdn);
		List<DiffSimpleName> field_names = fdn.getNames();
		List<DiffSimpleName> ren_fields = new ArrayList<DiffSimpleName>();
		for (DiffSimpleName name : field_names) {
			if (name.getDifference().isRenamed()) {
				ren_fields.add(name);
			}
		}
		if (ren_fields.size() > 0) {
			sb.append("\nRenamed Field Declarations :\n");
			String prefix = "";
			for (DiffSimpleName name : ren_fields) {
				sb.append(prefix);
				prefix = "\n";
				sb.append("   ");
				addToMap(sb.length(), name);
				sb.append(name);
				if (name.getDifference().getRelatedNode() != null) {
					sb.append(" (from ");
					DiffSimpleName related = (DiffSimpleName) name
							.getDifference().getRelatedNode();
					sb.append(related).append(")");
				}
			}
		}

		sb.append("\n###################################\n");
		return sb.toString();
	}
}
