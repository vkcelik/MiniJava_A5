package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJNegate extends MJUnaryOp {

	public MJNegate(MJExpression l) {
		super(l);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("!");
		this.arg.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// here you should enter the code to type check this class
		type.typeCheck();
		
		if (!type.isBoolean())
			throw new TypeCheckerException("expression must be of type boolean");
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
		
		
	}

}
