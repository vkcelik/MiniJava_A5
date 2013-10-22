	package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;

public class MJArray extends MJIdentifier {

	private MJIdentifier array;
	private MJExpression index;

	public MJArray(MJIdentifier array, MJExpression idx) {
		this.array = array;
		this.index = idx;
	}

	public MJIdentifier getArray() {
		return array;
	}

	public MJExpression getIndex() {
		return index;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.array.prettyPrint(prepri);
		prepri.print("[");
		this.index.prettyPrint(prepri);
		prepri.print("]");
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the identifier
		MJType idtype = this.array.typeCheck();
		
		// which must have array type
		if (!idtype.isArray()) {
			throw new TypeCheckerException(this.array.getName()+" must have array type");
		}
		
		// typecheck the index
		MJType idxtype = this.index.typeCheck();
		
		// which must have type integer
		if (!idxtype.isInt()) {
			throw new TypeCheckerException(this.array.getName()+" must have type int");			
		}
		
		// type of the element is that of the base type
		this.type = idtype.getBaseType();
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {
		variableInit(initialized, false);
	}
	
	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
			throws TypeCheckerException {
		
		// the index only uses variables
		this.index.variableInit(initialized);
		
		// the array might be defined - but we do not care about this
		// we do not track individual elements of the array
		// BUT we need to check whether array is initialized or not
		this.array.variableInit(initialized);
	}
	
}
