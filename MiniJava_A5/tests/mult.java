class testmain {

	public static void main(String[] args) {

		A a;
		int x;

		a = new A(5);
		x = 6 * a.getNumber();

		
		System.out.println(x);
	}
}

class A {
	int number;
	
	public A A(int n){
		this.number = n;
		return this;
	}
	
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
		return;
	}
}
