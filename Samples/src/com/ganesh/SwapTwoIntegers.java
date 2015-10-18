package com.ganesh;

class SwapTwoIntegers {

	public static void main(String[] args) {
		SwapTwoIntegers main = new SwapTwoIntegers();
		Integer a = 1234;
		Integer b = 4321;
		

		a = a + b;
		b = a - b;
		a = a - b;

		System.out.println(" A = " + a);
		System.out.println(" B = " + b);

		try {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		} finally {

		}
		main.test();
	}

	public void test(){
		
		A a = new A();
		a.f(1, 2);
		
		A ab = new B();
		ab.f(1, 2);
				
		
		B b = new B();
		b.f(1, 2);
		
	}

	class A {
		protected void f(int i, int j) {
			System.out.println(" Two variables");
		}
	}

	class B extends A {
		protected void f(int i) {
			System.out.println(" One variables");
		}
	}

}
