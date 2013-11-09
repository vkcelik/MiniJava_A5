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
		MJIdentifier obj = null;
		MJClass cl = null;
		String methodName = null;
		
		if (method instanceof MJSelector) {
			MJSelector selector = (MJSelector) method;
			obj = selector.getObject();
			obj.typeCheck();

			// Object must be of type class
			if (!obj.getType().isClass()) {
				throw new TypeCheckerException(obj.getName() +" is not type of class");
			}

			try {
				cl = IR.classes.lookup(obj.getType().getName());
			} catch (ClassNotFound e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			methodName = selector.getField().getName();
		} else {
			methodName = method.getName();
			if (methodName.equals("this")){
				cl=IR.currentClass;
			}
			if (methodName.equals("super")){
				cl = IR.currentClass.getSuperClass().getDecl();
			}
		}

		// Check the declaration
		MJVariable var;
		try {
			var = IR.find(obj.getName());
			var.typeCheck();
		} catch (VariableNotFound e) {
			throw new TypeCheckerException("Unkown identifier " + obj.getName());
		}


		// Type check all argument expressions
		for (MJExpression e: arglist){
			e.typeCheck();
		}

		try {
			this.target = IR.classes.lookupMethod(cl, methodName, arglist);
			this.target.typeCheck();
		} catch (ClassErrorMethod e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MethodNotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		type = target.getReturnType();

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
