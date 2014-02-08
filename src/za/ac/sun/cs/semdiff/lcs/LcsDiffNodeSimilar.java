package za.ac.sun.cs.semdiff.lcs;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.compare.CompareNodes;

public class LcsDiffNodeSimilar extends LongestCommonSubsequence<DiffNode> {

	List<DiffNode> X = null;
	List<DiffNode> Y = null;

	public LcsDiffNodeSimilar(List<DiffNode> original, List<DiffNode> revised) {
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
		return CompareNodes.isSimilar(x, y);
	}

	public List<DiffEntry<DiffNode>> getOptimalLcs() {
		List<List<DiffEntry<DiffNode>>> lcsDiffs = getDiffs();
		int best_index = 0;
		int best_sum = 0;
		for (int i = 0; i < lcsDiffs.size(); i++) {
			int sum = 0;
			for (DiffEntry<DiffNode> entry : lcsDiffs.get(i)) {
				if (entry.isSame()) {
					if (entry.getValue().equals(entry.getYValue())) {
						sum += 1;
					}
				}
			}
			if (sum > best_sum) {
				best_index = i;
				best_sum = sum;
			}
		}
		return lcsDiffs.get(best_index);
	}

}
