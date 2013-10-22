package compiler.IR;

import java.util.LinkedList;

import compiler.Compiler;
import compiler.PrettyPrinter;
import compiler.Exceptions.ClassAlreadyDeclared;
import compiler.Exceptions.ClassErrorField;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.InheritanceError;
import compiler.Exceptions.TypeCheckerException;

public class MJProgram extends IR {

	private LinkedList<MJClass> classes;

	public MJProgram(LinkedList<MJClass> cdl) {
		this.classes = cdl;
	}

	public LinkedList<MJClass> getClasses() {
		return classes;
	}

	public void prettyPrint(PrettyPrinter prettyPrinter) {
		for (MJClass c : classes) {
			c.prettyPrint(prettyPrinter);
		}
	}
	
	public MJType typeCheck() throws TypeCheckerException {

		initializeClassTable();
		initializeMethodTable();

		// finally we can typecheck the individual classes

		for (MJClass c : this.getClasses()) {
			c.typeCheck();
		}

		return MJType.getVoidType();
	}

	private void initializeClassTable() throws TypeCheckerException {

		// we need classes Object and String in our class list
		
		MJClass objectClass = createObjectClass();
		this.classes.add(objectClass);
		
		MJClass stringClass = createStringClass();
		this.classes.add(stringClass);		

		// now iterate over all classes in the program and add them to the
		// classtable

		for (MJClass c : this.getClasses()) {
			try {
				IR.classes.add(c);
			} catch (ClassAlreadyDeclared e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " already declared.");
			} catch (ClassErrorField e1) {
				throw new TypeCheckerException("Class " + c.getName()
						+ " has two fields with name " + e1.getMessage());
			}
		}
		
	}
	
	private MJClass createObjectClass() {
		
		MJClass oc = new MJClass("Object", "Object",
				new LinkedList<MJVariable>(), new LinkedList<MJMethod>());

		return oc;
	}

	private MJClass createStringClass() {
		
		// the string class has a field length of type int a nd a field text of type String
		
		LinkedList<MJVariable> varlist = new LinkedList<MJVariable>();
		MJVariable length = new MJVariable(MJType.getIntType(), "length");
		MJVariable text = new MJVariable(MJType.getClassType("String"), "text");
		varlist.add(text);
		varlist.addLast(length);

		MJClass oc = new MJClass("String", "Object", varlist, new LinkedList<MJMethod>());
		
		return oc;
	}
	
	private void initializeMethodTable() throws TypeCheckerException {
		for (MJClass c : this.getClasses()) {
			try {
				IR.classes.addMethods(c);
			} catch (ClassErrorMethod e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " already declared.");
			} catch (ClassNotFound e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " not found.");
			} catch (InheritanceError e1) {
				throw new TypeCheckerException("Class " + c.getName()
						+ " overwrites a method.");
			}
		}

	}
	
	public void variableInit() throws TypeCheckerException {

		// now we can invoke variableinit on the classes

		for (MJClass c : this.getClasses()) {
			c.variableInit();
		}
	}

}
