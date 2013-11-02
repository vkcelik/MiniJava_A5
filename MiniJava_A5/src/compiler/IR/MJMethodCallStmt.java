package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJMethodCallStmt extends MJStatement {

	private MJIdentifier id;
	private LinkedList<MJExpression> arglist;

	public MJMethodCallStmt(MJIdentifier m, LinkedList<MJExpression> arglist) {
		this.id = m;
		this.arglist = arglist;
	}

	public MJIdentifier getMethod() {
		return id;
	}

	public LinkedList<MJExpression> getArglist() {
		return arglist;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		boolean first = true;

		this.id.prettyPrint(prepri);
		prepri.print("(");
		for (MJExpression arg : arglist) {

			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}

			arg.prettyPrint(prepri);
		}
		prepri.println(");");
	}

	MJType typeCheck() throws TypeCheckerException {

		/* The method call type checks if the variable is declared and has class type, if all argument expressions
		type check, and if the variable’s class declares a method with the correct name and combination of
		argument types.*/

		if(id instanceof MJSelector){
			MJSelector sel = (MJSelector)id;
			MJType ty = sel.getType().typeCheck();
			if (!ty.isClass()){
				throw new TypeCheckerException("id is not of type Class");
			}
			MJClass cl = ty.getClass();
			
		} else {
			for (MJExpression e: arglist){
				e.typeCheck();
			}
		}
		
		



		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// here you should enter the code to check whether all variables are initialized
	}

}
