package za.ac.sun.cs.semdiff.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Utils {

	/**
	 * Read the file and output a String.
	 * 
	 * @param path
	 *            The path to the file.
	 * @return A String containing the specified file.
	 * @throws IOException
	 */
	public static String readFile(String path) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File(path));
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			return Charset.defaultCharset().decode(bb).toString();
		} catch (Exception e) {
		}
		try {
			stream.close();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Obtain the Compilation Unit from the parser.
	 * 
	 * @param source
	 *            The file to parse.
	 * @return A Compilation Unit.
	 */
	@SuppressWarnings("rawtypes")
	public static CompilationUnit GetCU(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(source.toCharArray());

		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		return (CompilationUnit) parser.createAST(null);
	}

	/**
	 * A private constructor to prevent the class from being explicitly
	 * instantiated by its callers.
	 */
	private Utils() {
	}

}
