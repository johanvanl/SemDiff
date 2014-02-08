package za.ac.sun.cs.semdiff.lcs;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;

public class LcsDiffNodeEqual extends LongestCommonSubsequence<DiffNode> {

	List<DiffNode> X = null;
	List<DiffNode> Y = null;

	public LcsDiffNodeEqual(List<DiffNode> original, List<DiffNode> revised) {
		this.X = original;
		this.Y = revised;
	}

	@Override
	protected int lengthOfX() {
		return this.X.size();
	}

	@Override
	protected int lengthOfY() {
		return this.Y.size();
	}

	@Override
	protected DiffNode valueOfX(int index) {
		return this.X.get(index);
	}

	@Override
	protected DiffNode valueOfY(int index) {
		return this.Y.get(index);
	}

	@Override
	protected boolean isEquals(DiffNode x, DiffNode y) {
		return x.equals(y);
	}

}
