package za.ac.sun.cs.semdiff.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.utils.Difference;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

public abstract class DiffNode {

	private int start = -1;
	private int length = 0;

	private Difference diff;

	/**
	 * The constructor for DiffNode.
	 */
	public DiffNode(ASTNode node) {
		this.diff = new Difference();

		if (node != null) {
			this.start = node.getStartPosition();
			this.length = node.getLength();
		}
	}

	/**
	 * Obtain the starting position of this DiffNode in the original source
	 * file.
	 * 
	 * @return The starting position.
	 */
	public int getStart() {
		return this.start;
	}

	public int getLength() {
		return this.length;
	}

	/**
	 * Obtain the ending position of this DiffNode in the original source file.
	 * 
	 * @return The ending position.
	 */
	public int getEnd() {
		return getStart() + getLength();
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	

	public Difference getDifference() {
		return this.diff;
	}

	public abstract boolean subtreeMatch0(DiffASTMatcher matcher, Object other);

	public final boolean subtreeMatch(DiffASTMatcher matcher, Object other) {
		return subtreeMatch0(matcher, other);
	}

	public abstract void accept0(DiffVisitor visitor);

	public final void accept(DiffVisitor visitor) {
		accept0(visitor);
	}

	final public void acceptChild(DiffVisitor visitor, DiffNode child) {
		if (child == null) {
			return;
		}
		child.accept(visitor);
	}

	@SuppressWarnings("rawtypes")
	final public void acceptChildren(DiffVisitor visitor, List children) {
		for (int i = 0; i < children.size(); i++) {
			DiffNode node = (DiffNode) children.get(i);
			node.accept(visitor);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return this.subtreeMatch(new DiffASTMatcher(), obj);
	}

}
