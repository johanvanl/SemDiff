package za.ac.sun.cs.semdiff.lcs;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LongestCommonSubsequence<VALUE> {

	private int[][] c;
	private List<DiffEntry<VALUE>> diff;
	private List<List<DiffEntry<VALUE>>> diffs;
	private List<VALUE> backtrack;

	public LongestCommonSubsequence() {
	}

	protected abstract int lengthOfX();
	protected abstract int lengthOfY();

	protected abstract VALUE valueOfX(int index);
	protected abstract VALUE valueOfY(int index);

	protected abstract boolean isEquals(VALUE x, VALUE y);

	private boolean isXYEqual(int i, int j) {
		return isEquals(valueOfXInternal(i), valueOfYInternal(j));
	}

	private VALUE valueOfXInternal(int i) {
		return valueOfX(i - 1);
	}

	private VALUE valueOfYInternal(int j) {
		return valueOfY(j - 1);
	}

	public void calculateLcs() {
		if (c != null) {
			return;
		}
		c = new int[lengthOfX() + 1][];
		for (int i = 0; i < c.length; i++) {
			c[i] = new int[lengthOfY() + 1];
		}

		for (int i = 1; i < c.length; i++) {
			for (int j = 1; j < c[i].length; j++) {
				if (isXYEqual(i, j)) {
					c[i][j] = c[i - 1][j - 1] + 1;
				} else {
					c[i][j] = max(c[i][j - 1], c[i - 1][j]);
				}
			}
		}
	}

	public int getLcsLength() {
		calculateLcs();
		return c[lengthOfX()][lengthOfY()];
	}

	public int getMinEditDistance() {
		calculateLcs();
		return lengthOfX() + lengthOfY() - 2 * abs(getLcsLength());
	}

	public List<VALUE> backtrack() {
		calculateLcs();
		if (this.backtrack == null) {
			this.backtrack = new ArrayList<VALUE>();
			backtrack(lengthOfX(), lengthOfY());
		}
		return this.backtrack;
	}

	public void backtrack(int i, int j) {
		calculateLcs();

		if (i == 0 || j == 0) {
			return;
		} else if (isXYEqual(i, j)) {
			backtrack(i - 1, j - 1);
			backtrack.add(valueOfXInternal(i));
		} else {
			if (c[i][j - 1] > c[i - 1][j]) {
				backtrack(i, j - 1);
			} else {
				backtrack(i - 1, j);
			}
		}
	}

	public List<DiffEntry<VALUE>> diff() {
		calculateLcs();
		if (this.diff == null) {
			this.diff = new ArrayList<DiffEntry<VALUE>>();
			diff(lengthOfX(), lengthOfY());
		}
		return this.diff;
	}

	private void diff(int i, int j) {
		while (!(i == 0 && j == 0)) {
			if (i > 0 && j > 0 && isXYEqual(i, j)) {
				this.diff.add(new DiffEntry<VALUE>(DiffType.EQUAL,
						valueOfXInternal(i), valueOfYInternal(j)));
				i--;
				j--;

			} else {
				if (j > 0 && (i == 0 || c[i][j - 1] >= c[i - 1][j])) {
					this.diff.add(new DiffEntry<VALUE>(DiffType.ADD,
							valueOfYInternal(j)));
					j--;

				} else if (i > 0 && (j == 0 || c[i][j - 1] < c[i - 1][j])) {
					this.diff.add(new DiffEntry<VALUE>(DiffType.REMOVE,
							valueOfXInternal(i)));
					i--;
				}
			}
		}

		Collections.reverse(this.diff);
	}

	public List<List<DiffEntry<VALUE>>> getDiffs() {
		calculateLcs();
		if (this.diffs == null) {
			this.diffs = new ArrayList<List<DiffEntry<VALUE>>>();
			diffs(lengthOfX(), lengthOfY(), new ArrayList<DiffEntry<VALUE>>());
			
			int index = 0;
			while (index < this.diffs.size()) {
				if (this.diffs.get(index).size() == 0) {
					this.diffs.remove(index);
					continue;
				}
				Collections.reverse(this.diffs.get(index));
				index++;
			}
			
		}
		return this.diffs;
	}

	private void diffs(int i, int j, List<DiffEntry<VALUE>> current) {

		if (i == 0 && j == 0) {
			this.diffs.add(current);
			return;
		}
		
		if (i > 0 && j > 0 && isXYEqual(i, j)) {
			List<DiffEntry<VALUE>> copy = new ArrayList<DiffEntry<VALUE>>(current);
			copy.add(
					new DiffEntry<VALUE>(DiffType.EQUAL, valueOfXInternal(i),
							valueOfYInternal(j)));
			diffs(i - 1, j - 1,copy);
		}

		if (j > 0 && (i == 0 || c[i][j] == c[i][j-1])) {
			List<DiffEntry<VALUE>> copy = new ArrayList<DiffEntry<VALUE>>(current);
			copy.add(
					new DiffEntry<VALUE>(DiffType.ADD, valueOfYInternal(j)));
			diffs(i, j-1, copy);
		}

		if (i > 0 && (j == 0 || c[i][j] == c[i-1][j])) {
			List<DiffEntry<VALUE>> copy = new ArrayList<DiffEntry<VALUE>>(current);
			copy.add(
					new DiffEntry<VALUE>(DiffType.REMOVE, valueOfXInternal(i)));
			diffs(i-1, j, copy);
		}

	}

	@Override
	public String toString() {
		calculateLcs();

		StringBuffer buf = new StringBuffer();
		buf.append("  ");
		for (int j = 1; j <= lengthOfY(); j++) {
			buf.append(valueOfYInternal(j));
		}
		buf.append("\n");
		buf.append(" ");
		for (int j = 0; j < c[0].length; j++) {
			buf.append(Integer.toString(c[0][j]));
		}
		buf.append("\n");
		for (int i = 1; i < c.length; i++) {
			buf.append(valueOfXInternal(i));
			for (int j = 0; j < c[i].length; j++) {
				buf.append(Integer.toString(c[i][j]));
			}
			buf.append("\n");
		}

		return buf.toString();
	}

	private static enum DiffType {
		ADD, REMOVE, EQUAL;
	}

	public static class DiffEntry<VALUE> {

		private DiffType type;
		private VALUE value_x = null;
		private VALUE value_y = null;

		public DiffEntry(DiffType type, VALUE value) {
			super();
			this.type = type;
			this.value_x = value;
		}

		public DiffEntry(DiffType type, VALUE value_x, VALUE value_y) {
			super();
			this.type = type;
			this.value_x = value_x;
			this.value_y = value_y;
		}

		public void setType(DiffType type) {
			this.type = type;
		}

		public void setValue(VALUE value) {
			this.value_x = value;
		}

		public void setYValue(VALUE value) {
			this.value_y = value;
		}

		public DiffType getType() {
			return this.type;
		}

		public VALUE getValue() {
			return this.value_x;
		}

		public VALUE getYValue() {
			return this.value_y;
		}

		public boolean isAdded() {
			return this.type == DiffType.ADD;
		}

		public boolean isRemoved() {
			return this.type == DiffType.REMOVE;
		}

		public boolean isSame() {
			return this.type == DiffType.EQUAL;
		}

		@Override
		public String toString() {
			return "[" + type.toString() + "] " + value_x.toString();
		}

	}

}
