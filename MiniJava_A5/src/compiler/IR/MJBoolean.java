package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.*;

public class MJBoolean extends MJExpression {

	private enum MJBooleanValues {
		True, False
	};

	private MJBooleanValues value;

	public static MJBoolean False = new MJBoolean(MJBooleanValues.False);
	public static MJBoolean True = new MJBoolean(MJBooleanValues.True);

	public MJBoolean(MJBooleanValues value) {
		this.value = value;
	}

	public boolean isTrue() {
		return (this.value == MJBooleanValues.True);
	}

	public boolean isFalse() {
		return (this.value == MJBooleanValues.False);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		if (this.isFalse()) {
			prepri.print("false");
		} else {
			prepri.print("true");
		}
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy
		this.type = MJType.getBooleanType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to do here
		return;
	}

}
