package compiler.IR;

import compiler.PrettyPrinter;

public abstract class MJStatement extends IR {
	
	public MJStatement() {}
	
	public abstract void prettyPrint(PrettyPrinter prepri);

}
