package za.ac.sun.cs.semdiff.similarity;

import java.util.List;

import za.ac.sun.cs.semdiff.ast.DiffNode;
import za.ac.sun.cs.semdiff.lcs.LcsString;
import za.ac.sun.cs.semdiff.visitors.TokenVisitor;

public class LcsDistance implements Similarity {

	public double similarity(DiffNode originalNode, DiffNode revisedNode) {
		List<String> original_tokens = TokenVisitor.getTokenList(originalNode);
		List<String> revised_tokens = TokenVisitor.getTokenList(revisedNode);

		LcsString lcs = new LcsString(original_tokens, revised_tokens);
		double ratio = 1.0 - ((double) lcs.getMinEditDistance() / Math.max(
				original_tokens.size(), revised_tokens.size()));
		return ratio;
	}

}
