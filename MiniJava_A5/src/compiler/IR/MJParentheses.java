package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJParentheses extends MJExpression {

	private MJExpression exp;

	public MJParentheses(MJExpression e) {
		this.exp = e;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("( ");
		this.exp.prettyPrint(prepri);
		prepri.print(" )");
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the argument
		
		this.type = this.exp.typeCheck();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// check the argument
		this.exp.variableInit(initialized);
	}

}
