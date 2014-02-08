package za.ac.sun.cs.semdiff.lcs;

import java.util.List;

public class LcsString extends LongestCommonSubsequence<String> {

	List<String> X = null;
	List<String> Y = null;

	public LcsString(List<String> original, List<String> revised) {
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
	protected String valueOfX(int index) {
		return this.X.get(index);
	}

	@Override
	protected String valueOfY(int index) {
		return this.Y.get(index);
	}

	@Override
	protected boolean isEquals(String x, String y) {
		if (x == null) {
			return false;
		}
		return x.equals(y);
	}

}
