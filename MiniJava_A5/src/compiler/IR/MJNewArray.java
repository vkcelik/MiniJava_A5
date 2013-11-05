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
		type.typeCheck();
		for (MJExpression e : arglist) {
			e.typeCheck();
		}
		try{
			target = IR.classes.lookupConstructor(type.getDecl());
		}
		catch (ClassErrorMethod e) {
			throw new TypeCheckerException("");
		} 
		catch (MethodNotFound e){
			throw new TypeCheckerException("");
		}

		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
	}

}
