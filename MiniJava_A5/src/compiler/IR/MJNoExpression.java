package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public final class MJNoExpression extends MJExpression {

	public void prettyPrint(PrettyPrinter prepri) {
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy
		this.type = MJType.getVoidType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to do here
		return;
	}

}
