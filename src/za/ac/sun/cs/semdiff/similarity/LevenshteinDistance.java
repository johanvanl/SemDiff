package za.ac.sun.cs.semdiff.similarity;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.visitors.TokenVisitor;

public class LevenshteinDistance implements Similarity {

	private static int minimum(int... nums) {
		int min = nums[0];

		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < min) {
				min = nums[i];
			}
		}

		return min;
	}

	private static int levenshtein(List<String> original, List<String> revised) {
		int m = original.size();
		int n = revised.size();

		int[][] dist = new int[m][n];
		for (int i = 0; i < m; i++) {
			dist[i][0] = i;
		}

		for (int i = 0; i < n; i++) {
			dist[0][i] = i;
		}

		for (int j = 1; j < n; j++) {
			for (int i = 1; i < m; i++) {
				if (original.get(i).equals(revised.get(j))) {
					dist[i][j] = dist[i - 1][j - 1];
				} else {
					dist[i][j] = minimum(dist[i - 1][j] + 1,
							dist[i][j - 1] + 1, dist[i - 1][j - 1] + 1);
				}
			}
		}

		return dist[m - 1][n - 1];
	}

	public double similarity(DiffNode originalNode, DiffNode revisedNode) {
		List<String> original_tokens = TokenVisitor.getTokenList(originalNode);
		List<String> revised_tokens = TokenVisitor.getTokenList(revisedNode);

		int dist = levenshtein(original_tokens, revised_tokens);
		double ratio = 1.0 - ((double) dist / Math.max(original_tokens.size(),
				revised_tokens.size()));

		return ratio;
	}

}
