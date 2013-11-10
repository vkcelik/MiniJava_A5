package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;

public class MJNewArray extends MJNew {

	private MJExpression size;

	public MJNewArray(MJType type, MJExpression size) {
		super(type);
		this.size = size;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("new " + this.type.getBaseType().getName() + "[");
		this.size.prettyPrint(prepri);
		prepri.print("]");
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// here you should enter the code to type check this class
		size.typeCheck();
		if(!size.getType().isInt())
			throw new TypeCheckerException("size of array must be of type int");
		
		this.type = MJType.getArrayType(MJType.getIntType());
	
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
		size.variableInit(initialized);
	}

}
