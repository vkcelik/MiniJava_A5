package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJMethodCallExpr extends MJExpression {

	private MJIdentifier method;
	private LinkedList<MJExpression> arglist;

	private MJMethod target;
	
	public MJMethodCallExpr(MJIdentifier m, LinkedList<MJExpression> arglist) {
		this.method = m;
		this.arglist = arglist;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		boolean first = true;

		this.method.prettyPrint(prepri);
		prepri.print("(");
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

		if (method instanceof MJSelector) {
			MJSelector selector = (MJSelector) method;
			MJIdentifier obj = selector.getObject();
			
			// Check the declaration
			MJVariable var;
			try {
				var = IR.find(obj.getName());
			} catch (VariableNotFound e) {
				throw new TypeCheckerException("Unkown identifier " + obj.getName());
			}
			
			// Object must be of type class
			MJType type = obj.getType();
			if (!type.isClass()) {
				throw new TypeCheckerException(obj.getName() +" is not type of class");
			}
			
		
			// Type check all argument expressions
			for (MJExpression e: arglist){
				e.typeCheck();
			}
			
			// Method name
		//	this.getMethod().getName();
			
			// Object type name
			type.getName();
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
		method.variableInit(initialized);
		
		for (MJExpression e : arglist) {
			e.variableInit(initialized);
		}
	}

}
