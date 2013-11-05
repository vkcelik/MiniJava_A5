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


		// here you should enter the code to type check this class
				
		if (id instanceof MJSelector) {
			MJSelector selector = (MJSelector) id;
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
			this.getMethod().getName();
			
			// Object type name
			type.getName();
		}


		return this.getMethod().type;


	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// here you should enter the code to check whether all variables are
		// initialized
		id.variableInit(initialized);
		
		for (MJExpression e : arglist) {
			e.variableInit(initialized);
		}
		// here you should enter the code to check whether all variables are initialized
	}

}
