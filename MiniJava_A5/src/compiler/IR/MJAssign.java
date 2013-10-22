package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorField;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJAssign extends MJStatement {

	private MJExpression rhs;
	private MJIdentifier lhs;

	public MJAssign(MJIdentifier lhs, MJExpression rhs) {
		this.rhs = rhs;
		this.lhs = lhs;
	}

	public MJIdentifier getLhs() {
		return this.lhs;
	}

	public MJExpression getRhs() {
		return this.rhs;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" = ");
		this.rhs.prettyPrint(prepri);
		prepri.println(";");
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the rhs and lhs
		MJType rhsType = this.rhs.typeCheck();
		MJType lhsType = this.lhs.typeCheck();
		
		// check that rhs is assignable to lhs
		if (!MJType.isAssignable(rhsType, lhsType)) {
			throw new TypeCheckerException("Types in assignment are not assignable ("+lhsType+","+rhsType+")");
		}
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// first check the rhs 
		this.rhs.variableInit(initialized);
		
		// only then check the lhs, which is assigned to
		// why do we need to first check rhs then lhs?
		
		this.lhs.variableInit(initialized, true);
	}

}
