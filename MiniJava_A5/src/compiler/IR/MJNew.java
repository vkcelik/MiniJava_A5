package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;

public class MJNew extends MJExpression {

	private LinkedList<MJExpression> arglist;
	private MJMethod target;

	public MJNew(MJType type) {
		this(type, new LinkedList<MJExpression>());
	}

	public MJNew(MJType type, LinkedList<MJExpression> arglist) {
		this.type = type;
		this.arglist = arglist;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		boolean first = true;

		prepri.print("new " + this.type + "(");
		for (MJExpression arg : arglist) {

			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}

			arg.prettyPrint(prepri);
		}
		prepri.print(")");

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


		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// here you should enter the code to check whether all variables are initialized
		for (MJExpression e : arglist) {
			e.variableInit(initialized);
		}
	}

}
