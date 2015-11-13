package com.ganesh;

public class EnumSamples implements Cloneable {

	public static void main(String[] args) throws Exception{
		EnumSamples main = new EnumSamples();
		main.test();
		EnumSamples cloned = (EnumSamples) main.clone();
		cloned.test();
	}

	public enum Singleton {
		INSTATNCE;

		private Singleton() {
			// TODO Auto-generated constructor stub
		}

		public static final Singleton getInstance() {
			return INSTATNCE;
		}
	}
	
	public enum AbstractTest1 implements Runnable {
		TEST1; 
		//abstract void type();

		public void run() {
			System.out.println("Run");
		}
		
		protected void testOveride(){
			AbstractTest testEnum = AbstractTest.TEST;
		}
	}

	public enum AbstractTest implements Runnable {
		TEST {
			void type() {

			}

			public void run() {
				super.run();
				System.out.println("Override run");

			}
			
			@Override
			protected void testOveride() {
				// TODO Auto-generated method stub
				super.testOveride();
			}

		};
		abstract void type();

		public void run() {
			System.out.println("Run");
		}
		
		protected void testOveride(){
			
		}
	}

	public void test() {
		Singleton tetst = Singleton.getInstance();

		Thread t = new Thread(AbstractTest.TEST);
		t.start();
		
		System.out.println(AbstractTest.TEST);
	}

}
