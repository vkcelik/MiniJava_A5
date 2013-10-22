package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableAlreadyDeclared;

public class MJMethod extends IR {

	private MJType returnType;
	private String name;
	private LinkedList<MJVariable> parameters;
	private MJBlock body;
	private boolean isStatic;
	private boolean isPublic;
	private boolean isEntry;

	public MJMethod(MJType type, String name, LinkedList<MJVariable> parList,
			MJBlock b, boolean isStatic, boolean isPublic, boolean isEntry) {
		this.returnType = type;
		this.name = name;
		this.parameters = parList;
		this.body = b;
		this.isStatic = isStatic;
		this.isPublic = isPublic;
		this.isEntry = isEntry;
	}

	public MJMethod(MJType type, String name, LinkedList<MJVariable> parList,
			MJBlock b, boolean isStatic, boolean isPublic) {
		
		this(type, name, parList, b, isStatic, isPublic, false);

	}
	
	public String getName() {
		return name;
	}

	public LinkedList<MJVariable> getParameters() {
		return parameters;
	}

	public MJType getReturnType() {
		return returnType;
	}

	public MJBlock getBody() {
		return body;
	}

//	public void setBody(MJBlock body) {
//		this.body = body;
//	}

	public boolean isStatic() {
		return this.isStatic;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	public boolean isConstructor() {
		return this.returnType.isConstructor();
	}

	public void prettyPrint(PrettyPrinter prepri) {
		if (this.isPublic()) {
			prepri.print("public ");
		}
		if (this.isStatic()) {
			prepri.print("static ");
		}
		if (!this.isConstructor()) {
			prepri.print(this.returnType + " ");
		}
		prepri.print(this.name + "(");
		boolean first = true;
		for (MJVariable v : this.parameters) {
			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}
			v.prettyPrint(prepri);
		}
		prepri.println(")");
		body.prettyPrint(prepri);
		prepri.println("");
	}

	MJType typeCheck() throws TypeCheckerException {

		// remember which method we are in
		IR.currentMethod = this;

		// constructors cannot be static
		if (this.isConstructor() && this.isStatic) {
			throw new TypeCheckerException("Constructor can not be static.");
		}

		// typecheck the return type
		this.returnType.typeCheck();

		// we need a new scope for the parameters
		IR.stack.enterScope();

		for (MJVariable par : this.parameters) {

			// each parameter is type checked
			par.typeCheck();

			// and added to the scope
			try {
				IR.stack.add(par);
			} catch (VariableAlreadyDeclared e) {
				throw new TypeCheckerException("Method "+this.name+" has duplicate parameter "+par.getName());
			}
		}

		// now we typecheck the body
		this.body.typeCheck();

		// and leave the scope
		IR.stack.leaveScope();

		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {

		// note that method arguments are initialized!!!
		for (MJVariable p : this.parameters) {
			initialized.add(p);
		}

		// check the method body
		this.body.variableInit(initialized);

	}

}
