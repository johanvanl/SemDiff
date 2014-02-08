package za.ac.sun.cs.semdiff.similarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.visitors.TokenVisitor;

public class DamerauDistance implements Similarity {

	private static int deleteCost = 1;
	private static int insertCost = 1;
	private static int replaceCost = 1;
	private static int swapCost = 1;

	private static int damerau(List<String> original, List<String> revised) {
		if (original.size() == 0) {
			return revised.size() * insertCost;
		}
		if (revised.size() == 0) {
			return original.size() * deleteCost;
		}
		int[][] table = new int[original.size()][revised.size()];
		Map<String, Integer> originalIndexByCharacter = new HashMap<String, Integer>();
		if (!original.get(0).equals(revised.get(0))) {
			table[0][0] = Math.min(replaceCost, deleteCost + insertCost);
		}
		originalIndexByCharacter.put(original.get(0), 0);
		for (int i = 1; i < original.size(); i++) {
			int deleteDistance = table[i - 1][0] + deleteCost;
			int insertDistance = (i + 1) * deleteCost + insertCost;
			int matchDistance = i
					* deleteCost
					+ (original.get(i).equals(revised.get(0)) ? 0 : replaceCost);
			table[i][0] = Math.min(Math.min(deleteDistance, insertDistance),
					matchDistance);
		}
		for (int j = 1; j < revised.size(); j++) {
			int deleteDistance = table[0][j - 1] + insertCost;
			int insertDistance = (j + 1) * insertCost + deleteCost;
			int matchDistance = j
					* insertCost
					+ (original.get(0).equals(revised.get(j)) ? 0 : replaceCost);
			table[0][j] = Math.min(Math.min(deleteDistance, insertDistance),
					matchDistance);
		}
		for (int i = 1; i < original.size(); i++) {
			int maxoriginalLetterMatchIndex = original.get(i).equals(
					revised.get(0)) ? 0 : -1;
			for (int j = 1; j < revised.size(); j++) {
				Integer candidateSwapIndex = originalIndexByCharacter
						.get(revised.get(j));
				int jSwap = maxoriginalLetterMatchIndex;
				int deleteDistance = table[i - 1][j] + deleteCost;
				int insertDistance = table[i][j - 1] + insertCost;
				int matchDistance = table[i - 1][j - 1];
				if (!original.get(i).equals(revised.get(j))) {
					matchDistance += replaceCost;
				} else {
					maxoriginalLetterMatchIndex = j;
				}
				int swapDistance;
				if (candidateSwapIndex != null && jSwap != -1) {
					int iSwap = candidateSwapIndex;
					int preSwapCost;
					if (iSwap == 0 && jSwap == 0) {
						preSwapCost = 0;
					} else {
						preSwapCost = table[Math.max(0, iSwap - 1)][Math.max(0,
								jSwap - 1)];
					}
					swapDistance = preSwapCost + (i - iSwap - 1) * deleteCost
							+ (j - jSwap - 1) * insertCost + swapCost;
				} else {
					swapDistance = Integer.MAX_VALUE;
				}
				table[i][j] = Math.min(
						Math.min(Math.min(deleteDistance, insertDistance),
								matchDistance), swapDistance);
			}
			originalIndexByCharacter.put(original.get(i), i);
		}
		return table[original.size() - 1][revised.size() - 1];
	}

	public double similarity(DiffNode originalNode, DiffNode revisedNode) {
		List<String> original_tokens = TokenVisitor.getTokenList(originalNode);
		List<String> revised_tokens = TokenVisitor.getTokenList(revisedNode);
		int dist = damerau(original_tokens, revised_tokens);
		double ratio = 1.0 - ((double) dist / Math.max(original_tokens.size(),
				revised_tokens.size()));

		return ratio;
	}

}
