package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJReturn extends MJStatement {

	private MJExpression returnExpression;

	public MJReturn(MJExpression retExp) {
		this.returnExpression = retExp;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("return");

		if (!(this.returnExpression instanceof MJNoExpression)) {
			prepri.print(" ");
			this.returnExpression.prettyPrint(prepri);
		}

		prepri.println(";");
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// get the type of the returned expression
		
		MJType retType = this.returnExpression.typeCheck();
		
		// and compare it to the return type of the current method
		
		if (!MJType.isAssignable(retType, IR.currentMethod.getReturnType())) {
			throw new TypeCheckerException("Return argument must have same type as current method.");
		}
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// visit the returned expression
		
		this.returnExpression.variableInit(initialized);
	}

}
