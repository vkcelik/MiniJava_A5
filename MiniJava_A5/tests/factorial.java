class testmain {
	// fejler når man tester. Fejlen skyldes den udleverede kode, da this fremgår to gange.
	public static void main(String[] args) {
		A a;
		int x;		

		a = new A();
		x = a.test(4);		

		System.out.println(x);
	}
}

class A {
	int test(int i) {
		int res;
		res = 0;
		if (i==1) {
			res = 1;
		} else {
			res = i * this.test ( i - 1 );
		}
		
		return res;
	}
}
