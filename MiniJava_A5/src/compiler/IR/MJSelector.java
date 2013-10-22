package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.*;

public class MJSelector extends MJIdentifier {

	private MJIdentifier object;
	private MJIdentifier field;

	public MJSelector(MJIdentifier t, MJIdentifier field) {
		this.object = t;
		this.field = field;
	}

	public MJIdentifier getObject() {
		return object;
	}

	public MJIdentifier getField() {
		return field;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.object.prettyPrint(prepri);
		prepri.print(".");
		this.field.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {

		// a selector has the form object.field

		// first type check the object
		// this sets also the object.decl 

		MJType idtype = this.object.typeCheck();

		// the object must have class type

		if (!idtype.isClass()) {
			throw new TypeCheckerException("Type of an object in a selector must be a class type.");
		}

		// now get the class declaration of object

		MJClass classDecl;
		try {
			classDecl = IR.classes.lookup(idtype.getName());
		} catch (ClassNotFound e) {
			throw new TypeCheckerException("No class declaration for object's type found.");
		}

		// now we can finally search for the field in the declaration

		MJVariable fieldDecl;

		try {
			fieldDecl = IR.classes.lookupField(classDecl, this.field.getName());
		} catch (ClassErrorField e) {
			throw new TypeCheckerException("Class "+ classDecl.getName() + 
					" has no field "+ this.field.getName() + ".");
		}

		// and from the field declaration we get the type...

		this.decl = fieldDecl;
		this.type = fieldDecl.getType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {
		variableInit(initialized, false);
	}

	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
	throws TypeCheckerException {

		// the object must be tested to be initialized
		this.object.variableInit(initialized);

		// but the field might be uninitialized
		// this does not matter - why?
	}

}
