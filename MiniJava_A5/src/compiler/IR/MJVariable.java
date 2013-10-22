package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJVariable extends IR {

	private MJType type;
	private String name;
	private boolean field;
	
	public MJVariable(MJType t, String name) {
		this.type = t;
		this.name = name;
	}

	public MJType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public boolean isField() {
		return this.field;
	}

	public void setField() {
		this.field = true;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.type + " " + this.name + ";");
	}

	public MJType typeCheck() throws TypeCheckerException {
		
		// we only need to typecheck the type of the variable
		// adding to the scope stack ensures that the name is unique
		
		this.getType().typeCheck();
		
		return MJType.getVoidType();
	}

	public void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// this only declares a variable, so nothing to be done here
	}

}
