class testmain {
	
	public static void main(String[] args) {
		A a = new A(5);
		int x = 6*a.getNumber();
		
		System.out.println(x);
	}
}

class A {	
	private int number;
	
	public A(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
}
