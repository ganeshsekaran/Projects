package com.ganesh;

public class StaticCheck {
	
	public static void main(String[] args) {
		StaticCheck.test();
		
		StaticCheck main = null;
		System.out.println(main);
		main.test();
	}
	
	public static void test(){
		System.out.println("Test");
	}

}
