package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJLess extends MJBinaryOp {

	public MJLess(MJExpression a, MJExpression b) {
		super(a, b);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" < ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();
		
		if (!leftType.isSame(rightType)){
			throw new TypeCheckerException("types in < op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}
		
		this.type=leftType;
		
		if (!this.type.isInt()) {
			new TypeCheckerException("Arguments to + must have type int.");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// here you should enter the code to check whether all variables are initialized
		this.lhs.variableInit(initialized);
		this.rhs.variableInit(initialized);
	}

}
