package za.ac.sun.cs.semdiff.utils;

import za.ac.sun.cs.semdiff.ast.DiffNode;

public class Difference {

	private DifferenceEnum diff = null;
	private DiffNode movedReference = null;

	private enum DifferenceEnum {
		UNCHANGED, ADDED, DELETED, RENAMED, MOVED;
	}

	public Difference() {
		this.diff = DifferenceEnum.UNCHANGED;
	}

	public void setUnchanged() {
		this.diff = DifferenceEnum.UNCHANGED;
	}

	public void setAdded() {
		this.diff = DifferenceEnum.ADDED;
	}

	public void setDeleted() {
		this.diff = DifferenceEnum.DELETED;
	}

	public void setRenamed() {
		this.diff = DifferenceEnum.RENAMED;
	}

	public void setMoved() {
		this.diff = DifferenceEnum.MOVED;
	}

	/**
	 * Sets the reference to the related node.
	 * 
	 * @param node
	 *            The related node to set.
	 */
	public void setRelatedReference(DiffNode node) {
		this.movedReference = node;
	}

	/**
	 * This method returns the reference to the related node in the AST being
	 * compared to. This will be used with a rename holding a reference to the
	 * renamed node.
	 * 
	 * @return The node.
	 */
	public DiffNode getRelatedNode() {
		return this.movedReference;
	}

	public boolean isUnchanged() {
		return this.diff == DifferenceEnum.UNCHANGED;
	}

	public boolean isAdded() {
		return this.diff == DifferenceEnum.ADDED;
	}

	public boolean isDeleted() {
		return this.diff == DifferenceEnum.DELETED;
	}

	public boolean isRenamed() {
		return this.diff == DifferenceEnum.RENAMED;
	}

	public boolean isMoved() {
		return this.diff == DifferenceEnum.MOVED;
	}

	public String getPrefix() {
		if (isAdded()) {
			return "___ADDED___";
		}

		if (isDeleted()) {
			return "___DELETED___";
		}

		if (isRenamed()) {
			return "___RENAMED___";
		}

		if (isMoved()) {
			return "___MOVED___";
		}

		return "";
	}
	
	@Override
	public String toString() {
		return diff.toString();
	}

}
