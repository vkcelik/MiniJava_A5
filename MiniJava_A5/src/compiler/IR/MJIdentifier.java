package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJIdentifier extends MJExpression {

	private String name;
	protected MJVariable decl;

	public MJIdentifier() {
	}

	public MJIdentifier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public MJVariable getDecl() {
		return decl;
	}

	public void setDecl(MJVariable v) {
		this.decl = v;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.name);
	}

	MJType typeCheck() throws TypeCheckerException {

		// find the declaration for the identifier on the stack
		MJVariable var;
		String name = this.name;
		
		if (this.name == "this" && IR.currentMethod.isStatic()) {
			throw new TypeCheckerException("this encountered in static method.");
		}

		if (this.name == "super") {
			if (IR.currentMethod.isStatic()) {
				throw new TypeCheckerException("super encountered in static method.");
			}
			name = "super";
		}
		
		

		try {
			var = IR.find(name);
		} catch (VariableNotFound e) {
			throw new TypeCheckerException("Unkown identifier " + this.name);
		}

		if (this.name != "super") {
			// remember the declaration
		
			this.decl = var;

			// and use the type
			this.type = var.getType();
		} else {
			
			// super can only occur in selectors, and there the declaration of the object does not matter
			this.decl = null;
			this.type = var.getType().getDecl().getSuperClass();
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		variableInit(initialized, false);
	}

	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
			throws TypeCheckerException {

		MJVariable var = this.decl;

		if (!lvalue) {
			// if we are on the right hand side
			// check whether the identifier is in the set
			if (!initialized.contains(var)) {
				throw new TypeCheckerException("Variable " + this.name
						+ " not initialized.");
			}
		} else {
			// if we are on the left hand side
			initialized.add(var);
		}
	}

}
