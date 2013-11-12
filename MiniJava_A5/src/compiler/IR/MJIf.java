package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJIf extends MJStatement {

	protected MJExpression condition;
	protected MJBlock thenblock;

	public MJIf(MJExpression condition, MJBlock thenblock) {
		this.condition = condition;
		this.thenblock = thenblock;
	}

	public MJExpression getCondition() {
		return condition;
	}

	public MJBlock getThenblock() {
		return thenblock;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("if (");
		this.condition.prettyPrint(prepri);
		prepri.println(")");
		this.thenblock.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {

		// here you should enter the code to type check this class
		condition.typeCheck();
		
		if(!condition.getType().isBoolean()){
			throw new TypeCheckerException("The expression must have type boolean.");
		}
		
		thenblock.typeCheck();

		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// here you should enter the code to check whether all variables are
		// initialized

		try {
			IR.find(condition.getType().getName()).variableInit(initialized);
			thenblock.variableInit(initialized);
		} catch (VariableNotFound e) {
			e.printStackTrace();
		}

	}

}
