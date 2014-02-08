package za.ac.sun.cs.semdiff;

import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Message;

import za.ac.sun.cs.semdiff.ast.DiffCompilationUnit;
import za.ac.sun.cs.semdiff.compare.CompareNodes;
import za.ac.sun.cs.semdiff.similarity.LcsDistance;
import za.ac.sun.cs.semdiff.similarity.Similarity;
import za.ac.sun.cs.semdiff.utils.PrintDiff;
import za.ac.sun.cs.semdiff.utils.Summary;
import za.ac.sun.cs.semdiff.utils.Utils;

public class SemDiff {

	private CompilationUnit originalCU = null;
	private CompilationUnit revisedCU = null;

	private DiffCompilationUnit originalAST = null;
	private DiffCompilationUnit revisedAST = null;

	/**
	 * Compute the semantic diff between two source files.
	 * 
	 * @param originalSource
	 *            The location of the old source code.
	 * @param revisedSource
	 *            The location of the new source code.
	 */
	public SemDiff(String originalSource, String revisedSource) {
		this.originalCU = Utils.GetCU(originalSource);
		this.originalAST = new DiffCompilationUnit(originalCU, originalSource);

		this.revisedCU = Utils.GetCU(revisedSource);
		this.revisedAST = new DiffCompilationUnit(revisedCU, revisedSource);

		CompareNodes.startComparison(originalAST, revisedAST);
	}

	public CompilationUnit getOriginalCompilationUnit() {
		return this.originalCU;
	}

	public CompilationUnit getRevisedCompilationUnit() {
		return this.revisedCU;
	}

	public DiffCompilationUnit getOriginalAST() {
		return this.originalAST;
	}

	public DiffCompilationUnit getRevisedAST() {
		return this.revisedAST;
	}

	public double getSimilarity() {
		Similarity sim = new LcsDistance();
		double similarity = sim.similarity(originalAST, revisedAST);
		return similarity;
	}

	public double getSimilarity(Similarity similaritymeasure) {
		double similarity = similaritymeasure.similarity(originalAST,
				revisedAST);
		return similarity;
	}

	public String getOriginalWithDifferenceTags() {
		return PrintDiff.printDiff(originalAST);
	}

	public String getRevisedWithDifferenceTags() {
		return PrintDiff.printDiff(revisedAST);
	}

	public Summary getOriginalSummary() {
		return new Summary(originalAST, getSimilarity(), getOriginalMessages());
	}

	public Summary getRevisedSummary() {
		return new Summary(revisedAST, getSimilarity(), getRevisedMessages());
	}

	public Message[] getOriginalASTMessages() {
		return this.originalCU.getMessages();
	}

	public Message[] getRevisedASTMessages() {
		return this.revisedCU.getMessages();
	}

	public String getOriginalMessages() {
		StringBuilder sb = new StringBuilder();
		Message[] messages = originalCU.getMessages();
		for (int i = 0; i < messages.length; i++) {
			sb.append(messages[i].getMessage()).append("\n");
		}
		return sb.toString();
	}

	public String getRevisedMessages() {
		StringBuilder sb = new StringBuilder();
		Message[] messages = revisedCU.getMessages();
		for (int i = 0; i < messages.length; i++) {
			sb.append(messages[i].getMessage()).append("\n");
		}
		return sb.toString();
	}

	public int totalNumberOfMessages() {
		return this.originalCU.getMessages().length
				+ this.revisedCU.getMessages().length;
	}

	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.out.println("Two filenames need to be provided.");
			System.out.println("The first one for the original Java source file");
			System.out.println("and the second for the revised source file.");
			System.exit(0);
		}

		String originalFilename = args[0];
		String revisedFilename = args[1];

		String originalSource = Utils.readFile(originalFilename);
		String revisedSource = Utils.readFile(revisedFilename);

		SemDiff semdiff = new SemDiff(originalSource, revisedSource);

		System.out.println("ORIGINAL :");
		System.out.println(semdiff.getOriginalSummary().toString());
		System.out.println(semdiff.getOriginalWithDifferenceTags());

		System.out.println("#################################");
		System.out.println("REVISED :");
		System.out.println(semdiff.getRevisedSummary().toString());
		System.out.println(semdiff.getRevisedWithDifferenceTags());

	}

}
