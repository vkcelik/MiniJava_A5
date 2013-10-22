class testmain {
	
	public static void main(String[] args) {
		
		A a;
		B b;

		a = new A();
		b = new B();
		
		a.test();
		a.print();
		b.test();
		b.print(1);
	}
}

class A {
	String x;
	
	void test() {
		this.x = "Hello World";
		return;
	}
	
	void print() {
		System.out.println( this.x );
		return;
	}
}

class B extends A {
	int x;

	void print(int a) {
		System.out.println( this.x );
		super.print();
		return;
	}
}