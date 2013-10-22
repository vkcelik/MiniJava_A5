package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJWhile extends MJStatement {

	private MJExpression condition;
	private MJBlock body;

	public MJWhile(MJExpression condition, MJBlock body) {
		this.condition = condition;
		this.body = body;
	}

	public MJBlock getBody() {
		return this.body;
	}

	public MJExpression getCondition() {
		return condition;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("while (");
		this.condition.prettyPrint(prepri);
		prepri.println(")");
		this.body.prettyPrint(prepri);
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// here you should enter the code to type check this class
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
	}

}
