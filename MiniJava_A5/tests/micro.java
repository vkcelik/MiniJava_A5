class testmain {
        public static void main(String[] args) { 
                int i;
                int j;

                j=1;
                i=j;
                System.out.println("Hello World");

        }
}

class TestClass1 {
        int data;
        
        public static boolean method(int arg1,int arg2,TestClass2 arg3){
                
        		boolean l;
                int result;
                
                result=0;
                
                l=(arg1<100 && arg2==3);
                //test
                if (l==false){
                        l = true;
                        result = 42;
                        data = arg1+arg2;
                }

                return !(result==0);
        }
        
        public void setData(int d){
                data=d;
                return;
        }
        public int toString(){
                return data;
        }
}

class TestClass2 extends TestClass1 {
        
        boolean condition;
        
        void method(){
                int a;
                
                a = 0;
                
                {
                        int b;
                        b=a;
                }
                return;
        }
        
        public int method2(TestClass1 data) {
                int N;
                int product;
                int j;

                int s;
                
                N=10;
                product = 1;
                j=1;

                s = data.data;
                
                if (!condition){
                        data.method(10,5,this);
                } else {
                        data.method(2,1,new TestClass2());
                }

                while(j<N+1) {
                    product =product*j;
                    j=j+1;
                }
                if (!(product < 100)) {
                        product=product-100;
                }
                return product;
        }
}
