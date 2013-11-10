package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJIfElse extends MJIf {

	private MJBlock elseblock;

	public MJIfElse(MJExpression condition, MJBlock thenblock, MJBlock elseblock) {
		super(condition, thenblock);
		this.elseblock = elseblock;
	}
	
	public MJBlock getElseblock() {
		return elseblock;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		super.prettyPrint(prepri);
		prepri.println("else");
		this.elseblock.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// here you should enter the code to type check this class
		if(!condition.getType().isBoolean()){
			throw new TypeCheckerException("The expression must have type boolean.");
		}
		
		thenblock.typeCheck();
		elseblock.typeCheck();
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
		try {
			IR.find(condition.getType().getName()).variableInit(initialized);
			thenblock.variableInit(initialized);
			elseblock.variableInit(initialized);
		} catch (VariableNotFound e) {
			e.printStackTrace();
		}
	}

}
