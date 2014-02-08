package za.ac.sun.cs.semdiff.visitors;

import java.util.ArrayList;
import java.util.List;

import za.ac.sun.cs.semdiff.ast.expressions.DiffSimpleName;
import za.ac.sun.cs.semdiff.ast.expressions.DiffType;

public class SimpleNameRename extends DiffVisitor {

	private List<String> from = null;
	private List<String> to = null;

	private List<DiffSimpleName> changed = null;

	public SimpleNameRename(List<DiffSimpleName> from, List<DiffSimpleName> to) {

		this.from = new ArrayList<String>();
		this.to = new ArrayList<String>();

		for (DiffSimpleName name : from) {
			this.from.add(new String(name.getName()));
		}

		for (DiffSimpleName name : to) {
			this.to.add(new String(name.getName()));
		}

		this.changed = new ArrayList<DiffSimpleName>();
	}

	public void revertRenames() {
		for (DiffSimpleName name : changed) {
			for (int i = 0; i < to.size(); i++) {
				if (name.getName().equals(to.get(i))) {
					name.setName(from.get(i));
					break;
				}
			}
		}
	}

	/*
	 * Need to rename with the String, otherwise we get problems with the
	 * references.
	 */
	@Override
	public boolean visit(DiffSimpleName node) {
		for (int i = 0; i < from.size(); i++) {
			if (node.getName().equals(from.get(i))) {
				node.setName(to.get(i));
				this.changed.add(node);
				return true;
			}
		}
		return true;
	}

	/*
	 * So that the DiffSimpleName within DiffType doesn't get renamed.
	 */
	@Override
	public boolean visit(DiffType node) {
		return false;
	}

}
