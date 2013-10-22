package compiler;

import compiler.Exceptions.*;
import compiler.Phases.Analysis;
import compiler.Phases.Frontend;
import compiler.IR.*;

public class Compiler {

	private static String filename;
	private static String outputfilename;
		
	private static boolean debug;
	
	private static String[] arguments=new String[0];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Compiler c = new Compiler(args);
			c.compile();
		} 
		catch (CompilerError e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public Compiler(String[] args) throws CompilerError {
		//
		// here we should inspect the arguments
		//

		if (args.length==0) {
			help();
		}

		Compiler.filename = null;
		Compiler.debug = false;

		for (int i=0;i<args.length;i++) {
			String arg = args[i];
			if (arg.charAt(0)!='-') {

				//
				// store the filename
				//

				Compiler.filename = args[i];
				Compiler.outputfilename = Compiler.filename.substring(0, Compiler.filename.lastIndexOf('.'))+".asm";
			} else {
				if (arg.equals("-v")) {
					Compiler.debug=true;
					continue;
				}
				if (arg.equals("-o")) {

					if (i==args.length) {
						throw new CompilerError("argument for -o is missing");
					}
					
					Compiler.outputfilename = args[i++];
					continue;
				}
				if (arg.equals("--")) {
					// from here on the arguments should go to the program
					
					Compiler.arguments = new String[(args.length - i - 1)];
					for (int ai = i+1; ai<args.length; ai++) {
						Compiler.arguments[ai-i-1] = args[ai];
					}
					break;
				}
			}
		}
		
		if (Compiler.filename == null) {
			throw new CompilerError("No filename specified");
		}

	}

	public void compile() throws CompilerError
	{
		IR ir;
		
		System.out.print("Parsing... ");

		try {
			ir = Frontend.parse(Compiler.filename);
		} catch (ParseError e) {
			throw new CompilerError("Parse Error: "+e.getMessage());
		}
		
		System.out.println("done.");
		
		if (Compiler.isDebug()) {
			ir.prettyPrint();
		}
		
		if (Compiler.isDebug()) {
			ir.prettyPrint();
		}
		
		System.out.println("Analysing... ");

		try {
			Analysis.analyse(ir);
		} catch (TypeCheckerException e) {
			throw new CompilerError("TypeCheckError "+e.getMessage());
		}

		System.out.println("done.");
		
		if (Compiler.isDebug()) {
			ir.prettyPrint();
		}
	}
	
	private void help() {
		
		System.err.println("\n");
		System.err.println("MiniJava compiler\n");
		System.err.println("====================\n\n");
		System.err.println("Invoke with \"java -jar path/to/jar/file \" or via GUI.");
		System.err.println("arguments: [-v] filename\n\n");
		System.err.println("  -v            be verbose\n");
		System.err.println("  filename      source filename\n");
		System.exit(-1);
	}

	public static String getFilename() {
		return filename;
	}

	public static String getOutputfilename() {
		return outputfilename;
	}

	public static String[] getArguments() {
		return arguments;
	}

	public static boolean isDebug() {
		return debug;
	}
	
}
