package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJString extends MJExpression {

	private String string;

	public MJString(String string) {
		this.string = string;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.string);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		this.type = MJType.getClassType("String");
		this.type.typeCheck();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to be done here
		
		return;
	}

}
