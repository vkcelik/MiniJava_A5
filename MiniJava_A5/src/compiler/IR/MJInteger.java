package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJInteger extends MJExpression {

	private int value;

	public MJInteger(String string) {
		this.value = Integer.parseInt(string);
	}

	public MJInteger(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(Integer.toString(this.value));
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy...
		this.type = MJType.getIntType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		// nothing to do here
		return;
	}

}
