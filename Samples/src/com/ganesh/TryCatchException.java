package com.ganesh;

public class TryCatchException {

	public static void main(String[] args) {
		System.out.println(TryCatchException.test());
	}
	
	private static void test2() {
		throw new MyException();
	}
	

	private static int test() {
		try {
			throw new MyException();
			// return 1;
		} catch (Exception e) {
			System.out.println("test");
			return 2;
		} finally {
			return 3;
		}
	}
	
}

class MyException extends Throwable{
	
}
